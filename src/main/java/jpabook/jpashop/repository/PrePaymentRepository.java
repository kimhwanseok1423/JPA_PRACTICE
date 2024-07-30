package jpabook.jpashop.repository;
import jpabook.jpashop.domain.PrePaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrePaymentRepository extends JpaRepository<PrePaymentEntity, Long> {
    Optional<PrePaymentEntity> findByMerchantUid(String merchantUid);
}