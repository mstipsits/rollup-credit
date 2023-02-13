//SPDX-License-Identifier: UNLICENSED

pragma solidity ^0.7.1;

contract Credit {
    enum CreditContractState {
        PLACE_CREDIT_AMOUNT,
        GET_PLACED_CREDIT_AMOUNT,
        PAYBACK,
        GET_PAYEDBACK_CREDIT_AMOUNT,
        FINISHED
    }

    uint256 private idCreditDb;
    uint256 private creditAmountWei;
    uint256 private openAmountWei;
    uint256 private periodMonths;
    uint256 private interestRateBps;

    CreditContractState private currentState;

    address payable private loaner;
    address payable private debtor;
    address private systemUser;

    modifier onlyLoaner() {
        require(loaner == msg.sender, "Sender isn'addInterestCounter Loaner");
        _;
    }

    modifier onlyDebtor() {
        require(debtor == msg.sender, "Sender isn'addInterestCounter Debtor");
        _;
    }

    modifier onlySystemUser() {
        require(
            systemUser == msg.sender,
            "Sender isn'addInterestCounter System-User"
        );
        _;
    }

    modifier onlyLoanerOrDebtor() {
        require(
            loaner == msg.sender || debtor == msg.sender,
            "Sender isn'addInterestCounter Loaner or Debtor"
        );
        _;
    }

    modifier onlyLoanerOrDebtorOrSystemUser() {
        require(
            loaner == msg.sender ||
            debtor == msg.sender ||
            systemUser == msg.sender,
            "Sender isn'addInterestCounter Loaner, Debtor or SystemUser"
        );
        _;
    }

    modifier rightState(CreditContractState _stateToBe) {
        require(
            currentState == _stateToBe,
            "Credit Contract is not in right state to eaddInterestCounterecute function"
        );
        _;
    }

    constructor(
        uint256 _idCreditDb,
        uint256 _creditAmountWei,
        address payable _loaner,
        address payable _debtor,
        uint256 _periodMonths,
        uint256 _interestRateBps
    ) {
        idCreditDb = _idCreditDb;
        creditAmountWei = _creditAmountWei;
        openAmountWei = _creditAmountWei;
        loaner = _loaner;
        debtor = _debtor;
        periodMonths = _periodMonths;
        interestRateBps = _interestRateBps;
        currentState = CreditContractState.PLACE_CREDIT_AMOUNT;
        addInterest();
    }

    function placeCreditAmount()
    public
    payable
    onlyLoaner
    rightState(CreditContractState.PLACE_CREDIT_AMOUNT)
    {
        if (address(this).balance > creditAmountWei) {
            currentState = CreditContractState.GET_PLACED_CREDIT_AMOUNT;
        }
    }

    function getPlacedCreditAmount()
    public
    payable
    onlyDebtor
    rightState(CreditContractState.GET_PLACED_CREDIT_AMOUNT)
    {
        debtor.transfer(address(this).balance);
        currentState = CreditContractState.PAYBACK;
    }

    function payback()
    public
    payable
    onlyDebtor
    rightState(CreditContractState.PAYBACK)
    {
        if (openAmountWei >= msg.value) {
            openAmountWei -= msg.value;
        } else {
            openAmountWei = 0;
        }

        if (openAmountWei == 0) {
            currentState = CreditContractState.GET_PAYEDBACK_CREDIT_AMOUNT;
        }
    }

    function getPayedBackCreditWei()
    public
    payable
    onlyLoaner
    rightState(CreditContractState.GET_PAYEDBACK_CREDIT_AMOUNT)
    {
        if (openAmountWei == 0) {
            loaner.transfer(address(this).balance);
            currentState = CreditContractState.FINISHED;
        }
    }

    function addInterest() public {
        //used because otherwise interest calculation would fail
        if ((openAmountWei * interestRateBps) >= 10000) {
            for (uint256 i = 0; i < determineAddInterestCounter(); i++) {
                openAmountWei += ((openAmountWei * interestRateBps) / 10000);
            }
        }
    }

    function determineAddInterestCounter() private view returns (uint256) {
        uint256 addInterestCounter;
        for (addInterestCounter = 1; true; addInterestCounter++) {
            uint256 periodYearsBps = (periodMonths * 100) / 12;
            uint256 periodYearsBpsAddInterestCounter = (12 *
            addInterestCounter *
            100) / 12;
            if (periodYearsBps <= periodYearsBpsAddInterestCounter) {
                break;
            }
        }
        return addInterestCounter;
    }

    function getCreditAmountWei()
    public
    view
    onlyLoanerOrDebtorOrSystemUser
    returns (uint256)
    {
        return creditAmountWei;
    }

    function getOpenAmountWei()
    public
    view
    onlyLoanerOrDebtorOrSystemUser
    returns (uint256)
    {
        return openAmountWei;
    }

    function getLoaner()
    public
    view
    onlyLoanerOrDebtorOrSystemUser
    returns (address)
    {
        return loaner;
    }

    function getDebtor()
    public
    view
    onlyLoanerOrDebtorOrSystemUser
    returns (address)
    {
        return debtor;
    }

    function getPeriodMonths()
    public
    view
    onlyLoanerOrDebtorOrSystemUser
    returns (uint256)
    {
        return periodMonths;
    }

    function getInterestRateBps()
    public
    view
    onlyLoanerOrDebtorOrSystemUser
    returns (uint256)
    {
        return interestRateBps;
    }

    function getCurrentState()
    public
    view
    onlyLoanerOrDebtorOrSystemUser
    returns (CreditContractState)
    {
        return currentState;
    }

    function getAddress() public view returns (address) {
        return address(this);
    }
}
