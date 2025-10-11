package az.company.orders.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
    ORDER_NOT_FOUND("Order Not Found with id: %s");

    private final String message;
}
