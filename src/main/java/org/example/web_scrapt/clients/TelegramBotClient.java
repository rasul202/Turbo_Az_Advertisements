package org.example.web_scrapt.clients;

import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "telegram-bot" , url = "${urls.telegram_bot}")
public interface TelegramBotClient {

    @GetMapping("/bot{token}/sendMessage")
    String sendMessage(@PathVariable("token") String token,
                           @RequestParam("chat_id") String chat_id,
                           @RequestParam("text") String text);

}
