import java.io.File;

public class TSNkit {
    public static void runTSNkit() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "./run_tsnkit.sh");
            processBuilder.directory(new File("../"));
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteCsv() {
        try {
            File directory = new File("../");
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.getName().startsWith("--") && file.getName().endsWith(".csv")) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void TSNkit2Omnet(String path) {
        try {
            File gcl = new File("../--GCL.csv");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}