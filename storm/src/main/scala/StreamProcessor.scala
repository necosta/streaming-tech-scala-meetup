import java.util.Properties

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization._
import org.apache.storm.kafka.bolt.KafkaBolt
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector
import org.apache.storm.kafka.spout._
import org.apache.storm.{Config, LocalCluster}
import org.apache.storm.topology.TopologyBuilder
import org.apache.storm.utils.Utils

import scala.collection.JavaConverters._

object StreamProcessor {

  private val TARGET_KAFKA_BROKER =
    "kafka-broker-0-scala-meetup.westeurope.cloudapp.azure.com"

  def main(args: Array[String]): Unit = {
    println("Started processing")

    val builder = new TopologyBuilder()

    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
              s"$TARGET_KAFKA_BROKER:9092")
    props.put("key.serializer",
              "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer",
              "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "default")

    val spoutConfig = KafkaSpoutConfig
      .builder(s"$TARGET_KAFKA_BROKER:9092", "SourceTopic")
      .setRecordTranslator(new DefaultRecordTranslator())
      .setProp(props.asScala.toMap[String, Object].asJava)
      .setProp(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
               classOf[StringDeserializer])
      .setProp(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
               classOf[StringDeserializer])
      .build()

    val kafkaSpout = new KafkaSpout(spoutConfig)
    builder.setSpout("ReadFromKafka", kafkaSpout).setNumTasks(3)

    builder
      .setBolt("transformMsg", new TruckBolt(), 3)
      .localOrShuffleGrouping("ReadFromKafka")

    val bolt = new KafkaBolt()
      .withTupleToKafkaMapper(
        new FieldNameBasedTupleToKafkaMapper("key", "transformed"))
      .withProducerProperties(props)
      .withTopicSelector(new DefaultTopicSelector("SinkTopic"))

    builder
      .setBolt("forwardToKafka", bolt, 3)
      .localOrShuffleGrouping("transformMsg")

    val conf = new Config()
    conf.setDebug(false)
    conf.setNumEventLoggers(1)
    conf.setMaxSpoutPending(200)
    conf.setMessageTimeoutSecs(60 * 10)
    conf.setNumWorkers(1)

    val cluster = new LocalCluster()

    cluster.submitTopology("KafkaTest", conf, builder.createTopology())

    // ToDo: Review the need to have this...
    Utils.sleep(1000 * 60 * 60)

    cluster.shutdown()
  }
}
