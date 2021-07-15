import java.util.Random;

import edu.upenn.cis573.graphs.*;

public class ReliabilityEvaluator {

    private static Random rand = new Random(System.currentTimeMillis());

    public static void main(String[] args) {

        // basically a Scanner
        In in = new In("graph.txt");

        // construct a Graph from the input file
        System.out.println("Building the graph, please wait a moment...");
        Graph graph = new Graph(in);
        System.out.println("Done building the graph.");

        ReliablePathFinder finder = new ReliablePathFinder(graph);

        // evaluate the reliability and execution time of ReliablePathFinder.findPath
        // using 1000 trials
        int success = 0, fail = 0;
        System.out.println("Running the trials, please wait a moment...");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            if (runTrial(finder)) {
                success++;
            } else {
                fail++;
            }
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("Success: " + success + "; Fail: " + fail + "; Time: " + time + "ms");
    }

    private static boolean runTrial(ReliablePathFinder finder) {
        // randomly generate source and destination vertices
        int src = rand.nextInt(10000);
        int dest = rand.nextInt(10000);

        try {
            finder.findPath(src, dest);
            // if it returns, then it must have passed the acceptance test
            return true;
        } catch (PathNotFoundException e) {
            // if it throws an exception, it must have failed to find a path
            return false;
        }

    }

}
