import java.io.File;

public class Omnet {
    
    public static void runOmnet() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "./run_opp.sh");
            processBuilder.directory(new File("../"));
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
