package at.ms.dec.blockchainservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAutoConfiguration
public class BlockchainServiceApplication {

    public static void main(String[] args) throws FileNotFoundException {
        System.setProperty("credit.factory.contract.address", determineCreditFactoryAddress());
        SpringApplication.run(BlockchainServiceApplication.class, args);
    }

    private static String determineCreditFactoryAddress() throws FileNotFoundException {
        final File deploymentInfoFile = System.getProperty("spring.profiles.active") != null && System.getProperty("spring.profiles.active").equals("optimistic")
                ? new File("./app/blockchain/credit-optimistic/deployment-info/index.ts")
                : new File("./app/blockchain/credit-zksync/deployment-info/index.ts");
        final Scanner scanner = new Scanner(deploymentInfoFile);

        while (scanner.hasNextLine()) {
            final String data = scanner.nextLine();
            if (data.contains("0x")) {
                final String creditFactoryAddress = data.substring(data.indexOf("0"), data.indexOf("}") - 1);
                scanner.close();
                return creditFactoryAddress;
            }
        }
        throw new IllegalStateException("CreditFactory Address could not be established");
    }

    @Bean
    public SmartInitializingSingleton reconfigureCf(CachingConnectionFactory cachingConnectionFactory) {
        return () -> cachingConnectionFactory.setConnectionNameStrategy(f -> "blockchain-service");
    }

    @Bean
    public MessageConverter messageConverter() {
        final ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
