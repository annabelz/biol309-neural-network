// import java.util.Random;

public class main {
    public static void main(String[] args) {

        main main = new main();

        // TODO: modify the example # to see the system with vs. without noise
        int example = 2;

        switch (example) {
            case 1: // example without noise
                main.ex1();
            case 2: // example with noise
                main.ex2();
        }
    }

    public void ex1() {
        System.out.println("Simulating case 1:");
        // create arguments for NeuralNet constructor
        Node startNodes[] = new Node[2];
        Node allNodes[] = new Node[3];
        Integer t = 4;
        // create arguments for Node constructor for node1
        Node inputs1[] = null;
        Node outputs1[] = new Node[1];
        Node node1 = new Node(0, 5, 7, inputs1, outputs1, 2);
        // create arguments for Node constructor for node2
        Node inputs2[] = null;
        Node outputs2[] = new Node[1];
        Node node2 = new Node(1, 2, 3, inputs2, outputs2, 1);
        // create arguments for Node constructor for node3
        Node inputs3[] = new Node[2];
        inputs3[0] = node1;
        inputs3[1] = node2;
        Node outputs3[] = null;
        Node node3 = new Node(2, 2, 7, inputs3, outputs3, 1);
        // set outputs for node1 and node2
        outputs1[0] = node3;
        outputs2[0] = node3;
        // add all created nodes to allNodes
        allNodes[0] = node1;
        allNodes[1] = node2;
        allNodes[2] = node3;
        // start simulation with nodes 1 and 2
        startNodes[0] = node1;
        startNodes[1] = node2;

        NeuralNet NN = new NeuralNet(startNodes, allNodes, t, false);
        NN.startNN();
    }

    public void ex2() {
        System.out.println("Simulating case 2:");
        // create arguments for NeuralNet constructor
        Node startNodes[] = new Node[2];
        Node allNodes[] = new Node[3];
        Integer t = 4;
        // create arguments for Node constructor for node1
        Node inputs1[] = null;
        Node outputs1[] = new Node[1];
        Node node1 = new Node(0, 5, 7, inputs1, outputs1, 2);
        // create arguments for Node constructor for node2
        Node inputs2[] = null;
        Node outputs2[] = new Node[1];
        Node node2 = new Node(1, 2, 3, inputs2, outputs2, 1);
        // create arguments for Node constructor for node3
        Node inputs3[] = new Node[2];
        inputs3[0] = node1;
        inputs3[1] = node2;
        Node outputs3[] = null;
        Node node3 = new Node(2, 2, 7, inputs3, outputs3, 1);
        // set outputs for node1 and node2
        outputs1[0] = node3;
        outputs2[0] = node3;
        // add all created nodes to allNodes
        allNodes[0] = node1;
        allNodes[1] = node2;
        allNodes[2] = node3;
        // start simulation with nodes 1 and 2
        startNodes[0] = node1;
        startNodes[1] = node2;

        NeuralNet NN = new NeuralNet(startNodes, allNodes, t, true);
        NN.startNN();
    }
}