package nl.SBDeveloper.MinetopiaPC.API;

import java.util.ArrayList;

import org.bukkit.Location;

public class Computer {
    private Location location;
    private ArrayList<User> users;
    private boolean inUse;

    public Computer(Location location) {
        this.location = location;
        this.users = new ArrayList<>();
        this.inUse = false;
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}