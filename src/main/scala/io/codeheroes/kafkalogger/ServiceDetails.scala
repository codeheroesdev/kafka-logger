package io.codeheroes.kafkalogger

final case class ServiceDetails(hostname: String,
                                host: String,
                                service: String,
                                version: String,
                                task: Option[String])

