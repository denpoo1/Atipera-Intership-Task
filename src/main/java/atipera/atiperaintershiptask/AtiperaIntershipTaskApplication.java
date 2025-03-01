package atipera.atiperaintershiptask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AtiperaIntershipTaskApplication {

        public static void main(String[] args) {
                SpringApplication.run(AtiperaIntershipTaskApplication.class, args);
        }

}
