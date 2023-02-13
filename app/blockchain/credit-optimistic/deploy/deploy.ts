import {ethers} from "hardhat";
import {deploymentAddressesBuilder} from "../utils/util";

async function main() {
    console.log(`Running deploy script for the CreditFactory contract`);
    //My Wallet PK
    // const wallet = new Wallet("0x54ede6b17518ff90ec27e4bf5b10fbd42995df086c24d2e4085438d11acdced6");
    //LocalHost Blockchain PK
    // const wallet = new Wallet("7726827caac94a7f9e1b160f7ea819f172f7b6f9d2a97f992c38edeab82d4110");
    // console.log(`Loaded wallet`);

    // const deployer = new Deployer(hre, wallet);
    // const artifact = await deployer.loadArtifact("CreditFactory");
    // console.log(`Loaded artifact`);
    //
    // const creditFactoryContract = await deployer.deploy(artifact, []);
    // console.log(`${artifact.contractName} was deployed to ${creditFactoryContract.address}`);


    const CreditFactory = await ethers.getContractFactory("CreditFactory");
    const creditFactoryContract = await CreditFactory.deploy();
    await creditFactoryContract.deployed();

    deploymentAddressesBuilder.addDeployment('CreditFactory', creditFactoryContract.address);
    deploymentAddressesBuilder.generateDeploymentAddressesFile();
}

main().catch((error) => {
    console.error(error);
    process.exitCode = 1;
});


