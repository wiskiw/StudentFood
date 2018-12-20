package by.wiskiw.studentfood.di.bus;

public class RecipeUpdateAction {

    public enum Type {
        UPDATE,
        REMOVED,
        ADDED
    }

    private int listPos;
    private Type type;

    public RecipeUpdateAction() {
    }

    public RecipeUpdateAction(Type type) {
        this.type = type;
    }

    public RecipeUpdateAction(int listPos, Type type) {
        this(type);
        this.listPos = listPos;
    }

    public int getListPos() {
        return listPos;
    }

    public Type getType() {
        return type;
    }

    public void setListPos(int listPos) {
        this.listPos = listPos;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RecipeUpdateAction{" +
                "listPos=" + listPos +
                ", type=" + type +
                '}';
    }
}
