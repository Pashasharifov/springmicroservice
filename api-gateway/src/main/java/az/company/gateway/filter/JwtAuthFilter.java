    package az.company.gateway.filter;

    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.io.Decoders;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.cloud.gateway.filter.GatewayFilterChain;
    import org.springframework.cloud.gateway.filter.GlobalFilter;
    import org.springframework.core.Ordered;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Component;
    import org.springframework.web.server.ServerWebExchange;
    import reactor.core.publisher.Mono;

    import javax.crypto.SecretKey;

    @Component
    public class JwtAuthFilter implements GlobalFilter, Ordered {
        @Value("${jwt.secret}")
        private String secret;

        private static final String AUTH_HEADER = "Authorization";
        private static final String BERAER_PREFIX = "Bearer ";

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            var request = exchange.getRequest();
            var path = request.getPath().toString();

            // Auth endpointleri kech
            if(path.contains("/v1/auth")){
                return chain.filter(exchange);
            }
            if (!request.getHeaders().containsKey(AUTH_HEADER)) {
                return unauthorized(exchange);
            }
            var authHeader = request.getHeaders().getFirst(AUTH_HEADER);
            if (authHeader == null || !authHeader.startsWith(BERAER_PREFIX)){
                return unauthorized(exchange);
            }
            var token = authHeader.substring(7);
            try {
                Claims claims = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
                // email ve rolu headere elave edek
                var mutatedRequest = exchange.getRequest().mutate()
                        .header("X-User-Email", claims.getSubject())
                        .header("X-User-Role", claims.get("role", String.class))
                        .build();
                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            } catch (Exception e) {
                return unauthorized(exchange);
            }
        }
        private Mono<Void> unauthorized(ServerWebExchange exchange){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        private SecretKey getSignKey(){
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            return Keys.hmacShaKeyFor(keyBytes);
        }
        @Override
        public int getOrder(){
            return -1;
        }

    }
