package org.filesearch;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class SearchTask implements Runnable {
    // TODO: Fields for file and keyword
    private File file;
    private String keyword;

    public SearchTask(File file, String keyword) {
        this.file = file;
        this.keyword = keyword;
        Logger.getLogger("org.apache.pdfbox").setLevel(Level.SEVERE);
    }

    @Override
    public void run() {

        boolean found = false;

        if (file.getName().endsWith(".txt")) {
            // Text file: scan line-by-line
            try (Stream<String> lines = Files.lines(file.toPath())) {
                found = lines.anyMatch(line -> line.contains(keyword));
            } catch (IOException e) {
                System.err.println("Error reading file: " + file.getAbsolutePath());
            }

        } else if (file.getName().endsWith(".pdf")) {
            // PDF file: extract text and search
            try (PDDocument document = PDDocument.load(file)) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(document);
                found = text.contains(keyword);
            } catch (IOException e) {
                System.err.println("Error reading PDF: " + file.getAbsolutePath());
            }

        } else {
            return;
        }

        if (found) {
            System.out.println("✅ Keyword found in: " + file.getName());
        } else {
            System.out.println("❌ Keyword not found in: " + file.getName());
        }
    }
}
