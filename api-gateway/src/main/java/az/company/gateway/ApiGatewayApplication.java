package az.company.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ms-product", r -> r
                        .path("/products/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://ms-product"))
                .route("ms-order", r -> r
                        .path("/orders/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8081"))
                .route("ms-payment", r -> r
                        .path("/payments/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8082"))
                .build();
    }

}
