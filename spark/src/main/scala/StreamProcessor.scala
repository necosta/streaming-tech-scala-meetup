import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object StreamProcessor {

  lazy val APP_NAME = "StreamProcessor"

  lazy val spark = {
    val session = SparkSession
      .builder()
      .appName(APP_NAME)
      .master("local[*]")
      .config("spark.sql.streaming.checkpointLocation", "/tmp/spark/checkpoint")
    session.getOrCreate()
  }

  def main(args: Array[String]): Unit = {

    val sourceDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "SourceTopic")
      .load()

    import spark.implicits._

    val transformedDF = sourceDF
      .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .as[(String, String)]

    val outputDF = transformedDF
      .withColumn("prefix", lit("spark-streaming-transformation: "))
      .withColumn("value", concat(col("prefix"), col("value")))

    val query = outputDF.writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("topic", "SinkTopic")
      .start()

    query.awaitTermination()
  }
}
