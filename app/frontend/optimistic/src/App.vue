<template>
  <div id="app">
    <div class="main-box">
      <div class="dec-welcome-text">
        <h1>Welcome to XXXX!</h1>
      </div>

      <div class="dec-information">
        <h3>You are interacting with the Credit Contract Factory: {{ CREDIT_FACTORY_CONTRACT_ADDRESS }} on the Arbitrum Network with Optimistic Rollups</h3>
        <h3>Currently there are {{ creditContractAmount }} Credit contracts deployed to the Blockchain.</h3>
      </div>

      <div class="create-credit">
        <h1>Create a new Credit</h1>

        <div class="credit-amount-input">
          <h3>Credit Amount in ETH</h3>
          <input v-model="inputCreditAmountEth" class="dec-input" placeholder="Credit Amount in ETH"/>
        </div>

        <div class="interest-rate-input">
          <h3>Interest Rate in percent</h3>
          <input v-model="inputInterestRatePercent" class="dec-input" placeholder="Interest Rate in percent"/>
        </div>

        <div class="periods-input">
          <h3>Period in Months</h3>
          <input v-model="inputPeriodInMonths" class="dec-input" placeholder="Period in Months"/>
        </div>

        <div class="create-credit-button">
          <button class="dec-button" v-on:click="createCreditContract">Create new Credit</button>
        </div>
      </div>

      <div class="all-possible-credits">
        <h1>All Possible Credits</h1>
        <p v-if="possibleCredits.length === 0" class="dec-not-found-msg">There are no possible credits found</p>
        <table v-if="possibleCredits.length > 0" aria-label="All possible credits table" class="table">
          <tr v-if="possibleCredits.length > 0" class="dec-table">
            <th id="pcCreditAmountTitle" class="dec-table-title">Credit Amount</th>
            <th id="pcLoanerAddressTitle" class="dec-table-title">Loaner Address</th>
            <th id="pcDebtorAddressTitle" class="dec-table-title">Debtor Address</th>
            <th id="pcOpenAmountTitle" class="dec-table-title">Open Amount</th>
            <th id="pcPeriodInMonthsTitle" class="dec-table-title">Period in months</th>
            <th id="pcInterestRatePerYearTitle" class="dec-table-title">Interest rate per year</th>
            <th id="pcActionTitle" class="dec-table-title">Action</th>
          </tr>
          <tr v-for="credit in possibleCredits" :key="credit.index">
            <td id="pcCreditAmount" class="dec-table">{{ credit.creditAmountEth }} ETH</td>
            <td id="pcLoanerAddress" class="dec-table">{{ credit.loanerAddress }}</td>
            <td id="pcDebtorAddress" class="dec-table">{{ credit.debtorAddress === null ? "-------" : credit.debtorAddress }}</td>
            <td id="pcOpenAmount" class="dec-table">{{ credit.openAmountEth === 0 ? "-------" : credit.openAmountEth + "ETH" }}</td>
            <td id="pcPeriodInMonths" class="dec-table">{{ credit.periodMonths }}</td>
            <td id="pcInterestRatePerYear" class="dec-table">{{ credit.interestRatePercent }} %</td>
            <td id="pcAction" class="dec-table">
              <button class="dec-button" v-on:click="acceptCreditContract(credit)">
                <span v-if="credit.txStatus === DecTransactionStatus.NO_STATUS">Accept Credit</span>
                <span v-else-if="credit.txStatus === DecTransactionStatus.SENDING_TX">Sending tx...</span>
                <span v-else-if="credit.txStatus === DecTransactionStatus.WAITING_FOR_COMMIT">Waiting until tx is committed...</span>
              </button>
            </td>
          </tr>
        </table>
      </div>

      <div class="my-credits">
        <h1>My Credits</h1>
        <p v-if="myCredits.length === 0" class="dec-not-found-msg">There are no credits attached to user </p>
        <table v-if="myCredits.length > 0" aria-label="My credits table" class="table">
          <tr v-if="myCredits.length > 0" class="dec-table">
            <th id="mSmartContractAddressTitle" class="dec-table-title">Smart Contract Address</th>
            <th id="mCreditAmountTitle" class="dec-table-title">Credit Amount</th>
            <th id="mLoanerAddressTitle" class="dec-table-title">Loaner Address</th>
            <th id="mDebtorAddressTitle" class="dec-table-title">Debtor Address</th>
            <th id="mOpenAmountTitle" class="dec-table-title">Open Amount</th>
            <th id="mPeriodInMonthsTitle" class="dec-table-title">Period in months</th>
            <th id="mInterestRatePerYearTitle" class="dec-table-title">Interest rate per year</th>
            <th id="mCurrenStateTitle" class="dec-table-title">Current State on Blockchain</th>
            <th id="mActionTitle" class="dec-table-title">Action</th>
          </tr>
          <tr v-for="credit in myCredits" :key="credit.index">
            <td id="mSmartContractAddress" class="dec-table">{{ credit.smartContractAddress }}</td>
            <td id="mCreditAmount" class="dec-table">{{ credit.creditAmountEth }} ETH</td>
            <td id="mLoanerAddress" class="dec-table">{{ credit.loanerAddress }}</td>
            <td id="mDebtorAddress" class="dec-table">{{ credit.debtorAddress }}</td>
            <td id="mOpenAmount" class="dec-table">{{ credit.openAmountEth !== 0 ? credit.openAmountEth + " ETH" : "--" }}</td>
            <td id="mPeriodInMonths" class="dec-table">{{ credit.periodMonths }}</td>
            <td id="mInterestRatePerYear" class="dec-table">{{ credit.interestRatePercent }} %</td>
            <td id="mCurrenState" class="dec-table">{{ credit.currentState }}</td>
            <td id="mAction" class="dec-table">
              <button v-if="signerAddress === credit.loanerAddress
                          && CreditContractState[credit.currentState] === CreditContractState.PLACE_CREDIT_AMOUNT"
                      class="dec-button"
                      v-on:click="placeCreditAmountOnContract(credit)">
                <span v-if="credit.txStatus === DecTransactionStatus.NO_STATUS">Place Credit Amount on Contract</span>
                <span v-else-if="credit.txStatus === DecTransactionStatus.SENDING_TX">Sending tx...</span>
                <span v-else-if="credit.txStatus === DecTransactionStatus.WAITING_FOR_COMMIT">Waiting until tx is committed...</span>
              </button>

              <button v-if="signerAddress === credit.debtorAddress
                         && CreditContractState[credit.currentState] === CreditContractState.GET_PLACED_CREDIT_AMOUNT"
                      class="dec-button"
                      v-on:click="getPlacedCreditAmount(credit)">
                <span v-if="credit.txStatus === DecTransactionStatus.NO_STATUS">Get Placed Credit Amount</span>
                <span v-else-if="credit.txStatus === DecTransactionStatus.SENDING_TX">Sending tx...</span>
                <span v-else-if="credit.txStatus === DecTransactionStatus.WAITING_FOR_COMMIT">Waiting until tx is committed...</span>
              </button>

              <div v-if="signerAddress === credit.debtorAddress
                    && CreditContractState[credit.currentState] === CreditContractState.PAYBACK"
                   class="payback">
                <input v-model="inputPaybackAmount" class="payback-input" placeholder="Payback Amount in ETH"/>

                <button class="dec-button" v-on:click="payback(credit)">
                  <span v-if="credit.txStatus === DecTransactionStatus.NO_STATUS">Payback</span>
                  <span v-else-if="credit.txStatus === DecTransactionStatus.SENDING_TX">Sending tx...</span>
                  <span v-else-if="credit.txStatus === DecTransactionStatus.WAITING_FOR_COMMIT">Waiting until tx is committed...</span>
                </button>
              </div>

              <button v-if="signerAddress === credit.loanerAddress
                       && CreditContractState[credit.currentState] === CreditContractState.GET_PAYEDBACK_CREDIT_AMOUNT"
                      class="dec-button" v-on:click="getPayedBackCredit(credit)">
                <span v-if="credit.txStatus === DecTransactionStatus.NO_STATUS">Get Payed Back Credit</span>
                <span v-else-if="credit.txStatus === DecTransactionStatus.SENDING_TX">Sending tx...</span>
                <span v-else-if="credit.txStatus === DecTransactionStatus.WAITING_FOR_COMMIT">Waiting until tx is committed...</span>
              </button>

            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import {Contract, Provider, Web3Provider} from 'web3';
