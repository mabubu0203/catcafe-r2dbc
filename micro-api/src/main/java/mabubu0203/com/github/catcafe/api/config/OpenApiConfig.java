package mabubu0203.com.github.catcafe.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return
        new OpenAPI()
            .components(
                new Components()
                    .addSecuritySchemes(
                        "ApiKeyAuth",
                        new SecurityScheme()
                            .type(SecurityScheme.Type.APIKEY)
                            .in(SecurityScheme.In.HEADER)
                            .name("X-API-KEY")
                            .description("A api key")))
            .addSecurityItem(
                new SecurityRequirement()
                    .addList("ApiKeyAuth"));
  }

  @Bean
  public GroupedOpenApi actuatorApi() {
    String[] paths = {"/actuator/**"};
    return GroupedOpenApi.builder()
        .group("actuator")
        .pathsToMatch(paths)
        .build();
  }

  @Bean
  public GroupedOpenApi authenticationApi() {
    String[] paths = {
        "/{cats}/authentication/x_api_key/generate",};
    return GroupedOpenApi.builder()
        .group("authentication")
        .pathsToMatch(paths)
        .build();
  }

  @Bean
  public GroupedOpenApi storeApi() {
    String[] paths = {
        "/{cats}/store",
        "/{cats}/stores",
        "/{cats}/stores/{store_id}"};
    return GroupedOpenApi.builder()
        .group("store")
        .pathsToMatch(paths)
        .build();
  }

}
