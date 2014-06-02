import java.util.Arrays;

public class Merge {

    private Merge(){}

    public static void sort(Comparable[] a) {
        if (a == null)
            throw new IllegalArgumentException("Array must not be null");

        if (a.length < 2)
            return;

        Comparable[] aux = new Comparable[a.length];

        sort(a, aux, 0, a.length - 1);

        assert isSorted(a);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int start, int end) {
        assert start <= end && end < a.length;
        System.out.print(Arrays.toString(a));
        System.out.println(" isSorted = " + isSorted(a));
        
        if (end > start) {
            int mid = start + (end - start) / 2;
            sort(a, aux, start, mid);
            sort(a, aux, mid + 1, end);
            merge(a, aux, start, end);
        }
        assert isSorted(a, start, end);
    }

    private static void merge(Comparable[] a, Comparable[] aux, int start, int end) {
        int leftMax = start + (end - start) / 2;
        int rightMax = end;
        int i = start;
        int j = leftMax + 1;

        assert isSorted(a, i, leftMax);
        assert isSorted(a, j, rightMax);

        System.out.println("i = " + i + ", leftMax = " + leftMax + ", j = " + j + ", rightMax = " + rightMax);
        if (less(a[leftMax], a[j])) {
            return;
        }
        for(int k = start; k <= end; k++) {
            if(i > leftMax)           aux[k] = a[j++];
            else if(j > rightMax)     aux[k] = a[i++];
            else if(less(a[i], a[j])) aux[k] = a[i++];
            else                      aux[k] = a[j++];
        }
        System.arraycopy(aux, start, a, start, end - start + 1);
    }

    private static boolean less(Comparable o1, Comparable o2) {
        return o1.compareTo(o2) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int start, int end) {
        for (int i = start + 1; i <= end; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

    public static void main(String[] args){
        Integer[] test = new Integer[]{2,3,41,5,243,3,21,22,55,223,14,665,3,2,3,4,36,33,1};
        System.out.println(Arrays.toString(test));
        Merge.sort(test);
        System.out.println(Arrays.toString(test));
    }
}
