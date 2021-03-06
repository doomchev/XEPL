package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import static ast.Entity.addCommand;
import ast.FunctionCall;
import ast.NativeFunction;
import vm.I64Subtract;

public class Subtraction extends NativeFunction {
  public Subtraction() {
    super("subtraction");
  }
  
  @Override
  public int getPriority() {
    return 13;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    return getPriorityType(param0, param1, NUMBER);
  }

  @Override
  public void functionToByteCode(FunctionCall call) {
    Entity type0 = call.getType();
    if(type0 == ClassEntity.i64Class) {
      addCommand(new I64Subtract());
    } else {
      throw new Error("Subtraction of " + type0.toString() + " is not implemented.");
    }
  }

  @Override
  public String getActionName() {
    return "subtracted";
  }
}
