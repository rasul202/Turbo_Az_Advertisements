package org.example.web_scrapt.service.host;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.web_scrapt.service.clients.TelegramBottClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults( level = AccessLevel.PRIVATE)
public class TelegramService {

    final TelegramBottClientService telegramBotClientService;

    @Value("${telegram_bot.token}")
    String token;

    @Value("${telegram_bot.chat_id}")
    String chat_id;

    public void sendMessage(String message) {
        boolean isSuccessful = telegramBotClientService.sendMessage(token , chat_id , message);
        if(isSuccessful != true){
            throw new RuntimeException("message has not been send successfully");
        }
    }

}
