package systems.miso
package core.stage

object Pipeline {
  def apply[I, O](stages: PipeStage[I, I]*): Pipeline[I, O] = new Pipeline(stages.toList)
}

class Pipeline[I, O](stages: List[PipeStage[I, I]]) {
  def execute(input: I): I = {
    stages.foldLeft(input) {
      (r, s) =>
        s.execute(r)
    }
  }
}
