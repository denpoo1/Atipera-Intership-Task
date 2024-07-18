/**
 * Created by Denys Durbalov
 * Date of creation: 7/17/24
 * Project name: Atipera-Intership-Task
 * email: den.dyrbalov25@gmail.com or s28680@pjwstk.edu.pl
 */

package atipera.atiperaintershiptask.dto.response;

import atipera.atiperaintershiptask.dto.response.feign_client.GithubBranchResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GithubResponse {
        private String repositoryName;
        private String ownerLogin;
        private List<GithubBranchResponse> branches;
}
