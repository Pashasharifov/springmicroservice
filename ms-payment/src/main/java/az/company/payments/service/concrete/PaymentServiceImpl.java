package az.company.payments.service.concrete;

import az.company.payments.dao.repository.PaymentRepository;
import az.company.payments.mapper.PaymentMapper;
import az.company.payments.model.request.CreatePaymentRequest;
import az.company.payments.service.abstraction.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.company.payments.mapper.PaymentMapper.PAYMENT_MAPPER;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public void pay(CreatePaymentRequest createPaymentRequest) {
        var paymentEntity = PAYMENT_MAPPER.buildPaymentEntity(createPaymentRequest);
        paymentRepository.save(paymentEntity);
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
