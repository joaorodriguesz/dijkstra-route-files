package files.route.dijkstra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ConfigFileCreator {
    public static String createDirectorieFileConfiguration() {
        String diretorioRaiz = System.getProperty("user.dir");
        String diretorioConfiguration = diretorioRaiz + "/test/configuration";
        String diretorioTest = diretorioRaiz + "/test";
        String arquivoConfig = "config.txt";
        String absolutePath = "";

        try {
            Files.createDirectories(Path.of(diretorioConfiguration));

            Path arquivoConfigPath = Path.of(diretorioConfiguration, arquivoConfig);
            StringBuilder conteudoArquivo = new StringBuilder();
            conteudoArquivo
                    .append("processed=")
                    .append(diretorioTest)
                    .append("/processed\n")
                    .append("unprocessed=")
                    .append(diretorioTest)
                    .append("/unprocessed\n");

            absolutePath = arquivoConfigPath.toAbsolutePath().toString();

            Files.write(arquivoConfigPath, conteudoArquivo.toString().getBytes(), StandardOpenOption.CREATE);
            System.out.println("Configuration file created: " + arquivoConfigPath.toAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Error creating directories or writing configuration file: " + ex.getMessage());
        }

        return absolutePath;
    }
}

