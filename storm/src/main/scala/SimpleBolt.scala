import org.apache.storm.topology.{BasicOutputCollector, OutputFieldsDeclarer}
import org.apache.storm.topology.base.BaseBasicBolt
import org.apache.storm.tuple.{Fields, Tuple, Values}

class SimpleBolt extends BaseBasicBolt {

  def execute(tuple: Tuple, collector: BasicOutputCollector): Unit = {
    val input = tuple.getStringByField("value")
    collector.emit(new Values(s"storm-transformation: $input"))
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("transformed"))
  }
}
