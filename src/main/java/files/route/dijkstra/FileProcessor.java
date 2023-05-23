package files.route.dijkstra;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class FileProcessor {
    private final String configFile;

    private String processedDirectory;

    private String notProcessedDirectory;

    private final String relativePathTest;

    public FileProcessor(String configFile) {
        this.configFile = configFile;
        this.relativePathTest = System.getProperty("user.dir") + "/test";
        this.readConfigFile();
    }

    private void readConfigFile () {
        Path configFilePath = Paths.get(this.configFile);

        try {
            List<String> directories = Files.lines(configFilePath)
                    .map(line -> line.replaceAll(".*=", ""))
                    .collect(Collectors.toList());

            processedDirectory = directories.get(0);
            notProcessedDirectory = directories.get(1);

            createDirectories(directories);

        } catch (Exception ex){
            System.out.println("Error reading directories");
        }


    }

    private void createDirectories(List<String> directories){
        directories.forEach(line -> {
            try {
                Files.createDirectories(Path.of(line));
            } catch (IOException e) {
                System.out.println("Error creating directories");
            }
        });
    }

    public void processFiles() {
        try {
            String newFileName = generateSequentialFileName();
            Path newFilePath = createFile(newFileName);
            System.out.println(newFilePath);
            //moveFile(newFilePath, processedDirectory, notProcessedDirectory);
        } catch (IOException ex) {
            System.out.println("Error processing files: " + ex.getMessage());
        }
    }

    private String generateSequentialFileName() throws IOException {
        Integer sequenceNumber = 0;

        String sequentialFileName = "route" + String.format("%02d", sequenceNumber) + ".txt";

        while (Files.exists(Paths.get(relativePathTest, sequentialFileName))) {
            sequenceNumber++;
            sequentialFileName = "route" + String.format("%02d", sequenceNumber) + ".txt";
        }

        return sequentialFileName;
    }


    private Path createFile(String fileName) throws IOException {
        Path filePath = Paths.get(relativePathTest, fileName);

        Files.createFile(filePath);
        System.out.println("File created: " + filePath.toAbsolutePath());

        return filePath;
    }

    private void moveFile(Path filePath, String successDirectory, String failureDirectory) {
        try {
            Path successFilePath = Paths.get(successDirectory, filePath.getFileName().toString());
            Files.move(filePath, successFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File moved to success directory: " + successFilePath.toAbsolutePath());
        } catch (IOException e) {
            moveToFailureDirectory(filePath, failureDirectory);
        }
    }

    private void moveToFailureDirectory(Path filePath, String failureDirectory) {
        try {
            Path failureFilePath = Paths.get(failureDirectory, filePath.getFileName().toString());
            Files.move(filePath, failureFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File moved to crash directory: " + failureFilePath.toAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Error moving file to failure directory: " + ex.getMessage());
        }
    }

}

