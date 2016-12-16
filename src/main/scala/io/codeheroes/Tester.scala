package io.codeheroes

import com.typesafe.scalalogging.StrictLogging

object Tester extends StrictLogging {

  def test() = {

    logger.info("info-mode")
    logger.debug("debug-mode")
    logger.warn("warn-mode")
    logger.error("error-mode")

    (1 to 50).foreach { _ =>
      Thread.sleep(1000)
      logger.error("A Fake exception occurred", new IllegalStateException("I'm an IllegalStateException", new NullPointerException("Hello null!")))
    }


  }


}
