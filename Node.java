// Node class. A node object represents one neuron.

import java.util.LinkedHashSet;

public class Node {
    Integer id;
    int weight;
    int threshold;
    int tau; // time decay constant
    Node[] inputs; // list of input connections
    Node[] outputs; // list of output connections
    int inputSum = 0; // sum of inputs
    int activated = 0; // if the neuron fires, this is 1
    int outputVal = 0;
    int tauCount = 0;

    public Node(int nodeNumber, int nodeWeight, int nodeThreshold, Node[] nodeInputs, Node[] nodeOutputs, int nodeTau) {
        id = nodeNumber;
        weight = nodeWeight;
        threshold = nodeThreshold;
        inputs = nodeInputs;
        outputs = nodeOutputs;
        tau = nodeTau;
    }

    public String toString() {

        String inputString = "";
        String outputString = "";

        if (inputs != null) {
            for (int n = 0; n < inputs.length; n++) {
                inputString = inputString.concat(inputs[n].id.toString());
            }
        }

        if (outputs != null) {
            for (int n = 0; n < outputs.length; n++) {
                outputString = outputString.concat(outputs[n].id.toString());
            }
        }

        String activatedString = "Inactive";
        if (activated == 1) {
            activatedString = "Active";
        }

        return "\n-----\n|Node ID: " + id + "\n|" + activatedString + "\n*" +
                "\n|Weight: " + weight + "\n|Threshold: " + threshold + "\n|Node inputs: " + inputString
                + "\n|Node outputs: " + outputString + "\n|Decay constant: " + tau +
                "\n*" + "\n|Sum of inputs: " + inputSum + "\n|Output value: " + outputVal + "\n|Decay count: "
                + tauCount + "\n-----\n";
    }

    // updates inputSum property based on node inputs
    public void updateInputSum() {
        if (inputs != null) {
            for (int i = 0; i < inputs.length; i++) {
                inputSum += inputs[i].outputVal;
            }
        }
    }

    // updates activated property based on inputSum and threshold, removes or adds
    // to activeNodes
    public void updateActivate(LinkedHashSet<Node> activeNodes) {
        if (inputSum >= threshold) {
            activated = 1;
            outputVal = weight;
            activeNodes.add(this);
        } else {
            activated = 0;
            activeNodes.remove(this);
        }
    }

    // updates node properties based on whether the decay constant has been reached
    public void updateTauCount(LinkedHashSet<Node> activeNodes) {
        if (tauCount > tau) {
            inputSum = 0;
            activated = 0;
            outputVal = 0;
            tauCount = 0;
            activeNodes.remove(this);
        }
    }
}
