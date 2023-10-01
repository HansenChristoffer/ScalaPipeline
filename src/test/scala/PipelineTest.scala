package systems.miso

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import core.stage.{AnnounceStage, CryptographyStage, PipeStage, Pipeline, ReceiverStage}

class PipelineTest extends AnyFlatSpec with should.Matchers {
  "A Pipeline" should "return the same value it got as argument" in {
    val pipeline: Pipeline[Array[Byte], Array[Byte]] = Pipeline[Array[Byte], Array[Byte]](
      ReceiverStage(): PipeStage[Array[Byte], Array[Byte]],
      CryptographyStage(): PipeStage[Array[Byte], Array[Byte]],
      AnnounceStage(): PipeStage[Array[Byte], Array[Byte]]
    )

    val expectedValue: Array[Byte] = Array(0x01, 0x03, 0x03, 0x07)
    val actualValue: Array[Byte] = pipeline.execute(expectedValue)

    actualValue shouldEqual(expectedValue)
  }
}
