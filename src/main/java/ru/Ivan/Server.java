package ru.Ivan;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 12345;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port);
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    out.println("Доступные операции: +, -, *, /");

                    String input = in.readLine();
                    if (input != null) {
                        String[] parts = input.split(" ");
                        if (parts.length != 3) {
                            out.println("Неверный формат запроса. Используйте: число операция число");
                            continue;
                        }

                        try {
                            double num1 = Double.parseDouble(parts[0]);
                            double num2 = Double.parseDouble(parts[2]);
                            String operation = parts[1];

                            double result = switch (operation) {
                                case "+" -> num1 + num2;
                                case "-" -> num1 - num2;
                                case "*" -> num1 * num2;
                                case "/" -> {
                                    if (num2 == 0) {
                                        throw new ArithmeticException("Деление на ноль");
                                    }
                                    yield num1 / num2;
                                }
                                default -> throw new IllegalArgumentException("Неизвестная операция");
                            };

                            out.println("Результат: " + result);
                        } catch (NumberFormatException e) {
                            out.println("Ошибка: введите корректные числа");
                        } catch (ArithmeticException | IllegalArgumentException e) {
                            out.println("Ошибка: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
