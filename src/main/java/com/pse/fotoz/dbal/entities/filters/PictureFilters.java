package com.pse.fotoz.dbal.entities.filters;

import com.pse.fotoz.dbal.entities.Picture;
import java.util.function.Predicate;

/**
 * Class providing filters on pictures.
 * @author Robert
 */
public class PictureFilters {

    public static Predicate<Picture> isVisible() {
        return p -> !p.isHidden() && 
                p.getApproved() == Picture.Approved.YES &&
                p.getPrice().doubleValue() > 0;
    }
}
