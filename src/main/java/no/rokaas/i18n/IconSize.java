package no.rokaas.i18n;

public enum IconSize {

    IKON_16x16("16x16"),
    IKON_24x24("24x24"),
    IKON_32x32("32x32"),
    IKON_48x48("48x48"),
    IKON_64x64("64x64"),
    IKON_128x128("128x128");

    private final String prefix;

    IconSize(String prefix) {
        this.prefix = prefix;
    }


    public String getPrefix() {
        return prefix;
    }
}
