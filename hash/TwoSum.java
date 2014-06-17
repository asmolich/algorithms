import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TwoSum {

    public static void main(String[] args) throws Exception {
        final Path file = Paths.get(args[0]);
        final List<String> strings = Files.readAllLines(file, Charset.defaultCharset());

        final Set<Long> set = strings.stream().mapToLong(Long::parseLong).boxed().collect(Collectors.toSet());

        //System.out.println(set);

        long count = LongStream.rangeClosed(-10000L, 10000L)
                               .parallel()
                               .filter(t -> set.stream()
                                               .parallel()
                                               .filter(i -> set.contains(t-i))
                                               .findFirst()
                                               .isPresent())
                               .count();
        System.out.println(count);
    }
}
