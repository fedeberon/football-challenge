package com.santex.challenge.footballdata.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * Created by federicoberon on 01/07/2019.
 */
public final class Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    public static <T> T getValue(Map<String, T> map, String key){
        try {
            return map.get(key);
        }
        catch (NullPointerException e){
              e.printStackTrace();
        }
        return null;
    }


    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[X]");

    public static <T> Consumer<T> wrapper(Consumer<T> consumer) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (NullPointerException e) {
                LOGGER.error("Exception parser data: " + e.getMessage());
            }
        };
    }

}
