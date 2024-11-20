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
            fileWriter.write("*.sw*.hasEgressTrafficShaping = true\n");
            fileWriter.write("*.sw*.hasIncomingStreams = true\n");
            fileWriter.write("*.sw*.hasOutgoingStreams = true\n");
            fileWriter.write("*.sw*.bridging.directionReverser.reverser.excludeEncapsulationProtocols = [\"ieee8021qctag\"]\n");
            fileWriter.write("*.ZCU*.bridging.streamCoder.encoder.mapping = [{stream: \"ST\", pcp: 7},{stream: \"ET\", pcp: 4},{stream: \"BE\", pcp: 0}]\n");
            fileWriter.write("*.ZCU*.bridging.streamCoder.decoder.mapping = [{pcp: 7, stream: \"ST\"},{pcp: 4, stream: \"ET\"}, {pcp: 0, stream: \"BE\"}]\n");
            fileWriter.write("*.sw*.bridging.streamCoder.decoder.mapping = [{pcp: 7, stream: \"ST\"}, {pcp: 4, stream: \"ET\"}, {pcp: 0, stream: \"BE\"}]\n");
            fileWriter.write("*.sw*.bridging.streamCoder.encoder.mapping = [{stream: \"ST\", pcp: 7},{stream: \"ET\", pcp: 4},{stream: \"BE\", pcp: 0}]\n");
            fileWriter.write("*.sw*.eth[*].macLayer.queue.numTrafficClasses = 3\n");
            fileWriter.write("*.sw*.eth[*].macLayer.queue.queue[0].display-name = \"BE\"\n");
            fileWriter.write("*.sw*.eth[*].macLayer.queue.queue[1].display-name = \"ET\"\n");
            fileWriter.write("*.sw*.eth[*].macLayer.queue.queue[2].display-name = \"ST\"\n");
            fileWriter.write("\n");
            for (Node node : network.nodes.values()) {
                writeNode(node);
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeNode(Node node) throws IOException {
        ArrayList<App> apps = node.getApps();
        if (node.haveInApp) fileWriter.write(String.format("*.%s.hasOutgoingStreams = true\n", node.name));
        if (node.haveOutApp) {
            fileWriter.write(String.format("*.%s.hasIncomingStreams = true\n", node.name));
            fileWriter.write(node.getIdentifierMap() + "\n");
        }
        fileWriter.write(String.format("*.%s.numApps = %d\n", node.name, apps.size()));
        for (int i = 0; i < apps.size(); ++i) {
            App app = apps.get(i);
            if (app.type == App.AppType.IN) {
                fileWriter.write(String.format("*.%s.app[%d].typename = \"UdpSinkApp\"\n", node.name, i));
                fileWriter.write(String.format("*.%s.app[%d].io.localPort = %d\n", node.name, i, app.port));
            } else {
                fileWriter.write(String.format("*.%s.app[%d].typename = \"UdpSourceApp\"\n", node.name, i));
                fileWriter.write(String.format("*.%s.app[%d].io.destAddress = \"%s\"\n", node.name, i, app.destAddress.name));
                fileWriter.write(String.format("*.%s.app[%d].io.destPort = %d\n", node.name, i, app.destPort));
                fileWriter.write(String.format("*.%s.app[%d].source.packetLength = %s\n", node.name, i, app.packetLength));
                fileWriter.write(String.format("*.%s.app[%d].source.productionInterval = %s\n", node.name, i, app.productionInterval));
                fileWriter.write(String.format("*.%s.app[%d].source.initialProductionOffset = %s\n", node.name, i, app.initialProductionOffset));
            }
        }
    }

    private void writeGCL(String tsnOutFile) {

    }
}
