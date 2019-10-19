# Apache Kafka (Kafka Streams)

Features:

* Free and open source distributed event processing computation
* Scalable, fault-tolerant data processing
* Seamless integration with Kafka

#### Main Page: [kafka.apache.org/documentation/streams](https://kafka.apache.org/documentation/streams/)

#### GitHub:  [apache/kafka](https://github.com/apache/kafka)

#### Latest stable release: [2.3.0](https://kafka.apache.org/downloads.html)

#### Community involvement: [Contributors](https://github.com/apache/kafka/graphs/contributors)

* **Used by:** NA
* **Watching:** 1 024
* **Stars:** 13 407
* **Forks:** 7 129
* **Topic: `kafka-streams`:** 345

#### How to run

* Locally: `sbt run`

or 

* Download Kafka
* Assemble JAR file: `sbt clean assembly`
* Execute:

```bash
java -cp ${PATH_TO_PROJECT}/kafka/target/scala-2.12/kafka-streams-0.1.0-SNAPSHOT.jar StreamProcessor
```

#### ToDo:
* Add unit-tests