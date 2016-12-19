package io.codeheroes.kafkalogger

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender
import org.slf4j.Logger


class HeroLogger(
                  appenders: List[Appender[ILoggingEvent]],
                  additive: Boolean = false,
                  removeDefaultAppenders: Boolean = false
                )(implicit loggerContext: LoggerContext) {

  def start() = {
    val root = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME)
    if (removeDefaultAppenders) root.detachAndStopAllAppenders()
    root.setAdditive(additive)
    appenders.foreach(root.addAppender)
    loggerContext.start()
    root
  }

}

object HeroLogger {

  def single(appender: Appender[ILoggingEvent], additive: Boolean = false, removeDefaultAppenders: Boolean = false)(implicit loggerContext: LoggerContext) = {
    new HeroLogger(List(appender), additive, removeDefaultAppenders)
  }

  def withDefaultKafkaAppender(endpoints: List[(String, Int)], serviceDetails: ServiceDetails, kafkaTopic: String)(implicit loggerContext: LoggerContext) = {
    val layout = new JSONLayout(serviceDetails)
    val config = KafkaAppenderConfiguration(layout, serviceDetails, kafkaTopic = kafkaTopic)
    new HeroLogger(List(new KafkaAppenderBuilder(endpoints, config).build()))
  }

}
