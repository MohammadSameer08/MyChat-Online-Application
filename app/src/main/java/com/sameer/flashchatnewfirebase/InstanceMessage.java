package com.sameer.flashchatnewfirebase;

public class InstanceMessage
{
   String message;
   String author;

    public InstanceMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public InstanceMessage() {
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
