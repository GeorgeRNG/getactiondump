package dev.dfonline.getactiondump.util;

import dev.dfonline.getactiondump.GetActionDump;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Support hover and click events

public class ChatUtil {

    public static void playSound(SoundEvent sound) {
        playSound(sound, 1F);
    }

    public static void playSound(SoundEvent sound, float pitch) {
        playSound(sound, 2F, pitch);
    }

    public static void playSound(SoundEvent sound, float pitch, float volume) {
        if (sound != null) {
            GetActionDump.MC.player.playSound(sound, volume, pitch);
        }
    }


    public static Text andsToSectionSigns(String msg) {
        StringBuilder result = new StringBuilder();

        Pattern p = Pattern.compile("(&[a-f0-9lonmkrA-FLONMRK])");
        Matcher m = p.matcher(msg);

        int lastIndex = 0;
        while (m.find()) {
            int start = m.start();
            String between = msg.substring(lastIndex, start);
            if (between.length() != 0) {
                result.append(between);
            }
            String replace = m.group().replaceAll("&", "§");
            result.append(replace);
            lastIndex = m.end();
        }

        String between = msg.substring(lastIndex);
        if (between.length() != 0) {
            result.append(between);
        }

        return Text.literal(result.toString());
    }



    public static void sendMessage(String text) {
        sendMessage(Text.literal(text), null);
    }

    public static void sendMessage(Text text) {
        sendMessage(text, null);
    }

    public static void sendMessage(String text, ChatType prefixType) {
        sendMessage(Text.literal(text), prefixType);
    }

    public static void sendMessage(Text text, ChatType prefixType) {
        if (GetActionDump.MC.player == null) return;
        String prefix = "";
        if (prefixType != null) {
            prefix = prefixType.getString();
        }
        GetActionDump.MC.player.sendMessage(andsToSectionSigns(String.valueOf(Text.literal(prefix).append(text))), false);
        if (prefixType == ChatType.FAIL) {
            GetActionDump.MC.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, 2, 0);
        }
    }

    public static void success(String s) {
        sendMessage(s, ChatType.SUCCESS);
    }

    public static void error(String s) {
        sendMessage(s, ChatType.FAIL);
    }

    public static void info(String s) {
        sendMessage(s, ChatType.INFO);
    }
}