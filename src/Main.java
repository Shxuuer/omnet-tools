public class Main {
    public static void main(String[] args) {
        // Writer writer = new Writer("/home/zhangzhi/qzz/omnetpp-6.0.3/workspace/inet4.5/motivation/omnetpp.ini");
        Writer writer = new Writer("./omnetpp.ini");
        
        NetWork network = createNetwork();

        writer.write(network);

        TSNkit.runTSNkit();
    }

    public static NetWork createNetwork() {
        // 创建四个ZCU节点，每个节点有5个App
        NetWork network = new NetWork();
        for (int i = 0; i < 4; ++i) {
            Node node = network.addNode(new Node("ZCU" + i));
            for (int j = 0; j < 5; ++j) node.addApp(new App(App.AppType.OUT, node, j));
        }
        // 创建一个ADC节点，有20个App
        Node adc = network.addNode(new Node("ADC"));
        for (int i = 0; i < 20; ++i) adc.addApp(new App(App.AppType.IN, adc, i));
        // 连接ZCU和ADC
        for (int i = 0; i < 4; ++i) {
            Node node = network.getNode("ZCU" + i);
            for (int j = 0; j < 5; ++j) {
                node.getApp(j).to(
                    network.getNode("ADC").getApp(i * 5 + j), "100B", "1s", "0s", "ET"
                );
            }
        }
        // 创建四个交换机
        for (int i = 0; i < 4; ++i) {
            int numTrafficClasses = 3;
            Switch sw = network.addSwitch(new Switch("sw" + i, numTrafficClasses));
            for (int j =0; j < numTrafficClasses; ++j) {
                SwitchQueue queue = new SwitchQueue("250Mbps", true, "ET");
                queue.setTypename("Ieee8021qCreditBasedShaper");
                sw.addQueue(queue);
            }
        }
        return network;
    }
}