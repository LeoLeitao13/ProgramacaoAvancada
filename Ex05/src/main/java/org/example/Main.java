package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1: Email");
            System.out.println("2: SMS");
            System.out.println("3: Push Notification");
            System.out.println("0: Sair");
            System.out.print("Escolha o tipo de notificacao: ");
            String type = scanner.nextLine();

            if (type.equals("0")) break;

            System.out.print("Digite a mensagem: ");
            String message = scanner.nextLine();

            Notification notification = NotificationFactory.createNotification(type);
            if (notification != null) {
                notification.send(message);
            } else {
                System.out.println("Tipo invalido");
            }
        }
        scanner.close();
    }
}