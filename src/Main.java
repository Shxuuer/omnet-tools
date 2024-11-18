public class Main {
    public static void main(String[] args) {
        Writer writer = new Writer();
        NetWork network = createNetwork();
        writer.write(network);
    }

    public static NetWork createNetwork() {
        NetWork network = new NetWork();
        for (int i = 0; i < 4; ++i) {
            Node node = network.addNode(new Node("ZCU" + i));
            for (int j = 0; j < 5; ++j) node.addApp(new App(App.AppType.OUT, node));
        }

        Node adc = network.addNode(new Node("ADC"));
        for (int i = 0; i < 20; ++i) adc.addApp(new App(App.AppType.IN, adc));

        for (int i = 0; i < 4; ++i) {
            Node node = network.getNode("ZCU" + i);
            for (int j = 0; j < 5; ++j) {
                node.getApp(j).to(
                    network.getNode("ADC").getApp(i * 5 + j), "100B", "1s", "0s", "ET"
                );
            }
        }
        return network;
    }
}