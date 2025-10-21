package az.company.orders.mapper;

import az.company.orders.dao.entity.OrderEntity;
import az.company.orders.model.client.request.CreatePaymentRequest;
import az.company.orders.model.request.CreateOrderRequest;

import java.math.BigDecimal;
import java.util.UUID;

public enum PaymentMapper {
    PAYMENT_MAPPER;

    public CreatePaymentRequest buildCreatePaymentRequest(CreateOrderRequest createOrderRequest,
                                                          OrderEntity orderEntity,
                                                          BigDecimal totalAmount){
        return CreatePaymentRequest.builder()
                .orderId(orderEntity.getId())
                .paymentType(createOrderRequest.getPaymentType())
                .amount(totalAmount)
                .referenceNumber(UUID.randomUUID().toString())
                .build();
    }
}
