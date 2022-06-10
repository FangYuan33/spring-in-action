package tacos.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Slf4j
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        log.info("Please go to this url to use swagger: http://localhost:8080/swagger-ui/ | Can you find this info?");

        ApiInfo apiInfo = new ApiInfo("Spring-Action", "方便的API调用", ApiInfo.DEFAULT.getVersion(),
                ApiInfo.DEFAULT.getTermsOfServiceUrl(), ApiInfo.DEFAULT.getContact(), ApiInfo.DEFAULT.getLicense(),
                ApiInfo.DEFAULT.getLicenseUrl(), ApiInfo.DEFAULT.getVendorExtensions());

        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo).select()
                .apis(RequestHandlerSelectors.basePackage("tacos.controller")).build();
    }
}