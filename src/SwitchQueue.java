public class SwitchQueue {
    String idleSlope;
    boolean initiallyOpen = true;
    String typename = "";
    int index;
    String displayName;

    public SwitchQueue(String idleSlope, boolean initiallyOpen, String displayName) {
        this.idleSlope = idleSlope;
        this.initiallyOpen = initiallyOpen;
        this.displayName = displayName;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String toString(String SwitchName) {
        StringBuilder sb = new StringBuilder();
        if (!"".equals(typename))
            sb.append(String.format("*.%s.eth[*].macLayer.queue.transmissionSelectionAlgorithm[%d].typename = \"%s\"\n", SwitchName, index, typename));
        sb.append(String.format("*.%s.eth[*].macLayer.queue.transmissionSelectionAlgorithm[%d].idleSlope = %s", SwitchName, index, idleSlope));
        sb.append(String.format("*.%s.eth[*].macLayer.queue.queue[%d].display-name = \"%s\"\n", SwitchName, index, displayName));
        sb.append(String.format("*.%s.eth[*].macLayer.queue.transmissionGate[%d].initiallyOpen = %b\n", SwitchName, index, initiallyOpen));
        return sb.toString();
    }
}
