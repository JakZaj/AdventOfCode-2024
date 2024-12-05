import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String line;
        String[] splitLine;

        Map<Integer, ArrayList<Integer>> pageOrderingRules = new HashMap<>();
        boolean isSecondSection = false;
        int result = 0;
        int resultPartTwo = 0;

        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {

                line = myReader.nextLine();

                if (line.equals("")){
                    isSecondSection = true;
                    continue;
                }

                if (!isSecondSection){
                    splitLine = line.split("\\|");
                    if (pageOrderingRules.get(Integer.parseInt(splitLine[0])) == null){
                        ArrayList<Integer> tmpIntArray = new ArrayList<>();
                        tmpIntArray.add(Integer.parseInt(splitLine[1]));
                        pageOrderingRules.put(Integer.parseInt(splitLine[0]), tmpIntArray);
                    } else {
                        pageOrderingRules.get(Integer.parseInt(splitLine[0])).add(Integer.parseInt(splitLine[1]));
                    }
                } else {
                    splitLine = line.split(",");
                    result += getMiddleNumberIfInRightOrder(pageOrderingRules, splitLine);
                    resultPartTwo += getMiddleNumberIfInRightOrderPartTwo(pageOrderingRules, splitLine);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        System.out.println(result);
        System.out.println(resultPartTwo);
    }

    public static int getMiddleNumberIfInRightOrder(Map<Integer, ArrayList<Integer>> pageOrderingRules, String[] splitLine) {

        for (int i = 0; i < splitLine.length - 1; i++){
            for (int x = 1 + i; x < splitLine.length; x++){
                if (pageOrderingRules.get(Integer.parseInt(splitLine[x])) == null)
                    continue;
                if (pageOrderingRules.get(Integer.parseInt(splitLine[x])).contains(Integer.parseInt(splitLine[i])))
                    return 0;
            }
        }
        return Integer.parseInt(splitLine[splitLine.length / 2]);
    }

    public static int getMiddleNumberIfInRightOrderPartTwo(Map<Integer, ArrayList<Integer>> pageOrderingRules, String[] splitLine) {

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (String s : splitLine)
            arrayList.add(Integer.parseInt(s));

        for (int i = 0; i < arrayList.size() - 1; i++){
            for (int x = 1 + i; x < arrayList.size(); x++){
                if (pageOrderingRules.get(arrayList.get(x)) == null)
                    continue;
                if (pageOrderingRules.get(arrayList.get(x)).contains(arrayList.get(i)))
                    return fixOrdering(pageOrderingRules, arrayList);
            }
        }
        return 0;
    }

    public static int fixOrdering(Map<Integer, ArrayList<Integer>> pageOrderingRules, ArrayList<Integer> arrayList) {

        for (int i = 0; i < arrayList.size() - 1; i++){
            for (int x = 1 + i; x < arrayList.size(); x++){
                if (pageOrderingRules.get(arrayList.get(x)) == null)
                    continue;
                if (pageOrderingRules.get(arrayList.get(x)).contains(arrayList.get(i))){
                    arrayList.add(i,arrayList.remove(arrayList.indexOf(arrayList.get(x))));
                    return fixOrdering(pageOrderingRules, arrayList);
                }
            }
        }
        return arrayList.get(arrayList.size()/2);
    }
}