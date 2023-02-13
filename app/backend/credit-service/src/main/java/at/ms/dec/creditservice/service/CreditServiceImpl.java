package at.ms.dec.creditservice.service;

import at.ms.dec.commons.dto.CreditDto;
import at.ms.dec.creditservice.bom.Credit;
import at.ms.dec.creditservice.exception.CreateCreditNotPossibleException;
import at.ms.dec.creditservice.exception.CreditNotFoundException;
import at.ms.dec.creditservice.repository.CreditDao;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CreditService")
public class CreditServiceImpl implements CreditService {

    private final Logger logger = LoggerFactory.getLogger(CreditServiceImpl.class);

    private final CreditDao creditDao;
    private final ModelMapper modelMapper;

    @Autowired
    public CreditServiceImpl(CreditDao creditDao, ModelMapper modelMapper) {
        this.creditDao = creditDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public Credit createCredit(CreditDto creditDto) throws CreateCreditNotPossibleException {
        if (creditDto == null) {
            throw new CreateCreditNotPossibleException();
        }
        return save(modelMapper.map(creditDto, Credit.class));
    }

    @Override
    public Credit updateCredit(CreditDto creditDto) throws CreditNotFoundException {
        if (creditDao.existsById(creditDto.getId())) {
            return save(modelMapper.map(creditDto, Credit.class));
        }
        throw new CreditNotFoundException();
    }

    @Override
    public void acceptCredit(long creditId, String debtorAddress) throws CreditNotFoundException {
        final Credit credit = findById(creditId);
        credit.setDebtorAddress(debtorAddress);
    }

    @Override
    public void updateCreditAfterSmatContractDeployedEvent(long creditId, String smartContractAddress, String debtorAddress) throws CreditNotFoundException {
        final Credit credit = findById(creditId);
        credit.setSmartContractAddress(smartContractAddress);
        credit.setDebtorAddress(debtorAddress);
    }

    @Override
    public Credit save(Credit credit) {
        return creditDao.save(credit);
    }

    @Override
    public List<Credit> findAll() {
        return creditDao.findAll();
    }


    @Override
    public Credit findById(long id) throws CreditNotFoundException {
        return creditDao.findById(id)
                .orElseThrow(CreditNotFoundException::new);
    }
}
