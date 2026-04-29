package az.company.orders.client;

import az.company.orders.client.decoder.CustomErrorDescoder;
import az.company.orders.model.client.request.CreatePaymentRequest;
import az.company.orders.model.client.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "ms-payment",
        configuration = CustomErrorDescoder.class
)
public interface PaymentClient {
    @PostMapping("/v1/payments")
    PaymentResponse pay(@RequestBody CreatePaymentRequest createPaymentRequest);

    @GetMapping("/v1/payments/order/{orderId}")
    PaymentResponse getPaymentByOrderId(@PathVariable Long orderId);
}
