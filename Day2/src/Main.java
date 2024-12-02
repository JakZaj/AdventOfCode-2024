import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String line;

        int safeReports = 0;
        int safeReportsPartTwo = 0;

        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {

                line = myReader.nextLine();
                String[] raportLevels = line.split("\\s+");

                if (isReportSafe(raportLevels)) {
                    safeReports++;
                    safeReportsPartTwo++;
                } else {
                    if (isReportSafePartTwo(raportLevels)){
                        safeReportsPartTwo++;
                    }
                }
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        System.out.println(safeReports);
        System.out.println(safeReportsPartTwo);
    }

    private static boolean isReportSafe(String[] raportLevels) {
        boolean isIncreasing = true;

        for (int i = 0; i < raportLevels.length - 1; i++){

            if (i == 0){
                if (Integer.parseInt(raportLevels[i]) - Integer.parseInt(raportLevels[i + 1]) < 0){
                    isIncreasing = true;
                } else {
                    isIncreasing = false;
                }
            }

            if (Math.abs(Integer.parseInt(raportLevels[i]) - Integer.parseInt(raportLevels[i + 1])) > 3 || Math.abs(Integer.parseInt(raportLevels[i]) - Integer.parseInt(raportLevels[i + 1])) == 0)
                break;
            if (isIncreasing && Integer.parseInt(raportLevels[i]) - Integer.parseInt(raportLevels[i + 1]) > 0)
                break;
            if (!isIncreasing && Integer.parseInt(raportLevels[i]) - Integer.parseInt(raportLevels[i + 1]) < 0)
                break;

            if (i == raportLevels.length - 2) {
                return true;
            }
        }

        return false;
    }

    private static boolean isReportSafePartTwo(String[] raportLevels) {
        for (int i = 0; i < raportLevels.length; i++){
            List<String> list = new ArrayList<>(Arrays.asList(raportLevels));
            list.remove(i);
            if (isReportSafe(list.toArray(new String[0])))
                return true;
        }

        return false;
    }
}