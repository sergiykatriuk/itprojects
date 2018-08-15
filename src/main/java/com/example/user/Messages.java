package com.example.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;

public class Messages implements Serializable {

    private ArrayList<Message> messages = new ArrayList<>();

    public Messages(String type, String message) {
        messages.add(new Message(type, message));
    }

    public Messages addMessage(String type, String message) {
        messages.add(new Message(type, message));
        return this;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();
        String result = gson.toJson(messages);
        result = result.replaceAll("\"", "\\\\\"");
        return result;
    }

}
