// Firefly
// Jerred Shepherd

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class firefly {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("firefly.in");
        File outputFile = new File("firefly.out");

        Scanner scanner = new Scanner(inputFile);
        PrintWriter printWriter = new PrintWriter(outputFile);

        int fireflyNum = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splitLine = line.split(" ");

            double range = Double.parseDouble(splitLine[0]);
            double chaserX = Double.parseDouble(splitLine[1]);
            double chaserY = Double.parseDouble(splitLine[2]);

            Point chaser = new Point(chaserX, chaserY);

            if (range == 0 && chaserX == 0 && chaserY == 0) {
                break;
            }

            fireflyNum += 1;

            boolean caught = false;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                splitLine = line.split(" ");

                double fireflyX = Double.parseDouble(splitLine[0]);
                double fireflyY = Double.parseDouble(splitLine[1]);

                Point firefly = new Point(fireflyX, fireflyY);

                System.out.println(firefly);

                if (fireflyX == -1 && fireflyY == -1) {
                    break;
                }

                if (caught) {
                    continue;
                }

                if (canChaserReachFireflyInNextTeleport(chaser, firefly, range)) {
                    caught = true;
                    System.out.println(String.format("Firefly %s caught at (%s,%s)",
                            fireflyNum, (int) firefly.x, (int) firefly.y));
                    printWriter.println(String.format("Firefly %s caught at (%s,%s)",
                            fireflyNum, (int) firefly.x, (int) firefly.y));

                } else {
                    chaser = findFurthestPointBetweenPointsInRange(chaser, firefly, range);
                    System.out.println(String.format("Moved to %s", chaser));
                }
            }

            if (!caught) {
                System.out.println(String.format("Firefly %s not caught", fireflyNum));
                printWriter.println(String.format("Firefly %s not caught", fireflyNum));
            }
        }

        printWriter.close();
    }

    private static Point findFurthestPointBetweenPointsInRange(Point chaser, Point firefly, double range) {
        double distance = calculateDistanceBetweenPoints(chaser, firefly);
        double newX = chaser.x - (range * (chaser.x - firefly.x)) / distance;
        double newY = chaser.y - (range * (chaser.y - firefly.y)) / distance;
        Point newPoint = new Point(newX, newY);
        return newPoint;
    }

    private static boolean canChaserReachFirefly(Point chaser, Point firefly) {
        return calculateDistanceBetweenPoints(chaser, firefly) <= 1;
    }

    private static boolean canChaserReachFireflyInNextTeleport(Point chaser, Point firefly, double range) {
        return calculateDistanceBetweenPoints(chaser, firefly) < range + 1;
    }

    private static double calculateDistanceBetweenPoints(Point a, Point b) {
        double yDiff = a.y - b.y;
        double xDiff = a.x - b.x;
        double yDiffSquared = Math.pow(yDiff, 2);
        double xDiffSquared = Math.pow(xDiff, 2);
        double distance = Math.sqrt(yDiffSquared + xDiffSquared);
//        System.out.println(String.format("Distance %s l1 %s r1 %s | a %s b %s", calculateDistanceBetweenPoints, yDiff, xDiff, a, b));
        return distance;
    }

    public static class Point {
        double x;
        double y;

        public Point(double x, double y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
