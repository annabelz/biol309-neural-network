import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Random;
// NeuralNet class. A NeuralNet object represents a neural network.

public class NeuralNet {

    boolean nnNoise; // indicates whether or not the network is implemented with noise (true if
                     // noisy)
    Node[] startNodes; // list of nodes that are active when the simulation begins
    Node[] allNodes; // list of all nodes in the network
    int maxTime; // number of times to loop through the system
    int curTime = 0;

    public NeuralNet(Node[] start, Node[] all, int t, boolean noise) {
        startNodes = start;
        allNodes = all;
        maxTime = t;
        nnNoise = noise;
    }

    public String toString() {
        String toPrint = "";
        for (int n = 0; n < allNodes.length; n++) {
            toPrint = toPrint.concat(allNodes[n].toString());
        }
        return toPrint;
    }

    // begins the simulation, starting by stimulating the nodes in startNodes
    public void startNN() {

        // print initial state
        System.out.println("Neural network at time 0: ");
        System.out.println(this.toString());

        // set of nodes to be updated (toUpdate)
        LinkedHashSet<Node> toUpdate = new LinkedHashSet<Node>();

        // set of nodes that are currently activated in the system (activNodes)
        LinkedHashSet<Node> activeNodes = new LinkedHashSet<>();

        // noise in the system is implemented by generating a number between 0-6 and
        // reducing 3, so a range of [-3, 3] added noise
        Random noise = new Random();

        // add all start nodes to set of activated nodes
        activeNodes.addAll(Arrays.asList(startNodes));

        // loop through each startNode and activate it, update the outputVal based on
        // weight and store its output connections in toUpdate
        for (int n = 0; n < startNodes.length; n++) {
            startNodes[n].activated = 1;
            startNodes[n].outputVal = startNodes[n].weight;
            if (startNodes[n].outputs != null) {
                toUpdate.addAll(Arrays.asList(startNodes[n].outputs));
            }
        }

        // loop through the system for the designated maxTime
        for (curTime = 1; curTime < maxTime; curTime++) {

            // update each of the currently active nodes according to time decay constant
            Node[] activeArray = new Node[activeNodes.size()];
            activeNodes.toArray(activeArray);

            for (int a = 0; a < activeArray.length; a++) {
                activeArray[a].tauCount++;
                if (activeArray[a].tauCount > activeArray[a].tau) {
                    activeArray[a].activated = 0;
                    activeArray[a].tauCount = 0;
                    activeArray[a].outputVal = 0;
                    activeArray[a].inputSum = 0;
                    activeNodes.remove(activeArray[a]);
                }
                if (activeArray[a].tauCount > 0) {
                    activeArray[a].inputSum = 0;
                }
            }

            Node[] updateArray = new Node[toUpdate.size()];
            toUpdate.toArray(updateArray);

            // loop through the nodes in updateArray, and update them
            for (int u = 0; u < updateArray.length; u++) {

                // node to be updated
                Node updateNode = updateArray[0];
                toUpdate.remove(updateArray[0]);

                // loop through the inputs to the node, update its inputSum property
                for (int sn = 0; sn < updateNode.inputs.length; sn++) {
                    updateNode.inputSum = updateNode.inputSum + updateNode.inputs[sn].outputVal;
                    // add noise
                    if (nnNoise) {
                        updateNode.inputSum += noise.nextInt(7) - 3;
                    }
                }
                // if the new inputSum is greater than the threshold, add the node to the set of
                // activeNodes and update
                if (updateNode.inputSum >= updateNode.threshold) {
                    updateNode.activated = 1;
                    updateNode.outputVal = updateNode.weight;
                    activeNodes.add(updateNode);
                } else { // if the node isn't activated, remove it from the set of activeNodes
                    updateNode.activated = 0;
                    updateNode.outputVal = 0;
                    updateNode.inputSum = 0;
                    activeNodes.remove(updateNode);
                }
                // if this node has output nodes, add those to the set of nodes toUpdate
                if (updateNode.outputs != null) {
                    toUpdate.addAll(Arrays.asList(updateNode.outputs));
                }
            }

            System.out.println("Neural network at time " + curTime + ": ");
            System.out.println(this.toString());
        }
    }
}
