import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        int totalDistance = 0;

        String line;

        ArrayList<Integer> leftSide = new ArrayList<>();
        ArrayList<Integer> rightSide = new ArrayList<>();
        Map<Integer, Integer> appearsInRight = new HashMap<>();

        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);


            while (myReader.hasNextLine()) {

                line = myReader.nextLine();
                String[] listNumber = line.split("\\s+");

                leftSide.add(Integer.parseInt(listNumber[0]));
                rightSide.add(Integer.parseInt(listNumber[1]));

                if (appearsInRight.get(Integer.parseInt(listNumber[1])) != null){
                    appearsInRight.replace(Integer.parseInt(listNumber[1]), appearsInRight.get(Integer.parseInt(listNumber[1])) + 1);
                } else {
                    appearsInRight.put(Integer.parseInt(listNumber[1]), 1);
                }

            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        leftSide.sort(Comparator.naturalOrder());
        rightSide.sort(Comparator.naturalOrder());

        for (int i = 0; i < leftSide.size(); i++){
            totalDistance += Math.abs(leftSide.get(i) - rightSide.get(i));
        }

        System.out.println(totalDistance);

        totalDistance = 0;

        for (int i = 0; i < leftSide.size(); i++){
            if (appearsInRight.get(leftSide.get(i)) != null){
                totalDistance += leftSide.get(i) * appearsInRight.get(leftSide.get(i));
            }

        }

        System.out.println(totalDistance);
    }
}