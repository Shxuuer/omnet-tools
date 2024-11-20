import java.util.HashMap;

public class NetWork {
    HashMap<String, Node> nodes = new HashMap<String, Node>();
    HashMap<String, Switch> switches = new HashMap<String, Switch>();

    public Node addNode(Node node) {
        nodes.put(node.name, node);
        return node;
    }

    public Node getNode(String name) {
        return nodes.get(name);
    }

    public Switch addSwitch(Switch sw) {
        switches.put(sw.name, sw);
        return sw;
    }

    public Switch getSwitch(String name) {
        return switches.get(name);
    }
}
