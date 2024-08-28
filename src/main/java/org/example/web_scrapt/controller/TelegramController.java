package org.example.web_scrapt.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.web_scrapt.service.host.TelegramService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("telegram")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class TelegramController {

    TelegramService telegramService;

    @GetMapping("send_message")
    public void sendMessage(){
        String exampleMessage = "it is an example message that has been send in " + LocalDateTime.now();
        telegramService.sendMessage(exampleMessage);
    }

}
