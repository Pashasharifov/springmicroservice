package az.company.ms_product.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(String message) {

}
