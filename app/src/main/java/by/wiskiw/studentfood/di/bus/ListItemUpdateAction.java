package by.wiskiw.studentfood.di.bus;

public class ListItemUpdateAction {

    public enum Type {
        UPDATE,
        REMOVED,
        ADDED
    }

    private int listPos;
    private Type type;

    public ListItemUpdateAction(Type type) {
        this.type = type;
    }

    public ListItemUpdateAction(int listPos, Type type) {
        this(type);
        this.listPos = listPos;
    }

    public int getListPos() {
        return listPos;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ListItemUpdateAction{" +
                "listPos=" + listPos +
                ", type=" + type +
                '}';
    }
}
