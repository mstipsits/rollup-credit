package at.ms.dec.gateway.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String GATEWAY_TOPIC = "GATEWAY.TOPIC";
    public static final String BLOCKCHAIN_SERVICE_TOPIC = "BLOCKCHAINSERVICE.TOPIC";

    public static final String CREDIT_SERVICE_QUEUE = "CREDITSERVICE.QUEUE";
    public static final String BLOCKCHAIN_SERVICE_QUEUE = "BLOCKCHAINSERVICE.QUEUE";

    public static final String DLX_BLOCKCHAIN_EXCHANGE = "DLX.BLOCKCHAIN.EXCHANGE";
    public static final String DLX_CREDIT_EXCHANGE = "DLX.CREDIT.EXCHANGE";

    public static final String DLQ_CREDITSERVICE_QUEUE = "DLQ.CREDITSERVICE.QUEUE";
    public static final String DLQ_BLOCKCHAIN_QUEUE = "DLQ.BLOCKCHAINSERVICE.QUEUE";

    public static final String CREDIT_EVENT = "creditEvent";
    public static final String ACCEPT_CREDIT_EVENT = "acceptCreditEvent";
    public static final String DEPLOYED_SMART_CONTRACT_EVENT = "deployedSmartContractEvent";

    @Bean
    public TopicExchange gatewayTopic() {
        return new TopicExchange(GATEWAY_TOPIC);
    }

    @Bean
    public TopicExchange blockchainServiceTopic() {
        return new TopicExchange(BLOCKCHAIN_SERVICE_TOPIC);
    }

    @Bean
    public Queue creditServiceQueue() {
        return QueueBuilder.durable(CREDIT_SERVICE_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_CREDIT_EXCHANGE)
                .build();
    }

    @Bean
    public Queue blockchainServiceQueue() {
        return QueueBuilder.durable(BLOCKCHAIN_SERVICE_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_BLOCKCHAIN_EXCHANGE)
                .build();
    }

    @Bean
    public FanoutExchange creditSerivceDlqExchange() {
        return new FanoutExchange(DLX_CREDIT_EXCHANGE);
    }

    @Bean
    public FanoutExchange blockchainServiceDlqExchange() {
        return new FanoutExchange(DLX_BLOCKCHAIN_EXCHANGE);
    }

    @Bean
    public Queue creditServiceDlq() {
        return QueueBuilder.durable(DLQ_CREDITSERVICE_QUEUE).build();
    }

    @Bean
    public Queue blockchainServiceDlq() {
        return QueueBuilder.durable(DLQ_BLOCKCHAIN_QUEUE).build();
    }


    @Bean
    public Binding bindingCreditDlq() {
        return BindingBuilder.bind(creditServiceDlq()).to(creditSerivceDlqExchange());
    }

    @Bean
    public Binding bindingBlockchainDlq() {
        return BindingBuilder.bind(blockchainServiceDlq()).to(blockchainServiceDlqExchange());
    }

    @Bean
    public Binding bindingCreditEventCreditSerivce(Queue creditServiceQueue, TopicExchange gatewayTopic) {
        return BindingBuilder.bind(creditServiceQueue).to(gatewayTopic).with(CREDIT_EVENT);
    }

    @Bean
    public Binding bindingAcceptCreditEventCreditService(Queue creditServiceQueue, TopicExchange gatewayTopic) {
        return BindingBuilder.bind(creditServiceQueue).to(gatewayTopic).with(ACCEPT_CREDIT_EVENT);
    }

    @Bean
    public Binding bindingAcceptCreditEventBlockchainService(Queue blockchainServiceQueue, TopicExchange gatewayTopic) {
        return BindingBuilder.bind(blockchainServiceQueue).to(gatewayTopic).with(ACCEPT_CREDIT_EVENT);
    }

    @Bean
    public Binding bindingDeployedSmartContractEventCreditService(Queue creditServiceQueue, TopicExchange blockchainServiceTopic) {
        return BindingBuilder.bind(creditServiceQueue).to(blockchainServiceTopic).with(DEPLOYED_SMART_CONTRACT_EVENT);
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
}
