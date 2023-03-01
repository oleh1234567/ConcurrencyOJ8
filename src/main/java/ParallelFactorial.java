import onjava.Timer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ParallelFactorial {
    static final int COUNT = 30;

    public static boolean isFactorial(long n) {
        int factorial = 1;
        for (int i = 1; factorial <= n; i++) {
            factorial = factorial * i;
            if(n == factorial)
                return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        Timer timer = new Timer();
        List<String> factorials =
                LongStream.iterate(1, i -> i + 1)
                        .parallel()
                        .filter(ParallelFactorial::isFactorial)
                        .limit(COUNT)
                        .mapToObj(Long::toString)
                        .collect(Collectors.toList());

        System.out.println(timer.duration());
        Files.write(Paths.get("factorials.txt"), factorials,
                StandardOpenOption.CREATE);
    }
}
