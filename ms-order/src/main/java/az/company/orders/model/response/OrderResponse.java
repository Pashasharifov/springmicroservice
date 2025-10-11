package az.company.orders.model.response;

import az.company.orders.model.enums.OrderStatus;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Long productId;
    private int quantity;
    private OrderStatus status;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
