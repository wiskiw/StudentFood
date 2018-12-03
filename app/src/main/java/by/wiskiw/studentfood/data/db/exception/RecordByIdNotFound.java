package by.wiskiw.studentfood.data.db.exception;

import by.wiskiw.studentfood.di.AppException;

public class RecordByIdNotFound extends AppException {

    private int id;


    public RecordByIdNotFound(int id) {
        super("");
        this.id = id;
    }

    public RecordByIdNotFound(int id, String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
