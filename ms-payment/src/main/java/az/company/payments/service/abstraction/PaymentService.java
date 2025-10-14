package az.company.payments.service.abstraction;

import az.company.payments.model.request.CreatePaymentRequest;

public interface PaymentService {
    void pay(CreatePaymentRequest createPaymentRequest);
    void refund();
    void cancel();
    void checkPaymentStatus();
    void checkRefundStatus();
    void checkCancelStatus();
}
