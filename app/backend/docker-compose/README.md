# Rollup credit - Dev Environment

Startup dev environment (Database and Message Broker)

```bash
docker-compose -f docker-compose/services.yml -p dec  up --always-recreate-deps --renew-anon-volumes --remove-orphans --force-recreate
```

In case RabbitMQ does not work as expected:

```bash
rabbitmqctl stop_app
rabbitmqctl reset
rabbitmqctl start_app
```