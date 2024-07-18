/**
 * Created by Denys Durbalov
 * Date of creation: 7/17/24
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubService {

        private final GithubClient githubClient;
        public static boolean apiIsBlocked;

        public GithubService(GithubClient githubClient) {
                this.githubClient = githubClient;
        }

        public List<GithubModel> getAllReposByUsername(String username) throws JsonProcessingException {

                if (!apiIsBlocked) {
                        List<GithubModel> models = new ArrayList<>();
                        List<GithubRepositoryResponse> repositoryModels = githubClient.getAllRepositoriesByUsername(username)
                                .orElseThrow(() -> new RuntimeException("User not found"));
                        if (repositoryModels.isEmpty()) {
                                throw new NotFoundException("Not found user with username " + username);
                        }
                        for (GithubRepositoryResponse model : repositoryModels) {
                                if (!model.isFork()) {
                                        GithubModel githubModel = new GithubModel();

                                        List<GithubBranchResponse> branchModels = githubClient.getAllBranchesByUsernameAndProjectName(username, model.getName())
                                                .orElseThrow(() -> new RuntimeException("Branches not found for repository " + model.getName()));
                                        githubModel.setBranches(branchModels);
                                        githubModel.setRepositoryName(model.getName());
                                        githubModel.setOwnerLogin(model.getOwner().getLogin());
                                        models.add(githubModel);
                                }
                        }
                        return models;
                } else {
                        throw new RateLimitException("API rate limit exceeded. Blocking until " + GithubFallbackFactory.unblockedAt);
                }
        }
}
