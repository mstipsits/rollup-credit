package at.ms.dec.creditservice.event;

import at.ms.dec.commons.dto.CreditDto;
import at.ms.dec.commons.event.DecEvent;
import at.ms.dec.commons.event.credit.*;
import at.ms.dec.commons.event.smartcontract.DeployedSmartContractEvent;
import at.ms.dec.creditservice.bom.Credit;
import at.ms.dec.creditservice.exception.CreateCreditNotPossibleException;
import at.ms.dec.creditservice.exception.CreditNotFoundException;
import at.ms.dec.creditservice.service.CreditService;
import at.ms.dec.gateway.config.RabbitMqConfig;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RabbitListener(queues = RabbitMqConfig.CREDIT_SERVICE_QUEUE, exclusive = true)
public class CreditServiceQueueListener {

    private final Logger logger = LoggerFactory.getLogger(CreditServiceQueueListener.class);
    private final CreditService creditService;
    private final ModelMapper modelMapper;

    public CreditServiceQueueListener(CreditService creditService, ModelMapper modelMapper) {
        this.creditService = creditService;
        this.modelMapper = modelMapper;
    }

    @RabbitHandler
    public void receiveAndConvert(CreateCreditEvent createCreditEvent) throws CreateCreditNotPossibleException {
        logEvent(createCreditEvent);
        creditService.createCredit(createCreditEvent.getCreditDto());
    }

    @RabbitHandler
    public void receiveAndConvert(UpdateCreditEvent updateCreditEvent) throws CreditNotFoundException {
        logEvent(updateCreditEvent);
        creditService.updateCredit(updateCreditEvent.getCreditDto());
    }

    @RabbitHandler
    public GetAllCreditsEvent receiveAndConvert(GetAllCreditsEvent getAllCreditsEvent) {
        logEvent(getAllCreditsEvent);
        final ArrayList<CreditDto> creditDtos = new ArrayList<>();
        final List<Credit> credits = creditService.findAll();
        credits.forEach(c -> creditDtos.add(modelMapper.map(c, CreditDto.class)));
        getAllCreditsEvent.setCreditDtos(creditDtos);
        return getAllCreditsEvent;
    }

    @RabbitHandler
    public GetCreditEvent receiveAndConvert(GetCreditEvent getCreditEvent) throws CreditNotFoundException {
        logEvent(getCreditEvent);
        final Credit credit = creditService.findById(getCreditEvent.getCreditDto().getId());
        getCreditEvent.setCreditDto(modelMapper.map(credit, CreditDto.class));
        return getCreditEvent;
    }

    @RabbitHandler
    public void receiveAndConvert(AcceptCreditEvent acceptCreditEvent) throws CreditNotFoundException {
        logEvent(acceptCreditEvent);
        creditService.acceptCredit(acceptCreditEvent.getCreditDto().getId(), acceptCreditEvent.getDebtorAddress());
    }

    @RabbitHandler
    public void receiveAndConvert(DeployedSmartContractEvent deployedSmartContractEvent) throws CreditNotFoundException {
        logEvent(deployedSmartContractEvent);
        creditService.updateCreditAfterSmatContractDeployedEvent(deployedSmartContractEvent.getCreditId(), deployedSmartContractEvent.getSmartContractAddress(),
                deployedSmartContractEvent.getDebtorAddress());
    }

    private void logEvent(DecEvent decEvent) {
        logger.info("received Event: {}", decEvent);
    }
}
