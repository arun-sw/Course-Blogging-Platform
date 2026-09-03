package com.course_blogging.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class GatewayRoutes {
    @Bean
    public RouterFunction<ServerResponse> userServiceRoutes() {
        return route("user-service")
                .route(path("/users/**").or(path("/auth/**")), http())
                .before(uri("http://localhost:8015"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> blogServiceRoutes() {
        return route("blog-service")
                .route(path("/blogs/**").or(path("/categories/**")).or(path("/tags/**")),
                        http()
                )
                .before(uri("http://localhost:8016"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> userServiceApiDocsRoute() {
        return route("user-service-api-docs")
                .route(path("/api-docs/user-service"), http())
                .before(rewritePath("/api-docs/user-service", "/v3/api-docs"))
                .before(uri("http://localhost:8015"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> blogServiceApiDocsRoute() {
        return route("blog-service-api-docs")
                .route(path("/api-docs/blog-service"), http())
                .before(rewritePath("/api-docs/blog-service", "/v3/api-docs"))
                .before(uri("http://localhost:8016"))
                .build();
    }
}
