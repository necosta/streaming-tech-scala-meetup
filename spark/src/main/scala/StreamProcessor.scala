import org.apache.spark.sql.{Column, SparkSession}
import org.apache.spark.sql.functions._

object StreamProcessor {

  private val TARGET_KAFKA_BROKER =
    "kafka-broker-0-scala-meetup.westeurope.cloudapp.azure.com"
  private val PREFIX = "spark-streaming-transformation"
  private val THRESHOLD_X = 0.1
  private val THRESHOLD_Y = 0.05

  lazy val spark = {
    val session = SparkSession
      .builder()
      .appName("StreamProcessor")
      .master("local[*]")
      .config("spark.sql.streaming.checkpointLocation", "/tmp/spark/checkpoint")
    session.getOrCreate()
  }

  def main(args: Array[String]): Unit = {

    val sourceDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", s"$TARGET_KAFKA_BROKER:9092")
      .option("subscribe", "SourceTopic")
      .load()

    import spark.implicits._

    val transformedDF = sourceDF
      .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .as[(String, String)]

    val outputDF = transformedDF
      .withColumn("value", transformTruckStream)

    val query = outputDF.writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", s"$TARGET_KAFKA_BROKER:9092")
      .option("topic", "SinkTopic")
      .start()

    query.awaitTermination()
  }

  private def transformSimpleStream: Column = {
    concat(lit(s"$PREFIX: "), col("value"))
  }

  private def transformTruckStream: Column = {
    val udfFunc = udf(transformTruckStreamInternal _)
    udfFunc(col("value"))
  }

  private def transformTruckStreamInternal(s: String): String = {
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

  private def repeat(v: Double) = {
    val n = Math.round(v * 500).toInt
    List.fill(n)('#').mkString
  }
}
