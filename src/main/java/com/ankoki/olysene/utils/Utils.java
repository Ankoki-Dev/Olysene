package com.ankoki.olysene.utils;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.TreeMap;

public final class Utils {
    private Utils() {}
    private static final TreeMap<Integer, String> rn = new TreeMap<>();

    public static String coloured(String uncoloured) {
        return ChatColor.translateAlternateColorCodes('&', uncoloured);
    }

    public String asString(String[] array, boolean spaces) {
        StringBuilder builder = new StringBuilder();
        for (String value : array) {
            builder.append(value);
            if (spaces) builder.append(" ");
        }
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

    public static boolean serverNewer(Version version, Version serverVersion) {
        int sMajor = Integer.parseInt(serverVersion.name().split("_")[1]);
        int sMinor = Integer.parseInt(serverVersion.name().split("_")[2].substring(1));

        int vMajor = Integer.parseInt(version.toString().split("_")[1]);
        int vMinor = Integer.parseInt(version.toString().split("_")[2].substring(1));

        sMajor *= 10;
        vMajor *= 10;
        int sFin = sMajor + sMinor;
        int vFin = vMajor + vMinor;

        return sFin >= vFin;
    }
}
