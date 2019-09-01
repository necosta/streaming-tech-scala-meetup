# Apache Storm

Features:

* Free and open source distributed realtime computation system
* Scalable, fault-tolerant data processing
* Designed to be usable with any programming language (with Apache Thrift)

#### Main Page: [storm.apache.org](https://storm.apache.org/)

#### GitHub:  [apache/storm](https://github.com/apache/storm)

#### Latest stable release: [2.0.0](https://storm.apache.org/downloads.html)

#### Community involvement: [Contributors](https://github.com/apache/storm/graphs/contributors)

* **Used by:** 3
* **Watching:** 632
* **Stars:** 5 819
* **Forks:** 3 948
* **Topic: `storm`:** 225

#### How to run

* Download Storm
* Assemble JAR file: `sbt clean assembly`
* Execute:

```bash
./bin/storm jar ${PATH_TO_PROJECT}/storm/target/scala-2.12/storm-assembly-0.1.0-SNAPSHOT.jar StreamProcessor
```

#### ToDo:
* Add unit-tests
