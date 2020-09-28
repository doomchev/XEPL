package ast.nativ;

import ast.Entity;
import static ast.Entity.addCommand;
import ast.FunctionCall;
import ast.NativeFunction;
import ast.Scope;
import java.util.LinkedList;
import vm.Command;
import vm.IfFalseGoto;
import vm.VMBase;
import vm.VMGoto;

public class IfOp extends NativeFunction {
  public IfOp() {
    super("ifOp");
  }
  
  @Override
  public int getPriority() {
    return 4;
  }
  
  @Override
  public Entity setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    return getPriorityType(parameters.get(1), parameters.getLast(), ANY);
  }

  @Override
  public void toByteCode(FunctionCall call) {
    call.parameters.getFirst().toByteCode();
    Command ifFalse = new IfFalseGoto();
    addCommand(ifFalse);
    Entity param0 = call.parameters.get(1);
    param0.toByteCode();
    conversion(param0.getType(), type);
    Command thenGoto = new VMGoto();
    addCommand(thenGoto);
    VMBase.gotos.add(ifFalse);
    VMBase.currentCommand = null;
    Entity param1 = call.parameters.getLast();
    param1.toByteCode();
    conversion(param1.getType(), type);
    VMBase.gotos.add(thenGoto);
  }

  @Override
  public String getActionName() {
    return "subtracted";
  }
}
