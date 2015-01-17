package no.rokaas.i18n;

public enum IconSize {

    ICON_16x16("16x16"),
    ICON_24x24("24x24"),
    ICON_32x32("32x32"),
    ICON_48x48("48x48"),
    ICON_64x64("64x64"),
    ICON_128x128("128x128");

    private final String prefix;

    IconSize(String prefix) {
        this.prefix = prefix;
    }


    public String getPrefix() {
        return prefix;
    }
}
