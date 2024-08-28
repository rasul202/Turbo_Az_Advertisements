package org.example.web_scrapt.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.web_scrapt.service.ScheduledTaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("scheduled")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class ScedulledTaskController {

    ScheduledTaskService scheduledTaskService;

    @GetMapping("start-sending-notification")
    public void startRunning(){
        scheduledTaskService.startRunning();
    }
    @GetMapping("stop-sending-notification")
    public void stopRunning(){
        scheduledTaskService.stopRunning();
    }

    @GetMapping("check-running")
    public boolean checkRunning(){
        return scheduledTaskService.checkRunning();
    }

}
