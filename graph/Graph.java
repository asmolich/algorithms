import java.util.ArrayList;
import java.util.List;

/**
  * The <tt>Graph</tt> class represents an undirected graph of vertices from 0 to <em>vertices</em> - 1.
  * It supports the following two primary operations: add an edge to the graph, iterate over all of the vertices
  * adjacent to a vertex. It also provides methods for returning the number of vertices <em>vertices</em> and
  * the number of edges <em>edges</em>. Parallel edges and self-loops are permitted.
  * <p>
  * This implementation uses an adjacency-lists representation, which is a vertex-indexed array of {@link List}
  * objects.
  */

public class Graph {

    private final int vertices;
    private int edges = 0;
    private List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public Graph(final int v) {
        vertices = v;
        adj = (List<Integer>[]) new List[vertices];
        for (int i = 0; i < vertices; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
    public Graph(final List<Integer>[] adjc) {
        vertices = adjc.length;
        adj = (List<Integer>[]) new List[vertices];
        for (int i = 0; i < vertices; i++) {
            final int listSize = adjc[i].size();
            adj[i] = new ArrayList<>(listSize);
            adj[i].addAll(adjc[i]);
            edges += listSize;
        }
    }

    public void addEdge(int i, int j) {
        if (i < 0 || i >= vertices) throw new IndexOutOfBoundsException();
        if (j < 0 || j >= vertices) throw new IndexOutOfBoundsException();
        adj[i].add(j);
        adj[j].add(i);
        edges++;
    }

    public int getVertices() {
        return vertices;
    }

    public int getEdges() {
        return edges;
    }
}
