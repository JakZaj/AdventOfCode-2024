import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        String line;

        int resultsOfMultiplications = 0;
        int resultsOfMultiplicationsPartTwo = 0;
        boolean disabled = false;


        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {

                line = myReader.nextLine();

                Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
                Matcher matcher = pattern.matcher(line);
                List<String> results = new ArrayList<>();

                while (matcher.find()) {
                    results.add(matcher.group());
                }

                Pattern patternFindDigit = Pattern.compile("(\\d+)");
                Matcher matcherDigit;

                for (String result : results) {
                    matcherDigit = patternFindDigit.matcher(result);
                    matcherDigit.find();
                    int x = Integer.parseInt(matcherDigit.group());
                    matcherDigit.find();
                    int y = Integer.parseInt(matcherDigit.group());
                    resultsOfMultiplications += x * y;
                }

                Pattern patternPartTwo = Pattern.compile("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)");
                Matcher matcherPartTwo = patternPartTwo.matcher(line);
                List<String> resultsPartTwo = new ArrayList<>();

                while (matcherPartTwo.find()) {
                    if (matcherPartTwo.group().equals("do()")){
                        disabled = false;
                        continue;
                    } else if (matcherPartTwo.group().equals("don't()")) {
                        disabled = true;
                        continue;
                    }
                    if (disabled)
                        continue;

                    resultsPartTwo.add(matcherPartTwo.group());
                }

                for (String result : resultsPartTwo) {
                    matcherDigit = patternFindDigit.matcher(result);
                    matcherDigit.find();
                    int x = Integer.parseInt(matcherDigit.group());
                    matcherDigit.find();
                    int y = Integer.parseInt(matcherDigit.group());
                    resultsOfMultiplicationsPartTwo += x * y;
                }
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        System.out.println(resultsOfMultiplications);
        System.out.println(resultsOfMultiplicationsPartTwo);
    }
}