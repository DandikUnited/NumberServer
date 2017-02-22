package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Timer;

@SpringBootApplication
@EnableAutoConfiguration
public class NumbersApp {
    public static void main(String[] args) {
        SpringApplication.run(NumbersController.class, args);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Tools.reporter(), 0, 10 * 1000);
    }
        public static void TerminateApp(){
            System.out.println("terminating the app...");
            System.exit(0);
    }
}