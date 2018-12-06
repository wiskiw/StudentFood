package by.wiskiw.studentfood.mvp.view.list;

import by.wiskiw.studentfood.data.db.repository.StaticRecipeRepositoryKt;
import by.wiskiw.studentfood.mvp.model.RecipeCategory;

public interface StaticCategoryListView extends CategoryListView {

    StaticRecipeRepositoryKt getStaticRecipeRep();

    RecipeCategory getRecipeCategory();

}
