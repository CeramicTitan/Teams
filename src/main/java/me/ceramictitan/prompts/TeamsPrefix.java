package me.ceramictitan.prompts;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationPrefix;

public class TeamsPrefix implements ConversationPrefix {

    @Override
    public String getPrefix(ConversationContext context) {
        return ChatColor.LIGHT_PURPLE+"[TeamsPlugin]";
    }
}