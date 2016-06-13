package me.ceramictitan.teams;


import me.ceramictitan.prompts.TeamCreationPrompt;
import me.ceramictitan.prompts.TeamsPrefix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamsPlugin extends JavaPlugin {
    public FileConfiguration teams = new YamlConfiguration();
    private ConversationFactory conversationFactory;
    private RequestHandler handler;
    private TeamManager tManager;


    public void onEnable(){
    if(!new File("plugins/teams/teams.yml").exists()){
        try {
            teams.save("plugins/teams/teams.yml");
            Bukkit.getLogger().info("TeamsPlugin.yml saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        try {
            teams.load("plugins/teams/teams.yml");
            Bukkit.getLogger().info("TeamsPlugin.yml loaded!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        this.tManager = new TeamManager();
        this.handler = new RequestHandler();
        this.conversationFactory = new ConversationFactory(this)
          .withModality(true)
          .withPrefix(new TeamsPrefix())
          .withFirstPrompt(new TeamCreationPrompt(this))
          .withLocalEcho(false)
          .withEscapeSequence("/quit")
          .withTimeout(45)
          .thatExcludesNonPlayersWithMessage("Go away evil console!");
    }
    public void onDisable(){
        try {
            teams.save("plugins/teams/teams.yml");
            Bukkit.getLogger().info("TeamsPlugin.yml saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("teams") && args.length == 1){
            if(args[0].equalsIgnoreCase("create")){
                conversationFactory.buildConversation((Conversable)sender).begin();
                return true;
            }else if(args[0].equalsIgnoreCase("disband")){

            }else if(args[0].equalsIgnoreCase("accept")){

            }else if(args[0].equalsIgnoreCase("decline")){

            }
        }else if (cmd.getName().equalsIgnoreCase("teams") && args.length == 2){
            if(args[0].equalsIgnoreCase("invite")){
                    if(sender instanceof Player){
                        Player requester = (Player)sender;
                        Player target = getServer().getPlayer(args[1]);
                        if(target !=null){
                            handler.sendRequest(requester, target);
                            requester.sendMessage("Invite sent to "+target.getName());
                            target.sendMessage("You have a team invite from "+ requester.getName()+"("+tManager.getTeamNameFromCreator(requester.getName())+")" );//Fixed NPE
                            return true;
                        }else{
                            sender.sendMessage(ChatColor.YELLOW+ String.valueOf(args[1])+ChatColor.RED+" is offline!");
                            return true;
                        }
                    }
            }
        }
        return false;
    }
    public FileConfiguration getTeamsFile(){
        return teams;
    }
}
