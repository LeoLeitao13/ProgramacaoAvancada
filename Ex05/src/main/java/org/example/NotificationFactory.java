package org.example;

class NotificationFactory {
    public static Notification createNotification(String type) {
        if (type.equals("1")) return new EmailNotification();
        if (type.equals("2")) return new SMSNotification();
        if (type.equals("3")) return new PushNotification();
        return null;
    }
}
