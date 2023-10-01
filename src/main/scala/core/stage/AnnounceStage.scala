package systems.miso
package core.stage

import org.apache.logging.log4j.scala.Logging

object AnnounceStage {
  def apply(): AnnounceStage = new AnnounceStage()
}

class AnnounceStage extends PipeStage[Array[Byte], Array[Byte]] with Logging {
  override def execute(input: Array[Byte]): Array[Byte] = {
    logger.info(s"AnnounceStage executed with the following byte array: ${input.mkString("Array(", ", ", ")")}");
    input
  }
}
