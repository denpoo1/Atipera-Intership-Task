/**
 * Created by Denys Durbalov
 * Date of creation: 7/17/24
 * Project name: Atipera-Intership-Task
 * email: den.dyrbalov25@gmail.com or s28680@pjwstk.edu.pl
 */

package atipera.atiperaintershiptask.feign_client;

import atipera.atiperaintershiptask.config.GitHubClientConfig;
import atipera.atiperaintershiptask.config.GithubFallbackFactory;
import atipera.atiperaintershiptask.dto.response.feign_client.GithubBranchResponse;
import atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "githubClient", url = "https://api.github.com",
        configuration = GitHubClientConfig.class,
        fallbackFactory = GithubFallbackFactory.class)
public interface GithubClient {

        @GetMapping(value = "/users/{username}/repos")
        Optional<List<GithubRepositoryResponse>> getAllRepositoriesByUsername(
                @PathVariable("username") String username
        );

        @GetMapping(value = "/repos/{username}/{project_name}/branches")
        Optional<List<GithubBranchResponse>> getAllBranchesByUsernameAndProjectName(
                @PathVariable("username") String username,
                @PathVariable("project_name") String projectName
        );
}
