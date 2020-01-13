package nl.SBDeveloper.MinetopiaPC.Managers;

import nl.SBDeveloper.MinetopiaPC.API.User;

import java.util.LinkedHashMap;
import java.util.UUID;

public class DataManager {
    private static LinkedHashMap<UUID, User> userMap = new LinkedHashMap<>();

    public static void addUser(User user) {
        userMap.put(user.getPlayerUUID(), user);
    }

    public static void removeUser(UUID user) {
        userMap.remove(user);
    }

    public static void setPassword(UUID user, String password) {
        userMap.get(user).setPassword(password);
    }

    public static boolean containsUser(UUID user) {
        return userMap.containsKey(user);
    }

}
