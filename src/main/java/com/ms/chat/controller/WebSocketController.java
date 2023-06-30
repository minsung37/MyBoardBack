package com.ms.chat.controller;

import com.ms.chat.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class WebSocketController {

    // 방에 들어온 유저 임시로 저장
    Map<String, List<String>> roomInfo = new HashMap<>();

    // 의존성 주입
    private final SimpMessagingTemplate simpMessagingTemplate;

    // roomId로 메시지 보내기
    @MessageMapping("/chat")
    public void sendMessage(ChatDto chatDto, SimpMessageHeaderAccessor accessor) {
        simpMessagingTemplate.convertAndSend("/sub/chat/" + chatDto.getRoomId(), chatDto);
    }


    // 유저가 처음 들어올때
    @MessageMapping("/welcome")
    public void sendWelcomeMessage(ChatDto chatDto, SimpMessageHeaderAccessor accessor) {
        List<String> check = roomInfo.get(chatDto.getRoomId());
        if (check == null) {
            List<String> list = new ArrayList<>();
            list.add(chatDto.getNickname());
            roomInfo.put(chatDto.getRoomId(), list);
        } else {
            check.add(chatDto.getNickname());
            roomInfo.put(chatDto.getRoomId(), check);
        }
        chatDto.setParticipants(roomInfo.get(chatDto.getRoomId()));
        simpMessagingTemplate.convertAndSend("/sub/welcome/" + chatDto.getRoomId(), chatDto);
    }


    // 유저가 나갈때
    @MessageMapping("/bye")
    public void bye(ChatDto chatDto, SimpMessageHeaderAccessor accessor) {
        List<String> list = roomInfo.get(chatDto.getRoomId());
        System.out.println(list);
        System.out.println(chatDto);
        System.out.println(chatDto.getNickname());
        list.remove(chatDto.getNickname());
        System.out.println(list);
        roomInfo.put(chatDto.getRoomId(), list);
        chatDto.setParticipants(roomInfo.get(chatDto.getRoomId()));
        simpMessagingTemplate.convertAndSend("/sub/bye/" + chatDto.getRoomId(), chatDto);
    }
}