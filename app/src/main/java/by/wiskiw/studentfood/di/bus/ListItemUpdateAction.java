package by.wiskiw.studentfood.di.bus;

public class ListItemUpdateAction {

    public enum Type {
        UPDATE,
        REMOVED,
        ADDED
    }

    private int listPos;
    private Type type;

    public ListItemUpdateAction(int listPos, Type type) {
        this.listPos = listPos;
        this.type = type;
    }

    public int getListPos() {
        return listPos;
    }

    public Type getType() {
        return type;
    }
}
