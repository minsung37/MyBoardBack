package com.ms.chat.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatDto {
    private String channelId;

    private Long userId;
    private String nickname;
    private String profileImg;
    private String chat;
}