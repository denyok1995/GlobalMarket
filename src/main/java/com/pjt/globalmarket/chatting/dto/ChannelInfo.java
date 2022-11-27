package com.pjt.globalmarket.chatting.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChannelInfo {

    String name;

    List<Long> userIds;
}
