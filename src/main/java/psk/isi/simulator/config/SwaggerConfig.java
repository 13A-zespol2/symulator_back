package psk.isi.simulator.config;

import com.google.common.base.Predicates;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

@EnableSwagger2
@Configuration
/**
 * Klasa odpowiadająca za obsługę Swaggera
 */
public class SwaggerConfig {
    /**
     * Metoda ustawiająca konfigurację Swaggera.
     * @return
     */
    @Bean
    public Docket myAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(Collections.singleton("application/json"))
                .consumes(Collections.singleton("application/json"))
                .ignoredParameterTypes(Authentication.class)
                .useDefaultResponseMessages(false)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Metoda ustawiająca informacje dodatkowe o aplikacji.
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Operator komomorkowy",
                "Backend",
                "1.0",
                "",
                new Contact("Operator komomorkowy", "", ""),
                "",
                "",
                new ArrayList<>());
    }


}
