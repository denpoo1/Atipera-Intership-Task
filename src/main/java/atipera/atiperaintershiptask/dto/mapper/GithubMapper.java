/**
 * Created by Denys Durbalov
 * Date of creation: 7/17/24
 * Project name: Atipera-Intership-Task
 * email: den.dyrbalov25@gmail.com or s28680@pjwstk.edu.pl
 */

package atipera.atiperaintershiptask.dto.mapper;

import atipera.atiperaintershiptask.dto.response.GithubResponse;
import atipera.atiperaintershiptask.model.GithubModel;

import java.util.List;

public class GithubMapper {

        public static List<GithubResponse> toResponseList(List<GithubModel> githubModels) {
                return githubModels.stream()
                        .map(model -> {
                                GithubResponse response = new GithubResponse();
                                response.setRepositoryName(model.getRepositoryName());
                                response.setOwnerLogin(model.getOwnerLogin());
                                response.setBranches(model.getBranches());
                                return response;
                        })
                        .toList();
        }
}

