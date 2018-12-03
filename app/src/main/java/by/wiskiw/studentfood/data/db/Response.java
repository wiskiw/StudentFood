package by.wiskiw.studentfood.data.db;

import by.wiskiw.studentfood.di.AppException;

public class Response<T> {

    private T data;
    private AppException exception;

    public Response(T data) {
        this.data = data;
    }

    public Response(AppException exception) {
        this.exception = exception;
    }

    public boolean isOk() {
        return exception == null;
    }

    public AppException getException() {
        return exception;
    }

    public void setException(AppException exception) {
        this.exception = exception;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
