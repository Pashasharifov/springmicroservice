package az.company.payments.controller;

import az.company.payments.model.request.CreatePaymentRequest;
import az.company.payments.service.abstraction.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void pay(@RequestBody CreatePaymentRequest createPaymentRequest){
        paymentService.pay(createPaymentRequest);
    }
}
