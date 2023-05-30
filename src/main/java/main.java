import files.route.dijkstra.ConfigFile;
import files.route.dijkstra.FileProcessor;


public class main {
    public static void main(String[] args) {

        //ConfigFileCreator configFile = new ConfigFileCreator("/home/joaorodrigues/Documents", "processado", "naoProcessado");
        ConfigFile configFile = new ConfigFile();
        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.readConfigFile(ConfigFile.getDestinationPath());
        fileProcessor.processFiles();
    }
}
