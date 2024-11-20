package omnet.tools.Net;

public class App {
    public enum AppType {
        IN, OUT
    }

    Node node;
    AppType type;
    int index;
    // in
    int port;
    // out
    int destPort;
    Node destAddress;
    String packetLength;
    String productionInterval;
    String initialProductionOffset;
    String streamName;

    public App (AppType appType, Node node, int index) {
        this.type = appType;
        this.node = node;
        this.index = index;
    }

    public void to(App destApp, String packetLength, String productionInterval, String initialProductionOffset, String streamName) {
        if (type == AppType.OUT) {
            if (destApp.type == AppType.IN) {
                this.destPort = destApp.port;
                this.destAddress = destApp.node;
                this.packetLength = packetLength;
                this.productionInterval = productionInterval;
                this.initialProductionOffset = initialProductionOffset;
                this.streamName = streamName;
            } else {
                throw new RuntimeException("IN app can't be used in to() method");
            }
        } else {
            throw new RuntimeException("IN app can't be used in to() method");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (type == AppType.IN) {
            sb.append(String.format("*.%s.app[%d].typename = \"UdpSinkApp\"\n", node.name, index));
            sb.append(String.format("*.%s.app[%d].io.localPort = %d\n", node.name, index, port));
        } else {
            sb.append(String.format("*.%s.app[%d].typename = \"UdpSourceApp\"\n", node.name, index));
            sb.append(String.format("*.%s.app[%d].io.destAddress = \"%s\"\n", node.name, index, destAddress.name));
            sb.append(String.format("*.%s.app[%d].io.destPort = %d\n", node.name, index, destPort));
            sb.append(String.format("*.%s.app[%d].source.packetLength = %s\n", node.name, index, packetLength));
            sb.append(String.format("*.%s.app[%d].source.productionInterval = %s\n", node.name, index, productionInterval));
            sb.append(String.format("*.%s.app[%d].source.initialProductionOffset = %s\n", node.name, index, initialProductionOffset));
        }
        return sb.toString();
    }
}
