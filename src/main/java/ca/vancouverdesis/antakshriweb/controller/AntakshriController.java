package ca.vancouverdesis.antakshriweb.controller;

import ca.vancouverdesis.antakshriweb.domain.Buzzer;
import ca.vancouverdesis.antakshriweb.domain.Scores;
import ca.vancouverdesis.antakshriweb.service.AntakshriService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
@Controller
public class AntakshriController {
    Logger logger = LoggerFactory.getLogger(AntakshriController.class);
    @Autowired
    AntakshriService antakshriService;

    @GetMapping("/buzzers")
    @ResponseBody
    public Map<String, Map<Integer, Buzzer>> getBuzzers() {
        logger.info("Getting buzzers");
        return antakshriService.getBuzzers();
    }

    @GetMapping("/home")
    public String home(Model model) {
        logger.info("Home page");
        model.addAttribute("message", "Register your team");
        model.addAttribute("buzzer", new Buzzer());
        return "add";
    }

    @PostMapping("/register")
    public String register(Model model, Buzzer buzzer) {
        logger.info("Registering team : "+ buzzer.getTeamName() + " : " + buzzer.getPersonName());
        model.addAttribute("buzzer", buzzer);
        model.addAttribute("message", "Welcome " + buzzer.getPersonName() + "!");
        return "buzzer";
    }

    @GetMapping("/buzzer")
    public String buzzer(Model model, Buzzer buzzer) {
        logger.info("Buzzer page");
        model.addAttribute("message", "Welcome " + buzzer.getPersonName() + "!");
        return "buzzer";
    }

    @PostMapping("/clicked")
    public String clicked(Model model, @RequestBody Buzzer buzzer) {
        logger.info("Buzzer clicked : "+ buzzer.getTeamName() + " : " + buzzer.getPersonName());
        model.addAttribute("message", "Welcome " + buzzer.getPersonName() + "!");
        antakshriService.addBuzzer(buzzer);
        return "buzzer";
    }

    @GetMapping("/reset")
    public void reset(Model model) {
        logger.info("Resetting buzzers");
        antakshriService.resetBuzzer();
    }

    @GetMapping("/antakshri")
    public String antakshri(Model model) {
        logger.info("Antakshri page");
        model.addAttribute("message", "Happy Diwali");
        return "antakshri";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        logger.info("Admin page");
        model.addAttribute("message", "Admin");
        model.addAttribute("scores", new Scores());
        return "admin";
    }

    @PostMapping("/submit-scores")
    public String submitScores(@RequestBody Scores scores) {
        logger.info("Submitting scores: " + scores.getTeamName() + " : " + scores.getScore());
        antakshriService.submitScores(scores);
        return "admin";
    }

    @GetMapping("/scores")
    @ResponseBody
    public Map<String, Integer> getScores() {
        Map<String, Integer> scores = antakshriService.getScores();
        logger.info("Getting scores: entry set" + scores.entrySet() + "!");
        logger.info("Getting scores: key set" + scores.keySet() + "!");
        return scores;
    }
}
