package main;

import org.springframework.util.MultiValueMap;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


import static main.NumbersApp.GetUniqueCounts;

public class Tools {

    static HashSet<String> uniques = new HashSet<>();
    static List<String> totalRequests = new ArrayList<>();
    public Tools() throws IOException {
    }

    public static BufferedWriter getOutputFile() throws IOException {
            return Files.newBufferedWriter(Paths.get("numbers.log"), StandardCharsets.UTF_8);
    }

    public static void writeSingle(String data, HashSet fileHash, BufferedWriter file)
    {
            if (fileHash.add(data)){
                try {
                    uniques.add(data);
                    file.write(data + "\n");
                    file.flush();
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
}