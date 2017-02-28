package main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import static main.Tools.*;

@RestController
@EnableAutoConfiguration
public class NumbersController {

    private BufferedWriter logFile = getOutputFile();
    static private HashSet<String> duplicateTracker = new HashSet<String>();

    public NumbersController() throws IOException {
    }

    @RequestMapping("*")
    public String nl() {
        return "<!DOCTYPE html>\n<html>\n<body>\n\n<img src=\"http://blog.newrelic.com/wp-content/uploads/sticker.jpg\" alt=\"NL\" style=\"width:304px;height:228px;\">\n\n</body>\n</html>";
    }
/**
    @RequestMapping(value = "/numbers", method = RequestMethod.POST)
    public String numbers(@RequestParam(value="data", required=false) String data) {
        return runWriteJobs(data, duplicateTracker);
    }

    @RequestMapping(value = "/numbers-form", method = RequestMethod.POST)
    public String numbersform(@RequestBody MultiValueMap<String, String> formData) {
        return runWriteJobs(formData, duplicateTracker);
    }
**/
}