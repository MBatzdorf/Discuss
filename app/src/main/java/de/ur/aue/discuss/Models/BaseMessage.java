package de.ur.aue.discuss.Models;


import java.util.Date;
import java.util.Calendar;

public class BaseMessage {

    private String sender;
    private String message;
    private Date dateOfCreation;

    public BaseMessage(String inSender, String inMessage, Date inDate)
    {
        this.sender = inSender;
        this.message = inMessage;
        this.dateOfCreation = inDate;
    }

    public String getMessage()
    {
        return message;
    }

    public String getSender()
    {
        return sender;
    }

    public String getCreatedAt()
    {
        return dateOfCreation.toString();
    }
}
