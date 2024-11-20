package omnet.tools.Net;

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

    public int getAppsSize() {
        return apps.size();
    }

    public App getApp(int index) {
        return apps.get(index);
    }

    public String getIdentifierMap() {
        int numberOfOutApps = 0;
        for (App app : apps) if (app.type == App.AppType.OUT) numberOfOutApps++;
        String[] identifiers = new String[numberOfOutApps];
        for (int i = 0; i < apps.size(); i++) {
            if (getApp(i).type == App.AppType.OUT)
                identifiers[i] = String.format("\n\t{stream: \"%s\", packetFilter: expr(udp.destPort == %d)}", getApp(i).streamName, getApp(i).destPort);
        }
        return String.format("*.%s.bridging.streamIdentifier.identifier.mapping = [%s]", name, String.join(",", identifiers));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (haveInApp) sb.append(String.format("*.%s.hasOutgoingStreams = true\n", name));
        if (haveOutApp) {
            sb.append(String.format("*.%s.hasIncomingStreams = true\n", name));
            sb.append(getIdentifierMap() + "\n");
        }
        sb.append(String.format("*.%s.numApps = %d\n", name, apps.size()));
        for (App app : apps) sb.append(app.toString());
        return sb.toString();
    }
}
