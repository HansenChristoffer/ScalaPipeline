package systems.miso
package core

import org.apache.logging.log4j.scala.Logging

import java.net.{ServerSocket, Socket}
import scala.util.Using

object Server {
  def apply(): Server = new Server()
}

class Server extends Runnable with Logging with AutoCloseable {
  private val MAX_BUFFER_SIZE: Int = 512
  private val serverSocket: ServerSocket = new ServerSocket(9999)
  private val connectionHandler: ConnectionHandler = ConnectionHandler()

  private var shouldRun: Boolean = true

  override def run(): Unit = {
    logger.info(s"Server is up and running on the port, ${serverSocket.getLocalPort}")

    val connectionHandlerThread: Thread = new Thread(connectionHandler)
    connectionHandlerThread.start()
    logger.info(s"Told ConnectionHandler to start!")

    Using(serverSocket) { _ =>
      while (shouldRun) {
          val socket: Socket = serverSocket.accept()
          socket.setKeepAlive(true)
          socket.setReceiveBufferSize(MAX_BUFFER_SIZE)
          socket.setSendBufferSize(MAX_BUFFER_SIZE)
          connectionHandler.addToQueue(socket)
        }
    }
  }

  override def close(): Unit = {
    logger.warn(s"Server has been told to shutdown!")
    shouldRun = false
    connectionHandler.close()

    if (!serverSocket.isClosed) {
      serverSocket.close()
    }
  }
}
