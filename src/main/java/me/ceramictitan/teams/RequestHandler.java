package me.ceramictitan.teams;

import com.google.common.collect.HashBiMap;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;

/**
 * Created by maxine on 6/08/14.
 */
public class RequestHandler {
    public static HashBiMap<UUID, UUID> requests = HashBiMap.create();

    public void sendRequest(Player requester, Player target){
        if(requester == target){
            return;
        }
        if(!requests.containsKey(target.getUniqueId()) || !requests.containsValue(target.getUniqueId()) && !requests.containsKey(requester.getUniqueId()) || !requests.containsValue(requester.getUniqueId())){
            requests.put(requester.getUniqueId(), target.getUniqueId());
            requester.sendMessage("[DEBUG] added players to hashmap");
        }
    }
    public boolean hasRequest(Player target){
        if(target == null){
            return false;
        }
        if(requests.containsValue(target.getUniqueId())){
            return true;
        }
        return false;
    }
    public Player getRequester(Player target){
        if (target != null) {
            for (Map.Entry<UUID, UUID> entry : requests.entrySet()) {
                if (entry.getValue().equals(target.getUniqueId())) {
                    return Bukkit.getPlayer(entry.getKey());
                }
            }
        }

        return null;
    }

    public String getRequesterName(Player target){
        return getRequester(target).getName();
    }
    public void acceptRequest(Player requester, Player target){
        if(target == null){
            return;
        }
        if(requester == null){
            return;
        }
    }
    public void denyRequest(Player requester, Player target){
        requester.sendMessage(target.getName()+ " declined your request!");
        target.sendMessage("Declined "+requester.getName()+"'s request!");
        clearUsers(requester, target);
    }
    public static void clearUsers(Player requester, Player target){
        if(requests.containsKey(target.getUniqueId()) || requests.containsValue(target.getUniqueId()) && requests.containsKey(requester.getUniqueId()) || requests.containsValue(requester.getUniqueId())){
            requests.remove(requester.getUniqueId());
            requests.remove(target.getUniqueId());
        }
    }
    //Debug
    public void clearUser(CommandSender sender){
        requests.remove(((Player)sender).getUniqueId());
    }
    public void clearRequests(){
        requests.clear();
    }
}
