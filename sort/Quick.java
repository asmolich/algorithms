import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Quick {

    private static int comparisons = 0;

    private static PivotStrategy strategy = PivotStrategy.BEGIN;

    private static enum PivotStrategy {
        BEGIN, END, RANDOM, MEDIAN;
    }

    private Quick(){}

    public static void sort(int[] a) {
        if (a == null)
            throw new IllegalArgumentException("Array must not be null");
        
        if (a.length < 2)
            return;

        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int start, int end) {
        if (end <= start) return;
        int pivotIndex = choosePivot(a, start, end);
        int partitionIndex = partition(a, start, end, pivotIndex);
        sort(a, start, partitionIndex - 1);
        sort(a, partitionIndex + 1, end);
    }

    public static int select(int[] a, int i) {
        if (a == null)
            throw new IllegalArgumentException("Array must not be null");
                
        if (i >= 0 && a.length <= i)
            return -1;

        strategy = PivotStrategy.END;

        return a[selectIndex(a, 0, a.length - 1, i)];
    }

    private static int selectIndex(int[] a, int start, int end, int i) {
        if (end <= start) return start;
        int pivotIndex = choosePivot(a, start, end);
        int partitionIndex = partition(a, start, end, pivotIndex);
        if (partitionIndex == i) {
            return partitionIndex; 
        } else if (partitionIndex > i) {
            return selectIndex(a, start, partitionIndex - 1, i);
        } else {
            return selectIndex(a, partitionIndex + 1, end, i - partitionIndex);
        }
    }

    private static int choosePivot(int[] a, int start, int end) {
        int pivotIndex; 
        switch(strategy) {
            case END:
                pivotIndex = end;
                break;
            case RANDOM:
                pivotIndex = (int)(start + Math.random()*(end - start));
                break;
            case MEDIAN:
                int mid = start + (end - start) / 2;
                pivotIndex = median(a, start, mid, end);
                break;
            case BEGIN:
            default:
                pivotIndex = start;
                break;
        }
        return pivotIndex;
    }

    private static int partition(int[] a, int start, int end, int pivotIndex) {
        comparisons += end - start;
        if (pivotIndex != start)
            exch(a, pivotIndex, start);
        int pivot = a[start];
        int i = start + 1, j = start + 1;
        for (int k = start + 1; k <= end; k++) {
            if(less(a[k], pivot)) {
                if (i == j)
                    i++;
                else
                    exch(a, k, i++);
            }
            j++;
        }
        exch(a, start, i - 1);
        return i - 1;
    }

    private static boolean less(int a, int b) {
        return a < b;
    }

    private static void exch(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static int median(int[] a, int i, int j, int k) {
        int mid;
        if (a[i] > a[j]) {
            if (a[j] > a[k]) {
                mid = j;
            } else if (a[i] > a[k]) {
                mid = k;
            } else {
                mid = i;
            }
        } else {
            if (a[i] > a[k]) {
                mid = i;
            } else if (a[j] > a[k]) {
                mid = k;
            } else {
                mid = j;
            }
        }
        return mid;
    }
    
    public static int getComparisonsCount(){
        return comparisons;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("You should pass file with input data");
            System.exit(1);
        }
        if (args.length == 2) {
            strategy = PivotStrategy.valueOf(args[1].trim().toUpperCase());
        }
        try(BufferedReader in = new BufferedReader(new FileReader(args[0]))){
            List<Integer> data = new ArrayList<>(100);
            String line = null;
            while((line = in.readLine()) != null){
                data.add(Integer.valueOf(line));
            }
            int[] test = new int[data.size()];
            int i = 0;
            for(Integer elem : data) {
                test[i++] = elem;
            }

            System.out.println(Arrays.toString(test));
            Quick.sort(test);
            System.out.println(Arrays.toString(test));
            System.out.println(Quick.getComparisonsCount());
        } 
    }
}
