package com.ankoki.olysene.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {
    private Utils() {}
    private static final TreeMap<Integer, String> rn = new TreeMap<>();

    public static String coloured(String uncoloured) {
        return ChatColor.translateAlternateColorCodes('&', uncoloured);
    }

    public static net.md_5.bungee.api.ChatColor fromColour(Color colour) {
        return net.md_5.bungee.api.ChatColor.of(colour);
    }

    public String asString(String[] array, boolean spaces) {
        StringBuilder builder = new StringBuilder();
        for (String s : array) {
            builder.append(s);
            if (spaces) builder.append(" ");
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    public static boolean checkPluginVersion(Plugin plugin, int major, int minor) {
        major *= 10;
        int pluginVer = Integer.parseInt(plugin.getDescription().getVersion().replace(".", ""));
        int required = major + minor;
        return pluginVer >= required;
    }

    public static String toRoman(int number) {
        if (rn.size() == 0) {
            rn.put(1000, "M");
            rn.put(900, "CM");
            rn.put(500, "D");
            rn.put(400, "CD");
            rn.put(100, "C");
            rn.put(90, "XC");
            rn.put(50, "L");
            rn.put(40, "XL");
            rn.put(10, "X");
            rn.put(9, "IX");
            rn.put(5, "V");
            rn.put(4, "IV");
            rn.put(1, "I");
        }
        int l = rn.floorKey(number);
        if (number == l) {
            return rn.get(number);
        }
        return rn.get(l) + toRoman(number - l);
    }

    //use 0.3, 0.3, 0.3, 0, 2, 4
    public static String rainbow(String message, double freq1, double freq2, double freq3,
                                 double phase1, double phase2, double phase3, boolean pastel) {
        int center = pastel ? 200 : 128;
        int width = pastel ? 55 : 127;
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for (String s : message.split("")) {
            float red = (float) (Math.sin(freq1 * i + phase1) * width + center);
            float green = (float) (Math.sin(freq2 * i + phase2) * width + center);
            float blue = (float) (Math.sin(freq3 * i + phase3) * width + center);
            if (red > 255 || red < 0) red = 0;
            if (green > 255 || green < 0) green = 0;
            if (blue > 255 || blue < 0) blue = 0;
            builder.append(net.md_5.bungee.api.ChatColor.of(new Color((int) red, (int) green, (int) blue))).append(s);
            i++;
        }
        return builder.toString();
    }

    public static void registerListeners(JavaPlugin plugin, Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getServer().getPluginManager().registerEvents(listener, plugin));
    }

    public static String hexColoured(String s) {
        String msg = ChatColor.translateAlternateColorCodes('&', s);
        Pattern p = Pattern.compile("#[\\da-fA-F]{6}");
        Matcher matcher = p.matcher(msg);
        if (matcher.find()) {
            int i = matcher.groupCount();
            msg = msg.replace(matcher.group(1), String.valueOf(net.md_5.bungee.api.ChatColor.of(fromHex(matcher.group(1)))));
        }
        return msg;
    }

    public static Color fromHex(String hex) {
        hex = hex.replace("#", "");
        return new Color(Integer.valueOf(hex.substring(0, 2), 16),
                Integer.valueOf(hex.substring(2, 4), 16),
                Integer.valueOf(hex.substring(4, 6), 16));
    }
}
