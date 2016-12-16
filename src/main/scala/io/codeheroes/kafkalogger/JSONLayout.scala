package io.codeheroes.kafkalogger

import ch.qos.logback.classic.pattern.{ExtendedThrowableProxyConverter, ThrowableHandlingConverter}
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.LayoutBase
import org.json4s.DefaultFormats


class JSONLayout(serviceDetails: ServiceDetails, stackTraceConverter: ThrowableHandlingConverter = new ExtendedThrowableProxyConverter) extends LayoutBase[ILoggingEvent] {

  import org.json4s.native.Serialization._

  private implicit val formats = DefaultFormats

  {
    stackTraceConverter.start()
  }

  override def doLayout(event: ILoggingEvent) = {
    val eventInMap = Map(
      "hostname" -> serviceDetails.hostname,
      "host" -> serviceDetails.host,
      "service" -> serviceDetails.service,
      "version" -> serviceDetails.version,
      "thread" -> event.getThreadName,
      "level" -> event.getLevel.toString,
      "task" -> serviceDetails.task.getOrElse(""),
      "time" -> event.getTimeStamp,
      "message" -> event.getFormattedMessage,
      "stackTrace" -> stackTraceConverter.convert(event)
    )
    writePretty(eventInMap)
  }

  override def stop() = {
    super.stop()
    stackTraceConverter.stop()
  }

}
