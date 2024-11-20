package omnet.tools.Net;

import java.util.HashMap;

public class NetWork {
    public HashMap<String, Node> nodes = new HashMap<String, Node>();
    public HashMap<String, Switch> switches = new HashMap<String, Switch>();

    public Node addNode(Node node) {
        nodes.put(node.name, node);
        return node;
    }

    public Node getNode(String name) {
        return nodes.get(name);
    }

    public int getNodeNum() {
        return nodes.size();
    }

    public Switch addSwitch(Switch sw) {
        switches.put(sw.name, sw);
        return sw;
    }

    public Switch getSwitch(String name) {
        return switches.get(name);
    }

    public int getSwitchNum() {
        return switches.size();
    }
}
