package com.example.snowwhite.codebot;

import java.util.List;

public class ChatMessage {
    private boolean isMine;
    private String content;
    private MessageType messageType;
    private List<String> options;

    public ChatMessage(String message, boolean mine, MessageType messageType) {
        content = message;
        isMine = mine;
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isImage() {
        return messageType == MessageType.IMAGE;
    }

    public boolean isMultipleChoice() {
        return messageType == MessageType.CHOICE;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }

    public enum MessageType {
        NORMAL, IMAGE, CHOICE
    }
}
