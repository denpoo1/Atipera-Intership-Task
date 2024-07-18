/**
 * Created by Denys Durbalov
 * Date of creation: 7/18/24
 * Project name: Atipera-Intership-Task
 * email: den.dyrbalov25@gmail.com or s28680@pjwstk.edu.pl
 */

package atipera.atiperaintershiptask.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitHubClientConfig {
        @Value("${github.token}")
        private String githubToken;

        @Bean
        public RequestInterceptor requestInterceptor() {
                return requestTemplate -> requestTemplate.header("Authorization", "Bearer " + githubToken);
        }
}
