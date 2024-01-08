package cde_test

import chisel3._
import chisel3.stage.ChiselStage
import org.chipsalliance.cde.config.{Config, Field, Parameters}

case object AXISNumBeats extends Field[Int]
case object AXISBeatWidth extends Field[Int]

trait HasAXIStreamParameters {
  val p: Parameters
  val numBeats = p(AXISNumBeats)
  val beatWidth = p(AXISBeatWidth)
  val totalWidth = numBeats * beatWidth
  require(numBeats > 0)
  require(beatWidth > 0)
}

class AXIStream(implicit val p: Parameters) extends Bundle 
  with HasAXIStreamParameters {
  val data = Output(Vec(numBeats, UInt(beatWidth.W)))
  val ready = Input(Bool())
  val valid = Output(Bool())
  val last = Output(Bool())
}

class Passthrough(implicit p: Parameters) extends Module {
  val io = IO(new Bundle{
    val in = Flipped(new AXIStream())
    val out = new AXIStream()
  })

  io.out <> io.in
}



object Main extends App {
  val cfg = new Config((site, here, up) => {
    case AXISNumBeats => 4
    case AXISBeatWidth => 8
  })
  val verilog = (new ChiselStage).emitVerilog(new Passthrough()(cfg))
  println(verilog)
}
