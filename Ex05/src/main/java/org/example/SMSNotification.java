package org.example;

class SMSNotification implements Notification {
    public void send(String message) {
        System.out.println("SMS enviado: " + message);
    }
}
