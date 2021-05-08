package org.china2b2t.azurmgr.api;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ITool {
    /**
     * Get the list of the settings
     * @return
     */
    public List<String> getKeys();

    /**
     * Get the type of the specified key
     * @param key
     * @return
     */
    public int getType(@Nonnull String key);

    /**
     * Change the value of the specified key
     * @param key
     * @param value
     * @return
     */
    public boolean setKey(@Nonnull String key, @Nullable Object value);
}
