package com.example.snowwhite.codebot;

public class ChatMessage {
    private boolean isMine;
    private String content;
    private MessageType messageType;
    private String[] options;

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

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String[] getOptions() {
        return options;
    }

    public enum MessageType {
        NORMAL, IMAGE, CHOICE
    }
}
