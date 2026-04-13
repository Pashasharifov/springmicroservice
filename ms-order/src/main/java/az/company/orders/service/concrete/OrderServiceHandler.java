package az.company.orders.service.concrete;

import az.company.orders.client.PaymentClient;
import az.company.orders.client.ProductClient;
import az.company.orders.dao.repository.OrderRepository;
import az.company.orders.exception.CustomFeignException;
import az.company.orders.exception.NotFoundException;
import az.company.orders.mapper.OrderMapper;
import az.company.orders.mapper.PaymentMapper;
import az.company.orders.model.client.request.CreatePaymentRequest;
import az.company.orders.model.client.request.ReduceQuantityRequest;
import az.company.orders.model.enums.ErrorMessage;
import az.company.orders.model.enums.OrderStatus;
import az.company.orders.model.request.CreateOrderRequest;
import az.company.orders.model.response.OrderResponse;
import az.company.orders.service.abstraction.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static az.company.orders.mapper.OrderMapper.ORDER_MAPPER;
import static az.company.orders.mapper.PaymentMapper.PAYMENT_MAPPER;
import static az.company.orders.model.enums.OrderStatus.*;
import static java.lang.String.format;
import static java.math.BigDecimal.valueOf;

@Service
@RequiredArgsConstructor
public class OrderServiceHandler implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceHandler.class);

    @Override
    @Transactional
    public void createOrder(CreateOrderRequest createOrderRequest){
        var orderEntity = ORDER_MAPPER.buildOrderEntity(createOrderRequest);
//        var productResponse = productClient.getProductById(createOrderRequest.getProductId());
        var productResponse = circuitBreakerFactory.create("productService").run(
                () -> productClient.getProductById(createOrderRequest.getProductId()),
                throwable -> {
                    logger.error("Product service is down: {}", throwable.getMessage());
                    throw new CustomFeignException("Product servisi failed");
                }
        );

//        var reduceQuantityRequest = new ReduceQuantityRequest(
//          createOrderRequest.getProductId(),
//          createOrderRequest.getQuantity()
//        );
        var totalAmount =  productResponse.getPrice().multiply(valueOf(createOrderRequest.getQuantity()));
        orderEntity.setAmount(totalAmount);
        orderRepository.save(orderEntity);

        try {
            circuitBreakerFactory.create("productService").run(
                    () -> {
                        productClient.reduceQuantity(
                                new ReduceQuantityRequest(
                                        createOrderRequest.getProductId(),
                                        createOrderRequest.getQuantity()
                                )
                        );
                        return null;
                    },
                    throwable -> {
                        logger.error("Reduce quantity failed : {}", throwable.getMessage());
                        throw new CustomFeignException("Reduce quantity failed");
                    }
            );
//        productClient.reduceQuantity(reduceQuantityRequest);
//        var pay = paymentClient.pay(PAYMENT_MAPPER.buildCreatePaymentRequest(createOrderRequest, orderEntity, totalAmount));
            circuitBreakerFactory.create("paymentService").run(
                    () -> paymentClient.pay(PAYMENT_MAPPER.buildCreatePaymentRequest(createOrderRequest, orderEntity, totalAmount)),
                    throwable -> {
                        logger.error("Payment failed : {}", throwable.getMessage());
                        throw new CustomFeignException("Payment service failed ");
                    }
            );
            orderEntity.setStatus(APPROVED);
        } catch (CustomFeignException e) {
            logger.error("Order failed with body: {}", e.getMessage());
            orderEntity.setStatus(REJECTED);
        }
        orderRepository.save(orderEntity);
    }

    @Override
    public OrderResponse getOrderById(Long id){
        var orderEntity =  orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format(ErrorMessage.ORDER_NOT_FOUND.getMessage(), id)));
        logger.info("order entity : {}", orderEntity.getId());
//        var productResponse = productClient.getProductById(orderEntity.getProductId());
        var productResponse = circuitBreakerFactory.create("productService").run(
                () -> productClient.getProductById(orderEntity.getProductId()),
                throwable -> null
        );
        logger.info("product : {}", productResponse);
//        var paymentResponse = paymentClient.getPaymentByOrderId(orderEntity.getId());
        var paymentResponse = circuitBreakerFactory.create("paymentService").run(
                () -> paymentClient.getPaymentByOrderId(orderEntity.getId()),
                throwable -> null
        );
        logger.info("payment : {}", paymentResponse);
        return ORDER_MAPPER.buildOrderResponse(
                orderEntity,
                productResponse,
                paymentResponse);
    }
}
