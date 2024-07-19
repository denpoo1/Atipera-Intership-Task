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
import atipera.atiperaintershiptask.exception.UnauthorizedException;
import atipera.atiperaintershiptask.feign_client.GithubClient;
import feign.FeignException;
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
public class GithubFallbackFactory implements FallbackFactory<GithubClient> {

        private static volatile Instant unblockedAt = Instant.now();
        private static volatile boolean apiIsBlocked = false;

        public static synchronized Instant getUnblockedAt() {
                return unblockedAt;
        }

        public static synchronized void setUnblockedAt(Instant unblockedAt) {
                GithubFallbackFactory.unblockedAt = unblockedAt;
        }

        public static synchronized boolean isApiBlocked() {
                return apiIsBlocked;
        }

        public static synchronized void setApiBlocked(boolean apiIsBlocked) {
                GithubFallbackFactory.apiIsBlocked = apiIsBlocked;
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

        /**
         * This logic is made due to best practices according to the github api documentation. I am attaching a list of documentation that I used to make this logic:
         *     https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-api?apiVersion=2022-11-28
         *     https://docs.github.com/en/rest/using-the-rest-api/troubleshooting-the-rest-api?apiVersion=2022-11-28
         *     https://docs.github.com/en/rest/using-the-rest-api/best-practices-for-using-the-rest-api?apiVersion=2022-11-28
         */
        
        private synchronized void handleException(Throwable cause) {
                if (cause instanceof FeignException.Forbidden) {
                        FeignException.Forbidden exception = (FeignException.Forbidden) cause;

                        if (exception.responseHeaders().containsKey("Retry-After")) {
                                String retryAfter = exception.responseHeaders().get("Retry-After").stream().findFirst().get();
                                int retryAfterSeconds = Integer.parseInt(retryAfter);
                                setUnblockedAt(Instant.now().plusSeconds(retryAfterSeconds));
                        } else if (exception.responseHeaders().containsKey("X-RateLimit-Remaining")) {
                                String rateLimitReset = exception.responseHeaders().get("X-RateLimit-Reset").stream().findFirst().get();
                                long resetTime = Long.parseLong(rateLimitReset);
                                setUnblockedAt(Instant.ofEpochSecond(resetTime));
                        } else {
                                setUnblockedAt(Instant.now().plusSeconds(60));
                        }

                        log.warn("API rate limit exceeded. Blocking until {}", getUnblockedAt());
                        setApiBlocked(true);
                        throw new RateLimitException("API rate limit exceeded. Blocking until " + getUnblockedAt());

                } else if (cause instanceof FeignException.Unauthorized) {
                        throw new UnauthorizedException("Unauthorized. Please check token expiration");
                }
        }

        // Scheduled method to clear the block
        @Scheduled(fixedRate = 10000)
        public static synchronized void clearBlock() {
                if (Instant.now().isAfter(getUnblockedAt())) {
                        log.info("API block cleared");
                        setUnblockedAt(Instant.now());
                        setApiBlocked(false);
                }
        }
}
