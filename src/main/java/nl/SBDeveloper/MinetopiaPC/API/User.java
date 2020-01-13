package nl.SBDeveloper.MinetopiaPC.API;

import java.util.UUID;

public class User {

    private UUID playerUUID;
    private String password;

    public User(UUID playerUUID, String password) {
        this.playerUUID = playerUUID;
        this.password = password;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
