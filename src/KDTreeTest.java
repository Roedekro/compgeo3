import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class KDTreeTest {

    private static final Random random = new Random();

    private static final int N_MIN = 1000;
    private static final int N_MAX = 50000;
    private static final int N_GRANULARITY = 1000;

    private static final int X_MIN = 0;
    private static final int X_MAX = 100000;
    private static final int Y_MIN = 0;
    private static final int Y_MAX = 100000;
    private static final int Z_MIN = 0;
    private static final int Z_MAX = 100000;

    public static void main(String[] args) throws IOException {
        Map<Integer, Long> results = new HashMap<>();
        for (int n = N_MIN; n <= N_MAX; n += N_GRANULARITY) {
            // Generate all n points
            System.out.println("Generating points for " + n + "/" + N_MAX);
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                Point p = null;
                while (p == null || points.contains(p)) {
                    int x = random.nextInt(X_MAX - X_MIN) + X_MIN;
                    int y = random.nextInt(Y_MAX - Y_MIN) + Y_MIN;
                    int z = random.nextInt(Z_MAX - Z_MIN) + Z_MIN;
                    p = new Point(x, y, z);
                }
                points.add(p);
            }

            // Create a range that is effectively 50% width and height of
            // the area storing the points, placed right in the middle
            int x1 = -(X_MAX - X_MIN) / 2;
            int x2 = (X_MAX - X_MIN) / 2;
            int y1 = -(Y_MAX - Y_MIN) / 2;
            int y2 = (Y_MAX - Y_MIN) / 2;
            int z1 = -(Z_MAX - Z_MIN) / 2;
            int z2 = (Z_MAX - Z_MIN) / 2;
            Range range = new Range(x1, x2, y1, y2, z1, z2);

            // Build the tree and compute the points for the range
            System.out.println("Building tree...");
            KDTreeNode root = KDTree.buildKDTree(points);
            System.out.println("Calculating points in range...");
            long startTime = System.currentTimeMillis();
            List<Point> result = KDTree.searchKDTree(root, range);
            long endTime = System.currentTimeMillis();

            // Verify the result
            List<Point> bruteForceResult = new ArrayList<>();
            for (Point p : points) {
                if (range.contains(p)) bruteForceResult.add(p);
            }
            if (!(bruteForceResult.containsAll(result) && result.containsAll(bruteForceResult))) {
                throw new RuntimeException("Result is wrong!");
            }

            // Save the results to the map
            results.put(n, endTime - startTime);
        }

        // Save results to disk
        System.out.println("Saving results to disk...");
        File file = new File("kdtree/result_" + System.currentTimeMillis() + ".csv");
        file.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(file);
        fw.write("n,runtime" + System.lineSeparator());
        for (Map.Entry<Integer,Long> entry : results.entrySet()) {
            fw.write(entry.getKey() + "," + entry.getValue() + System.lineSeparator());
        }
        fw.close();
    }

}
