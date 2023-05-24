package files.route.dijkstra;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileValidationRunnable implements Runnable {
    private String filePath;

    private String threadName;

    private String processedDirectory;

    private String notProcessedDirectory;

    private File directoryOrigen;

    public FileValidationRunnable(File directory, String filePath, String threadName) {
        this.filePath = filePath;
        this.threadName = threadName;
        this.directoryOrigen = directory;
    }

    @Override
    public void run() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Lógica de processamento da linha do arquivo
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            // Tratamento de exceção, se necessário
//            e.printStackTrace();
//        }

        Thread.currentThread().setName(threadName);

        try {
            Thread.sleep(10000);
            System.out.println(threadName);
            //moveFile();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void moveFile() {
        Path filePath = Path.of(this.filePath);
        try {
            Path successFilePath = Paths.get(directoryOrigen.toString(), "processed", filePath.getFileName().toString());
            System.out.println(filePath);
            Files.move(filePath, successFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File moved to success directory: " + successFilePath.toAbsolutePath());
        } catch (Exception e) {
           moveToFailureDirectory(filePath);
        }
    }
    private void moveToFailureDirectory(Path filePath) {
        try {
            Path failureFilePath = Paths.get(directoryOrigen.toString(), "unprocessed", filePath.getFileName().toString());
            Files.move(filePath, failureFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File moved to crash directory: " + failureFilePath.toAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Error moving file to failure directory: " + ex.getMessage());
        }
    }
}

