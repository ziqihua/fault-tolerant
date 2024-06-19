import java.util.*;

import edu.upenn.cis573.graphs.*;

public class ReliablePathFinder extends PathFinder {

    public ReliablePathFinder(Graph g) {
        super(g);
    }

    // Enum to define the type of search to perform
    public enum SearchType {
        BFS_SRC_DEST,
        DFS_SRC_DEST,
        BFS_DEST_SRC,
        DFS_DEST_SRC
    }

    // Method to run a specific search type
    private List<Integer> runSearch(SearchType type, int src, int dest) {
        switch (type) {
            case BFS_SRC_DEST:
                return bfs(src, dest);
            case DFS_SRC_DEST:
                return dfs(src, dest);
            case BFS_DEST_SRC:
                return bfs(dest, src);
            case DFS_DEST_SRC:
                return dfs(dest, src);
            default:
                return null;
        }
    }

    public List<Integer> findPath(int src, int dest, SearchType[] order) throws PathNotFoundException {
        for (SearchType type : order) {
            List<Integer> path = runSearch(type, src, dest);
            if (type == SearchType.BFS_DEST_SRC || type == SearchType.DFS_DEST_SRC) {
                Collections.reverse(path);
            }
            if (isValidPath(src, dest, path)) {
                return path;
            }
        }
        throw new PathNotFoundException();
    }

    // Overloaded findPath method to use the default ordering
    @Override
    public List<Integer> findPath(int src, int dest) throws PathNotFoundException {
        SearchType[] defaultOrder = {
                SearchType.BFS_SRC_DEST,
                SearchType.DFS_SRC_DEST,
                SearchType.BFS_DEST_SRC,
                SearchType.DFS_DEST_SRC
        };
        return findPath(src, dest, defaultOrder);
    }


    public boolean isValidPath(int src, int dest, List<Integer> path) {
        if (path == null || path.isEmpty()) {
            return false;
        }

        if (path.get(0) != src || path.get(path.size() - 1) != dest) {
            return false;
        }

        for (int i = 0; i < path.size() - 1; i++) {
            int current = path.get(i);
            int next = path.get(i + 1);
            boolean edgeExists = false;

            for (int adjacent : graph.adj(current)) {
                if (adjacent == next) {
                    edgeExists = true;
                    break;
                }
            }

            if (!edgeExists) {
                return false;
            }
        }

        return true;
    }
}
