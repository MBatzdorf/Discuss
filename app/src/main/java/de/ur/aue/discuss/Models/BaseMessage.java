package de.ur.aue.discuss.Models;


import java.util.Date;
import java.util.Calendar;

public class BaseMessage {

    private String title;
    private int id;
    private String message;

    public BaseMessage(String title, String inMessage)
    {
        this.message = inMessage;
        this.title = title;
    }

    public BaseMessage(String title, int id, String inMessage)
    {
        this.message = inMessage;
        this.title = title;
        this.id = id;
    }

    public String getMessage()
    {
        return message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
}
