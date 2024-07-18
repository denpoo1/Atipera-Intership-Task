/**
 * Created by Denys Durbalov
 * Date of creation: 7/17/24
 * Project name: Atipera-Intership-Task
 * email: den.dyrbalov25@gmail.com or s28680@pjwstk.edu.pl
 */

package atipera.atiperaintershiptask.controller;

import atipera.atiperaintershiptask.dto.mapper.GithubMapper;
import atipera.atiperaintershiptask.dto.response.GithubResponse;
import atipera.atiperaintershiptask.service.GithubService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {

        private final GithubService githubService;

        public GithubController(GithubService githubService) {
                this.githubService = githubService;
        }

        @GetMapping("/{username}/repos")
        public List<GithubResponse> getAllReposByUsername(@PathVariable String username) throws JsonProcessingException {
                return GithubMapper.toResponseList(githubService.getAllReposByUsername(username));
        }
}
