import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class KargerMinCut {

    @SuppressWarnings({"unchecked"})
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("You should pass file with input data");
            System.exit(1);
        }
        int numberOfExperiments = 1;
        if (args.length == 2) {
            numberOfExperiments = Integer.parseInt(args[1]);
        }
        try(BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
            Map<Integer, List<Integer>> data = new HashMap<>();
            String line = null;
            while((line = in.readLine()) != null){
                String[] res = line.split("\\s");
                if (res.length > 0) {
                    Integer key = Integer.valueOf(res[0]);
                    List<Integer> adj = new ArrayList<>(res.length - 1);
                    for (int i = 1; i < res.length; i++) {
                        adj.add(Integer.valueOf(res[i]));
                    }
                    data.put(key, adj);
                }
            }

            //contraction(data, 1, 2);
            //System.out.println(data);

            int minCut = data.size();
            for (int exp = 0; exp < numberOfExperiments; exp++) {
                Map<Integer, List<Integer>> expData = copyMap(data);
                //System.out.println(""+expData+", size = "+expData.size());
                int size = expData.size();
                for (int t = 0; t < size - 2; t++) {
                    Integer key1 = new ArrayList<Integer>(expData.keySet()).get((int)(Math.random() * (expData.size() - 1)));
                    List<Integer> adj1 = expData.get(key1);
                    //System.out.println(adj1);
                    Integer key2 = adj1.get((int) (Math.random() * adj1.size())); 
                    //System.out.println("contraction " + key1 + ", " + key2);
                    contraction(expData, key1, key2);
                    //System.out.println(expData);
                }
                int localMinCut = expData.values().iterator().next().size();
                System.out.println("localMinCut = " + localMinCut);
                if (minCut > localMinCut) {
                    minCut = localMinCut;
                }
            }
            System.out.println("minCut = " + minCut);
        }
    }

    private static Map<Integer, List<Integer>> copyMap(Map<Integer, List<Integer>> map) {
        Map<Integer, List<Integer>> res = new HashMap<>();
        for(Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            res.put(entry.getKey(), new ArrayList<Integer>(entry.getValue()));
        }
        return res;
    }

    private static void contraction(Map<Integer, List<Integer>> expData, Integer key1, Integer key2) {
        List<Integer> adj1 = expData.get(key1);
        List<Integer> adj2 = expData.get(key2);
        for (Iterator<Integer> it = adj2.iterator(); it.hasNext();) {
            Integer current = it.next();
            if (current.equals(key1)) {
                it.remove();
            }
        }
        adj1.addAll(adj2);
        expData.remove(key2);
        for (Iterator<Integer> it = adj1.iterator(); it.hasNext();) {
            Integer current = it.next();
            if (current.equals(key2)) {
                it.remove();
            }
            else {
                List<Integer> currentAdj = expData.get(current);
                int count = 0;
                for (Iterator<Integer> itr = currentAdj.iterator(); itr.hasNext();) {
                    if (itr.next().equals(key2)) {
                       itr.remove();
                       count++;
                    }                                
                }
                for (int i = 0; i < count; i++) {
                    currentAdj.add(key1);
                }
            }
        }
    }
}

