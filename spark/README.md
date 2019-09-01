# Apache Spark (Spark Streaming)

Features:

* Free and open source distributed batch and streaming computation
* Scalable, fault-tolerant data processing
* Seamless integration with Spark

#### Main Page: [spark.apache.org/streaming](https://spark.apache.org/streaming)

#### GitHub:  [apache/spark](https://github.com/apache/spark)

#### Latest stable release: [2.4.3](https://spark.apache.org/downloads.html)

#### Community involvement: [Contributors](https://github.com/apache/spark/graphs/contributors)

* **Used by:** 227
* **Watching:** 2 135
* **Stars:** 23 203
* **Forks:** 19 909
* **Topic: `spark`:** 3 921

#### How to run

* Locally: `sbt run`

or 

* Download Spark
* Assemble JAR file: `sbt clean assembly`
* Execute:

```bash
./bin/spark-submit --class StreamProcessor \
${PATH_TO_PROJECT}/spark/target/scala-2.12/spark-streaming-assembly-0.1.0-SNAPSHOT.jar
```

#### ToDo:
* Add unit-tests