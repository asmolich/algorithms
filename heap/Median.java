import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Median {
    public static void main(final String[] args) throws Exception {
        final Path file = Paths.get(args[0]);
        final List<String> stringNums = Files.readAllLines(file, Charset.defaultCharset());

        final List<Integer> data = stringNums.stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

        // Max queue
        final PriorityQueue<Integer> lo = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(final Integer o1, final Integer o2) {
                return o2.compareTo(o1);
            }
        });
        // Min queue
        final PriorityQueue<Integer> hi = new PriorityQueue<>();

        int medianSum = data.stream().mapToInt(element -> {
                final int loSize = lo.size();
                if (loSize == 0) {
                    lo.offer(element);
                } else {
                    final int hiSize = hi.size();
                    final Integer loMax = lo.peek();
                    if (element < loMax) {
                        lo.offer(element);
                        if (loSize > hiSize) {
                            hi.offer(lo.poll());
                        }
                    } else {
                        hi.offer(element);
                        if (loSize <= hiSize) {
                            lo.offer(hi.poll());
                        }
                    }
                }

                final Integer localMedian = lo.peek();
                return localMedian;
            }).sum();

        System.out.println(medianSum % 10000);
    }
}
