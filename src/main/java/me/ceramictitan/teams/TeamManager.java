package me.ceramictitan.teams;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxine on 6/08/14.
 */
public class TeamManager {
    private List<Team> teamCache = new ArrayList<Team>();

    public void addTeam(Team team){
        teamCache.add(team);
    }
    public void removeTeam(Team team){
        teamCache.remove(team);
    }
    public String getTeamNameFromCreator(String creator){
        for(Team t : teamCache){
            if(t.getCreator().equals(creator)){
                return t.getName();
            }
        }
        return "";
    }
    public String getCreatorOfTeam(String name){
        for(Team t : teamCache){
            if(t.getName().equals(name)){
                return t.getCreator();
            }
        }
        return "";
    }
    public ChatColor getTeamColor(String name){
        for(Team t : teamCache){
            if(t.getName().equals(name)){
                return t.getColor();
            }
        }
        return null;
    }
}
