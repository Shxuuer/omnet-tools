package omnet.tools.SoftwareInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

import omnet.tools.utils.Pair;

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

    private static int getStreams(String fileName, HashMap<String, ArrayList<Pair<Integer, Integer>>> streams) {
        Scanner scanner = null;
        int cycle = 0;
        // 获取文件指针
        try {
            File gcl = new File(fileName);
            scanner = new Scanner(gcl);
            scanner.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 读取数据
        while (scanner.hasNextLine()) {
            Matcher matcher = Pattern.compile("\\d+").matcher(scanner.nextLine());
            ArrayList<Integer> lineData = new ArrayList<>();
            while (matcher.find()) lineData.add(Integer.parseInt(matcher.group()));
            cycle = lineData.get(5);
            // 前三条数字作为key
            String key = lineData.get(0) + "," + lineData.get(1) + "," + lineData.get(2);
            if (streams.containsKey(key)) {
                streams.get(key).add(new Pair<Integer,Integer>(lineData.get(3), lineData.get(4)));
            } else {
                ArrayList<Pair<Integer, Integer>> tmp = new ArrayList<>();
                tmp.add(new Pair<Integer,Integer>(lineData.get(3), lineData.get(4)));
                streams.put(key, tmp);
            }
        }
        scanner.close();
        return cycle;
    }

    public static String TSNkit2Omnet() {
        runTSNkit();
        StringBuilder result = new StringBuilder();
        HashMap<String, ArrayList<Pair<Integer, Integer>>> streams = new HashMap<>();
        int cycle = getStreams("../--GCL.csv", streams);
        // 按照first排序
        for (ArrayList<Pair<Integer, Integer>> tmp : streams.values()) {
            Collections.sort(tmp, new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                    return o1.getFirst() - o2.getFirst();
                }
            });
        }
        // 合并相邻两个first和second相同的pair
        for (ArrayList<Pair<Integer, Integer>> tmp : streams.values()) {
            for (int i = 0; i < tmp.size() - 1; i++) {
                if (tmp.get(i).getSecond().equals(tmp.get(i + 1).getFirst())) {
                    tmp.get(i).setSecond(tmp.get(i + 1).getSecond());
                    tmp.remove(i + 1);
                    i--;
                }
            }
        }
        // 形成时间段表
        HashMap<String, ArrayList<Integer>> timeTable = new HashMap<>();
        for (String tmp : streams.keySet()) {
            int lastTime = 0;
            ArrayList<Integer> timeTableTmp = new ArrayList<>();
            for (Pair<Integer, Integer> pair : streams.get(tmp)) {
                timeTableTmp.add(pair.getFirst() - lastTime);
                timeTableTmp.add(pair.getSecond() - pair.getFirst());
                lastTime = pair.getSecond();
            }
            timeTableTmp.add(cycle - lastTime + timeTableTmp.get(0));
            timeTableTmp.remove(0);
            timeTable.put(tmp, timeTableTmp);
        }
        // 输出时间段表
        for (String tmp : timeTable.keySet()) {
            result.append(tmp + " ");
            for (int i = 0; i < timeTable.get(tmp).size(); i++) {
                result.append(timeTable.get(tmp).get(i) + " ");
            }
            result.append("\n");
        }


        
        
        deleteCsv();
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(TSNkit2Omnet());
    }
}
