import java.util.Properties

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka._

object StreamProcessor {
  def main(args: Array[String]): Unit = {

    implicit val typeInfo: TypeInformation[String] =
      TypeInformation.of(classOf[String])

    val env = StreamExecutionEnvironment.createLocalEnvironment()

    val properties = new Properties()
    properties.put("zookeeper.connect", "localhost:2181")
    properties.put("bootstrap.servers", "localhost:9092")

    env
      .addSource(
        new FlinkKafkaConsumer[String](
          "SourceTopic",
          KafkaStringSchema,
          properties
        ))
      .map(word => s"flink-transformation: $word")
      .addSink(
        new FlinkKafkaProducer[String](
          "SinkTopic",
          new KafkaStringSerializationSchema("SinkTopic"),
          properties,
          FlinkKafkaProducer.Semantic.EXACTLY_ONCE))

    env.execute()
  }
}
