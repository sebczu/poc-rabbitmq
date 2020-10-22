### **POC-RABBITMQ**
**spring boot amqp + rabbitmq**

#### 1. Run rabbitmq
/docker
```bash
docker-compose up
```

#### 2. Rabbitmq admin panel
```bash
http://localhost:15672
username: admin
password: test
```

#### 3. Build package
```bash
mvn clean install
```

#### 4. Run service
```bash
mvn clean install -Prun
```