// import java.util.Random;

public class main {
    public static void main(String[] args) {

        Node startNodes[] = new Node[2];
        Node allNodes[] = new Node[3];
        Integer t = 5;

        Node inputs1[] = null;
        Node outputs1[] = new Node[1];

        Node node1 = new Node(0, 5, 7, inputs1, outputs1, 2);

        Node inputs2[] = null;
        Node outputs2[] = new Node[1];

        Node node2 = new Node(1, 2, 3, inputs2, outputs2, 1);

        Node inputs3[] = new Node[2];
        inputs3[0] = node1;
        inputs3[1] = node2;

        Node outputs3[] = null;

        Node node3 = new Node(2, 2, 7, inputs3, outputs3, 1);

        outputs1[0] = node3;
        outputs2[0] = node3;

        allNodes[0] = node1;
        allNodes[1] = node2;
        allNodes[2] = node3;

        startNodes[0] = node1;
        startNodes[1] = node2;

        NeuralNet NN = new NeuralNet(startNodes, allNodes, t);

        // System.out.println(NN.toString());

        NN.startNN();

    }
}