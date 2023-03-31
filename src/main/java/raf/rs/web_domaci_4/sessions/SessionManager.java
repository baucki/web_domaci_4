package raf.rs.web_domaci_4.sessions;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SessionManager {

    List<String> validIds;
    List<String> invalidIds;

    public static SessionManager instance;

    private SessionManager() {
        validIds = new CopyOnWriteArrayList<>();
        invalidIds = new CopyOnWriteArrayList<>();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public List<String> getValidIds() {
        return validIds;
    }

    public List<String> getInvalidIds() {
        return invalidIds;
    }
}
