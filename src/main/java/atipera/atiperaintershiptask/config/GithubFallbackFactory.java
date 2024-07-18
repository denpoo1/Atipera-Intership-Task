/**
 * Created by Denys Durbalov
 * Date of creation: 7/18/24
 * Project name: Atipera-Intership-Task
 * email: den.dyrbalov25@gmail.com or s28680@pjwstk.edu.pl
 */

package atipera.atiperaintershiptask.config;

import atipera.atiperaintershiptask.dto.response.feign_client.GithubBranchResponse;
import atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse;
import atipera.atiperaintershiptask.exception.RateLimitException;
import atipera.atiperaintershiptask.feign_client.GithubClient;
import atipera.atiperaintershiptask.service.GithubService;
import feign.FeignException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Data
public class GithubFallbackFactory implements FallbackFactory<GithubClient> {

        public static volatile Instant unblockedAt = Instant.now();

        public static void setUnblockedAt(Instant unblockedAt) {
                GithubFallbackFactory.unblockedAt = unblockedAt;
        }

        @Override
        public GithubClient create(Throwable cause) {
                return new GithubClient() {

                        @Override
                        public Optional<List<GithubRepositoryResponse>> getAllRepositoriesByUsername(String username) {
                                handleException(cause);
                                return Optional.of(Collections.emptyList());
                        }

                        @Override
                        public Optional<List<GithubBranchResponse>> getAllBranchesByUsernameAndProjectName(String username, String projectName) {
                                handleException(cause);
                                return Optional.of(Collections.emptyList());
                        }
                };
        }

        private void handleException(Throwable cause) {
                if (cause instanceof FeignException.Forbidden) {
                        FeignException.Forbidden exception = (FeignException.Forbidden) cause;

                        if (exception.responseHeaders().containsKey("Retry-After")) {
                                String retryAfter = exception.responseHeaders().get("Retry-After").toString();
                                int retryAfterSeconds = Integer.parseInt(retryAfter);
                                unblockedAt = Instant.now().plusSeconds(retryAfterSeconds);
                                log.warn("API rate limit exceeded. Blocking until {}", unblockedAt);
                        } else if (exception.responseHeaders().containsKey("X-RateLimit-Remaining")) {
                                String rateLimitReset = exception.responseHeaders().get("X-RateLimit-Reset").stream().findFirst().get();
                                long resetTime = Long.parseLong(rateLimitReset);
                                unblockedAt = Instant.ofEpochSecond(resetTime);
                                log.warn("API rate limit exceeded. Blocking until {}", unblockedAt);
                        } else {
                                // Default block for 1 minute
                                unblockedAt = Instant.now().plusSeconds(60);
                                log.warn("API rate limit exceeded. Blocking for 1 minute until {}", unblockedAt);
                        }
                        GithubService.apiIsBlocked = true;
                        throw new RateLimitException("API rate limit exceeded. Blocking until " + GithubFallbackFactory.unblockedAt);
                }
        }

        // Scheduled method to clear the block
        @Scheduled(fixedRate = 10000)
        public void clearBlock() {
                if (Instant.now().isAfter(unblockedAt)) {
                        log.info("API block cleared");
                        unblockedAt = Instant.now();
                        GithubService.apiIsBlocked = false;
                }
        }
}
