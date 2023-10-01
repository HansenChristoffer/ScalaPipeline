package systems.miso
package core.stage

import org.apache.logging.log4j.scala.Logging

object ReceiverStage {
  def apply(): ReceiverStage = new ReceiverStage()
}

class ReceiverStage extends PipeStage[Array[Byte], Array[Byte]] with Logging {
  override def execute(input: Array[Byte]): Array[Byte] = {
    logger.info(s"ReceiverStage executed with the following byte array: ${input.mkString("Array(", ", ", ")")}");
    input
  }
}
