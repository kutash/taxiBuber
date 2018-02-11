package com.kutash.taxibuber.websocket;

import com.kutash.taxibuber.entity.User;
import javax.websocket.Session;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The type Push context.
 */
class PushContext {

    private static AtomicBoolean instanceCreated = new AtomicBoolean();
    private final static ReentrantLock lock = new ReentrantLock();
    private static PushContext instance;
    private Map<User, Set<Session>> sessions;

    private PushContext() {
        if (instance != null) {
            throw new IllegalStateException("Already instantiated");
        }
        sessions = new ConcurrentHashMap<>();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PushContext getInstance() {
        if (!instanceCreated.get()){
            lock.lock();
            try {
                if (instance == null) {
                    instance = new PushContext();
                    instanceCreated.set(true);
                }
            }finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Add.
     *
     * @param session the session
     * @param user    the user
     */
    void add(Session session, User user) {
        sessions.computeIfAbsent(user, v -> ConcurrentHashMap.newKeySet()).add(session);
    }

    /**
     * Remove.
     *
     * @param session the session
     */
    void remove(Session session) {
        sessions.values().forEach(v -> v.removeIf(e -> e.equals(session)));
    }

    /**
     * Gets sessions.
     *
     * @return the sessions
     */
    Map<User, Set<Session>> getSessions() {
        return sessions;
    }
}
