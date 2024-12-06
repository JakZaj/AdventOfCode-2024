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

        System.out.println(getVisitedPosition(new Point(guardPosition.x, guardPosition.y), obstructions, new Point(xSize, ySize)));
        System.out.println(getVisitedPositionPartTwo(new Point(guardPosition.x, guardPosition.y), obstructions, new Point(xSize, ySize)));
    }

    public static int getVisitedPosition(Point guardPosition, Map<Point, Boolean> obstructions, Point areaSize){
        Map<Point, Boolean> visitedPosition = new HashMap<>();
        enum MoveDirection {
            TOP,
            RIGHT,
            BOTTOM,
            LEFT
        }
        MoveDirection moveDirection = MoveDirection.TOP;

        while (guardPosition.x < areaSize.x && guardPosition.x >= 0 && guardPosition.y < areaSize.y && guardPosition.y >= 0){
            switch (moveDirection){
                case TOP :
                    if (obstructions.get(new Point(guardPosition.x, guardPosition.y - 1)) == null){
                        visitedPosition.putIfAbsent(new Point(guardPosition.x, guardPosition.y), true);
                        guardPosition.y--;
                    } else {
                        moveDirection = MoveDirection.RIGHT;
                    }
                    break;
                case RIGHT:
                    if (obstructions.get(new Point(guardPosition.x + 1, guardPosition.y)) == null){
                        visitedPosition.putIfAbsent(new Point(guardPosition.x, guardPosition.y), true);
                        guardPosition.x++;
                    } else {
                        moveDirection = MoveDirection.BOTTOM;
                    }
                    break;
                case BOTTOM:
                    if (obstructions.get(new Point(guardPosition.x, guardPosition.y + 1)) == null){
                        visitedPosition.putIfAbsent(new Point(guardPosition.x, guardPosition.y), true);
                        guardPosition.y++;
                    } else {
                        moveDirection = MoveDirection.LEFT;
                    }
                    break;
                case LEFT:
                    if (obstructions.get(new Point(guardPosition.x - 1, guardPosition.y)) == null){
                        visitedPosition.putIfAbsent(new Point(guardPosition.x, guardPosition.y), true);
                        guardPosition.x--;
                    } else {
                        moveDirection = MoveDirection.TOP;
                    }
                    break;

            }
        }


        return visitedPosition.size();
    }

    public static int getVisitedPositionPartTwo(Point guardPosition, Map<Point, Boolean> obstructions, Point areaSize){
        int diffrentPositionsCount = 0;

        for (int y = 0; y < areaSize.y; y++){
            for (int x = 0; x < areaSize.x; x++){
                if (obstructions.get(new Point(x, y)) != null){
                    continue;
                }
                if (guardPosition.x == x && guardPosition.y == y){
                    continue;
                }

                obstructions.put(new Point(x, y), true);

                if (isStuckInLoop(new Point(guardPosition.x, guardPosition.y), MoveDirection.TOP, obstructions, areaSize))
                    diffrentPositionsCount++;

                obstructions.remove(new Point(x, y));

            }
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

    public static void print(Point guardPosition, Map<Point, Boolean> obstructions, Point areaSize){
        for (int y = 0; y < areaSize.y; y++){
            for (int x = 0; x < areaSize.x; x++){
                if (obstructions.get(new Point(x, y)) != null){
                    System.out.print("#");
                    continue;
                }
                if (guardPosition.x == x && guardPosition.y == y){
                    System.out.print("^");
                    continue;
                }
                System.out.print(".");

            }
            System.out.println("");
        }
    }
}