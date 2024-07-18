/**
 * Created by Denys Durbalov
 * Date of creation: 7/18/24
 * Project name: Atipera-Intership-Task
 * email: den.dyrbalov25@gmail.com or s28680@pjwstk.edu.pl
 */

package atipera.atiperaintershiptask.service;

import atipera.atiperaintershiptask.config.GithubFallbackFactory;
import atipera.atiperaintershiptask.dto.response.feign_client.GithubBranchResponse;
import atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse;
import atipera.atiperaintershiptask.exception.NotFoundException;
import atipera.atiperaintershiptask.exception.RateLimitException;
import atipera.atiperaintershiptask.feign_client.GithubClient;
import atipera.atiperaintershiptask.model.GithubModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GithubService {

        private final GithubClient githubClient;

        public GithubService(GithubClient githubClient) {
                this.githubClient = githubClient;
        }

        public List<GithubModel> getAllReposByUsername(String username) throws JsonProcessingException {
                if (isApiBlocked()) {
                        log.error("API rate limit exceeded. Blocking until {}", GithubFallbackFactory.getUnblockedAt());
                        throw new RateLimitException(
                                "API rate limit exceeded. Blocking until " + GithubFallbackFactory.getUnblockedAt());
                }

                List<GithubRepositoryResponse> repositoryModels = githubClient.getAllRepositoriesByUsername(username).get();

                if (repositoryModels.isEmpty()) {
                        throw new NotFoundException("Not found user with username " + username);
                }

                return repositoryModels.stream()
                        .filter(repo -> !repo.isFork())
                        .map(repo -> convertToModel(repo, username))
                        .toList();
        }

        private GithubModel convertToModel(GithubRepositoryResponse repositoryResponse, String username) {
                GithubModel githubModel = new GithubModel();
                List<GithubBranchResponse> branchModels = githubClient
                        .getAllBranchesByUsernameAndProjectName(username, repositoryResponse.getName())
                        .orElseThrow(() ->
                                new NotFoundException("Branches not found for repository "
                                        + repositoryResponse.getName()));
                githubModel.setBranches(branchModels);
                githubModel.setRepositoryName(repositoryResponse.getName());
                githubModel.setOwnerLogin(repositoryResponse.getOwner().getLogin());
                return githubModel;
        }

        private static synchronized boolean isApiBlocked() {
                return GithubFallbackFactory.isApiBlocked();
        }
}
