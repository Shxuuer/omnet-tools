public class App {
    enum AppType {
        IN, OUT
    }

    Node node;
    AppType type;
    // in
    int port;
    // out
    int destPort;
    Node destAddress;
    String packetLength;
    String productionInterval;
    String initialProductionOffset;
    String streamName;

    public App (AppType appType, Node node) {
        this.type = appType;
        this.node = node;
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
}
