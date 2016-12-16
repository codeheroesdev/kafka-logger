package io.codeheroes

import ch.qos.logback.classic.spi.ILoggingEvent
import com.github.danielwegener.logback.kafka.keying.KeyingStrategy

class ServiceOrientedKeyingStrategy(serviceName: String, hostname: String, host: String) extends KeyingStrategy[ILoggingEvent] {

  override def createKey(e: ILoggingEvent) = s"$serviceName:$hostname@$host".getBytes

}
