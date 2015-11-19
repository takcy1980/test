package com.pse.fotoz.dbal.entities;

import com.pse.fotoz.helpers.forms.ColorOptionWrapper;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Robert
 */
@Entity
@Table(name="product_options")
public class ProductOption implements HibernateEntity {
    
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private ColorOption color;
    
    @Column(name = "offset_x")
    private int offsetX;
    
    @Column(name = "offset_y")
    private int offsetY;
    
    @Column(name = "width")
    private int width;
    
    @Column(name = "height")
    private int height;
    
    private transient Map<String, String> labels;
    
    public int getId() {
        return id;
    }

    public ColorOption getColor() {
        return color;
    }
    
    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }
    
    public String getColorLabel() {
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
    
    public String getColorImage() {
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

    public void setColor(ColorOption color) {
        this.color = color;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public static enum ColorOption {
        COLOR, GRAYSCALE, SEPIA
    }
}
