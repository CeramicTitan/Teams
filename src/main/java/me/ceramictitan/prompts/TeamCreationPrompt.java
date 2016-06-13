package me.ceramictitan.prompts;

import me.ceramictitan.teams.Team;
import me.ceramictitan.teams.TeamManager;
import me.ceramictitan.teams.TeamsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamCreationPrompt extends ValidatingPrompt{
    TeamsPlugin plugin;
    TeamManager teamManager;
    public TeamCreationPrompt(TeamsPlugin plugin){
        this.teamManager = new TeamManager();
        this.plugin=plugin;
    }


    @Override
    public String getPromptText(ConversationContext context) {
        return ChatColor.BLUE+"What would you like to call your team?";
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String s) {
        Boolean allowed = !plugin.getTeamsFile().contains(s);
        String name = context.getForWhom() instanceof Player ? ((Player) context.getForWhom()).getName() : null;
        if (!allowed) {
            context.getForWhom().sendRawMessage(ChatColor.RED + "Team already exists.");
        }

        return allowed;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String s) {
        context.setSessionData("name", s);
        context.setSessionData("creator", ((Player)context.getForWhom()).getName());
        context.getForWhom().sendRawMessage(ChatColor.BLUE+ "Your team name is "+ s);
        return new TeamColorPrompt();
    }

public class TeamColorPrompt extends ValidatingPrompt{

    @Override
    protected boolean isInputValid(ConversationContext conversationContext, String s) {
        ChatColor color = ChatColor.valueOf(s.startsWith("&") ? ChatColor.translateAlternateColorCodes('&',s) : s);
        if(color != null)
            return true;
        else{
           conversationContext.getForWhom().sendRawMessage(ChatColor.RED+"Not an applicable color!");
        }
        return false;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String s) {
        context.setSessionData("color", s);
        ChatColor color = ChatColor.valueOf(s.startsWith("&") ? ChatColor.translateAlternateColorCodes('&',s) : s);
        context.getForWhom().sendRawMessage(ChatColor.BLUE+"Your color is "+ color+ s);
        return new TeamCreatedPrompt();
    }

    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return ChatColor.BLUE+"What would you like your team color to be?";
    }

}
    public class TeamCreatedPrompt extends MessagePrompt{

        @Override
        protected Prompt getNextPrompt(ConversationContext context) {
            return Prompt.END_OF_CONVERSATION;
        }

        @Override
        public String getPromptText(ConversationContext context) {
            String creator = (String)context.getSessionData("creator");
            String name = (String)context.getSessionData("name");
            String color = (String)context.getSessionData("color");
            String code = color.startsWith("&") ? ChatColor.translateAlternateColorCodes('&',color) : color;
            plugin.getTeamsFile().set(name+".color", code);
            plugin.getTeamsFile().set(name+".creator", creator);
            teamManager.addTeam(new Team(name,new ArrayList<UUID>(),ChatColor.valueOf(code), creator));
            return ChatColor.BLUE+"Team created :)";
        }
    }
}
