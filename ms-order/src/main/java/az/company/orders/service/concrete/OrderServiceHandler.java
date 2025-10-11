package az.company.orders.service.concrete;

import az.company.orders.dao.repository.OrderRepository;
import az.company.orders.exception.NotFoundException;
import az.company.orders.mapper.OrderMapper;
import az.company.orders.model.enums.ErrorMessage;
import az.company.orders.model.request.CreateOrderRequest;
import az.company.orders.model.response.OrderResponse;
import az.company.orders.service.abstraction.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.company.orders.mapper.OrderMapper.ORDER_MAPPER;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class OrderServiceHandler implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public void createOrder(CreateOrderRequest createOrderRequest){
        orderRepository.save(ORDER_MAPPER.buildOrderEntity(createOrderRequest));
    }

    @Override
    public OrderResponse getOrderById(Long id){
        return orderRepository.findById(id)
                .map(ORDER_MAPPER::buildOrderResponse)
                .orElseThrow(() -> new NotFoundException(
                        format(ErrorMessage.ORDER_NOT_FOUND.getMessage(), id)));
    }
}
