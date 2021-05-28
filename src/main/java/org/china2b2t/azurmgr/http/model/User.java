package org.china2b2t.azurmgr.http.model;

public class User {
    public String nickname;
    public long expire;

    /**
     * Default model for users
     * 
     * @param nickname
     * @param email
     */
    public User(String nickname, long expire) {
        this.nickname = nickname;
        this.expire = expire;
    }
}
