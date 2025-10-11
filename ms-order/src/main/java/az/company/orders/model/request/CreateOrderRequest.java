package az.company.orders.model.request;

import az.company.orders.model.constants.ApplicationConstants;
import az.company.orders.model.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static az.company.orders.model.constants.ApplicationConstants.*;
import static az.company.orders.model.constants.ApplicationConstants.PRODUCT_ID_IS_REQUIRED;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {
    @NotNull(message = PRODUCT_ID_IS_REQUIRED)
    private Long productId;
    @NotNull(message = QUANTITY_IS_REQUIRED)
    private Integer quantity;
    @NotNull(message = AMOUNT_IS_REQUIRED)
    private BigDecimal amount;
    private PaymentType paymentType;

}
