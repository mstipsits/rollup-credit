package at.ms.dec.creditservice.service;

import at.ms.dec.commons.dto.CreditDto;
import at.ms.dec.creditservice.bom.Credit;
import at.ms.dec.creditservice.exception.CreateCreditNotPossibleException;
import at.ms.dec.creditservice.exception.CreditNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CreditService {

    @Transactional
    Credit createCredit(CreditDto creditDto) throws CreateCreditNotPossibleException;

    @Transactional
    Credit updateCredit(CreditDto creditDto) throws CreditNotFoundException;

    @Transactional
    void acceptCredit(long creditId, String debtorAddress) throws CreditNotFoundException;

    @Transactional
    void updateCreditAfterSmatContractDeployedEvent(long creditId, String smartContractAddress, String debtorAddress) throws CreditNotFoundException;

    @Transactional
    Credit save(Credit credit);

    @Transactional(readOnly = true)
    List<Credit> findAll();

    @Transactional(readOnly = true)
    Credit findById(long id) throws CreditNotFoundException;

}
