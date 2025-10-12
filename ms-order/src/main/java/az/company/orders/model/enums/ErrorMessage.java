package az.company.orders.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
    ORDER_NOT_FOUND("Order Not Found with id: %s"),
    CLIENT_ERROR("Client error occured while making the request"),
    SERVER_ERROR("Server error occured while making the request");

    private final String message;
}
