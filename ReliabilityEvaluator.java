import java.util.Random;

import edu.upenn.cis573.graphs.*;

public class ReliabilityEvaluator {

    private static Random rand = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        In in = new In("graph.txt");
        System.out.println("Building the graph, please wait a moment...");
        Graph graph = new Graph(in);
        System.out.println("Done building the graph.");

        ReliablePathFinder finder = new ReliablePathFinder(graph);

        ReliablePathFinder.SearchType[][] orders = {
                {
                        ReliablePathFinder.SearchType.BFS_SRC_DEST,
                        ReliablePathFinder.SearchType.DFS_SRC_DEST,
                        ReliablePathFinder.SearchType.BFS_DEST_SRC,
                        ReliablePathFinder.SearchType.DFS_DEST_SRC
                },
                {
                        ReliablePathFinder.SearchType.DFS_SRC_DEST,
                        ReliablePathFinder.SearchType.BFS_SRC_DEST,
                        ReliablePathFinder.SearchType.DFS_DEST_SRC,
                        ReliablePathFinder.SearchType.BFS_DEST_SRC
                },
                {
                        ReliablePathFinder.SearchType.BFS_SRC_DEST,
                        ReliablePathFinder.SearchType.BFS_DEST_SRC,
                        ReliablePathFinder.SearchType.DFS_SRC_DEST,
                        ReliablePathFinder.SearchType.DFS_DEST_SRC
                },
                {
                        ReliablePathFinder.SearchType.DFS_SRC_DEST,
                        ReliablePathFinder.SearchType.DFS_DEST_SRC,
                        ReliablePathFinder.SearchType.BFS_SRC_DEST,
                        ReliablePathFinder.SearchType.BFS_DEST_SRC
                }
        };

        for (int i = 0; i < orders.length; i++) {
            evaluateOrder(finder, orders[i], i + 1);
        }
    }

    private static void evaluateOrder(ReliablePathFinder finder, ReliablePathFinder.SearchType[] order, int orderNumber) {
        int success = 0, fail = 0;
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            if (runTrial(finder, order)) {
                success++;
            } else {
                fail++;
            }
        }

        long time = System.currentTimeMillis() - start;
        System.out.println("Order " + orderNumber + " - Success: " + success + "; Fail: " + fail + "; Time: " + time + "ms");
    }

    private static boolean runTrial(ReliablePathFinder finder, ReliablePathFinder.SearchType[] order) {
        int src = rand.nextInt(10000);
        int dest = rand.nextInt(10000);

        try {
            finder.findPath(src, dest, order);
            return true;
        } catch (PathNotFoundException e) {
            return false;
        }
    }
}
