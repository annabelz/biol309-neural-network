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
        return toPrint;
    }

    public void startNN() {
        System.out.println("Neural network at time 0: ");
        System.out.println(this.toString());

        LinkedHashSet<Node> toUpdate = new LinkedHashSet<Node>();
        LinkedHashSet<Node> activeNodes = new LinkedHashSet<>();
        Random rand = new Random();

        activeNodes.addAll(Arrays.asList(startNodes));

        for (int n = 0; n < startNodes.length; n++) {
            for (int sn = 0; sn < startNodes[n].outputs.length; sn++) {
                startNodes[n].activated = 1;
                startNodes[n].outputVal = startNodes[n].activated * startNodes[n].weight;
                if (startNodes[n].outputs != null) {
                    toUpdate.addAll(Arrays.asList(startNodes[n].outputs));
                }
            }
        }


        for (curTime = 1; curTime < maxTime; curTime++) {
            Node[] activeArray = new Node[activeNodes.size()];
            activeNodes.toArray(activeArray);

            for (int a = 0; a < activeArray.length; a++) {
                activeArray[a].tauCount++;
                if (activeArray[a].tauCount > activeArray[a].tau) {
                    activeArray[a].activated = 0;
                    activeArray[a].tauCount = 0;
                    activeNodes.remove(activeArray[a]);
                }
            }

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
                    activeNodes.add(updateNode);
                } else {
                    updateNode.activated = 0;
                    activeNodes.remove(updateNode);
                }
                if (updateNode.outputs != null) {
                    toUpdate.addAll(Arrays.asList(updateNode.outputs));
                }
            }
            System.out.println("Neural network at time " + curTime + ": ");
            System.out.println(this.toString());
        }
    }

}
