import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String line = "";
        int xSize = 0, ySize = 0;

        Map<Character, ArrayList<Point>> antennasArrays = new HashMap<>();
        Map<Point, Character> pointsOfAntennas = new HashMap<>();

        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {

                line = myReader.nextLine();

                for (int i = 0; i < line.length(); i++){
                    if (line.charAt(i) == '.')
                        continue;

                    pointsOfAntennas.put(new Point(i, ySize), line.charAt(i));

                    antennasArrays.computeIfAbsent(line.charAt(i), k -> new ArrayList<>());
                    antennasArrays.get(line.charAt(i)).addLast(new Point(i, ySize));
                }

                ySize++;
            }
            xSize = line.length();

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        System.out.println(getAllAntinodes(antennasArrays, pointsOfAntennas, xSize, ySize));
        System.out.println(getAllAntinodesPartTwo(antennasArrays, pointsOfAntennas, xSize, ySize));
    }

    public static int getAllAntinodes(Map<Character, ArrayList<Point>> antennasArrays, Map<Point, Character> pointsOfAntennas, int xSize, int ySize) {
        Map<Point, Boolean> pointsOfAntinodes = new HashMap<>();

        for (char c = '0'; c <= '9'; c++){
            if (antennasArrays.get(c) == null)
                continue;
            for (int i = 0; i < antennasArrays.get(c).size() - 1; i++){
                for (int k = 1 + i; k < antennasArrays.get(c).size(); k++){
                    pointsOfAntinodes = updateAntinodes(antennasArrays.get(c).get(i), antennasArrays.get(c).get(k), pointsOfAntinodes, xSize, ySize);
                }
            }
        }

        for (char c = 'a'; c <= 'z'; c++){
            if (antennasArrays.get(c) == null)
                continue;
            for (int i = 0; i < antennasArrays.get(c).size() - 1; i++){
                for (int k = 1 + i; k < antennasArrays.get(c).size(); k++){
                    pointsOfAntinodes = updateAntinodes(antennasArrays.get(c).get(i), antennasArrays.get(c).get(k), pointsOfAntinodes, xSize, ySize);
                }
            }
        }

        for (char c = 'A'; c <= 'Z'; c++){
            if (antennasArrays.get(c) == null)
                continue;
            for (int i = 0; i < antennasArrays.get(c).size() - 1; i++){
                for (int k = 1 + i; k < antennasArrays.get(c).size(); k++){
                    pointsOfAntinodes = updateAntinodes(antennasArrays.get(c).get(i), antennasArrays.get(c).get(k), pointsOfAntinodes, xSize, ySize);
                }
            }
        }

        return pointsOfAntinodes.size();
    }

    public static Map<Point, Boolean> updateAntinodes(Point firstElement, Point secondElement,Map<Point, Boolean> pointsOfAntinodes, int xSize, int ySize){
        Point diff = new Point(firstElement.x - secondElement.x, firstElement.y - secondElement.y);

        if (firstElement.x < secondElement.x){
            if (firstElement.y < secondElement.y){

                if (firstElement.x - diff.x * -1 >= 0 && firstElement.y - diff.y * -1 >= 0)
                    pointsOfAntinodes.computeIfAbsent(new Point(firstElement.x - diff.x * -1, firstElement.y - diff.y * -1), k -> true);

                if (secondElement.x + diff.x * -1 < xSize && secondElement.y + diff.y * -1 < ySize)
                    pointsOfAntinodes.computeIfAbsent(new Point(secondElement.x + diff.x * -1, secondElement.y + diff.y * -1), k -> true);

            } else {
                if (firstElement.x - diff.x * -1 >= 0 && firstElement.y + diff.y < ySize)
                    pointsOfAntinodes.computeIfAbsent(new Point(firstElement.x - diff.x * -1, firstElement.y + diff.y), k -> true);

                if (secondElement.x + diff.x * -1 < xSize && secondElement.y - diff.y >= 0)
                    pointsOfAntinodes.computeIfAbsent(new Point(secondElement.x + diff.x * -1, secondElement.y - diff.y), k -> true);
            }

        } else {

            if (firstElement.y < secondElement.y){
                if (firstElement.x + diff.x < xSize && firstElement.y - diff.y * -1 >= 0)
                    pointsOfAntinodes.computeIfAbsent(new Point(firstElement.x + diff.x, firstElement.y - diff.y * -1), k -> true);

                if (secondElement.x - diff.x >= 0 && secondElement.y + diff.y * -1 < ySize)
                    pointsOfAntinodes.computeIfAbsent(new Point(secondElement.x - diff.x, secondElement.y + diff.y * -1), k -> true);

            } else {
                if (firstElement.x + diff.x < xSize && firstElement.y + diff.y * -1 < ySize)
                    pointsOfAntinodes.computeIfAbsent(new Point(firstElement.x + diff.x, firstElement.y + diff.y), k -> true);

                if (secondElement.x - diff.x >= 0 && secondElement.y - diff.y * -1 >= 0)
                    pointsOfAntinodes.computeIfAbsent(new Point(secondElement.x - diff.x, secondElement.y - diff.y), k -> true);
            }
        }
        return pointsOfAntinodes;
    }

    public static int getAllAntinodesPartTwo(Map<Character, ArrayList<Point>> antennasArrays, Map<Point, Character> pointsOfAntennas, int xSize, int ySize) {
        Map<Point, Boolean> pointsOfAntinodes = new HashMap<>();

        for (char c = '0'; c <= '9'; c++){
            if (antennasArrays.get(c) == null)
                continue;
            for (int i = 0; i < antennasArrays.get(c).size() - 1; i++){
                for (int k = 1 + i; k < antennasArrays.get(c).size(); k++){
                    pointsOfAntinodes = updateAntinodesPartTwo(antennasArrays.get(c).get(i), antennasArrays.get(c).get(k), pointsOfAntinodes, xSize, ySize);
                }
            }
        }

        for (char c = 'a'; c <= 'z'; c++){
            if (antennasArrays.get(c) == null)
                continue;
            for (int i = 0; i < antennasArrays.get(c).size() - 1; i++){
                for (int k = 1 + i; k < antennasArrays.get(c).size(); k++){
                    pointsOfAntinodes = updateAntinodesPartTwo(antennasArrays.get(c).get(i), antennasArrays.get(c).get(k), pointsOfAntinodes, xSize, ySize);
                }
            }
        }

        for (char c = 'A'; c <= 'Z'; c++){
            if (antennasArrays.get(c) == null)
                continue;
            for (int i = 0; i < antennasArrays.get(c).size() - 1; i++){
                for (int k = 1 + i; k < antennasArrays.get(c).size(); k++){
                    pointsOfAntinodes = updateAntinodesPartTwo(antennasArrays.get(c).get(i), antennasArrays.get(c).get(k), pointsOfAntinodes, xSize, ySize);
                }
            }
        }

        return pointsOfAntinodes.size();
    }

    public static Map<Point, Boolean> updateAntinodesPartTwo(Point firstElement, Point secondElement,Map<Point, Boolean> pointsOfAntinodes, int xSize, int ySize){
        Point diff = new Point(firstElement.x - secondElement.x, firstElement.y - secondElement.y);

        pointsOfAntinodes.computeIfAbsent(new Point(firstElement.x, firstElement.y), k -> true);
        pointsOfAntinodes.computeIfAbsent(new Point(secondElement.x, secondElement.y), k -> true);

        boolean firstOutOffRange = false;
        boolean secoundOutOffRange = false;

        int i = 1;

        while (!firstOutOffRange || !secoundOutOffRange) {

            if (firstElement.x < secondElement.x) {
                if (firstElement.y < secondElement.y) {

                    if (firstElement.x - (diff.x * -1 * i) >= 0 && firstElement.y - (diff.y * -1 * i) >= 0)
                        pointsOfAntinodes.computeIfAbsent(new Point(firstElement.x - (diff.x * -1 * i), firstElement.y - (diff.y * -1 * i)), k -> true);
                    else
                        firstOutOffRange = true;

                    if (secondElement.x + (diff.x * -1 * i) < xSize && secondElement.y + (diff.y * -1 * i) < ySize)
                        pointsOfAntinodes.computeIfAbsent(new Point(secondElement.x + (diff.x * -1 * i), secondElement.y + (diff.y * -1 * i)), k -> true);
                    else
                        secoundOutOffRange = true;

                } else {
                    if (firstElement.x - (diff.x * -1 * i) >= 0 && firstElement.y + (diff.y * i) < ySize)
                        pointsOfAntinodes.computeIfAbsent(new Point(firstElement.x - (diff.x * -1 * i), firstElement.y + (diff.y * i)), k -> true);
                    else
                        firstOutOffRange = true;

                    if (secondElement.x + (diff.x * -1 * i) < xSize && secondElement.y - (diff.y * i) >= 0)
                        pointsOfAntinodes.computeIfAbsent(new Point(secondElement.x + (diff.x * -1 * i), secondElement.y - (diff.y * i)), k -> true);
                    else
                        secoundOutOffRange = true;
                }

            } else {

                if (firstElement.y < secondElement.y) {
                    if (firstElement.x + (diff.x * i) < xSize && firstElement.y - (diff.y * -1 * i) >= 0)
                        pointsOfAntinodes.computeIfAbsent(new Point(firstElement.x + (diff.x * i), firstElement.y - (diff.y * -1 * i)), k -> true);
                    else
                        firstOutOffRange = true;

                    if (secondElement.x - (diff.x * i) >= 0 && secondElement.y + (diff.y * -1 * i) < ySize)
                        pointsOfAntinodes.computeIfAbsent(new Point(secondElement.x - (diff.x * i), secondElement.y + (diff.y * -1 * i)), k -> true);
                    else
                        secoundOutOffRange = true;

                } else {
                    if (firstElement.x + (diff.x * i) < xSize && firstElement.y + (diff.y * -1 * i) < ySize)
                        pointsOfAntinodes.computeIfAbsent(new Point(firstElement.x + (diff.x * i), firstElement.y + (diff.y * -1 * i)), k -> true);
                    else
                        firstOutOffRange = true;

                    if (secondElement.x - (diff.x * i) >= 0 && secondElement.y - (diff.y * -1 * i) >= 0)
                        pointsOfAntinodes.computeIfAbsent(new Point(secondElement.x - (diff.x * i), secondElement.y - (diff.y * -1 * i)), k -> true);
                    else
                        secoundOutOffRange = true;
                }
            }
            i++;
        }
        return pointsOfAntinodes;
    }
}