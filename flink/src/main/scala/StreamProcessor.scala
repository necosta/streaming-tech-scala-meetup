import java.util.Properties

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka._

object StreamProcessor {

  private val TARGET_KAFKA_BROKER = "localhost"
  private val PREFIX = "flink-transformation"
  private val THRESHOLD_X = 0.1
  private val THRESHOLD_Y = 0.05

  def main(args: Array[String]): Unit = {

    implicit val typeInfo: TypeInformation[String] =
      TypeInformation.of(classOf[String])

    val env = StreamExecutionEnvironment.createLocalEnvironment()

    val properties = new Properties()
    properties.put("zookeeper.connect", "localhost:2181")
    properties.put("bootstrap.servers", s"$TARGET_KAFKA_BROKER:9092")

    env
      .addSource(
        new FlinkKafkaConsumer[String](
          "SourceTopic",
          KafkaStringSchema,
          properties
        ))
      .map(transformSimpleStream)
      .addSink(
        new FlinkKafkaProducer[String](
          "SinkTopic",
          new KafkaStringSerializationSchema("SinkTopic"),
          properties,
          FlinkKafkaProducer.Semantic.EXACTLY_ONCE))

    env.execute()
  }

  private def transformSimpleStream: String => String = { s =>
    s"$PREFIX: $s"
  }

  private def transformTruckStream: String => String = { s =>
    {
      val values = s
        .dropRight(1)
        .drop(1)
        .replace(")", "")
        .replace("(", "")
        .split(",")
        .dropRight(1)
        .map(_.toDouble)
        .toList

      values match {
        case v :: _ :: _ :: _ if v - 0.05 > THRESHOLD_X =>
          s"$PREFIX: REVERSE ${repeat(v)}"
        case v :: _ :: _ :: _ if v - 0.05 < -THRESHOLD_X =>
          s"$PREFIX: SPEED ${repeat(v)}"
        case _ :: v :: _ :: _ if v - 0.02 > THRESHOLD_Y =>
          s"$PREFIX: RIGHT ${repeat(v)}"
        case _ :: v :: _ :: _ if v - 0.02 < -THRESHOLD_Y =>
          s"$PREFIX: LEFT ${repeat(v)}"
        case _ => s"$PREFIX:"
      }
    }
  }

  private def repeat(v: Double) = {
    val n = Math.round(v * 500).toInt
    List.fill(n)('#').mkString
  }
}
