package org.filesearch;

import java.io.File;
import java.util.concurrent.ExecutorService;

public class FileSearcher {
    // TODO: Fields for keyword and executor service
    private final ExecutorService executorService;
    private final String keyWord;

    public FileSearcher(String keyword, ExecutorService executorService) {
        this.executorService = executorService;
        this.keyWord = keyword;
    }

    public void searchInDirectory(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File f : files) {
            if(f.getName().startsWith(".")){
                continue;
            }
            if (f.isDirectory()) {
                searchInDirectory(f.getPath());
                continue;
            }
            executorService.submit(
                    ()->{
                        Thread.currentThread().setName("Search-" + f.getName());
                        new SearchTask(f,keyWord).run();
                    });}
    }
}
