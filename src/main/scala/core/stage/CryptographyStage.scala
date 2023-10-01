package systems.miso
package core.stage

import org.apache.logging.log4j.scala.Logging

object CryptographyStage {
  def apply(): CryptographyStage = new CryptographyStage()
}

class CryptographyStage extends PipeStage[Array[Byte], Array[Byte]] with Logging {
  override def execute(input: Array[Byte]): Array[Byte] = {
    logger.info(s"CryptographyStage executed with the following byte array: ${input.mkString("Array(", ", ", ")")}");
    input
  }
}
