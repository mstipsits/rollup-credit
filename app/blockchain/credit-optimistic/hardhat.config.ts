import {HardhatUserConfig} from "hardhat/config";
import "@nomicfoundation/hardhat-toolbox";

const config: HardhatUserConfig = {
    solidity: "0.8.17",
};

export default config;

const GOERLI_PRIVATE_KEY = "54ede6b17518ff90ec27e4bf5b10fbd42995df086c24d2e4085438d11acdced6";

module.exports = {
    solidity: "0.8.9",
    networks: {
        goerli: {
            url: `https://arb-goerli.g.alchemy.com/v2/qHgcrtcbkkjr4cG_P206WLbvVmUTjucH`,
            accounts: [GOERLI_PRIVATE_KEY]
        }
    }
};
