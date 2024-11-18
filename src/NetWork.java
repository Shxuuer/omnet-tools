import java.util.HashMap;

public class NetWork {
    HashMap<String, Node> nodes = new HashMap<String, Node>();

    public Node addNode(Node node) {
        nodes.put(node.name, node);
        return node;
    }

    public Node getNode(String name) {
        return nodes.get(name);
    }
}
