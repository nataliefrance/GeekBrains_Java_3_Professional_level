package lesson_3;

import java.io.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        //1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
        System.out.println(Arrays.toString(fileToArray("F:\\Natasha\\Repo\\GeekBrains\\GeekBrains_Java_3_Professional_level\\src\\main\\java\\lesson_3\\1.txt")));


    }

    public static byte[] fileToArray(String path) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             InputStream inputStream = new BufferedInputStream(new FileInputStream(path))
        ) {
            while (inputStream.read() > 0) {
                int data = inputStream.read();
                outputStream.write(data);
            }
            return outputStream.toByteArray();
        }
    }
}
