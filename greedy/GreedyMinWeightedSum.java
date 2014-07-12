import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

public class GreedyMinWeightedSum {
    private static class Job {
        static final Comparator<Job> RATIO = new Comparator<Job>() {

            @Override
            public int compare(final Job o1, final Job o2) {
                return Double.compare(o2.getRatio(), o1.getRatio());
            }
        };
        static final Comparator<Job> DIFF = new Comparator<Job>() {

            @Override
            public int compare(final Job o1, final Job o2) {
                final int cmp = o2.getDifference() - o1.getDifference();
                if (cmp == 0) {
                    return o2.weight - o1.weight;
                }
                return cmp;
            }
        };
        private int weight;
        private int length;

        Job(final int w, final int l) {
            weight = w;
            length = l;
        }

        public double getRatio() {
            return weight / (double) length;
        }

        public int getDifference() {
            return weight - length;
        }

        public int getWeight() {
            return weight;
        }

        public int getLength() {
            return length;
        }

        @Override
        public String toString() {
            return "Job [weight=" + weight + ", length=" + length + "]";
        }
    }

    public static void main(final String[] args) throws IOException {
        final Path file = Paths.get(args[0]);
        final List<String> stringNums = Files.readAllLines(file,
                Charset.defaultCharset());

        // final int numberOfJobs = Integer.parseInt(stringNums.iterator().next());

        final List<Job> jobs = toJobList(stringNums);

        System.out.println("DIFF\t" + sumOfWeightedCompletion(jobs, Job.DIFF));
        System.out.println("RATIO\t" + sumOfWeightedCompletion(jobs, Job.RATIO));
    }

    private static long sumOfWeightedCompletion(final List<Job> jobs,
            final Comparator<Job> comparator) {

        //jobs.stream().sorted(comparator).sequential().forEach(i -> System.out.println(i));
        return jobs.stream().sorted(comparator).sequential()
                .mapToLong(new ToLongFunction<Job>() {
                    long completionTime = 0;

                    @Override
                    public long applyAsLong(final Job job) {
                        completionTime += job.getLength();
                        return completionTime * job.getWeight();
                    }
                }).sum();
    }

    private static List<Job> toJobList(final List<String> stringNums) {
        final List<Job> jobs = stringNums.stream().skip(1)
                .map(GreedyMinWeightedSum::toJob)
                .collect(Collectors.<Job> toList());
        return jobs;
    }

    public static Job toJob(final String input) {
        final String[] split = input.split("\\s+");
        final int weight = Integer.parseInt(split[0]);
        final int length = Integer.parseInt(split[1]);
        return new Job(weight, length);
    }
}
