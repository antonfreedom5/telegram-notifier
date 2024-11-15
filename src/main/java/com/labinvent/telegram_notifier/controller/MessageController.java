package com.labinvent.telegram_notifier.controller;

import com.labinvent.telegram_notifier.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SenderService senderService;

    @PostMapping(value = "/")
    public void send(@RequestBody String message) {
        senderService.send(message);
    }

    @PostMapping(value = "/file")
    public void send(@RequestParam("file") MultipartFile file) {
        senderService.send(file);
    }
}
