package nl.SBDeveloper.MinetopiaPC.API;

import java.util.ArrayList;

import org.bukkit.Location;

public class Computer {

    private Location location;
    private ArrayList<User> users;
    private boolean inUse;

    public Location getLocation() {
        return location;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean getInUse() {
        return inUse;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public boolean containsUser(User user) {
        return this.users.contains(user);
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

}