import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    enum Operator {
        MULTIPLY,
        ADD,
        COMBINE
    }

    public static void main(String[] args) {

        String line = "";

        ArrayList<Long> equationsResult = new ArrayList<>();
        ArrayList<ArrayList<Long>> numbers = new ArrayList<>();

        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {

                line = myReader.nextLine();

                String[] equationsSplit = line.split(":");
                String[] numbersSplit = equationsSplit[1].replaceFirst("^\\s*", "").split(" ");

                equationsResult.addLast(Long.parseLong(equationsSplit[0]));
                numbers.addLast(new ArrayList<>());
                for (String s : numbersSplit){
                    numbers.getLast().addLast(Long.parseLong(s));
                }

            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        Long result = 0L;
        Long resultPartTwo = 0L;

        for (int i = 0; i < equationsResult.size(); i++){
            if (isValidOperation(equationsResult.get(i), numbers.get(i), new ArrayList<>()))
                result += equationsResult.get(i);

            if (isValidOperationPartTwo(equationsResult.get(i), numbers.get(i), new ArrayList<>()))
                resultPartTwo += equationsResult.get(i);
        }

        System.out.println(result);
        System.out.println(resultPartTwo);
    }

    public static boolean isValidOperation(Long operationResult, ArrayList<Long> numbers, ArrayList<Operator> operators){
        ArrayList<Operator> newOperatorsMulti = new ArrayList<>();
        ArrayList<Operator> newOperatorsAdd = new ArrayList<>();

        if (operators.size() != numbers.size() - 1){
            for (Operator o : operators){
                newOperatorsMulti.addLast(o);
                newOperatorsAdd.addLast(o);
            }

            newOperatorsMulti.addLast(Operator.MULTIPLY);
            if (isValidOperation(operationResult, numbers, newOperatorsMulti))
                return true;

            newOperatorsAdd.addLast(Operator.ADD);
            if (isValidOperation(operationResult, numbers, newOperatorsAdd))
                return true;

            return false;
        }
        Long result = numbers.getFirst();
        for (int i = 1; i < numbers.size(); i++){
            switch (operators.get(i - 1)){
                case ADD :
                    result += numbers.get(i);
                    break;
                case MULTIPLY:
                    result *= numbers.get(i);
                    break;
            }
        }

        if (Objects.equals(result, operationResult))
            return true;
        return false;
    }

    public static boolean isValidOperationPartTwo(Long operationResult, ArrayList<Long> numbers, ArrayList<Operator> operators){
        ArrayList<Operator> newOperatorsMulti = new ArrayList<>();
        ArrayList<Operator> newOperatorsAdd = new ArrayList<>();
        ArrayList<Operator> newOperatorsCombine = new ArrayList<>();

        if (operators.size() != numbers.size() - 1){
            for (Operator o : operators){
                newOperatorsMulti.addLast(o);
                newOperatorsAdd.addLast(o);
                newOperatorsCombine.addLast(o);
            }

            newOperatorsMulti.addLast(Operator.MULTIPLY);
            if (isValidOperationPartTwo(operationResult, numbers, newOperatorsMulti))
                return true;

            newOperatorsAdd.addLast(Operator.ADD);
            if (isValidOperationPartTwo(operationResult, numbers, newOperatorsAdd))
                return true;

            newOperatorsCombine.addLast(Operator.COMBINE);
            if (isValidOperationPartTwo(operationResult, numbers, newOperatorsCombine))
                return true;

            return false;
        }
        Long result = numbers.getFirst();
        for (int i = 1; i < numbers.size(); i++){
            switch (operators.get(i - 1)){
                case ADD :
                    result += numbers.get(i);
                    break;
                case MULTIPLY:
                    result *= numbers.get(i);
                    break;
                case COMBINE:
                    for (int x = 0; x < numbers.get(i).toString().length(); x++)
                        result *= 10;
                    result += numbers.get(i);
                    break;
            }
        }

        if (Objects.equals(result, operationResult))
            return true;
        return false;
    }
}