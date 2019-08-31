import java.util.Properties

import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.apache.kafka.streams.{StreamsBuilder, StreamsConfig, TopologyTestDriver}
import org.scalatest.{FlatSpec, Matchers}
import org.apache.kafka.streams.processor.MockProcessorContext
import org.apache.kafka.streams.test.OutputVerifier
import org.apache.kafka.streams.processor.ProcessorSupplier

class StreamProcessorTest extends FlatSpec with Matchers {

  // ToDo: Finalize this test...
  it should "test stream transformations" ignore {
    val builder = new StreamsBuilder()
    val topology = builder.build()

    topology.addSource("sourceProcessor", "input-topic")
    topology.addProcessor("processor", ??? ,"sourceProcessor")
    topology.addSink("sinkProcessor", "output-topic", "processor")

    val props = new Properties()
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test")
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234")
    val testDriver = new TopologyTestDriver(topology, props)

    import org.apache.kafka.streams.test.ConsumerRecordFactory
    val factory = new ConsumerRecordFactory[String, String]("input-topic",
      new StringSerializer(), new StringSerializer())
    testDriver.pipeInput(factory.create("value1", 123))

    val outputRecord = testDriver.readOutput("output-topic", new StringDeserializer(), new StringDeserializer())

    OutputVerifier.compareKeyValue(outputRecord, "key", "value1")

    testDriver.close()
  }
}
