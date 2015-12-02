# Kafka
Apache Kafka.
If you want to use Kafka with Scala, you must make sure the version of your Scala match with Kafka you are going to install.
## Installation
1. Download Kafka
2. We first make our Kafka DIR called ``` mkdir myKafka```
3. Then, ```cd myKafka ```
4. ```cp ~/Downloads/kafka_2.11-0.9.0.0.tgz .```
5. ```tar xzf kafka_2.11-0.9.0.0.tgz ```
6. Make DIR for logs of Kafka  for e.g., two Kafka brokers in the same machine ```mkdir kafka-log1 ```, ``` mkdir kafka-log2 ```
7. Configure Kafka ``` vi config/server.properties ```
8. Make sure broker.id is unique
9. Specify the log path in the log section e.g., ```log.dirs=~/dev/myKafka/kafka-log1```
10. Specify your Zookeeper url if you have already installed on your organization
11. Save the config and quit
12. Start Zookeeper (Do not do this in production) ```bin/zookeeper-server-start.sh config/zookeeper.properties  & ```
13. Start the Kafka Broker 1 ```bin/kafka-server-start.sh config/server.properties & ```
14. Make a copy of the first server properties for the second broker ```cp config/server.properties  config/server2.properties ```
15. vi config/server2.properties
16. Change the ```broker.id=1```, the port to ```port=9091``` and change the log dir to e.g., ```log.dirs=~/dev/myKafka/kafka-log2 ```
17. We do not need to change the Zookeeper config because both brokers talk to the same Zookeeper
16. Start the Kafka broker 2 ```bin/kafka-server-start.sh config/server2.properties & ```
17. See the result ```ps -ef | grep kafka ```

### Create Topics
With two replication--factor because we have two brokers ```bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic myfirstTopic --partition 2 --replication-factor 2 ```

### Describe the topics
```bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic myfirstTopic ```
### List the topics

### Producer

### Consomer

### Java API for Kafka












