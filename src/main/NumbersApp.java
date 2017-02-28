package main;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static main.Tools.getOutputFile;
import static main.Tools.isNumeric;

public class NumbersApp {
    final public static int MAX_CLIENTS = 5;
    final public static int PORT = 4000;

    static private HashSet<String> duplicateTracker = new HashSet<String>();
    static HashSet<String> uniques = new HashSet<>();
    static List<String> totalRequests = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedWriter file = getOutputFile();
        ServerSocket server = new ServerSocket(PORT, MAX_CLIENTS);
        System.out.println("Server has started on 127.0.0.1:4000.");
        Boolean open = Boolean.TRUE;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Tools.reporter(), 0, 10 * 1000);

        while (open) {

            Socket client = server.accept();
            InputStreamReader input = new InputStreamReader(client.getInputStream());
            BufferedReader br = new BufferedReader(input);
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                if ( inputLine.equals("terminate") ){
                    TerminateApp();
                }
                if ( inputLine.length() != 9 || !isNumeric(inputLine)) {
                    System.out.println("bad input");
                    break;
                }
                Tools.writeSingle(inputLine, duplicateTracker, file);
            }
            br.close();
            client.close();
        }
    }
        public static void TerminateApp(){
            System.out.println("terminating the app...");
            System.exit(0);
    }
    public static int GetUniqueCounts(){
        return duplicateTracker.size();
    }
}