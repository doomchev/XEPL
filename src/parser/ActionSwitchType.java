package parser;

import java.util.LinkedList;
import static parser.Base.currentScope;
import static parser.Base.log;

public class ActionSwitchType extends ActionSwitch {
  private static class Entry {
    private final Category category;
    private final Action action;

    public Entry(Category category, Action action) {
      this.category = category;
      this.action = action;
    }
  }
  
  private final LinkedList<Entry> entries = new LinkedList<>();
  public Action defaultAction;
  private final int index;

  public ActionSwitchType(int index) {
    this.index = index;
  }

  @Override
  public void setStringAction(String token, Action action) {
    parsingCodeError("String key is not allowed");
  }

  @Override
  public void setOtherAction(Action action) {
    defaultAction = action;
  }

  @Override
  public void setCategoryAction(Category category, Action action) {
    entries.add(new Entry(category, action));
  }

  @Override
  public Action execute() {
    Category type = currentScope.variables[index].type;
    for(Entry entry : entries) {
      if(entry.category == type) {
        if(log) System.out.println(" SWITCH TO " + type.name);
        return entry.action;
      }
    }
    return defaultAction;
  }

}