package at.ms.dec.blockchainservice.service;

import at.ms.dec.commons.event.smartcontract.DeployedSmartContractEvent;
import at.ms.dec.gateway.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("EventPublishServiceImpl")
public class EventPublishServiceImpl implements EventPublishService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EventPublishServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishDeployedSmartContractEvent(long creditId, String smartContractAddress, String debtorAddress) {
        final DeployedSmartContractEvent deployedSmartContractEvent = DeployedSmartContractEvent.builder()
                .creditId(creditId)
                .smartContractAddress(smartContractAddress)
                .debtorAddress(debtorAddress)
                .build();
        rabbitTemplate.convertAndSend(RabbitMqConfig.BLOCKCHAIN_SERVICE_TOPIC, RabbitMqConfig.DEPLOYED_SMART_CONTRACT_EVENT, deployedSmartContractEvent);
    }
}