import {ethers} from 'ethers';
import axios from "axios";

const CREDIT_FACTORY_CONTRACT_ADDRESS = "0x8A63f188803Ff2087c7D82e8be7bf3B21DAD2bf0";
const CREDIT_FACTORY_CONTRACT_ABI = require("./abi/CreditFactoryAbi.json");

const CREDIT_CONTRACT_ABI = require("./abi/CreditAbi.json");

const api = axios
    .create({
      baseURL: "http://localhost:8090/api/gateway",
    })

const CreditContractState = {
  PLACE_CREDIT_AMOUNT: 0,
  GET_PLACED_CREDIT_AMOUNT: 1,
  PAYBACK: 2,
  GET_PAYEDBACK_CREDIT_AMOUNT: 3,
  FINISHED: 4
}

const DecTransactionStatus = {
  NO_STATUS: 0,
  SENDING_TX: 1,
  WAITING_FOR_COMMIT: 2,
}

function initializeCreditContractWithAbi(credit) {
  return new Contract(credit.smartContractAddress, CREDIT_CONTRACT_ABI, this.signer);
}

export default {
  name: "App",
  data() {
    return {
      CREDIT_FACTORY_CONTRACT_ADDRESS,
      CreditContractState,
      DecTransactionStatus,
      inputCreditAmountEth: "",
      inputInterestRatePercent: "",
      inputPeriodInMonths: "",
      inputPaybackAmount: "",
      provider: null,
      signer: null,
      signerAddress: null,
      creditFactoryContract: null,
      creditContractAmount: null,
      credit: {
        index: null,
        smartContractAddress: null,
        creditAmountWei: null,
        creditAmountEth: null,
        openAmountWei: null,
        openAmountEth: null,
        loanerAddress: null,
        debtorAddress: null,
        periodMonths: null,
        interestRatePercent: null,
        currentState: null,
        txStatus: null,
      },
      possibleCredits: [],
      myCredits: [],
    };
  },
  mounted: function () {
    window.ethereum
        .request({method: "eth_requestAccounts"})
        .then(() => {
          this.loadMainScreen();
        })
        .catch((e) => console.log(e));
  },
  methods: {
    initializeProviderAndSigner() {
      this.provider = new Provider('https://arb-goerli.g.alchemy.com/v2/qHgcrtcbkkjr4cG_P206WLbvVmUTjucH');
      this.signer = (new Web3Provider(window.ethereum)).getSigner();
      this.signer.getAddress()
          .then((signerAddress) => this.signerAddress = signerAddress)
          .catch(() => console.log("Signer Address cannot be determined"));

      this.creditFactoryContract = new Contract(
          CREDIT_FACTORY_CONTRACT_ADDRESS,
          CREDIT_FACTORY_CONTRACT_ABI,
          this.signer
      );
    },
    async getCreditByIndex(index) {
      return this.creditContractAmount !== 0
          ? this.creditFactoryContract.getCreditById(index)
          : 0;
    },
    async getAmountOfCrediContracts() {
      return this.creditFactoryContract.getAmountOfCredits();
    },
    async getOverrides() {
      return {};
    },
    async acceptCreditContract(credit) {
      credit.txStatus = DecTransactionStatus.SENDING_TX;
      try {
        await this.creditFactoryContract.createCreditContract(
            credit.index,
            credit.creditAmountWei,
            credit.loanerAddress,
            this.signerAddress,
            credit.periodMonths,
            credit.interestRatePercent * 100,
            {}
        );
        credit.txStatus = DecTransactionStatus.WAITING_FOR_COMMIT;
        await this.updatePossibleCredits();
      } catch (error) {
        console.log("AcceptCredit not possible");
      }
      credit.txStatus = DecTransactionStatus.NO_STATUS;
    },
    async placeCreditAmountOnContract(credit) {
      credit.txStatus = DecTransactionStatus.SENDING_TX;
      try {
        const creditContract = initializeCreditContractWithAbi.call(this, credit);
        const feeInGas = await creditContract.estimateGas.placeCreditAmount();
        const gasPriceInUnits = await this.provider.getGasPrice();

        const feePlusCreditAmount = feeInGas.mul(gasPriceInUnits).add(credit.creditAmountWei);
        let overrides = {
          value: ethers.utils.parseEther(ethers.utils.formatEther(feePlusCreditAmount))
        }

        await creditContract.placeCreditAmount(overrides);
        credit.txStatus = DecTransactionStatus.WAITING_FOR_COMMIT;
        await this.updateMyCredits();
      } catch (e) {
        console.log(e);
      }
      credit.txStatus = DecTransactionStatus.NO_STATUS;
    },
    async getPlacedCreditAmount(credit) {
      credit.txStatus = DecTransactionStatus.SENDING_TX;
      try {
        const creditContract = initializeCreditContractWithAbi.call(this, credit);
        await creditContract.getPlacedCreditAmount();
        credit.txStatus = DecTransactionStatus.WAITING_FOR_COMMIT;
        await this.updateMyCredits();
      } catch (e) {
        console.log(e);
      }
      credit.txStatus = DecTransactionStatus.NO_STATUS;
    },
    async payback(credit) {
      credit.txStatus = DecTransactionStatus.SENDING_TX;
      try {
        const creditContract = initializeCreditContractWithAbi.call(this, credit);
        const feeInGas = await creditContract.estimateGas.payback();
        const gasPriceInUnits = await this.provider.getGasPrice();

        const feePlusPayback = Number(ethers.utils.formatEther(feeInGas.mul(gasPriceInUnits))) + Number(this.inputPaybackAmount);
        let overrides = {
          value: ethers.utils.parseEther(feePlusPayback.toString())
        }

        await creditContract.payback(overrides);
        credit.txStatus = DecTransactionStatus.WAITING_FOR_COMMIT;
        await this.updateMyCredits();
      } catch (e) {
        console.log(e);
      }
      credit.txStatus = DecTransactionStatus.NO_STATUS;
    },
    async getPayedBackCredit(credit) {
      credit.txStatus = DecTransactionStatus.SENDING_TX;
      try {
        const creditContract = initializeCreditContractWithAbi.call(this, credit);
        await creditContract.getPayedBackCreditWei();
        credit.txStatus = DecTransactionStatus.WAITING_FOR_COMMIT;
        await this.updateMyCredits();
      } catch (e) {
        console.log(e);
      }
      credit.txStatus = DecTransactionStatus.NO_STATUS;
    },
    loadMainScreen() {
      this.initializeProviderAndSigner();
      if (!this.provider || !this.signer) {
        alert("Not connected to Metamask");
        return;
      }
      this.fetchPossibleCredits();
      this.fetchMyCredits();
      this.creditFactoryContract.on("CreditCreatedEvent", (from, to, value, event) => {
        console.log(event);
        this.updateMyCredits();
        this.updatePossibleCredits();
      });
    },
    async fetchPossibleCredits() {
      return api
          .get(`/credit`)
          .then(response => {
            response.data.forEach((data) => {

              const credit = {
                index: data.id,
                smartContractAddress: data.smartContractAddress,
                creditAmountWei: data.creditAmountWei.toString(),
                creditAmountEth: ethers.utils.formatEther(data.creditAmountWei.toString()),
                openAmountWei: 0,
                openAmountEth: 0,
                loanerAddress: data.loanerAddress,
                debtorAddress: data.debtorAddress,
                periodMonths: data.periodMonths,
                interestRatePercent: data.interestRatePercent,
                txStatus: DecTransactionStatus.NO_STATUS,
              };
              if (credit.smartContractAddress == null) {
                this.possibleCredits.push(credit);
              }
            });
          })
          .catch((e) => console.log(e));
    },
    async fetchMyCredits() {
      this.getAmountOfCrediContracts()
          .then(async (amountOfCredits) => {
            this.creditContractAmount = amountOfCredits;
            for (let i = 0; i < amountOfCredits; i++) {
              try {
                const smartContractAddress = await this.creditFactoryContract.getCreditById(i);
                const creditContract = new Contract(smartContractAddress, CREDIT_CONTRACT_ABI, this.signer)

                const creditAmountWei = await creditContract.getCreditAmountWei();
                const openAmountWei = await creditContract.getOpenAmountWei();
                const loanerAddress = await creditContract.getLoaner();
                const debtorAddress = await creditContract.getDebtor();
                const periodMonths = await creditContract.getPeriodMonths();
                const interestRateBps = await creditContract.getInterestRateBps();
                const currentStateId = await creditContract.getCurrentState();

                const credit = {
                  index: i,
                  smartContractAddress: smartContractAddress,
                  creditAmountWei: creditAmountWei,
                  creditAmountEth: ethers.utils.formatEther(creditAmountWei.toString()),
                  openAmountWei: openAmountWei.toString(),
                  openAmountEth: ethers.utils.formatEther(openAmountWei.toString()),
                  loanerAddress: loanerAddress,
                  debtorAddress: debtorAddress,
                  periodMonths: periodMonths,
                  interestRatePercent: (interestRateBps / 100),
                  currentState: Object.keys(CreditContractState).find(key => CreditContractState[key] === currentStateId),
                  txStatus: DecTransactionStatus.NO_STATUS,
                };
                this.myCredits.push(credit);
              } catch (err) {
                console.log("Credit could not be loaded from Blockchain");
              }
            }
          })
    },
    async createCreditContract() {
      await api
          .post(`/credit`, {
            creditDto: {
              smartContractAddress: null,
              creditAmountWei: ethers.utils.parseEther(this.inputCreditAmountEth).toString(),
              loanerAddress: this.signerAddress,
              interestRatePercent: this.inputInterestRatePercent.toString(),
              periodMonths: this.inputPeriodInMonths.toString(),
            }
          })
      await this.updatePossibleCredits();
    },
    async updatePossibleCredits() {
      this.possibleCredits = [];
      await this.fetchPossibleCredits();
    },
    async updateMyCredits() {
      this.myCredits = [];
      await this.fetchMyCredits();
    },
  },
};
</script>

