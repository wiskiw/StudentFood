package by.wiskiw.studentfood.data.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Log;

import com.esafirm.imagepicker.model.Image;

import java.io.File;
import java.io.IOException;

import by.wiskiw.studentfood.data.FoodFileManager;
import by.wiskiw.studentfood.di.FoodApp;

public class RecipeImageFileManager {

    private static final String RECIPE_IMAGE_DIR = "recipe-image";
    private Context context;

    public RecipeImageFileManager(Context context) {
        this.context = context;
    }

    private File getDestinationDir() {
        File dir = new File(context.getFilesDir(), RECIPE_IMAGE_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    @Nullable
    public File saveImageToAppFolder(Image image) {
        File srcImage = new File(image.getPath());
        //Log.d(FoodApp.LOG_TAG, "srcImage: " + srcImage.toString());

        File finalPath = null;
        try {
            //Log.d(FoodApp.LOG_TAG, "getDestinationDir(): " + getDestinationDir().toString());
            FoodFileManager.copyOrMoveFile(srcImage, getDestinationDir(), true);

            File fileToRename = new File(getDestinationDir().getPath(), image.getName());

            //Log.d(FoodApp.LOG_TAG, "rename: " + fileToRename.toString());
            String finalFileName = String.valueOf(image.getId()) + ".jpg";
            finalPath = new File(getDestinationDir().getPath(), finalFileName);
            FoodFileManager.renameFile(fileToRename, finalPath);

            //Log.d(FoodApp.LOG_TAG, "finalPath : " + finalPath.toString());
        } catch (IOException e) {
            Log.e(FoodApp.LOG_TAG, "saveImageToAppFolder error: ", e);
        }
        return finalPath;


    }

    @Nullable
    public Bitmap getImageBitmapByName(String imageFileName) {
        File imgFile = new File(getDestinationDir(), imageFileName);
        if (imgFile.exists()) {
            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        } else {
            return null;
        }
    }

}
