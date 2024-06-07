# Kafka

## **작업 환경**
OS: Ubuntu  
Tool: Intellij  
Builder: gradle  
Framework: Spring-Boot  
Language: Java  
KafkaVersion: kafka_2.13

# Kafka 설치 및 실행
1. zookeeper 실행
```bash
# 다운받은 kafka 디렉토리 내에서 실행하였을 때 경로
xxx@kafka:~/kafka_2.13-3.6.1$ ./bin/zookeeper-server-start.sh ./config/zookeeper.properties 
```

2. kafka-server 실행
```bash
xxx@kafka:~/kafka_2.13-3.6.1$ ./bin/kafka-server-start.sh ./config/server.properties
```

3. topic 생성 - 이벤트 저장할 주제
```bash
xxx@kafka:~/kafka_2.13-3.6.1$ ./bin/kafka-topics.sh --create --topic ${원하는topic명:kafka-test} --bootstrap-server ${사용하길 원하는 주소:localhost:9092}
```

4. topic 생성 확인
```bash
xxx@kafka:~/kafka_2.13-3.6.1$ ./bin/kafka-topics.sh --describe --topic ${topicName:kafka-test}  --bootstrap-server ${Address:localhost:9092}
Topic: kafka-test	TopicId: JW42CEjhS1qqnwIZ1cvdzQ	PartitionCount: 1	ReplicationFactor: 1	Configs: 
	Topic: kafka-test	Partition: 0	Leader: 0	Replicas: 0	Isr: 0
```

5. Producer로 Event 발신
```bash
xxx@kafka:~/kafka_2.13-3.6.1$ ./bin/kafka-console-producer.sh --topic ${topicName:kafka-test} --bootstrap-server ${Address:localhost:9092}
>This is my first event
```

6. Consumer로 Event수신
```bash
xxx@kafka:~/kafka_2.13-3.6.1$ ./bin/kafka-console-consumer.sh --topic ${topicName:kafka-test} --bootstrap-server ${Address:localhost:9092}
```

## Docker로 작업

1. Apache Kafka Image Download
```bash
docker pull apache/kafka:3.7.0
```

2. Kafka Docker Container 생성 및 실행
```bash
docker run -p 9092:9092 apache/kafka:3.7.0 
```
---
# Kafka 구성요소
### Producer
Producer는 지정한 Topic에 데이터를 보내는 역할을 담당합니다.  
### Consumer
Consumer는 Topic에 쌓여있는 데이터를 읽어오는 역할을 담당합니다.  
그룹으로 묶게되면 그룹내에서는 Topic을 하나만 처리하며, 하나의 Consumer가 Topic의 데이터를 해소한 경우 같은 Group에 존재하는 나머지 Consumer들은 데이터를 읽어들일 수 없습니다.
### Topic
Topic은 주제라고 부르며 Topic은 여러개의 파티션으로 나뉘어 관리됩니다.  
각 파티션은 메시지를 순서를 보장하여 저장 및 소모합니다.
### Partition
Topic내 논리적인 분할 단위이며, Offset을 통해 데이터의 순서를 보장합니다.  
파티션을 통해 Topic은 병렬 처리가 가능합니다.

---
# 실제 작업하며 발전하는 과정
## Kafka 구축 후 고민 및 해결 방안
### 1. 기본 구성으로 구축
kafka를 사용해서 String데이터만 송/수신 해보았습니다.  
실제 업무에서는 구조없이 단순데이터를 kafka를 통해 송/수신하지 않기 때문에 실제 업무에서 사용하듯이 데이터 구조가 필요해 보입니다.

추가적으로 구현되어야하는 것
- 데이터 구조 추가
- Kafka의 Topic-id와 같이 kafka Event의 payload뿐 아니라 header에 담기는 Event-id와 같은 정보들을 가져올 수 있어야합니다.

### 2. 발행 Payload에 구조추가
일반 데이터를 Event로 발행하는 일은 거의 없어 구조를 추가하였습니다.

추가적으로 구현되어야하는 것
- 다양한 데이터 타입 제공 및 Consuming -> 다형성으로 해결가능하지 않을까?
- 여러 데이터 타입의 조회 확인 -> Spring Event와 같이 Listener의 parameter로 받아들이는 객체 타입에 따라 작동하는지 확인필요
