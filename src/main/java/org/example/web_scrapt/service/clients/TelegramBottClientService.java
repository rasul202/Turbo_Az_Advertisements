package org.example.web_scrapt.service.clients;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.web_scrapt.clients.TelegramBotClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class TelegramBottClientService {

    TelegramBotClient telegramBotClient;

    public boolean sendMessage(String token , String chat_id , String message){
        String stringJson = telegramBotClient.sendMessage(token , chat_id , message);
        JSONObject jsonObject = new JSONObject(stringJson);
        return jsonObject.getBoolean("ok");
    }

}
