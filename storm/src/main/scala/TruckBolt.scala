import org.apache.storm.topology.{BasicOutputCollector, OutputFieldsDeclarer}
import org.apache.storm.topology.base.BaseBasicBolt
import org.apache.storm.tuple.{Fields, Tuple, Values}

class TruckBolt extends BaseBasicBolt {

  private val PREFIX = "storm-transformation"
  private val THRESHOLD_X = 0.1
  private val THRESHOLD_Y = 0.05

  def execute(tuple: Tuple, collector: BasicOutputCollector): Unit = {
    val input = tuple.getStringByField("value")
    collector.emit(new Values(s"${transformTruckStream(input)}"))
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("transformed"))
  }

  private def transformTruckStream(s: String): String = {
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
