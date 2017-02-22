package main;

import org.springframework.util.MultiValueMap;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.*;

import static main.NumbersApp.TerminateApp;
import static main.NumbersController.GetUniqueCounts;

public class Tools {

    static HashSet<String> uniques = new HashSet<>();
    static List<String> totalRequests = new ArrayList<>();

    public static BufferedWriter getOutputFile() throws IOException {
        try {
            return Files.newBufferedWriter(Paths.get("numbers.log"), StandardCharsets.UTF_8);
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static List<String> stripSpaces(String payload) {
        return  new ArrayList<String>(Arrays.asList(payload.split("\\\\n")));
    }

    public static void writeFromList(List<String> numbers, HashSet fileHash) {
        for (String n : numbers) {
            writeSingle(n, fileHash);
        }
    }

    public static void writeSingle(String data, HashSet fileHash)
    {
            if (fileHash.add(data)){
                try {
                BufferedWriter file = Files.newBufferedWriter(Paths.get("numbers.log"),
                        StandardCharsets.UTF_8,
                        StandardOpenOption.APPEND);
                file.write(data + "\n");
                file.close();
                uniques.add(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        totalRequests.add(data);
    }

    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    public static void statusEvery10Secs(HashSet<String> uniqs, List<String> totals) {
        System.out.println("Received "
                + (uniqs.size()) +
                " unique numbers, "
                + (totals.size() - uniqs.size())
                + " duplicates. Unique total: "
                + GetUniqueCounts());
    }

    static class reporter extends TimerTask {
        @Override
        public void run() {
            statusEvery10Secs(uniques, totalRequests);
            totalRequests.clear();
            uniques.clear();
        }
    }

    public static String runWriteJobs(String data, HashSet duplicateTracker){
        List<String> numbers = stripSpaces(data);
        for (String n : numbers) {
            if (n.equals("terminate")) {
                TerminateApp();
            }
            if (!isNumeric(n) && n.length() != 10) {
                return null;
            } else {
                //writeFromList(n, duplicateTracker);
                writeSingle(n, duplicateTracker);
            }
        }
        return "OK";
    }

    public static String runWriteJobs(MultiValueMap<String,String> formData, HashSet duplicateTracker){
        List<String> data = formData.get("data");
        List<String> numbers = new ArrayList<String>(Arrays.asList(data.get(0).split("\\n")));
        for (String n : numbers) {
            if (n.equals("terminate")) {
                TerminateApp();
            }
            if (!isNumeric(n) && n.length() != 10) {
                return null;
            } else {
                //writeFromList(n, duplicateTracker);
                writeSingle(n, duplicateTracker);
            }
        }
        return "OK";
    }
}