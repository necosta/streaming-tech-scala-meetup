import java.util
import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder, StreamsConfig}

object StreamProcessor {
  private val config = {
    val properties = new Properties()
    properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-application")
    properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
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
      sourceStream.flatMapValues(v =>
        util.Arrays.asList(s"kafka-streams-transformation: $v"))
    transformedStream.to("SinkTopic")
    val streams = new KafkaStreams(builder.build(), config)
    streams.start()
  }
}
