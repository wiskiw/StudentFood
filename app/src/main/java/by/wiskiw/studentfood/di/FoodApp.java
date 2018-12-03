package by.wiskiw.studentfood.di;

import android.app.Application;

import io.paperdb.Paper;

public class FoodApp extends Application {

    public static final String LOG_TAG = "StudentApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
    }
}
