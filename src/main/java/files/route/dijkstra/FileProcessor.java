package files.route.dijkstra;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class FileProcessor {
    private String configFile;

    private String relativePathTest;
    public void readConfigFile (String configFile) {
        this.configFile = configFile;
        this.relativePathTest = System.getProperty("user.dir") + "/test";

        Path configFilePath = Paths.get(this.configFile);

        try {
            List<String> directories = Files.lines(configFilePath)
                    .map(line -> line.replaceAll(".*=", ""))
                    .collect(Collectors.toList());

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
        FileProcessorPoolManager fileProcessorPoolManager = new FileProcessorPoolManager();

        try {
            String newFileName = generateSequentialFileName();
            Path newFilePath = createFile(newFileName);
            System.out.println(relativePathTest);
            fileProcessorPoolManager.processFilesInDirectory(relativePathTest);
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
}

