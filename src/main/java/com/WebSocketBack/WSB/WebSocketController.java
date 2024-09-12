package com.WebSocketBack.WSB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.HtmlUtils;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class WebSocketController {

    public int timeOffset = 0;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Message message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Sveiki atvykę, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
    private int counter = 0;

    @MessageMapping("/offset")
    public void setOffset(int offset){
        timeOffset = offset;
    }

    @Scheduled(fixedRate = 1000)
    public void sendGreeting() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime offsetTime = time.plusHours(timeOffset);
        String now = offsetTime.format(formatter);

        messagingTemplate.convertAndSend("/topic/heyhey", now);
    }

    @Scheduled(fixedRate = 5000)
    public void sendJoke() {

        messagingTemplate.convertAndSend("/topic/jokes", getJokes());
    }
    public String getJokes(){
        List<String> jokeList = new ArrayList<>();
        String joke = "How is my wallet like an onion? Every time I open it, I cry.";
        String joke2 = "What do you call a dog who meditates? Aware wolf.";
        String joke3 = "What kind of fish do penguins catch at night? Star fish.";
        String joke4 = "Which vegetable has the best kung fu? Broc-lee.";
        String joke5 = "Can a frog jump higher than a house? Of course, a house can't jump.";
        jokeList.add(joke);
        jokeList.add(joke2);
        jokeList.add(joke3);
        jokeList.add(joke4);
        jokeList.add(joke5);
        Random rand = new Random();

        return jokeList.get(rand.nextInt(0,5));
    }
}

