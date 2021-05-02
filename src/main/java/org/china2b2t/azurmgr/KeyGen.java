package org.china2b2t.azurmgr;

public class KeyGen {
    public static String genKey() {
        String source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();

        String str;

        for (int i = 0; i < 24; i++) {
            str = String.valueOf(source.charAt((int) Math.floor(Math.random() * source.length())));
            builder.append(str);
            source = source.replaceAll(str, "");
        }
        
        return builder.toString();
    }
}
