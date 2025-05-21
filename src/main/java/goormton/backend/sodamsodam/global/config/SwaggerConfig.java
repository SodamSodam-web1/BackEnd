package goormton.backend.sodamsodam.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("구름톤 노트 프로젝트 API")
                .description("구름톤 백엔드 웹1팀 스터디를 위한 간단한 노트 프로젝트 API 문서")
                .version("1.0");

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .schemaRequirement("BearerAuth", securityScheme);
    }
}
