package ru.Ivan;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            // Получаем доступные операции от сервера
            System.out.println(in.readLine());

            while (true) {
                // Ввод данных от пользователя
                System.out.print("Введите запрос (например, 5 + 3): ");
                String userInput = scanner.nextLine();
                if ("exit".equalsIgnoreCase(userInput)) {
                    System.out.println("Выход...");
                    break;
                }

                // Отправляем запрос на сервер
                out.println(userInput);

                // Получаем и выводим результат
                String response = in.readLine();
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
