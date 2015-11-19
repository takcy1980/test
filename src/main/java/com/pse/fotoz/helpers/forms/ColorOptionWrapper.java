package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.dbal.entities.ProductOption.ColorOption;
import java.util.Map;

/**
 *
 * @author Robert
 */
public class ColorOptionWrapper {
    
    private final Map<String, String> labels;
    private final ColorOption color;

    private ColorOptionWrapper(Map<String, String> labels, ColorOption color) {
        this.labels = labels;
        this.color = color;
    }
    
    /**
     * Returns an internationalized name for the contained color option.
     * @return Internationalized name for the contained color.
     */
    public String getName() {        
        switch (color) {
            case COLOR:
                return labels.get("color_options_color");
            case GRAYSCALE:
                return labels.get("color_options_grayscale");
            case SEPIA:
                return labels.get("color_options_sepia");
            default:
                return null;
        }
    }
    
    /**
     * Returns the filename of an image representing this color option.
     * @return Filename of an image representing this color option.
     */
    public String getImage() {
        switch (color) {
            case COLOR:
                return "color.jpg";
            case GRAYSCALE:
                return "grayscale.jpg";
            case SEPIA:
                return "sepia.jpg";
            default:
                return null;
        }
    }
    
    public ColorOption getColor() {
        return color;
    }
    
    /**
     * Wraps a color option with labels.
     * @param color The color option.
     * @param labels The labels.
     * @return Color option wrapper wrapping the color option.
     */
    public static ColorOptionWrapper of(ColorOption color, 
            Map<String, String> labels) {
        return new ColorOptionWrapper(labels, color);
    }
}
