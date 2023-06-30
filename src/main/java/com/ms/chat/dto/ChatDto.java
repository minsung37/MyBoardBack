package com.ms.chat.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ChatDto {

    private String roomId;
    private Long userId;
    private String nickname;
    private String profileImg;
    private String chat;
    private List<String> participants;

}