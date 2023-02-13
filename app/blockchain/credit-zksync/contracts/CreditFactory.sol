//SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.1;

import "./Credit.sol";

contract CreditFactory {
    Credit[] public allCredits;

    event CreditCreatedEvent(
        uint256 idCreditDb,
        address smartContractAddress,
        address debtor
    );

    constructor() {}

    function createCreditContract(
        uint256 _idCreditDb,
        uint256 _creditAmountWei,
        address payable _loaner,
        address payable _debtor,
        uint256 _periodMonths,
        uint256 _interestRateBps
    ) public {
        Credit credit = new Credit(
            _idCreditDb,
            _creditAmountWei,
            _loaner,
            _debtor,
            _periodMonths,
            _interestRateBps
        );
        allCredits.push(credit);
        emit CreditCreatedEvent(_idCreditDb, address(credit), _debtor);
    }

    function isCreditPresent(uint256 creditId) public view returns (bool) {
        return allCredits.length > creditId;
    }

    function getCreditById(uint256 creditId) public view returns (Credit) {
        return allCredits[creditId];
    }

    function getAmountOfCredits() public view returns (uint256) {
        return allCredits.length;
    }
}