package by.wiskiw.studentfood.mvp.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import by.wiskiw.studentfood.utils.diff.util.DiffUtilItem;

public class SimpleRecipe implements DiffUtilItem {

    private static final int NULL_ID = -1;

    private int id;

    private String title = "";
    private String description = "";
    private List<CookStep> steps = new ArrayList<>();
    private Set<RecipeCategory> categories = new HashSet<>();

    private boolean mine = false;
    private boolean favorite = false;


    @NonNull
    private String headerImageFileName = "";


    public SimpleRecipe(int id) {
        this.id = id;
    }

    public static SimpleRecipe nullRecipe() {
        return new SimpleRecipe(NULL_ID);
    }

    public SimpleRecipe(SimpleRecipe other) {
        this.id = other.id;
        this.title = other.title;
        this.description = other.description;
        this.steps = other.steps;
        this.categories = other.categories;
        this.mine = other.mine;
        this.favorite = other.favorite;
        this.headerImageFileName = other.headerImageFileName;
    }

    public int getId() {
        return id;
    }

    public boolean isNull() {
        return id == NULL_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getHeaderImageFileName() {
        return headerImageFileName;
    }

    public void setHeaderImageFileName(@NonNull String headerImageFileName) {
        this.headerImageFileName = headerImageFileName;
    }

    public List<CookStep> getSteps() {
        return steps;
    }

    public void addStep(CookStep cookStep) {
        steps.add(cookStep);
    }

    public void addCategory(RecipeCategory category) {
        categories.add(category);
    }

    public boolean isIt(RecipeCategory category) {
        return categories.contains(category);
    }

    public Long getCookTime() {
        long time = 0L;
        for (CookStep step : steps) {
            time += step.getTime();
        }
        return time;
    }

    public String getCookTimeString() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(getCookTime());
        return calendar.get(GregorianCalendar.MINUTE) + " min";
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        String cookSteps = steps.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return "SimpleRecipe (" + id + ") {" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", steps=" + cookSteps +
                ", mine=" + mine +
                ", favorite=" + favorite +
                ", headerImageFileName=" + headerImageFileName +
                '}';
    }
}
