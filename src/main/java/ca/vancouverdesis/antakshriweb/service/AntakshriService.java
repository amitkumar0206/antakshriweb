package ca.vancouverdesis.antakshriweb.service;

import ca.vancouverdesis.antakshriweb.domain.Buzzer;
import ca.vancouverdesis.antakshriweb.domain.Scores;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

@Service
public class AntakshriService {
    Logger logger = LoggerFactory.getLogger(AntakshriService.class);
    private ConcurrentSkipListMap<ZonedDateTime, Buzzer> audienceMap;
    private ConcurrentSkipListMap<ZonedDateTime, Buzzer> playerMap;
    @Getter
    private Map<String, Integer> scores;

    public AntakshriService() {
        audienceMap = new ConcurrentSkipListMap<>(
                Comparator.comparingLong(v -> v.toInstant().toEpochMilli()));
        playerMap = new ConcurrentSkipListMap<>(
                Comparator.comparingLong(v -> v.toInstant().toEpochMilli()));
        scores = new HashMap<>();
        scores.put("Deewane", 0);
        scores.put("Parwane", 0);
        scores.put("Mastane", 0);
        scores.put("Afsane", 0);
        scores.put("Taraane", 0);
        scores.put("Anjaane", 0);
        scores.put("Suhaane", 0);
        scores.put("Sayaane", 0);
    }

    public void addBuzzer(Buzzer buzzer) {
        logger.info("Adding buzzer : " + buzzer.getTeamName() + " : " + buzzer.getPersonName());
        if("AUDIENCE".equalsIgnoreCase(buzzer.getTeamName())) {
            audienceMap.put(ZonedDateTime.now(), buzzer);
        } else {
            playerMap.put(ZonedDateTime.now(), buzzer);
        }
    }

    public void resetBuzzer() {
        logger.info("Service: Resetting buzzers");
        audienceMap.clear();
        playerMap.clear();
    }

    public Map<String, Map<Integer, Buzzer>> getBuzzers() {
        logger.info("Service: Getting buzzers");
        Map<String, Map<Integer, Buzzer>> returnMap = new HashMap<>();
        Map<Integer, Buzzer> buzzerMap = new HashMap<>();
        Map<Integer, Buzzer> finalBuzzerMap = buzzerMap;
        audienceMap.values().stream().toList().forEach(buzzer -> {
            finalBuzzerMap.put(finalBuzzerMap.size() + 1, buzzer);
        });
        returnMap.put("AUDIENCE", buzzerMap);

        buzzerMap = new HashMap<>();
        Map<Integer, Buzzer> finalBuzzerMap1 = buzzerMap;
        playerMap.values().stream().toList().forEach(buzzer -> {
            finalBuzzerMap1.put(finalBuzzerMap1.size() + 1, buzzer);
        });
        returnMap.put("PLAYER", buzzerMap);
        return returnMap;
    }

    public void submitScores(Scores teamScore) {
        logger.info("Submitting scores : " + teamScore.getTeamName() + " : " + teamScore.getScore());
        scores.put(teamScore.getTeamName(), teamScore.getScore());
    }
}