import net.liftweb.json.DefaultFormats
import org.apache.flink.api.common.serialization._
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.java.typeutils.TypeExtractor
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema
import org.apache.kafka.clients.producer.ProducerRecord
import net.liftweb.json.Serialization.write

object KafkaStringSchema
    extends SerializationSchema[String]
    with DeserializationSchema[String] {

  override def serialize(t: String): Array[Byte] = t.getBytes("UTF-8")

  override def isEndOfStream(t: String): Boolean = false

  override def deserialize(bytes: Array[Byte]): String =
    new String(bytes, "UTF-8")

  override def getProducedType: TypeInformation[String] =
    TypeExtractor.getForClass(classOf[String])
}

class KafkaStringSerializationSchema(topic: String)
    extends KafkaSerializationSchema[String] {

  override def serialize(
      element: String,
      timestamp: java.lang.Long): ProducerRecord[Array[Byte], Array[Byte]] = {

    implicit val formats = DefaultFormats
    new ProducerRecord[Array[Byte], Array[Byte]](
      topic,
      write(element)
        .replace("\"", "") // Hack: Remove quotes properly
        .getBytes("UTF-8"))
  }
}
