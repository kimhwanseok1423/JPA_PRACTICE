package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class PrePaymentEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String merchantUid;

    private BigDecimal amount;
}
