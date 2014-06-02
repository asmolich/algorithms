import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Inversion {

    private Inversion(){}

    public static long count(final int[] a) {
        if (a == null)
            throw new IllegalArgumentException("Array must not be null");

        if (a.length < 2)
            return 0;

        int[] aux = new int[a.length];
        
        return sortAndCount(a, aux, 0, a.length - 1);
    }

    private static long sortAndCount(final int[] a, final int[] aux, final int start, final int end) {

        //System.out.println(Arrays.toString(a));
        //System.out.println("start = " + start + ", end = " + end);
        if (end - start <= 0) return 0;

        final int mid = start + (end - start) / 2;
        final long leftCount  = sortAndCount(a, aux, start, mid);
        final long rightCount = sortAndCount(a, aux, mid + 1, end);
        final long splitCount = mergeAndCountSplitInv(a, aux, start, end);
        //System.out.println("count = " + (leftCount + rightCount + splitCount));
        return leftCount + rightCount + splitCount;
    }

    private static long mergeAndCountSplitInv(final int[] a, final int[] aux, final int start, final int end) {
        int leftMax = start + (end - start) / 2;
        int rightMax = end;
        int i = start;
        int j = leftMax + 1;
        long count = 0;

        if (a[leftMax] < a[j]) {
            return count;
        }
        for(int k = start; k <= end; k++) {
            if(i > leftMax) {
                aux[k] = a[j++];
            } else if(j > rightMax) {
                aux[k] = a[i++];
            } else if(a[i] < a[j]){
                aux[k] = a[i++];
            } else {
                aux[k] = a[j++];
                count += leftMax - i + 1;
            }
        }
        System.arraycopy(aux, start, a, start, end - start + 1);
        return count;
    }

    public static void main(final String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("You should pass file with input data");
            System.exit(1);
        }
        try(BufferedReader in = new BufferedReader(new FileReader(args[0]))){
            List<Integer> data = new ArrayList<>(100);
            String line = null;
            while((line = in.readLine()) != null){
                data.add(Integer.valueOf(line));
            }
            final int[] test = new int[data.size()];
            int i = 0;
            for(Integer elem : data) {
                test[i++] = elem;
            }
                //new int[]//{1,3,5,2,4,6};
                //{1, 2, 3, 4, 5, 6, 7, 8, 10, 9};

            //System.out.println(Arrays.toString(test));
            System.out.println(Inversion.count(test));
            //System.out.println(Arrays.toString(test));
        }
    }
}

