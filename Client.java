import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("Введите координаты для удара (row col): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                if (row <= 0 || row > 10 || col <= 0 || col > 10) {
                    System.out.println("Координаты вне диапазона, попробуйте снова.");
                    continue;
                }

                int request = row * 10 + col; // Преобразование координат в одно число
                outputStream.write(request);

                int response = inputStream.read();
                if (response == -1) break;

                if (response == 1) {
                    System.out.println("Попадание!");
                } else {
                    System.out.println("Мимо.");
                }
                outputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
