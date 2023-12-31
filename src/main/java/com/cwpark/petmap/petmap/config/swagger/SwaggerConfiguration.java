package com.cwpark.petmap.petmap.config.swagger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.core.env.Environment;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String API_NAME = "자몽샵";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "자몽샵 API";

    // 접속 경로 : http://localhost:8080/swagger-ui/
    @Bean
    public Docket api() {
        Parameter parameterBuilder = (Parameter) new ParameterBuilder()
            .name(HttpHeaders.AUTHORIZATION)
            .description("Access Tocken")
            .modelRef(new ModelRef("string"))
            .parameterType("header")
            .required(false)
            .build();

        List<Parameter> globalParameters = new ArrayList<>();
        globalParameters.add(parameterBuilder);

        return new Docket(DocumentationType.SWAGGER_2)
            .globalOperationParameters(globalParameters)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.cwpark.petmap.petmap"))
            .paths(PathSelectors.ant("/api/**"))
            .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(API_NAME)
            .version(API_VERSION)
            .description(API_DESCRIPTION)
            .build();
    }

    // Redisson 사용 시(actuator)
    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier
            , ServletEndpointsSupplier servletEndpointsSupplier
            , ControllerEndpointsSupplier controllerEndpointsSupplier
            , EndpointMediaTypes endpointMediaTypes
            , CorsEndpointProperties corsProperties
            , WebEndpointProperties webEndpointProperties
            , Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration()
                , new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }

    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }
}