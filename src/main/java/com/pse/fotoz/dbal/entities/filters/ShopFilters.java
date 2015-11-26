package com.pse.fotoz.dbal.entities.filters;

import com.pse.fotoz.dbal.entities.PictureSession;
import com.pse.fotoz.dbal.entities.Shop;
import java.util.function.Predicate;

/**
 * Class prividing filters on shops.
 * @author Robert
 */
public class ShopFilters {
    
    /**
     * A shop is considered visible if it contains at least one picture session
     * with at least one non-hidden picture.
     * @return 
     */
    public static Predicate<Shop> isVisible() {
        Predicate<PictureSession> isPublic = session -> session.isPublic();
        Predicate<PictureSession> hasPublicPicture = session -> 
                session.getPictures().stream().
                        anyMatch(picture -> !picture.isHidden());
        
        return shop -> shop.getSessions().stream().
                anyMatch(isPublic.and(hasPublicPicture));
    }
}
