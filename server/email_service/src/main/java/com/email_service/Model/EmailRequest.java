package com.email_service.Model;

public class EmailRequest {
    private String to;
    private String subject;
    private String  message;


    public EmailRequest() {
    }
    public EmailRequest(String to, String subject, String message ) {
        this.to = to;
        this.subject=subject;
        this.message=message;
    }
    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public String getSubject() {
        return subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTo(String to) {
        this.to = to;
    }
    @Override
    public String toString() {
        return "EmailRequest{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
