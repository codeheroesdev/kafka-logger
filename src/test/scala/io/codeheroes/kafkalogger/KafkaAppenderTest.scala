package io.codeheroes.kafkalogger

import com.typesafe.scalalogging.StrictLogging
import net.manub.embeddedkafka.ConsumerExtensions._
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

class KafkaAppenderTest extends TestSpec with StrictLogging {

  val consumer = new KafkaConsumer[String, String](consumerProps, new StringDeserializer, new StringDeserializer)

  "The logger" should "publish a message to a kafka topic" in {

    logger.info("info message")

    val json = consumer.consumeLazily("logs").take(1).map(_._2.asJson).head
    (json \ "hostname").extract[String] shouldBe "hostnameTest"
    (json \ "host").extract[String] shouldBe "hostTest"
    (json \ "service").extract[String] shouldBe "serviceTest"
    (json \ "version").extract[String] shouldBe "versionTest"
    (json \ "message").extract[String] shouldBe "info message"
    (json \ "time").extract[String] should not be null
    (json \ "thread").extract[String] should not be null
    (json \ "task").extract[String] shouldBe empty
    (json \ "stackTrace").extract[String] shouldBe empty
    (json \ "level").extract[String] shouldBe "INFO"

  }

  it should "publish multiple messages of different level to a kafka topic" in {

    logger.info("info message")
    logger.debug("debug message")
    logger.error("error message")

    val result = consumer.consumeLazily("logs").take(3).toList

    result.map(_._1).forall(_ == "serviceTest:hostnameTest@hostTest") shouldBe true
    result.map(x => (x._2.asJson \ "level").extract[String]) shouldBe List("INFO", "DEBUG", "ERROR")

  }

  it should "return a stack trace if the log contained an exception" in {

    logger.error("error message", new IllegalStateException("hello"))

    val json = consumer.consumeLazily("logs").map(_._2.asJson).head
    (json \ "stackTrace").extract[String] should not be empty
    (json \ "stackTrace").extract[String] should include("IllegalStateException")
    (json \ "stackTrace").extract[String] should include("hello")

  }

}
