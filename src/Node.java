import java.util.ArrayList;

public class Node {
    String name;
    private ArrayList<App> apps = new ArrayList<App>();
    Integer used_port = 1000;
    boolean haveInApp = false;
    boolean haveOutApp = false;

    public Node(String name) {
        this.name = name;
    }

    public void addApp(App app) {
        this.apps.add(app);
        if (app.type == App.AppType.IN) haveInApp = true;
        if (app.type == App.AppType.OUT) haveOutApp = true;
        if (app.type == App.AppType.IN) app.port = used_port++;
    }

    public ArrayList<App> getApps() {
        return apps;
    }

    public App getApp(int index) {
        return apps.get(index);
    }

    public String toString() {
        return name;
    }

    public String getIdentifierMap() {
        int numberOfOutApps = 0;
        for (App app : apps) if (app.type == App.AppType.OUT) numberOfOutApps++;
        String[] identifiers = new String[numberOfOutApps];
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).type == App.AppType.OUT)
                identifiers[i] = String.format("\n\t{stream: \"%s\", packetFilter: expr(udp.destPort == %d)}", apps.get(i).streamName, apps.get(i).port);
        }
        return String.format("*.%s.bridging.streamIdentifier.identifier.mapping = [%s]", name, String.join(",", identifiers));
    }
}
