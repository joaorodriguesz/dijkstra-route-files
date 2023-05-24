import files.route.dijkstra.ConfigFileCreator;
import files.route.dijkstra.FileProcessor;


public class main {
    public static void main(String[] args) {

        String configFile = ConfigFileCreator.createDirectorieFileConfiguration();
        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.readConfigFile(configFile);
        fileProcessor.processFiles();
    }
}
