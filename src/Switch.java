import java.util.ArrayList;

public class Switch {
    String name;
    int queue_numTrafficClasses;
    private ArrayList<SwitchQueue> queues = new ArrayList<SwitchQueue>();

    public Switch(String name, int queue_numTrafficClasses) {
        this.name = name;
        this.queue_numTrafficClasses = queue_numTrafficClasses;
    }

    public int getQueueSize() {
        return queues.size();
    }

    public void addQueue(SwitchQueue queue) {
        queue.index = getQueueSize();
        this.queues.add(queue);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("*.%s.eth[*].macLayer.queue.numTrafficClasses = %d\n", name, queue_numTrafficClasses));
        
        sb.append(String.format("*.%s.hasEgressTrafficShaping = true\n", name));
        sb.append(String.format("*.%s.hasIncomingStreams = true\n", name));
        sb.append(String.format("*.%s.hasOutgoingStreams = true\n", name));
        sb.append(String.format("*.%s.bridging.directionReverser.reverser.excludeEncapsulationProtocols = [\"ieee8021qctag\"]\n", name));
        for (SwitchQueue queue : queues) sb.append(queue.toString(name));
        return sb.toString();
    }
}
