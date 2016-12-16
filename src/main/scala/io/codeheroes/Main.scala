package io.codeheroes

import ch.qos.logback.classic.LoggerContext
import io.codeheroes.akka.http.lb.Endpoint
import org.slf4j.LoggerFactory

object Main extends App {

  implicit val loggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]

  val serviceDetails = ServiceDetails("hostname", "host", "service", "version", "task", "security")
  val layout = new JSONLayout(serviceDetails)
  val kafkaAppenderConfiguration = KafkaAppenderConfiguration(layout, serviceDetails)
  val kafkaAppender = new KafkaAppenderBuilder().build(List(Endpoint("localhost", 9092)), kafkaAppenderConfiguration)

  val logger = new HeroLogger(List(kafkaAppender))

  logger.start()
  loggerContext.start()

  Tester.test()

}
