package az.company.payments.service.concrete;

import az.company.payments.dao.repository.PaymentRepository;
import az.company.payments.exception.NotFoundException;
import az.company.payments.mapper.PaymentMapper;
import az.company.payments.model.request.CreatePaymentRequest;
import az.company.payments.model.response.PaymentResponse;
import az.company.payments.service.abstraction.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.company.payments.mapper.PaymentMapper.PAYMENT_MAPPER;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponse pay(CreatePaymentRequest createPaymentRequest) {
        var paymentEntity = PAYMENT_MAPPER.buildPaymentEntity(createPaymentRequest);
        paymentRepository.save(paymentEntity);
        return PAYMENT_MAPPER.buildPaymentResponse(paymentEntity);
    }

    @Override
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .map((PAYMENT_MAPPER::buildPaymentResponse))
                .orElseThrow(()->new NotFoundException(
                        "Payment not found by order id: " + orderId
                ));
    }

    @Override
    public void refund() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void checkPaymentStatus() {

    }

    @Override
    public void checkRefundStatus() {

    }

    @Override
    public void checkCancelStatus() {

    }
}
