# Rollup credit - Smart Contracts ZkSync

## Create Artifacts via the zksolc compiler

```bash
 yarn hardhat compile
 ```

Artifacts are added to /artifacts-zk

The byte code can be extracted from Credit.json and CreditFactory.json The abi can be also found in Credit.json and CreditFactory.json

## Deploy to local dev environment via cli

```bash
yarn hardhat deploy-zksync
```

## Deploy to zksync testnet via cli

```bash
yarn hardhat deploy-zksync --network zkTestnet
```
