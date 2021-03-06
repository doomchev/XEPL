
import base.Base;
import base.Module;
import base.Processor;
import parser.Rules;
import vm.VMBase;

public class Guess extends Base {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.epc");
    Module module = Module.read("examples/guess.es", rules);
    Processor.process();
    VMBase.prepare(true);
  }
}
