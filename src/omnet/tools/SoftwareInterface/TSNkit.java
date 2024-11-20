package omnet.tools.SoftwareInterface;

import java.io.File;
import java.util.Scanner;

public class TSNkit {
    public static void runTSNkit() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "./bash/run_tsnkit.sh");
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

    public static String TSNkit2Omnet() {
        runTSNkit();
        StringBuilder result = new StringBuilder();
        try {
            File gcl = new File("../--GCL.csv");
            // 指针跳过第一行
            Scanner scanner = new Scanner(gcl);
            scanner.nextLine();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // deleteCsv();
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(TSNkit2Omnet());
    }
}