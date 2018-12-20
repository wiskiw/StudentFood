package by.wiskiw.studentfood.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FoodFileManager {

    public static void copyOrMoveFile(File file, File dir, boolean isCopy) throws IOException {
        File newFile = new File(dir, file.getName());
        FileChannel outputChannel = null;
        FileChannel inputChannel = null;
        try {
            outputChannel = new FileOutputStream(newFile).getChannel();
            inputChannel = new FileInputStream(file).getChannel();
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            inputChannel.close();
            if (!isCopy)
                file.delete();
        } finally {
            if (inputChannel != null) inputChannel.close();
            if (outputChannel != null) outputChannel.close();
        }
    }

    public static boolean renameFile(File oldFile, File newFile) throws IOException {
        if (newFile.exists()) throw new IOException("file exists");

        // Rename file (or directory)
        return oldFile.renameTo(newFile);
    }

}
