package lesson_3;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        //1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
        System.out.println(Arrays.toString(fileToArray("F:\\Natasha\\Repo\\GeekBrains\\GeekBrains_Java_3_Professional_level\\src\\main\\java\\lesson_3\\1.txt")));

        //2. Последовательно сшить 5 файлов в один (файлы примерно 100 байт).
        concatFiles();

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

    public static void concatFiles() throws IOException {
        List<FileInputStream> listOfFiles = Arrays.asList(
                new FileInputStream("src/main/java/lesson_3/2_1.txt"),
                new FileInputStream("src/main/java/lesson_3/2_2.txt"),
                new FileInputStream("src/main/java/lesson_3/2_3.txt"),
                new FileInputStream("src/main/java/lesson_3/2_4.txt"),
                new FileInputStream("src/main/java/lesson_3/2_5.txt")
        );

        try (SequenceInputStream sis = new SequenceInputStream(Collections.enumeration(listOfFiles));
             FileOutputStream fos = new FileOutputStream("src/main/java/lesson_3/2_result.txt")) {
            int x;
            while ((x = sis.read()) != -1) {
                fos.write(x);
            }
        }

    }
}
