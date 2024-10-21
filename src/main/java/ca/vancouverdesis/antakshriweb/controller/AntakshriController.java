package ca.vancouverdesis.antakshriweb.controller;

import ca.vancouverdesis.antakshriweb.domain.Buzzer;
import ca.vancouverdesis.antakshriweb.domain.Scores;
import ca.vancouverdesis.antakshriweb.service.AntakshriService;
import ca.vancouverdesis.antakshriweb.util.StringUtils;
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
        return antakshriService.getBuzzers();
    }

    @GetMapping("/player")
    public String player(Model model) {
        model.addAttribute("message", "Register yourself on your Team");
        model.addAttribute("buzzer", new Buzzer());
        return "player";
    }

    @GetMapping("/audience")
    public String audience(Model model) {
        model.addAttribute("message", "Register yourself as Audience");
        Buzzer buzzer = new Buzzer();
        buzzer.setTeamName("AUDIENCE");
        model.addAttribute("buzzer", buzzer);
        return "audience";
    }

    @PostMapping("/register")
    public String register(Model model, Buzzer buzzer) {
        logger.info("Registering team : {} : {}", buzzer.getTeamName(), buzzer.getPersonName());
        buzzer.setPersonName(StringUtils.toTitleCase(buzzer.getPersonName()));
        model.addAttribute("buzzer", buzzer);
        String text = "Welcome " + buzzer.getPersonName();
        if("PLAYER".equalsIgnoreCase(buzzer.getTeamName())) {
           text += " from team " + buzzer.getTeamName();
        }
        text+= "!";

        model.addAttribute("message", text);
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
        return scores;
    }

    @GetMapping("/reset-scores")
    public String resetScores() {
        logger.info("Resetting scores");
        antakshriService.resetScores();
        return "admin";
    }
}
