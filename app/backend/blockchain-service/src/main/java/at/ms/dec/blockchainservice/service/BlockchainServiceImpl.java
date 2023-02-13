package at.ms.dec.blockchainservice.service;

import io.zksync.protocol.ZkSync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventValues;
import org.web3j.model.CreditFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

@Service("BlockchainService")
public class BlockchainServiceImpl implements BlockchainService {

    private final Logger logger = LoggerFactory.getLogger(BlockchainServiceImpl.class);
    private final ZkSync zkSync;
    private final EventPublishService eventPublishService;

    @Autowired
    public BlockchainServiceImpl(@Value("${blockchain.address}") String blockchainAddress,
                                 @Value("${credit.factory.contract.address}") String creditFactoryContractAddress,
                                 EventPublishService eventPublishService) {
        this.zkSync = ZkSync.build(new HttpService(blockchainAddress));
        this.eventPublishService = eventPublishService;
        subscribeToBlochainEvent(creditFactoryContractAddress);
    }

    private void subscribeToBlochainEvent(String creditFactoryContractAddress) {
        final EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, creditFactoryContractAddress);
        zkSync.ethLogFlowable(filter).subscribe(log -> {
            final EventValues eventValues = CreditFactory.staticExtractEventParameters(CreditFactory.CREDITCREATEDEVENT_EVENT, log);
            final long creditIdDb = ((BigInteger) eventValues.getNonIndexedValues().get(0).getValue()).longValue();
            final String smartContractAddress = eventValues.getNonIndexedValues().get(1).toString();
            final String debtorAddress = eventValues.getNonIndexedValues().get(2).toString();
            logger.info("Received Blockchain Event: creditIdDb: <{}> smartContractAddress: <{}> debtorAddress: <{}>", creditIdDb, smartContractAddress, debtorAddress);
            //use event only once
            eventPublishService.publishDeployedSmartContractEvent(creditIdDb, smartContractAddress, debtorAddress);
        });
    }

}
