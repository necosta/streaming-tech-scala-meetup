import java.util
import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder, StreamsConfig}

object StreamProcessor {

  private val TARGET_KAFKA_BROKER = "localhost"
  private val PREFIX = "kafka-streams-transformation"
  private val THRESHOLD_X = 0.1
  private val THRESHOLD_Y = 0.05

  private val config = {
    val properties = new Properties()
    properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-application")
    properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                   s"$TARGET_KAFKA_BROKER:9092")
    properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                   Serdes.String().getClass)
    properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                   Serdes.String().getClass)
    properties
  }

  def main(args: Array[String]): Unit = {
    val builder = new StreamsBuilder()
    val sourceStream = builder.stream[String, String]("SourceTopic")

    val transformedStream =
      sourceStream.flatMapValues(transformSimpleStream.apply(_))
    transformedStream.to("SinkTopic")
    val streams = new KafkaStreams(builder.build(), config)
    streams.start()
  }

  private def transformSimpleStream: String => util.List[String] = { s =>
    util.Arrays.asList(s"$PREFIX: $s")
  }

  private def transformTruckStream: String => util.List[String] = { s =>
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

      util.Arrays.asList(values match {
        case v :: _ :: _ :: _ if v - 0.05 > THRESHOLD_X =>
          s"$PREFIX: REVERSE ${repeat(v)}"
        case v :: _ :: _ :: _ if v - 0.05 < -THRESHOLD_X =>
          s"$PREFIX: SPEED ${repeat(v)}"
        case _ :: v :: _ :: _ if v - 0.02 > THRESHOLD_Y =>
          s"$PREFIX: RIGHT ${repeat(v)}"
        case _ :: v :: _ :: _ if v - 0.02 < -THRESHOLD_Y =>
          s"$PREFIX: LEFT ${repeat(v)}"
        case _ => s"$PREFIX:"
      })
    }
  }

  private def repeat(v: Double) = {
    val n = Math.round(v * 500).toInt
    List.fill(n)('#').mkString
  }
}
