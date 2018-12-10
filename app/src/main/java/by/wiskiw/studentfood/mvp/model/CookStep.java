package by.wiskiw.studentfood.mvp.model;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

import by.wiskiw.studentfood.utils.RecipeTimeUtil;
import by.wiskiw.studentfood.utils.diff.util.DiffUtilItem;

public class CookStep implements DiffUtilItem {

    private String text = "";
    private Long time = 0L;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setTimeMin(int minute) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.MINUTE, minute);
        this.time = calendar.getTimeInMillis();
    }

    public String getTimeString() {
        return RecipeTimeUtil.INSTANCE.formatToString(time);
    }

    @NonNull
    @Override
    public String toString() {
        return "CookStep{" +
                "text='" + text + '\'' +
                ", time= " + getTimeString() + " (" + time + ")" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CookStep cookStep = (CookStep) o;

        if (text != null ? !text.equals(cookStep.text) : cookStep.text != null) return false;
        return time != null ? time.equals(cookStep.time) : cookStep.time == null;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public int getId() {
        return this.hashCode();
    }
}
