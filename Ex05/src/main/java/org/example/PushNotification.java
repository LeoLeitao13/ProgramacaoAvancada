package org.example;

class PushNotification implements Notification {
    public void send(String message) {
        System.out.println("Push Notification enviado: " + message);
    }
}