package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private String merchant_uid;
    private String imp_uid;

    // 생성자와 다른 메서드는 여기에...

    public String getMerchantUid() {
        return this.merchant_uid;
    }

    public String getImpUid() {
        return this.imp_uid;
    }
}