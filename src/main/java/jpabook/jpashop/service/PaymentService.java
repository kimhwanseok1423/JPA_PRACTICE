package jpabook.jpashop.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jpabook.jpashop.domain.PaymentDTO;
import jpabook.jpashop.domain.PrePaymentEntity;
import jpabook.jpashop.repository.PrePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service

public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private IamportClient api;

    @Autowired
    private PrePaymentRepository prePaymentRepository;

    public PaymentService() {
     //   this.api = new IamportClient("5861284161312803", "4FmiqjbJOEJbaizelCxpCZATcsvCBBvnvhqa1oqZILqtwtcLmiW1hJCZOgTXokpTLtS9zYgItYdB1ng7");
    }

    @Transactional
    public void postPrepare(PrePaymentEntity request) throws IamportResponseException, IOException {
        PrepareData prepareData = new PrepareData(request.getMerchantUid(), request.getAmount());
        api.postPrepare(prepareData);  // 사전 등록 API

        PrePaymentEntity savedEntity = prePaymentRepository.save(request);
        logger.info("Saved entity: {}", savedEntity);
    }
    public Payment validatePayment(PaymentDTO request) throws IamportResponseException, IOException {



        PrePaymentEntity prePayment = prePaymentRepository.findByMerchantUid(request.getMerchant_uid())
                .orElseThrow(() -> new NoSuchElementException("해당 merchant_uid를 찾을 수 없습니다."));
        BigDecimal preAmount = prePayment.getAmount();

        IamportResponse<Payment> iamportResponse = api.paymentByImpUid(request.getImp_uid());
        BigDecimal paidAmount = iamportResponse.getResponse().getAmount(); // 사용자가 실제 결제한 금액

        if (!preAmount.equals(paidAmount)) {
            CancelData cancelData = cancelPayment(iamportResponse);
            api.cancelPaymentByImpUid(cancelData);
        }

        return iamportResponse.getResponse();
    }

    public CancelData cancelPayment(IamportResponse<Payment> response) {
        return new CancelData(response.getResponse().getImpUid(), true);
    }
}
