package dev.dfonline.getactiondump.util;

public enum ChatType {
    SUCCESS("§a§l» ", 'f'),
    FAIL("§4§l» ", 'c'),
    INFO("§9§l» ", 'b');

    private final String prefix;
    private final char trailing;

    ChatType(String prefix, char trailing) {
        this.prefix = prefix;
        this.trailing = trailing;
    }

    public String getString() {
        return this.prefix;
    }

    public char getTrailing() {
        return trailing;
    }
}