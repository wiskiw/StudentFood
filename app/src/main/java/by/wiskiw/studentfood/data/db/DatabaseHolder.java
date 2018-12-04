package by.wiskiw.studentfood.data.db;

import io.paperdb.Book;
import io.paperdb.Paper;

public class DatabaseHolder {

    private static final String BOOK_NAME_RECIPE = "recipes-database";
    private static Book paperBook;

    public static Book getDatabase() {
        if (paperBook == null) {
            paperBook = Paper.book(BOOK_NAME_RECIPE);
        }
        return paperBook;
    }

    private DatabaseHolder() {
    }


}
