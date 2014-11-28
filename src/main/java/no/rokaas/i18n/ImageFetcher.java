package no.rokaas.i18n;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/** Fetching images/icons from property files. Using flyweight pattern for speed. */
public final class ImageFetcher {
    private static Logger logger = Logger.getLogger(ImageFetcher.class.getName());

    private static ResourceBundle imageBundle;
    private static MessageFormat formatter = new MessageFormat("");


    private ImageFetcher() {
    }

    public static List<Image> getIconSeries(String property, IconSize... sizes) {
        List<Image> ikoner = new ArrayList<>(sizes.length);
        for (IconSize iconSize : sizes) {
            String path = getPathWithDefaultValue(property, null, iconSize.getPrefix());
            InputStream stream = ImageFetcher.class.getResourceAsStream(path);
            if (stream != null) {
                ikoner.add(new Image(stream));
            } else {
                logger.warning(String.format("Could not find resource at %s", path));
            }
        }
        return ikoner;
    }

    public static String getPathWithDefaultValue(String property, String defaultValue, Object... arguments) {
        String tekst = getPathWithDefaultValue(property, defaultValue);
        formatter.setLocale(Locale.getDefault());
        formatter.applyPattern(tekst);
        return formatter.format(arguments);
    }


    public static String getPathWithDefaultValue(String property, String defaultValue) {
        if (!getImage().containsKey(property) && defaultValue != null) {
            return defaultValue;
        }

        try {
            return getImage().getString(property).trim();
        } catch (Exception e) {
            throw new IllegalArgumentException("Missing property '" + property + "'", e);
        }
    }

    private static ResourceBundle getImage() {
        if (imageBundle == null) {
            imageBundle = ResourceBundle.getBundle("i18n/image", Locale.getDefault());
        }
        return imageBundle;
    }

}
