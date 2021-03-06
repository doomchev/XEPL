package parser;

import base.Module;
import ast.Block;
import ast.Entity;
import ast.EntityStack;
import ast.FunctionCall;
import ast.ID;
import ast.NativeFunction;
import ast.Variable;

public class ActionCreate extends Action {
  private final EntityStack stack;
  private final NativeFunction function;
  private final ID type;

  public ActionCreate(EntityStack stack, ID type, NativeFunction function) {
    this.stack = stack;
    this.type = type;
    this.function = function;
  }
  
  @Override
  public Action execute() {
    if(type == Entity.classParameterID) {
      if(log) log("CREATE CLASS PARAMETER");
      ID id = EntityStack.id.pop();
      EntityStack.classStack.peek().parameters.add(new Variable(id));
    } else if(type == Entity.moduleID) {
      String name = EntityStack.id.pop().string;
      Module.current.modules.add(new Module(modulesPath + name + "/" + name
          + ".es"));
    } else if(stack.isStringBased()) {
      String string = prefix + text.substring(tokenStart, textPos);
      if(log) log("CREATE " + stack.name.string + "(" + string + ")");
      stack.push(stack.createFromString(string));
      prefix = "";
      tokenStart = textPos;
    } else if(type != null) {
      if(log) log("CREATE BLOCK " + type.string);
      EntityStack.block.push(new Block(type));
    } else if(function != null) {
      if(log) log("CREATE FUNCTION " + function.getName() + ")");
      stack.push(new FunctionCall(function));
    } else {
      currentAction = this;
      stack.push(stack.create());
      currentAction = null;
      if(log) log("CREATE " + stack.name.string + "(" + stack.peek().toString()
          + ")");
    }
    return nextAction;
  }

  @Override
  public String toString() {
    return stack.name.string + (type == null ? "" : "-" + type.string);
  }
}
