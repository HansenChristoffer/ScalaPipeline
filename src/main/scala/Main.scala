package systems.miso

import core.Server

import org.apache.logging.log4j.{LogManager, Logger}

object Main {
  def main(args: Array[String]): Unit = {
    val logger: Logger = LogManager.getFormatterLogger()
    val server: Server = Server()

    val serverThread: Thread = new Thread(server)
    serverThread.start()

    Runtime.getRuntime.addShutdownHook(new Thread {
      override def run(): Unit = {
        logger.warn("Received signal to shutdown...")
        server.close()
      }
    })
  }
}