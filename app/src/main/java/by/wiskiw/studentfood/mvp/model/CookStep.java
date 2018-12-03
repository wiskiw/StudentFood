package by.wiskiw.studentfood.mvp.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CookStep {

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
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        return calendar.get(GregorianCalendar.MINUTE) + " min";
    }

}
