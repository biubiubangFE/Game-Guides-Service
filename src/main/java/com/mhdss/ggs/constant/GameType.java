package com.mhdss.ggs.constant;

public enum GameType {

    DOTA_PIECE((byte) 1, "刀塔自走棋", "DOTA_Piece"),
    PUBG((byte) 2, "绝地求生", "PUBG"),
    EVALUATING((byte) 3, "主机游戏", "evaluating");

    private final Byte type;
    private final String desc;
    private final String code;

    GameType(Byte type, String desc, String code) {
        this.type = type;
        this.desc = desc;
        this.code = code;
    }

    public static GameType getType(Byte status) {

        for (GameType type : GameType.values()) {
            if (status.equals(type.getType())) {
                return type;
            }
        }
        throw new IllegalArgumentException();
    }

    public static GameType getCode(String code) {

        for (GameType game : GameType.values()) {
            if (code.equals(game.getCode())) {
                return game;
            }
        }
        throw new IllegalArgumentException();
    }

    public Byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }
}
