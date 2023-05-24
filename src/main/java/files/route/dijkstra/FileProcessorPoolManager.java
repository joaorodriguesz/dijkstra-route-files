package files.route.dijkstra;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileProcessorPoolManager {
    private static final int MIN_THREADS = 1;
    private static final int MAX_THREADS = 6;

    private ExecutorService executor;

    public FileProcessorPoolManager() {
        // Determina o número de threads com base no número de processadores disponíveis
        int numThreads = Math.min(MAX_THREADS, Math.max(MIN_THREADS, Runtime.getRuntime().availableProcessors()));

        // Cria um pool de threads com o número de threads calculado
        executor = Executors.newFixedThreadPool(numThreads);
    }

    public void processFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);

        List<File> files = List.of(Objects.requireNonNull(directory.listFiles()));

        files.forEach(file -> {
            if (file.isFile()) {
                executor.execute(new FileValidationRunnable(directory, file.getPath(), "thread=" + file.getName()));
            }
        });

        executor.shutdown();
    }
}


