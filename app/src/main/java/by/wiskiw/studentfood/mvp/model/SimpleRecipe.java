package by.wiskiw.studentfood.mvp.model;

import android.support.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleRecipe {

    private int id;

    private String title = "";
    private String description = "";
    private List<CookStep> steps = new ArrayList<>();
    private Set<RecipeCategory> categories = new HashSet<>();

    @Nullable
    private File headerImageFile;


    public SimpleRecipe(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    @Nullable
    public File getHeaderImageFile() {
        return headerImageFile;
    }

    public void setHeaderImageFile(@Nullable File headerImageFile) {
        this.headerImageFile = headerImageFile;
    }

    public List<CookStep> getSteps() {
        return steps;
    }

    public void addStep(CookStep cookStep) {
        steps.add(cookStep);
    }

    public void addCategory(RecipeCategory recipeCategory) {
        categories.add(recipeCategory);
    }

    public boolean isIt(RecipeCategory recipeCategory) {
        return categories.contains(recipeCategory);
    }

    public Set<RecipeCategory> getCategories() {
        return categories;
    }
}
