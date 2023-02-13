package at.ms.dec.gateway.web.rest;

import at.ms.dec.commons.dto.CreditDto;
import at.ms.dec.commons.event.credit.*;
import at.ms.dec.gateway.config.RabbitMqConfig;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gateway/credit")
@CrossOrigin(origins = "http://localhost:8080")
public class CreditResource {

    private final Logger logger = LoggerFactory.getLogger(CreditResource.class);
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CreditResource(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CreditDto>> retrieveAllCredits() {
        final GetAllCreditsEvent getAllCreditsEvent = rabbitTemplate.convertSendAndReceiveAsType(RabbitMqConfig.GATEWAY_TOPIC, RabbitMqConfig.CREDIT_EVENT, GetAllCreditsEvent.builder()
                .build(), ParameterizedTypeReference.forType(GetAllCreditsEvent.class));
        if (getAllCreditsEvent == null || CollectionUtils.isEmpty(getAllCreditsEvent.getCreditDtos())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getAllCreditsEvent.getCreditDtos(), HttpStatus.OK);
    }

    @GetMapping(path = "/{creditId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreditDto> retrieveCredit(@PathVariable long creditId) {
        final GetCreditEvent getCreditEvent = GetCreditEvent.builder()
                .creditDto(CreditDto.builder().id(creditId).build())
                .build();
        final GetCreditEvent getCreditEventResponse = rabbitTemplate.convertSendAndReceiveAsType(RabbitMqConfig.GATEWAY_TOPIC, RabbitMqConfig.CREDIT_EVENT, getCreditEvent,
                ParameterizedTypeReference.forType(GetCreditEvent.class));
        if (getCreditEventResponse == null || getCreditEventResponse.getCreditDto() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getCreditEventResponse.getCreditDto(), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreditDto> createCredit(@Valid @RequestBody CreateCreditEvent createCreditEvent) {
        logger.info("create credit: {}", createCreditEvent.getCreditDto());
        rabbitTemplate.convertAndSend(RabbitMqConfig.GATEWAY_TOPIC, RabbitMqConfig.CREDIT_EVENT, createCreditEvent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreditDto> updateCredit(@Valid @RequestBody UpdateCreditEvent updateCreditEvent) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.GATEWAY_TOPIC, RabbitMqConfig.CREDIT_EVENT, updateCreditEvent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreditDto> acceptCredit(@Valid @RequestBody AcceptCreditEvent acceptCreditEvent) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.GATEWAY_TOPIC, RabbitMqConfig.ACCEPT_CREDIT_EVENT, acceptCreditEvent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
