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
        // reducing 3, so a range of [-3, 3]
        Random noise = new Random();

        // add all start nodes to set of activated nodes
        activeNodes.addAll(Arrays.asList(startNodes));

        // loop through each startNode and activate it, update the outputVal based on
        // weight and store its output connections in toUpdate
        for (int n = 0; n < startNodes.length; n++) {
            startNodes[n].activated = 1;
            startNodes[n].inputSum = startNodes[n].threshold;
            startNodes[n].outputVal = startNodes[n].weight;
            if (startNodes[n].outputs != null) {
                toUpdate.addAll(Arrays.asList(startNodes[n].outputs));
            }
        }

        // loop through the system for the designated maxTime
        for (curTime = 1; curTime <= maxTime; curTime++) {

            // update each of the currently active nodes according to time decay constant
            Node[] activeArray = new Node[activeNodes.size()];
            activeNodes.toArray(activeArray);

            for (int a = 0; a < activeArray.length; a++) {
                Node activeNode = activeArray[a];

                // increase tauCount of each activated node
                activeNode.tauCount++;

                // update node based on tauCount
                activeNode.updateTauCount(activeNodes);
                activeNode.updateActivate(activeNodes);
            }

            Node[] updateArray = new Node[toUpdate.size()];
            toUpdate.toArray(updateArray);

            // loop through the nodes in updateArray, and update them
            for (int u = 0; u < updateArray.length; u++) {

                // node to be updated
                Node updateNode = updateArray[u];
                toUpdate.remove(updateArray[u]);

                // loop through the inputs to the node, update its inputSum property
                if (updateNode.inputs != null) {
                    for (int sn = 0; sn < updateNode.inputs.length; sn++) {
                        updateNode.updateInputSum();
                        // add noise
                        if (nnNoise) {
                            updateNode.inputSum += noise.nextInt(7) - 3;
                        }
                    }
                }
                // update whether the node was activated or not
                updateNode.updateActivate(activeNodes);

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
