import {Wallet} from "zksync-web3";
// @ts-ignore
import * as ethers from "ethers";
import {HardhatRuntimeEnvironment} from "hardhat/types";
import {Deployer} from "@matterlabs/hardhat-zksync-deploy";
import {deploymentAddressesBuilder} from "../utils/util";

export default async function (hre: HardhatRuntimeEnvironment) {
    console.log(`Running deploy script for the CreditFactory contract`);
    const wallet = hre.network.name === "zkTestnet"
        ? new Wallet("54ede6b17518ff90ec27e4bf5b10fbd42995df086c24d2e4085438d11acdced6")
        : new Wallet("7726827caac94a7f9e1b160f7ea819f172f7b6f9d2a97f992c38edeab82d4110");
    console.log(`Loaded wallet`);

    const deployer = new Deployer(hre, wallet);
    const artifact = await deployer.loadArtifact("CreditFactory");
    console.log(`Loaded artifact`);

    const creditFactoryContract = await deployer.deploy(artifact, []);
    console.log(`${artifact.contractName} was deployed to ${creditFactoryContract.address}`);

    deploymentAddressesBuilder.addDeployment('CreditFactory', creditFactoryContract.address);
    deploymentAddressesBuilder.generateDeploymentAddressesFile();
}

