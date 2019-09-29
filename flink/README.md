# Apache Flink

Features:

* A streaming-first runtime that supports both batch processing and data streaming programs
* A runtime that supports very high throughput and low event latency at the same time
* Support for event time and out-of-order processing in the DataStream API, based on the Dataflow Model
* Flexible windowing (time, count, sessions, custom triggers) across different time semantics (event time, processing time)
* Fault-tolerance with exactly-once processing guarantees
* Natural back-pressure in streaming programs
* Libraries for Graph processing (batch), Machine Learning (batch), and Complex Event Processing (streaming)
* Built-in support for iterative programs (BSP) in the DataSet (batch) API
* Custom memory management for efficient and robust switching between in-memory and out-of-core data processing algorithms

#### Main Page: [spark.apache.org/streaming](https://flink.apache.org)

#### GitHub:  [apache/flink](https://github.com/apache/flink)

#### Latest stable release: [1.9.0](https://flink.apache.org/downloads.html)

#### Community involvement: [Contributors](https://github.com/apache/flink/graphs/contributors)

* **Used by:** 99
* **Watching:** 804
* **Stars:** 10 496
* **Forks:** 5 621
* **Topic: `flink`:** 339

#### How to run

* Locally: `sbt run`

or 

* Download Flink
* Assemble JAR file: `sbt clean assembly`
* Execute:

```bash
./bin/flink run ${PATH_TO_PROJECT}/flink/target/scala-2.12/storm-assembly-0.1.0-SNAPSHOT.jar
```

#### ToDo:
* Add unit-tests