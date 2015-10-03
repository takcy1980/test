package com.pse.fotoz.helpers.ajax;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.Picture;
import java.util.Optional;

/**
 *
 * @author Robert
 */
public class AjaxHelper {
    public static void approvePicture(int pictureId) throws 
            HibernateException, IllegalArgumentException {
        Optional<Picture> picture = 
                HibernateEntityHelper.byId(Picture.class, pictureId);
        
        if (!picture.isPresent()) {
            throw new IllegalArgumentException("Given id does not match any "
                    + "picture.");
        } else {
            picture.get().setApproved(Picture.Approved.YES);
            picture.get().persist();
        }
    }
    
    public static void rejectPicture(int pictureId) throws 
            HibernateException, IllegalArgumentException {
        Optional<Picture> picture = 
                HibernateEntityHelper.byId(Picture.class, pictureId);
        
        if (!picture.isPresent()) {
            throw new IllegalArgumentException("Given id does not match any "
                    + "picture.");
        } else {
            picture.get().setApproved(Picture.Approved.NO);
            picture.get().persist();
        }
    }
}
