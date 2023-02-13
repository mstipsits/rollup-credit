package at.ms.dec.blockchainservice.service;

public interface EventPublishService {

    void publishDeployedSmartContractEvent(long id, String smatContractAddress, String debtorAddress);
}