<style>
#app {
  font-family: sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: black;
  margin-top: 30px;
}

#app ul {
  display: inline-block;
}

.main-box {
  text-align: left;
  margin: auto;
  margin-top: 40px;
}

.all-possible-credits {
  text-align: left;
}

.my-credits {
  text-align: left;
}

.create-credit {
  margin-top: 20px;
}

.dec-button {
  background-color: green;
  border: 1px solid black;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 14px;
  border-radius: 12px
}

.table {
  margin-left: auto;
  margin-right: auto;
}

.dec-table {
  border: 1px solid;
  padding: 10px;
  text-align: center;
}

.dec-table-title {
  background-color: dodgerblue;
  color: white;
  border: 1px solid black;
  padding: 10px;
  text-align: center;
  text-decoration: none;
}

.dec-input {
  background-color: white;
  width: 30%;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
}

.credit-amount-input {
  padding: 1px;
  text-align: center;
  text-decoration: none;
}

.interest-rate-input {
  padding: 1px;
  text-align: center;
  text-decoration: none;
}

.periods-input {
  padding: 1px;
  text-align: center;
  text-decoration: none;
}

.payback-input {
  padding: 1px;
  text-align: center;
  text-decoration: none;
  margin-bottom: 4px;
  color: black;
}

.create-credit-button {
  text-align: center;
  margin-left: auto;
  margin-right: auto;
}

.dec-not-found-msg {
  padding: 0;
  margin: 2px;
  color: black;
  text-align: center;
  font-size: 18px;
}

h1 {
  margin: 35px 15px 15px;
  text-align: center;
  text-decoration: none;
  color: dodgerblue;
}

h3 {
  padding: 0;
  margin: 2px;
}

</style>
