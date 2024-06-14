package com.debait.debait.room.entity;

import lombok.Data;

import java.util.List;

public class RoomSocket {

    @Data
    public static class Rules {
        private String debater;
        private String msg;
        private int time;
    }

    @Data
    public static class RoomInfo {
        private String name;
        private String description;
        private int teamSize;
        private int orderSize;
        private List<Rules> rules;
    }
}
