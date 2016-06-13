package me.ceramictitan.teams;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Team {
    String name;
    ArrayList<UUID> team;
    ChatColor color;
    String creator;
    public Team(String name,ArrayList<UUID> team, ChatColor color, String creator){
        //Setting defaults.
        this.name = name;
        this.team=team;
        this.color=color;
        this.creator=creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team(String name){

    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public List<UUID> getTeam() {

        return team;
    }

    public void setTeam(ArrayList<UUID> team) {
        this.team = team;
    }
    public void add(UUID uuid){
        getTeam().add(uuid);
    }
    public boolean contains(UUID uuid){
        return getTeam().contains(uuid);
    }
}
