package com.ankoki.olysene.utils;

public enum Version {
    UNKNOWN(false, false, 0),
    v1_7_R1(true, false, 1),
    v1_7_R2(true, false, 2),
    v1_7_R3(true, false, 3),
    v1_7_R4(true, false, 4),
    v1_8_R1(true, false, 5),
    v1_9_R1(true, false, 6),
    v1_10_R1(true, false, 7),
    v1_11_R1(true, false, 8),
    v1_12_R1(true, false, 9),
    v1_12_R2(true, false, 10),
    v1_13_R1(false, false, 11),
    v1_14_R1(false, false, 12),
    v1_15_R1(false, false, 13),
    v1_16_R1(false, true, 14),
    v1_16_R2(false, true, 15),
    v1_16_R3(false, true, 16),
    v1_16_R4(false, true, 17);

    private final boolean legacy;
    private final boolean supportsHex;
    private final int ver;

    Version(boolean legacy, boolean supportsHex, int ver) {
        this.legacy = legacy;
        this.supportsHex = supportsHex;
        this.ver = ver;
    }

    public boolean isLegacy() {
        return legacy;
    }
    public boolean supportsHex() {
        return supportsHex;
    }
    public int getVer() {
        return ver;
    }
    public boolean isLaterThan(Version version) {
        return version.getVer() < this.ver;
    }
    public boolean isOlderThan(Version version) {
        return version.getVer() > this.ver;
    }
}
