package systems.miso
package core.stage

trait PipeStage[I, O] {
  def execute(input: I): O
}
