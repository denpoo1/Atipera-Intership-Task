/**
 * Created by Denys Durbalov
 * Date of creation: 7/17/24
 * Project name: Atipera-Intership-Task
 * email: den.dyrbalov25@gmail.com or s28680@pjwstk.edu.pl
 */

package atipera.atiperaintershiptask.dto.response.feign_client;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class GithubBranchResponse implements Serializable {
        private String name;
        private GithubCommitResponse commit;
}
