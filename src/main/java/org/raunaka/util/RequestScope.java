package org.raunaka.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author rAun007
 */
public class RequestScope {

    private static ThreadLocal<ConcurrentHashMap<Object, Object>> scope = new ThreadLocal<>();

    public static void init() {
        scope.set(new ConcurrentHashMap<>());
    }

    public static void destroy() {
        scope.remove();
    }

    public static void initWithState(ConcurrentHashMap<Object, Object> state) {
        scope.set(state);
    }

    public static ConcurrentHashMap<Object, Object> getScope() {
        return new ConcurrentHashMap<>(scope.get());
    }

    public static void put(Object key, Object value) {
        if (!contextInitialized()) throw new RuntimeException("Request scope not initialized ");
        scope.get().put(key, value);
    }

    public static Object get(Object key) {
        if (!contextInitialized()) throw new RuntimeException("Request scope not initialized ");
        return scope.get().get(key);
    }

    private static boolean contextInitialized() {
        return null != scope.get();
    }

    public static boolean isSet() {
        return contextInitialized();
    }
}
