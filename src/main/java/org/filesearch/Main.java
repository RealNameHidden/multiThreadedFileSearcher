package org.filesearch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        String path = "/Users/8ball/Downloads";

        try {
            ExecutorService executor = Executors.newFixedThreadPool(5);

            FileSearcher fileSearcher = new FileSearcher("dheeraj", executor);
            fileSearcher.searchInDirectory(path);

            executor.shutdown();
        } catch (Exception e) {
            System.err.println("Exception in main thread");
        }
    }
}
