package org.china2b2t.azurmgr.http.Model;

public class User {
    public String nickname;
    public String email;
    public long expire;

    /**
     * Default model for users
     * 
     * @param nickname
     * @param email
     */
    public User(String nickname, String email, long expire) {
        this.nickname = nickname;
        this.email = email;
        this.expire = expire;
    }
}
