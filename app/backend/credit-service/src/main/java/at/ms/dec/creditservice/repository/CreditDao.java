package at.ms.dec.creditservice.repository;

import at.ms.dec.creditservice.bom.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditDao extends JpaRepository<Credit, Long> {
    //empty
}
