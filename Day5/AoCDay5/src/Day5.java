import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Day5 {



    public static void main(String[] args) {
        task1(readFileLines("D:\\AdventOfCode\\2022\\Day5\\input.txt"));
    }

    private static String task1(List<String> fileInput) {
        List<String> crateLines = new ArrayList<>();
        List<String> instructions = new ArrayList<>();
        boolean fillingCrates = true;
        for(String line : fileInput) {
            if(line.trim().equals("")) {
                fillingCrates = false;
            } else if (fillingCrates) {
                crateLines.add(line);
            } else {
                instructions.add(line);
            }
        }
        List<Stack<Character>> crateSymbols = getCrateSymbols(crateLines);
        List<String> simpleInstructions = getSimpleInstructions(instructions);
        performInstructions(crateSymbols, simpleInstructions);
        for (Stack<Character> crate : crateSymbols) {
            System.out.println(crate.peek());
        }
        return "";
    }

    private static void performInstructions(List<Stack<Character>> crateSymbols,
                                                              List<String> instructions) {
        for(String line : instructions) {
            String[] digits = line.split("(?<=.)");
            int amount;
            Stack<Character> fromCrates;
            Stack<Character> toCrates;
            if(digits.length == 3) {
                amount = Integer.parseInt(digits[0]);
                fromCrates = crateSymbols.get(Integer.parseInt(digits[1]) - 1);
                toCrates = crateSymbols.get(Integer.parseInt(digits[2]) - 1);
            } else {
                amount = Integer.parseInt(digits[0] + digits[1]);
                fromCrates = crateSymbols.get(Integer.parseInt(digits[2]) - 1);
                toCrates = crateSymbols.get(Integer.parseInt(digits[3]) - 1);
            }
            Stack<Character> tempToStack = new Stack<>();
            for (int i = 0; i < amount; i++) {
                if(!fromCrates.isEmpty()) {
                    tempToStack.push(fromCrates.pop());
                }
            }
            while(!tempToStack.isEmpty()) {
                toCrates.push(tempToStack.pop());
            }
        }
    }

    private static List<String> getSimpleInstructions(List<String> instructions) {
        List<String> simpleInstructions = new ArrayList<>();
        for(String instruction : instructions) {
            simpleInstructions.add(instruction.replaceAll("[^0-9]", ""));
        }
        return simpleInstructions;
    }

    private static List<Stack<Character>> getCrateSymbols(List<String> crateLines) {
        String crateNumbers = crateLines.get(crateLines.size() - 1);
        int indexes = crateNumbers.replaceAll("\\s", "").length();
        crateLines.remove(crateLines.size() - 1);
        Collections.reverse(crateLines);
        List<Stack<Character>> crateGroups = new ArrayList<>();
        for(int i = 0; i < indexes; i++) {
            Stack<Character> crateGroup = new Stack<>();
            int symbolPos = crateNumbers.indexOf((i + 1) + "");
            for(String line : crateLines) {
                char symbol = line.toCharArray()[symbolPos];
                if(symbol != ' ') {
                    crateGroup.add(symbol);
                }
            }
            crateGroups.add(crateGroup);
        }
        return crateGroups;
    }

    private static List<String> readFileLines(String path) {
        List<String> lines = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
