package org.china2b2t.azurmgr.http.utils;

import java.util.HashMap;

import org.china2b2t.azurmgr.KeyGen;
import org.china2b2t.azurmgr.Main;
import org.china2b2t.azurmgr.http.model.User;

public class TokenMgr {
    public static HashMap<String, User> userBucket = new HashMap<>();

    /**
     * Generate a token
     *
     * @param user
     * @return
     */
    public static String newToken(User user) {
        String token = KeyGen.genKey();
        userBucket.put(token, user);
        return token;
    }

    /**
     * Validate a token
     *
     * @param token
     * @return
     */
    public static boolean validate(String token) {
        if(token.equals(Main.roConfig.getString("master-token"))) {
            return true;
        }
        if(!userBucket.containsKey(token)) {
            return false;
        }
        User user = userBucket.get(token);
        boolean isExpired = System.currentTimeMillis() >= user.expire;
        if(isExpired) {
            userBucket.remove(token);
        } else {
            User tmp = userBucket.get(token);
            tmp.expire = System.currentTimeMillis() + 12000000;
            userBucket.put(token, tmp);
        }
        return isExpired ? false : true;
    }

    /**
     * Get the user from a token
     *
     * @param token
     * @return
     */
    public static User getUser(String token) {
        if(!userBucket.containsKey(token)) {
            return null;
        }
        return userBucket.get(token);
    }
}
