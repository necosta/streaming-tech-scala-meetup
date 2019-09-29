# Streaming data from a LEGO Truck 🚚

Using data streamed from our custom made LEGO Truck,
we will present several Scala-based streaming event processing technologies
such as Apache Kafka (Kafka Streams), Apache Storm
and Apache Spark (Spark Streaming).

We will show key differences and we will try to compare them in a fair manner
on aspects like performance, implementation learning curve, stability, etc.


![TDH LEGO Truck](docs/img/tdh_truck.jpg)

### Project breakdown

* `docs` -> Contains demo slides. See [README.md](./docs/README.md)
* `kafka` -> Contains Kafka Streams Scala example. See [README.md](./kafka/README.md)
* `spark` -> Contains Spark Streaming Scala example. See [README.md](./spark/README.md)
* `storm` -> Contains Storm Scala example. See [README.md](./storm/README.md)

### Technologies under evaluation

* Apache Kafka (Kafka Streams)
* Apache Spark (Spark Streaming)
* Apache Storm
* [Apache Flink](https://flink.apache.org/) (not implemented)
* [Akka Streams](https://doc.akka.io/docs/akka/current/stream/index.html) (not implemented)
* [Apache Samza](http://samza.apache.org/) (not implemented)

### Presentation

[Slides on GitHub Pages](https://necosta.github.io/streaming-tech-scala-meetup/)

### Pre-requisites

* Install [Java](https://adoptopenjdk.net/)
* Install [SBT](https://www.scala-sbt.org/download.html)
* Download [Kafka](https://kafka.apache.org/downloads)
* Download [Storm](https://storm.apache.org/downloads.html)

### How to run (example)

1. Start Zookeeper: `./bin/zookeeper-server-start.sh config/zookeeper.properties`
1. Start Kafka broker: `./bin/kafka-server-start.sh config/server.properties`
1. Create a Kafka producer: `./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic SourceTopic`
1. Start all stream processors:
  1. Kafka streams: `sbt run`
  1. Spark streaming: `sbt run`
  1. Storm: `./bin/storm jar storm-assembly.jar StreamProcessor`
1. Setup Kafka consumer: `./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic SinkTopic`

### Disclaimer

The views, thoughts, and opinions expressed in this repository belong
solely to the author, and not to the author’s employer, organization,
committee or other group or individual.

### License

This project is licensed under the terms of the MIT license. See [LICENSE](LICENSE)

🚚🚍🚚🚍🚚🚍🚚🚍🚚🚍🚚🚍
