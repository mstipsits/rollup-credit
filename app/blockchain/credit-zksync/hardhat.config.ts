require("@matterlabs/hardhat-zksync-deploy");
require("@matterlabs/hardhat-zksync-solc");

module.exports = {
    zksolc: {
        version: "1.2.0",
        compilerSource: "binary",
        settings: {
            optimizer: {
                enabled: true,
            },
            experimental: {
                dockerImage: "matterlabs/zksolc",
                tag: "v1.2.0",
            },
        },
    },
    networks: {
        hardhat: {
            zksync: true,
        },
        zkTestnet: {
            url: "https://zksync2-testnet.zksync.dev", // URL of the zkSync network RPC
            ethNetwork: "goerli", // URL of the Ethereum Web3 RPC, or the identifier of the network (e.g. `mainnet` or `goerli`)
            zksync: true
        },
    },
    solidity: {
        version: "0.8.1",
    },
};