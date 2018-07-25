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

            Coordinate chaser = new Coordinate(chaserX, chaserY);

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

                Coordinate firefly = new Coordinate(fireflyX, fireflyY);

                System.out.println(firefly);

                if (fireflyX == -1 && fireflyY == -1) {
                    break;
                }

                if (!caught && canReach(chaser, firefly)) {
                    caught = true;
                    System.out.println(String.format("Firefly %s caught at (%s,%s)",
                            fireflyNum, (int) firefly.x, (int) firefly.y));
                } else {
                    // this part needs work
                    double half = range / 2;
                    chaser.x += half;
                    chaser.y += half;
//                    System.out.println(String.format("Moved to %s", chaser));
                }
            }

            if (!caught) {
                System.out.println(String.format("Firefly %s not caught", fireflyNum));
            }
        }
    }

    private static boolean canReach(Coordinate chaser, Coordinate firefly) {
        return distance(chaser, firefly) <= 1;
    }

    private static double distance(Coordinate l, Coordinate r) {
        double x = Math.pow(l.y - r.y, 2);
        double y = Math.pow(l.x - r.x, 2);
        double distance = Math.sqrt(x + y);
//        System.out.println(String.format("Distance %s l1 %s r1 %s | l %s r %s", distance, x, y, l, r));
        return distance;
    }

    public static class Coordinate {
        double x;
        double y;

        public Coordinate(double x, double y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
