package ca.vancouverdesis.antakshriweb.service;

import ca.vancouverdesis.antakshriweb.domain.Buzzer;
import ca.vancouverdesis.antakshriweb.domain.Scores;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

@Service
public class AntakshriService {
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
        if("AUDIENCE".equalsIgnoreCase(buzzer.getTeamName())) {
            audienceMap.put(ZonedDateTime.now(), buzzer);
        } else {
            playerMap.put(ZonedDateTime.now(), buzzer);
        }
    }

    public void resetBuzzer() {
        audienceMap.clear();
        playerMap.clear();
    }

    public Map<String, Map<Integer, Buzzer>> getBuzzers() {
//        Map<String, Map<Integer, Buzzer>> returnMap = new HashMap<>();
//        Map<Integer, Buzzer> buzzerMap = new HashMap<>();
//        Map<Integer, Buzzer> finalBuzzerMap = buzzerMap;
//        audienceMap.values().stream().toList().forEach(buzzer -> {
//            finalBuzzerMap.put(finalBuzzerMap.size() + 1, buzzer);
//        });
//        returnMap.put("AUDIENCE", buzzerMap);
//
//        buzzerMap = new HashMap<>();
//        Map<Integer, Buzzer> finalBuzzerMap1 = buzzerMap;
//        playerMap.values().stream().toList().forEach(buzzer -> {
//            finalBuzzerMap1.put(finalBuzzerMap1.size() + 1, buzzer);
//        });
//        returnMap.put("PLAYER", buzzerMap);

        Map<String, Map<Integer, Buzzer>> returnMap = new HashMap<>();
        Map<Integer, Buzzer> buzzerMap = new HashMap<>();
        buzzerMap.put(1, new Buzzer("AUDIENCE", "Rahul"));
        buzzerMap.put(2, new Buzzer("AUDIENCE", "Amit"));
        buzzerMap.put(3, new Buzzer("AUDIENCE", "Avi"));
        buzzerMap.put(4, new Buzzer("AUDIENCE", "Ekta"));
        buzzerMap.put(5, new Buzzer("AUDIENCE", "Vipin"));
        returnMap.put("AUDIENCE", buzzerMap);

        Map<Integer, Buzzer> playBuzzer = new HashMap<>();
        playBuzzer.put(1, new Buzzer("AUDIENCE", "Rahul"));
        playBuzzer.put(2, new Buzzer("AUDIENCE", "Amit"));
        playBuzzer.put(3, new Buzzer("AUDIENCE", "Avi"));
        playBuzzer.put(4, new Buzzer("AUDIENCE", "Ekta"));
        playBuzzer.put(5, new Buzzer("AUDIENCE", "Vipin"));
        returnMap.put("PLAYER", playBuzzer);

        return returnMap;
    }

    public void submitScores(Scores teamScore) {
        scores.put(teamScore.getTeamName(), teamScore.getScore());
    }
}