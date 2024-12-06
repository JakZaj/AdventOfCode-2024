import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    enum MoveDirection {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT
    }

    public static void main(String[] args) {
        String line = "";
        Point guardPosition = null;
        Map<Point, Boolean> obstructions = new HashMap<>();
        int ySize = 0, xSize = 0;

        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            char character;

            while (myReader.hasNextLine()) {

                line = myReader.nextLine();

                for (int i = 0; i < line.length(); i++){
                    character = line.charAt(i);

                    if (character == '^')
                        guardPosition = new Point(i, ySize);

                    if (character == '#')
                        obstructions.put(new Point(i, ySize), true);
                }
                ySize++;
            }

            xSize = line.length();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        Tuple2<Integer, ArrayList<Point>> tuple2 = getVisitedPosition(new Point(guardPosition.x, guardPosition.y), obstructions, new Point(xSize, ySize));

        System.out.println(tuple2.getFirst());
        System.out.println(getVisitedPositionPartTwo(new Point(guardPosition.x, guardPosition.y), obstructions, new Point(xSize, ySize), tuple2.getSecond()));
    }

    public static Tuple2<Integer, ArrayList<Point>> getVisitedPosition(Point guardPosition, Map<Point, Boolean> obstructions, Point areaSize){
        Map<Point, Boolean> visitedPosition = new HashMap<>();
        MoveDirection moveDirection = MoveDirection.TOP;
        ArrayList<Point> visitedArray = new ArrayList<>();

        while (guardPosition.x < areaSize.x && guardPosition.x >= 0 && guardPosition.y < areaSize.y && guardPosition.y >= 0){
            switch (moveDirection){
                case TOP :
                    if (obstructions.get(new Point(guardPosition.x, guardPosition.y - 1)) == null){
                        visitedPosition.putIfAbsent(new Point(guardPosition.x, guardPosition.y), true);
                        if (!visitedArray.contains(new Point(guardPosition.x, guardPosition.y)))
                            visitedArray.addLast(new Point(guardPosition.x, guardPosition.y));

                        guardPosition.y--;
                    } else {
                        moveDirection = MoveDirection.RIGHT;
                    }
                    break;
                case RIGHT:
                    if (obstructions.get(new Point(guardPosition.x + 1, guardPosition.y)) == null){
                        visitedPosition.putIfAbsent(new Point(guardPosition.x, guardPosition.y), true);
                        if (!visitedArray.contains(new Point(guardPosition.x, guardPosition.y)))
                            visitedArray.addLast(new Point(guardPosition.x, guardPosition.y));

                        guardPosition.x++;
                    } else {
                        moveDirection = MoveDirection.BOTTOM;
                    }
                    break;
                case BOTTOM:
                    if (obstructions.get(new Point(guardPosition.x, guardPosition.y + 1)) == null){
                        visitedPosition.putIfAbsent(new Point(guardPosition.x, guardPosition.y), true);
                        if (!visitedArray.contains(new Point(guardPosition.x, guardPosition.y)))
                            visitedArray.addLast(new Point(guardPosition.x, guardPosition.y));

                        guardPosition.y++;
                    } else {
                        moveDirection = MoveDirection.LEFT;
                    }
                    break;
                case LEFT:
                    if (obstructions.get(new Point(guardPosition.x - 1, guardPosition.y)) == null){
                        visitedPosition.putIfAbsent(new Point(guardPosition.x, guardPosition.y), true);
                        if (!visitedArray.contains(new Point(guardPosition.x, guardPosition.y)))
                            visitedArray.addLast(new Point(guardPosition.x, guardPosition.y));

                        guardPosition.x--;
                    } else {
                        moveDirection = MoveDirection.TOP;
                    }
                    break;
            }
        }
        return new Tuple2<>(visitedPosition.size(), visitedArray);
    }

    public static int getVisitedPositionPartTwo(Point guardPosition, Map<Point, Boolean> obstructions, Point areaSize, ArrayList<Point> visitedPosition){
        int diffrentPositionsCount = 0;
        visitedPosition.removeFirst();

        for (Point point : visitedPosition){
            if (obstructions.get(point) != null)
                    continue;

            obstructions.put(point, true);
            if (isStuckInLoop(new Point(guardPosition.x, guardPosition.y), MoveDirection.TOP, obstructions, areaSize))
                diffrentPositionsCount++;

            obstructions.remove(point);
        }
        return diffrentPositionsCount;
    }

    public static boolean isStuckInLoop(Point guardPosition, MoveDirection moveDirection, Map<Point, Boolean> obstructions, Point areaSize){

        Map<Point, Integer> visitedCount = new HashMap<>();

        while (guardPosition.x < areaSize.x && guardPosition.x >= 0 && guardPosition.y < areaSize.y && guardPosition.y >= 0){
            switch (moveDirection){
                case TOP :

                    if (obstructions.get(new Point(guardPosition.x, guardPosition.y - 1)) == null){
                        if (visitedCount.get(new Point(guardPosition.x, guardPosition.y)) == null){
                            visitedCount.put(new Point(guardPosition.x, guardPosition.y), 1);
                        } else {
                            visitedCount.replace(new Point(guardPosition.x, guardPosition.y), visitedCount.get(new Point(guardPosition.x, guardPosition.y)) + 1);
                            if (visitedCount.get(new Point(guardPosition.x, guardPosition.y)) > 4)
                                return true;
                        }

                        guardPosition.y--;
                    } else {
                        moveDirection = MoveDirection.RIGHT;
                    }
                    break;
                case RIGHT:

                    if (obstructions.get(new Point(guardPosition.x + 1, guardPosition.y)) == null){
                        if (visitedCount.get(new Point(guardPosition.x, guardPosition.y)) == null){
                            visitedCount.put(new Point(guardPosition.x, guardPosition.y), 1);
                        } else {
                            visitedCount.replace(new Point(guardPosition.x, guardPosition.y), visitedCount.get(new Point(guardPosition.x, guardPosition.y)) + 1);
                            if (visitedCount.get(new Point(guardPosition.x, guardPosition.y)) > 4)
                                return true;
                        }

                        guardPosition.x++;
                    } else {
                        moveDirection = MoveDirection.BOTTOM;
                    }
                    break;
                case BOTTOM:

                    if (obstructions.get(new Point(guardPosition.x, guardPosition.y + 1)) == null){
                        if (visitedCount.get(new Point(guardPosition.x, guardPosition.y)) == null){
                            visitedCount.put(new Point(guardPosition.x, guardPosition.y), 1);
                        } else {
                            visitedCount.replace(new Point(guardPosition.x, guardPosition.y), visitedCount.get(new Point(guardPosition.x, guardPosition.y)) + 1);
                            if (visitedCount.get(new Point(guardPosition.x, guardPosition.y)) > 4)
                                return true;
                        }

                        guardPosition.y++;
                    } else {
                        moveDirection = MoveDirection.LEFT;
                    }
                    break;
                case LEFT:

                    if (obstructions.get(new Point(guardPosition.x - 1, guardPosition.y)) == null){
                        if (visitedCount.get(new Point(guardPosition.x, guardPosition.y)) == null){
                            visitedCount.put(new Point(guardPosition.x, guardPosition.y), 1);
                        } else {
                            visitedCount.replace(new Point(guardPosition.x, guardPosition.y), visitedCount.get(new Point(guardPosition.x, guardPosition.y)) + 1);
                            if (visitedCount.get(new Point(guardPosition.x, guardPosition.y)) > 4)
                                return true;
                        }
                        guardPosition.x--;
                    } else {
                        moveDirection = MoveDirection.TOP;
                    }
                    break;
            }
        }
        return false;
    }

    public static class Tuple2<K, V> {

        private K first;
        private V second;

        public Tuple2(K first, V second){
            this.first = first;
            this.second = second;
        }

        public K getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }
    }
}