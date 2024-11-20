import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Writer {
    FileWriter fileWriter;
    String network_path = "inet.networks.tsn.motivation";
    String description = "Loop simulation";
    String simulation_time = "5s";

    public Writer(String iniFilePath) {
        try {
            fileWriter = new FileWriter(iniFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(NetWork network) {
        try {
            fileWriter.write("[General]\n");
            fileWriter.write("network = " + network_path + "\n");
            fileWriter.write("description = " + description + "\n");
            fileWriter.write("sim-time-limit = " + simulation_time + "\n");
            fileWriter.write("\n");
            // 偷懒了，直接写死了
            fileWriter.write("*.ZCU*.bridging.streamCoder.encoder.mapping = [{stream: \"ST\", pcp: 7},{stream: \"ET\", pcp: 4},{stream: \"BE\", pcp: 0}]\n");
            fileWriter.write("*.ZCU*.bridging.streamCoder.decoder.mapping = [{pcp: 7, stream: \"ST\"},{pcp: 4, stream: \"ET\"}, {pcp: 0, stream: \"BE\"}]\n");
            fileWriter.write("*.sw*.bridging.streamCoder.decoder.mapping = [{pcp: 7, stream: \"ST\"}, {pcp: 4, stream: \"ET\"}, {pcp: 0, stream: \"BE\"}]\n");
            fileWriter.write("*.sw*.bridging.streamCoder.encoder.mapping = [{stream: \"ST\", pcp: 7},{stream: \"ET\", pcp: 4},{stream: \"BE\", pcp: 0}]\n");

            fileWriter.write("\n");
            for (Node node : network.nodes.values()) {
                fileWriter.write(node.toString());
            }
            fileWriter.write("\n");
            for(Switch sw : network.switches.values()) {
                fileWriter.write(sw.toString());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
