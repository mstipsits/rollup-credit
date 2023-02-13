package org.web3j.model;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.8.7.
 */
@SuppressWarnings("rawtypes")
public class CreditFactory extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610eee806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806321d29dcf1461005c578063397189e6146100765780638b8b5dae146100af5780639401a74c146100e0578063b5c784a71461012a575b600080fd5b610064610147565b60408051918252519081900360200190f35b6100936004803603602081101561008c57600080fd5b503561014d565b604080516001600160a01b039092168252519081900360200190f35b6100cc600480360360208110156100c557600080fd5b5035610174565b604080519115158252519081900360200190f35b610128600480360360c08110156100f657600080fd5b508035906020810135906001600160a01b03604082013581169160608101359091169060808101359060a0013561017b565b005b6100936004803603602081101561014057600080fd5b5035610287565b60005490565b6000818154811061015a57fe5b6000918252602090912001546001600160a01b0316905081565b6000541190565b600086868686868660405161018f906102b0565b80878152602001868152602001856001600160a01b03168152602001846001600160a01b031681526020018381526020018281526020019650505050505050604051809103906000f0801580156101ea573d6000803e3d6000fd5b50600080546001810182559080527f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e5630180546001600160a01b038084166001600160a01b03199092168217909255604080518b815260208101929092529187168183015290519192507fde68efefe49f52ee949c722f520f185e5f23d58d46b51eb7b76af382909ff65c919081900360600190a150505050505050565b600080828154811061029557fe5b6000918252602090912001546001600160a01b031692915050565b610bfb806102be8339019056fe608060405234801561001057600080fd5b50604051610bfb380380610bfb833981810160405260c081101561003357600080fd5b508051602082015160408301516060840151608085015160a09095015160008590556001849055600284905560058054600680546001600160a01b0319166001600160a01b038087169190911790915560038990556004849055610100600160a81b0319909116610100918616919091021760ff1916905593949293919290916100bb6100c6565b50505050505061014f565b612710600454600254021061010f5760005b6100e0610111565b81101561010d5761271060045460025402816100f857fe5b600280549290910490910190556001016100d8565b505b565b600060015b6000600c6003546064028161012757fe5b049050600c6104b083020480821161014057505061014a565b5050600101610116565b905090565b610a9d8061015e6000396000f3fe6080604052600436106100c25760003560e01c80636e3178471161007f578063b261bb7d11610059578063b261bb7d146101a1578063b9a48c5c146101b6578063cf0f06d3146101cb578063ff3ac264146101e0576100c2565b80636e3178471461017c5780637e78fdb914610184578063854bec8714610199576100c2565b80630d09365c146100c757806333b513fd146100de578063369cd8f5146100e6578063378aa7011461010d57806338cc4831146101435780635e751b6114610174575b600080fd5b3480156100d357600080fd5b506100dc6101f5565b005b6100dc610240565b3480156100f257600080fd5b506100fb6102ff565b60408051918252519081900360200190f35b34801561011957600080fd5b50610122610381565b6040518082600481111561013257fe5b815260200191505060405180910390f35b34801561014f57600080fd5b50610158610406565b604080516001600160a01b039092168252519081900360200190f35b6100dc61040a565b6100dc6104f1565b34801561019057600080fd5b506101586105eb565b6100dc610676565b3480156101ad57600080fd5b506100fb610747565b3480156101c257600080fd5b506100fb6107c9565b3480156101d757600080fd5b5061015861084b565b3480156101ec57600080fd5b506100fb6108db565b612710600454600254021061023e5760005b61020f61095d565b81101561023c57612710600454600254028161022757fe5b60028054929091049091019055600101610207565b505b565b60055461010090046001600160a01b0316331461028e5760405162461bcd60e51b8152600401808060200182810382526024815260200180610a446024913960400191505060405180910390fd5b60008060055460ff1660048111156102a257fe5b146102de5760405162461bcd60e51b815260040180806020018281038252604a8152602001806109fa604a913960600191505060405180910390fd5b60015447111561023c57600580546001919060ff191682805b021790555050565b60055460009061010090046001600160a01b031633148061032a57506006546001600160a01b031633145b8061033f57506007546001600160a01b031633145b61037a5760405162461bcd60e51b815260040180806020018281038252603a8152602001806109c0603a913960400191505060405180910390fd5b5060035490565b60055460009061010090046001600160a01b03163314806103ac57506006546001600160a01b031633145b806103c157506007546001600160a01b031633145b6103fc5760405162461bcd60e51b815260040180806020018281038252603a8152602001806109c0603a913960400191505060405180910390fd5b5060055460ff1690565b3090565b6006546001600160a01b031633146104535760405162461bcd60e51b815260040180806020018281038252602481526020018061099c6024913960400191505060405180910390fd5b60018060055460ff16600481111561046757fe5b146104a35760405162461bcd60e51b815260040180806020018281038252604a8152602001806109fa604a913960600191505060405180910390fd5b6006546040516001600160a01b03909116904780156108fc02916000818181858888f193505050501580156104dc573d6000803e3d6000fd5b50600580546002919060ff19166001836102f7565b60055461010090046001600160a01b0316331461053f5760405162461bcd60e51b8152600401808060200182810382526024815260200180610a446024913960400191505060405180910390fd5b60038060055460ff16600481111561055357fe5b1461058f5760405162461bcd60e51b815260040180806020018281038252604a8152602001806109fa604a913960600191505060405180910390fd5b60025461023c576005546040516001600160a01b0361010090920491909116904780156108fc02916000818181858888f193505050501580156105d6573d6000803e3d6000fd5b50600580546004919060ff19166001836102f7565b60055460009061010090046001600160a01b031633148061061657506006546001600160a01b031633145b8061062b57506007546001600160a01b031633145b6106665760405162461bcd60e51b815260040180806020018281038252603a8152602001806109c0603a913960400191505060405180910390fd5b506006546001600160a01b031690565b6006546001600160a01b031633146106bf5760405162461bcd60e51b815260040180806020018281038252602481526020018061099c6024913960400191505060405180910390fd5b60028060055460ff1660048111156106d357fe5b1461070f5760405162461bcd60e51b815260040180806020018281038252604a8152602001806109fa604a913960600191505060405180910390fd5b34600254106107265760028054349003905561072c565b60006002555b60025461023c57600580546003919060ff19166001836102f7565b60055460009061010090046001600160a01b031633148061077257506006546001600160a01b031633145b8061078757506007546001600160a01b031633145b6107c25760405162461bcd60e51b815260040180806020018281038252603a8152602001806109c0603a913960400191505060405180910390fd5b5060025490565b60055460009061010090046001600160a01b03163314806107f457506006546001600160a01b031633145b8061080957506007546001600160a01b031633145b6108445760405162461bcd60e51b815260040180806020018281038252603a8152602001806109c0603a913960400191505060405180910390fd5b5060015490565b60055460009061010090046001600160a01b031633148061087657506006546001600160a01b031633145b8061088b57506007546001600160a01b031633145b6108c65760405162461bcd60e51b815260040180806020018281038252603a8152602001806109c0603a913960400191505060405180910390fd5b5060055461010090046001600160a01b031690565b60055460009061010090046001600160a01b031633148061090657506006546001600160a01b031633145b8061091b57506007546001600160a01b031633145b6109565760405162461bcd60e51b815260040180806020018281038252603a8152602001806109c0603a913960400191505060405180910390fd5b5060045490565b600060015b6000600c6003546064028161097357fe5b049050600c6104b083020480821161098c575050610996565b5050600101610962565b90509056fe53656e6465722069736e27616464496e746572657374436f756e74657220446562746f7253656e6465722069736e27616464496e746572657374436f756e746572204c6f616e65722c20446562746f72206f722053797374656d5573657243726564697420436f6e7472616374206973206e6f7420696e20726967687420737461746520746f2065616464496e746572657374436f756e74657265637574652066756e6374696f6e53656e6465722069736e27616464496e746572657374436f756e746572204c6f616e6572a2646970667358221220b84dec3ce17900671b494c7cf7fdfe72d95859be866ed91ce142996c04ab93d764736f6c63430007010033a26469706673582212201ca7b40d2fd23be0f80e3cde37042cee752b12b238f4b927b0bc9828843b502b64736f6c63430007010033";

    public static final String FUNC_ALLCREDITS = "allCredits";

    public static final String FUNC_CREATECREDITCONTRACT = "createCreditContract";

    public static final String FUNC_GETAMOUNTOFCREDITS = "getAmountOfCredits";

    public static final String FUNC_GETCREDITBYID = "getCreditById";

    public static final String FUNC_ISCREDITPRESENT = "isCreditPresent";

    public static final Event CREDITCREATEDEVENT_EVENT = new Event("CreditCreatedEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected CreditFactory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CreditFactory(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CreditFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CreditFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<CreditCreatedEventEventResponse> getCreditCreatedEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CREDITCREATEDEVENT_EVENT, transactionReceipt);
        ArrayList<CreditCreatedEventEventResponse> responses = new ArrayList<CreditCreatedEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CreditCreatedEventEventResponse typedResponse = new CreditCreatedEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.idCreditDb = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.smartContractAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.debtor = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CreditCreatedEventEventResponse> creditCreatedEventEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CreditCreatedEventEventResponse>() {
            @Override
            public CreditCreatedEventEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CREDITCREATEDEVENT_EVENT, log);
                CreditCreatedEventEventResponse typedResponse = new CreditCreatedEventEventResponse();
                typedResponse.log = log;
                typedResponse.idCreditDb = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.smartContractAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.debtor = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CreditCreatedEventEventResponse> creditCreatedEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CREDITCREATEDEVENT_EVENT));
        return creditCreatedEventEventFlowable(filter);
    }

    public RemoteFunctionCall<String> allCredits(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ALLCREDITS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> createCreditContract(BigInteger _idCreditDb, BigInteger _creditAmountWei, String _loaner, String _debtor, BigInteger _periodMonths, BigInteger _interestRateBps) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATECREDITCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_idCreditDb), 
                new org.web3j.abi.datatypes.generated.Uint256(_creditAmountWei), 
                new org.web3j.abi.datatypes.Address(160, _loaner), 
                new org.web3j.abi.datatypes.Address(160, _debtor), 
                new org.web3j.abi.datatypes.generated.Uint256(_periodMonths), 
                new org.web3j.abi.datatypes.generated.Uint256(_interestRateBps)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getAmountOfCredits() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETAMOUNTOFCREDITS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getCreditById(BigInteger creditId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCREDITBYID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(creditId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isCreditPresent(BigInteger creditId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISCREDITPRESENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(creditId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static CreditFactory load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CreditFactory(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CreditFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CreditFactory(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CreditFactory load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CreditFactory(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CreditFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CreditFactory(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CreditFactory> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CreditFactory.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<CreditFactory> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CreditFactory.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CreditFactory> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CreditFactory.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CreditFactory> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CreditFactory.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class CreditCreatedEventEventResponse extends BaseEventResponse {
        public BigInteger idCreditDb;

        public String smartContractAddress;

        public String debtor;
    }
}
