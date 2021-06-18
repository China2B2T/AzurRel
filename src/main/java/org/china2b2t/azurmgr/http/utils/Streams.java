package org.china2b2t.azurmgr.http.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Streams {
    /**
     * Convert InputStream to String
     *
     * @param is
     * @return
     * @throws Exception
     */
    public static String is2string(InputStream is) throws Exception {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(is, "UTF-8");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}