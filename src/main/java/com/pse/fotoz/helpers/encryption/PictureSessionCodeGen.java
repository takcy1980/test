package com.pse.fotoz.helpers.encryption;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Creates a unique picture session code.
 * 
 * @author Gijs
 */
public class PictureSessionCodeGen {

    /**
     * Creates a unique picture session code based on the shops id and it's
     * number of sessions.
     * 
     * @param shopId Id of the shop owning the picture session.
     * @param nrOfSessions Number of sessions the shop currently has.
     * @return unique picture session code.
     */
    public static String sessionCode(int shopId, int nrOfSessions) {

        return new StringBuilder()
                .append(shopId)
                .append(nrOfSessions+1)
                .append(RandomStringUtils.random(8, true, true))
                .toString();
    }

}
