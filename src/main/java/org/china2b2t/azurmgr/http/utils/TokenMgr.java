package org.china2b2t.azurmgr.http.utils;

import java.util.HashMap;

import org.china2b2t.azurmgr.KeyGen;
import org.china2b2t.azurmgr.http.Model.User;

public class TokenMgr {
    public static HashMap<String, User> userBucket = new HashMap<>();

    public static String newToken(User user) {
        String token = KeyGen.genKey();
        userBucket.put(token, user);
        return token;
    }

    public static boolean validate(String token) {
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
}
