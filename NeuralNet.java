import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Random;

public class NeuralNet {

    Node[] startNodes;
    Node[] allNodes;
    int maxTime;
    int curTime = 0;

    public NeuralNet(Node[] start, Node[] all, int t) {

        startNodes = start;
        allNodes = all;
        maxTime = t;
    }

    public String toString() {
        String toPrint = "";
        for (int n = 0; n < allNodes.length; n++) {
            toPrint = toPrint.concat(allNodes[n].toString());
        }
        String timeString = "\nNeural network at time " + curTime + ": \n";
        toPrint = timeString.concat(toPrint);
        return toPrint;
    }

    public String startNN() {
        LinkedHashSet<Node> toUpdate = new LinkedHashSet<Node>();
        LinkedHashSet<Node> activeNodes = new LinkedHashSet<>();
        Random rand = new Random();

        for (int n = 0; n < startNodes.length; n++) {
            for (int sn = 0; sn < startNodes[n].outputs.length; sn++) {
                startNodes[n].activated = 1;
                startNodes[n].outputVal = startNodes[n].activated * startNodes[n].weight;
                startNodes[n].tauCount++;
                if (startNodes[n].tauCount > startNodes[n].tau) {
                    startNodes[n].activated = 0;
                    startNodes[n].tauCount = 0;
                }
                if (startNodes[n].outputs != null) {
                    toUpdate.addAll(Arrays.asList(startNodes[n].outputs));
                }
            }
        }

        activeNodes.addAll(Arrays.asList(startNodes));

        for (curTime = 1; curTime < maxTime; curTime++) {
            while (!toUpdate.isEmpty()) {
                Node[] updateArray = new Node[toUpdate.size()];
                toUpdate.toArray(updateArray);
                Node updateNode = updateArray[0];
                toUpdate.remove(updateArray[0]);

                for (int sn = 0; sn < updateNode.inputs.length; sn++) {
                    updateNode.inputSum += updateNode.inputs[sn].outputVal; //TODO: add noise
                }
                if (updateNode.inputSum >= updateNode.threshold) {
                    updateNode.activated = 1;
                    updateNode.tauCount++;
                    if (updateNode.tauCount > updateNode.tau) {
                        updateNode.activated = 0;
                        updateNode.tauCount = 0;
                    }
                } else {
                    updateNode.activated = 0;
                }
                if (updateNode.outputs != null) {
                    toUpdate.addAll(Arrays.asList(updateNode.outputs));
                }
            }
            System.out.println("ANN at time " + curTime + " :");
            System.out.println(this.toString());
        }
        return this.toString();
    }

}
