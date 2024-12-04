import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        String line;
        Map<Point, String> wordSearch = new HashMap<>();
        int ySize = 0;
        int xSize = 0;

        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {

                line = myReader.nextLine();
                xSize = line.length();

                for (int i = 0; i < xSize; i++){
                    wordSearch.put(new Point(i, ySize), String.valueOf(line.charAt(i)));
                }
                ySize++;
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        int result = 0;
        int resultPartTwo = 0;
        for (int y = 0; y < ySize; y++){
            for (int x = 0; x < xSize; x++){
                result += checkPoint(new Point(x, y), wordSearch);
                resultPartTwo += checkPointPartTwo(new Point(x, y), wordSearch);
            }
        }

        System.out.println(result);
        System.out.println(resultPartTwo);
    }
    public static int checkPoint(Point pointToCheck, Map<Point, String> wordSearch) {

        if (wordSearch.get(new Point(pointToCheck.x, pointToCheck.y)) == null || !wordSearch.get(new Point(pointToCheck.x, pointToCheck.y)).equals("X"))
            return 0;

        String worldToSearch = "XMAS";
        int counter = 0;
        boolean top = true, topRight = true, right = true, bottomRight = true, bottom = true, bottomLeft = true, left = true, topLeft = true;

        for (int i = 1; i < worldToSearch.length(); i++){
            if (top)
                top = checkLetter(new Point(pointToCheck.x, pointToCheck.y - i), wordSearch, String.valueOf(worldToSearch.charAt(i)));

            if (topRight)
                topRight = checkLetter(new Point(pointToCheck.x + i, pointToCheck.y - i), wordSearch, String.valueOf(worldToSearch.charAt(i)));

            if (right)
                right = checkLetter(new Point(pointToCheck.x + i, pointToCheck.y), wordSearch, String.valueOf(worldToSearch.charAt(i)));

            if (bottomRight)
                bottomRight = checkLetter(new Point(pointToCheck.x + i, pointToCheck.y + i), wordSearch, String.valueOf(worldToSearch.charAt(i)));

            if (bottom)
                bottom = checkLetter(new Point(pointToCheck.x, pointToCheck.y + i), wordSearch, String.valueOf(worldToSearch.charAt(i)));

            if (bottomLeft)
                bottomLeft = checkLetter(new Point(pointToCheck.x - i, pointToCheck.y + i), wordSearch, String.valueOf(worldToSearch.charAt(i)));

            if (left)
                left = checkLetter(new Point(pointToCheck.x - i, pointToCheck.y), wordSearch, String.valueOf(worldToSearch.charAt(i)));

            if (topLeft)
                topLeft = checkLetter(new Point(pointToCheck.x - i, pointToCheck.y - i), wordSearch, String.valueOf(worldToSearch.charAt(i)));
        }

        if (top)
            counter++;

        if (topRight)
            counter++;

        if (right)
            counter++;

        if (bottomRight)
            counter++;

        if (bottom)
            counter++;

        if (bottomLeft)
            counter++;

        if (left)
            counter++;

        if (topLeft)
            counter++;

        return counter;
    }

    public static boolean checkLetter(Point pointToCheck, Map<Point, String> wordSearch, String letter) {
        if (wordSearch.get(new Point(pointToCheck.x, pointToCheck.y)) == null || !wordSearch.get(new Point(pointToCheck.x, pointToCheck.y)).equals(letter))
            return false;
        return true;
    }

    public static int checkPointPartTwo(Point pointToCheck, Map<Point, String> wordSearch) {

        if (wordSearch.get(new Point(pointToCheck.x, pointToCheck.y)) == null || !wordSearch.get(new Point(pointToCheck.x, pointToCheck.y)).equals("A"))
            return 0;

        int letterM = 0;
        int letterS = 0;

        if (wordSearch.get(new Point(pointToCheck.x - 1, pointToCheck.y - 1)) == null || wordSearch.get(new Point(pointToCheck.x + 1, pointToCheck.y - 1)) == null || wordSearch.get(new Point(pointToCheck.x - 1, pointToCheck.y + 1)) == null || wordSearch.get(new Point(pointToCheck.x + 1, pointToCheck.y + 1)) == null){
            return 0;
        } else {
            if (wordSearch.get(new Point(pointToCheck.x - 1, pointToCheck.y - 1)).equals("M")) {
                letterM++;
                if (wordSearch.get(new Point(pointToCheck.x + 1, pointToCheck.y + 1)).equals("S")){
                    letterS++;
                }

            } else if (wordSearch.get(new Point(pointToCheck.x - 1, pointToCheck.y - 1)).equals("S")) {
                letterS++;
                if (wordSearch.get(new Point(pointToCheck.x + 1, pointToCheck.y + 1)).equals("M")){
                    letterM++;
                }
            }

            if (letterM == 1 && letterS == 1){
                if (wordSearch.get(new Point(pointToCheck.x + 1, pointToCheck.y - 1)).equals("M")) {
                    letterM++;
                    if (wordSearch.get(new Point(pointToCheck.x - 1, pointToCheck.y + 1)).equals("S")){
                        letterS++;
                    }

                } else if (wordSearch.get(new Point(pointToCheck.x + 1, pointToCheck.y - 1)).equals("S")) {
                    letterS++;
                    if (wordSearch.get(new Point(pointToCheck.x - 1, pointToCheck.y + 1)).equals("M")){
                        letterM++;
                    }
                }

                if (letterM == 2 && letterS == 2){
                    return 1;
                }
            }
        }
        return 0;
    }
}