package systems.miso
package core

import org.apache.logging.log4j.scala.Logging
import core.stage.{AnnounceStage, CryptographyStage, PipeStage, Pipeline, ReceiverStage}

import java.net.Socket
import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}
import scala.util.Using

object ConnectionHandler {
  def apply(): ConnectionHandler = new ConnectionHandler()
}

class ConnectionHandler() extends Runnable with Logging with AutoCloseable {
  private val MAX_PACKET_SIZE: Int = 512
  private val pipeline: Pipeline[Array[Byte], Array[Byte]] =
    Pipeline[Array[Byte], Array[Byte]](
      ReceiverStage(): PipeStage[Array[Byte], Array[Byte]],
      CryptographyStage(): PipeStage[Array[Byte], Array[Byte]],
      AnnounceStage(): PipeStage[Array[Byte], Array[Byte]]
    )
  private val queue: BlockingQueue[Socket] = new LinkedBlockingQueue[Socket]()

  private var shouldRun: Boolean = true

  override def run(): Unit = {
    logger.info(s"ConnectionHandler has been started!")

    while (shouldRun) {
      val socket: Socket = pollQueue()

      if (socket != null) {
        handleSocket(socket)
      }
    }
  }

  private def handleSocket(socket: Socket): Unit = {
    Using(socket) { _ =>
      logger.info(s"Handling socket from [${socket.getInetAddress}]")
      try {
        val buffer: Array[Byte] = new Array[Byte](MAX_PACKET_SIZE)
        val bytesRead: Int = socket.getInputStream.read(buffer)

        // If bytesRead is -1 then that means we've not read any bytes
        if (bytesRead != -1) {
          // Process the buffer within this if-statement
          val result = pipeline.execute(buffer.slice(0, bytesRead))

          if (result.nonEmpty) {
            logger.info(s"Pipeline end result: ${result.mkString("Array(", ", ", ")")}")
          } else {
            logger.warn(s"Nothing has been read from inbound buffer!")
          }
        } else {
          logger.warn(s"Nothing has been read from inbound buffer!")
        }
      } catch {
        case e: Exception =>
          logger.error(s"Got an error while trying to read from inbound buffer!\n$e")
      }
    }
  }

  def addToQueue(socket: Socket): Unit = {
    if (socket != null) {
      queue.add(socket)
      logger.info(s"Added a socket from [${socket.getInetAddress}] to the queue")
    }
  }

  private def pollQueue(): Socket = {
    logger.info(s"Polling queue for a socket!")
    queue.take()
  }

  override def close(): Unit = {
    logger.warn(s"ConnectionHandler has been told to shutdown!")
    shouldRun = false

    if (!queue.isEmpty) {
      queue.forEach(s  => s.close())
      queue.clear()
    }
  }
}
