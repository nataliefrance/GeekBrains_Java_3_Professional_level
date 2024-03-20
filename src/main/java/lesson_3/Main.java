package lesson_3;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        //1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
        System.out.println(Arrays.toString(fileToArray("src/main/java/lesson_3/1.txt")));

        //2. Последовательно сшить 5 файлов в один (файлы примерно 100 байт).
        concatFiles();

        //3. Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb).
        // Вводим страницу (за страницу можно принять 1800 символов),
        // программа выводит ее в консоль.
        // Контролируем время выполнения: программа не должна загружаться дольше 10 секунд, а чтение – занимать свыше 5 секунд.
        readBigFile("src/main/java/lesson_3/3.txt");
    }

    public static void readBigFile(String name) {
        long start = System.currentTimeMillis();
        try (RandomAccessFile raf = new RandomAccessFile(name, "r"); //"r" - режим чтения
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            long fileLength = raf.length();
            long pageLength = 1800;
            long pagesCount = fileLength / pageLength;
            byte[] byteArray = new byte[1800];
            System.out.println("Время запуска программы: " + (System.currentTimeMillis() - start));
            while (true) {
                System.out.println("\nВведите страницу от 1 до " + pagesCount + ". Для выхода введите 0");
                int page = Integer.parseInt(reader.readLine());
                if (page <= pagesCount && page > 0) {
                    long startSearch = System.currentTimeMillis();
                    raf.seek((page - 1) * pageLength);
                    raf.read(byteArray, 0, byteArray.length); //Считывает byteArray.length байтов в массив byteArray начиная с 0 места
                    String utf8 = new String(byteArray, "UTF-8");
                    System.out.println(utf8 + "\n");
                    System.out.println("Время поиска: " + (System.currentTimeMillis() - startSearch) + " миллисекунд.");
                } else if (page == 0) {
                    System.out.println("Досвидули!");
                    break;
                } else {
                    System.out.println("Такой страницы не существует.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
