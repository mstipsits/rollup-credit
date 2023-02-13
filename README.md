# Rollup Credit

Overall process of Rollup Credit:
 1. Startup dev environment (apps/backend/docker-compose)
 2. Deploy CreditContractFactory via CLI to Blockchain (/apps/blockchain/credit-zksync or /apps/blockchain/credit-optimistic)
 3. Configure blockchain.address in application.properties of BlockchainService (/apps/backend/blockchain-service)
 4. Startup Microservice: Gateway, CreditService and BlockchainService (/apps/backend)
 5. Startup Frontend (/apps/frontend/zksync or /apps/frontend/optimistic)

For further instructions use the READMEs in the submodules.
