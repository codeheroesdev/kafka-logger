package io.codeheroes.kafkalogger

import java.nio.charset.StandardCharsets

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Layout
import com.github.danielwegener.logback.kafka.KafkaAppender
import com.github.danielwegener.logback.kafka.delivery.{AsynchronousDeliveryStrategy, DeliveryStrategy}
import com.github.danielwegener.logback.kafka.encoding.{KafkaMessageEncoder, LayoutKafkaMessageEncoder}
import com.github.danielwegener.logback.kafka.keying.KeyingStrategy

case class KafkaAppenderConfiguration(layout: Layout[ILoggingEvent], serviceDetails: ServiceDetails, appenderName: String = "kafka-appender", kafkaTopic: String = "logs")

class KafkaAppenderBuilder(endpoints: List[(String, Int)], configuration: KafkaAppenderConfiguration)(implicit loggerContext: LoggerContext) {

  def build() = {
    val keyingStrategy = new ServiceOrientedKeyingStrategy(configuration.serviceDetails.service, configuration.serviceDetails.hostname, configuration.serviceDetails.host)
    val deliveryStrategy = new AsynchronousDeliveryStrategy()
    val layout = configuration.layout
    layout.start()
    val encoder = setupEncoder(layout)
    setupKafkaAppender(configuration.appenderName, configuration.kafkaTopic, endpoints, encoder, keyingStrategy, deliveryStrategy)
  }

  private def setupEncoder(layout: Layout[ILoggingEvent]) = {
    val encoder = new LayoutKafkaMessageEncoder[ILoggingEvent](layout, StandardCharsets.UTF_8)
    encoder.setContext(loggerContext)
    encoder.start()
    encoder
  }

  private def setupKafkaAppender(name: String, topic: String, endpoints: List[(String, Int)], encoder: KafkaMessageEncoder[ILoggingEvent], keyingStrategy: KeyingStrategy[ILoggingEvent], deliveryStrategy: DeliveryStrategy) = {
    val kafkaAppender = new KafkaAppender[ILoggingEvent]()
    kafkaAppender.setContext(loggerContext)
    kafkaAppender.setName(name)
    kafkaAppender.setEncoder(encoder)
    kafkaAppender.addProducerConfig(s"bootstrap.servers=${endpoints.map { case (host, port) => s"$host:$port" }.mkString(",")}")
    kafkaAppender.setKeyingStrategy(keyingStrategy)
    kafkaAppender.setDeliveryStrategy(deliveryStrategy)
    kafkaAppender.setTopic(topic)
    kafkaAppender.start()
    kafkaAppender
  }

}


