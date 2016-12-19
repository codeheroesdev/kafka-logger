package io.codeheroes.kafkalogger

import java.util.Properties

import akka.serialization.Serialization
import ch.qos.logback.classic.LoggerContext
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import net.manub.embeddedkafka.EmbeddedKafka
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods._
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import org.slf4j.LoggerFactory

trait TestSpec extends FlatSpec with Matchers with Json4sSupport with EmbeddedKafka with BeforeAndAfterAll {

  protected implicit val formats = DefaultFormats
  protected implicit val serialization = Serialization
  private implicit val loggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]

  override protected def beforeAll() = {
    EmbeddedKafka.start()
    setupLogger()
  }

  private def setupLogger(): Unit = {
    val serviceDetails = ServiceDetails("hostnameTest", "hostTest", "serviceTest", "versionTest")
    val layout = new JSONLayout(serviceDetails)
    val kafkaAppenderConfiguration = KafkaAppenderConfiguration(layout, serviceDetails, kafkaTopic = "logs")
    val kafkaAppender = new KafkaAppenderBuilder(List(("localhost", 6001)), kafkaAppenderConfiguration).build()
    val logger = HeroLogger.single(kafkaAppender)
    logger.start()
  }

  override protected def afterAll() = EmbeddedKafka.stop()

  protected implicit class JsonString(string: String) {
    def asJson = parse(string)
  }

  lazy val consumerProps: Properties = {
    val props = new Properties()
    props.put("group.id", "test")
    props.put("bootstrap.servers", "localhost:6001")
    props.put("auto.offset.reset", "earliest")
    props
  }

}
