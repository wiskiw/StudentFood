package by.wiskiw.studentfood.data.db.dao.dummy;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;

import java.util.Set;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;

public class DummyRecipeReader {

    private Context context;

    public DummyRecipeReader(Context context) {
        this.context = context;
    }

    public Set<SimpleRecipe> read() {
        XmlPullParser xpp = context.getResources().getXml(R.xml.dammy_recipes);
        DummyRecipeParser parser = new DummyRecipeParser();
        parser.parse(xpp);
        return parser.getRecipeSet();
    }

}
