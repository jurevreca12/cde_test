package cde_test

import chisel3._
import chisel3.stage.ChiselStage

object Main extends App {
  val verilog = (new ChiselStage).emitVerilog(new Passthrough)
  println(verilog)
}

class AXIStream extends Bundle {
  val data = Output(UInt(32.W))
  val ready = Input(Bool())
  val valid = Output(Bool())
  val last = Output(Bool())
}


class Passthrough extends Module {
  val io = IO(new Bundle{
    val in = Flipped(new AXIStream())
    val out = new AXIStream()
  })

  io.out <> io.in
}
