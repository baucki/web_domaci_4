package raf.rs.web_domaci_4.database;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerDatabase {

    private volatile static ServerDatabase instance;

    private volatile Map<String, Integer> monday;
    private volatile Map<String, Integer> tuesday;
    private volatile Map<String, Integer> wednesday;
    private volatile Map<String, Integer> thursday;
    private volatile Map<String, Integer> friday;

    private ServerDatabase() {
        monday = new ConcurrentHashMap<>();
        tuesday = new ConcurrentHashMap<>();
        wednesday = new ConcurrentHashMap<>();
        thursday = new ConcurrentHashMap<>();
        friday = new ConcurrentHashMap<>();
    }

    public static ServerDatabase getInstance() {
        if (instance == null) {
            instance = new ServerDatabase();
        }
        return instance;
    }

    public Map<String, Integer> getMonday() {
        return monday;
    }

    public Map<String, Integer> getTuesday() {
        return tuesday;
    }

    public Map<String, Integer> getWednesday() {
        return wednesday;
    }

    public Map<String, Integer> getThursday() {
        return thursday;
    }

    public Map<String, Integer> getFriday() {
        return friday;
    }

    public void clear() {
        monday.clear();
        tuesday.clear();
        wednesday.clear();
        thursday.clear();
        friday.clear();
    }

}
