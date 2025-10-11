package az.company.orders.controller;

import az.company.orders.model.request.CreateOrderRequest;
import az.company.orders.model.response.OrderResponse;
import az.company.orders.service.abstraction.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest){
        orderService.createOrder(createOrderRequest);
    }

    @GetMapping("/{id}")
//    @RestController()
    public OrderResponse getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }
}
