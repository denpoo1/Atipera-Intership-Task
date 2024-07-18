/**
 * Created by Denys Durbalov
 * Date of creation: 7/17/24
 * Project name: Atipera-Intership-Task
 * email: den.dyrbalov25@gmail.com or s28680@pjwstk.edu.pl
 */

package atipera.atiperaintershiptask.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommonExceptionTemplate {
        private Integer status;
        private String message;
}
