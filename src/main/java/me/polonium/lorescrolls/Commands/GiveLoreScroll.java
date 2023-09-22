package me.polonium.lorescrolls.Commands;

import me.polonium.lorescrolls.LoreScroll;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GiveLoreScroll implements CommandExecutor {
    private JavaPlugin plugin;

    public GiveLoreScroll(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED +"Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("lorescrolls.give")) {
            player.sendMessage(ChatColor.RED +"You don't have permission to use this command.");
            return true;
        }
        ItemStack loreScroll = LoreScroll.createLoreScroll(plugin);

        if (args.length == 0) {
            player.getInventory().addItem(loreScroll);
            player.sendMessage(ChatColor.GREEN +"You received a lore scroll.");
        } else if (args.length == 1) {
            Player targetPlayer = sender.getServer().getPlayer(args[0]);
            if (targetPlayer != null) {
                targetPlayer.getInventory().addItem(loreScroll);
                player.sendMessage(ChatColor.AQUA +  "You gave a lore scroll to " + targetPlayer.getName() + ".");
                targetPlayer.sendMessage(ChatColor.GREEN +"You received a lore scroll from " + player.getName() + ".");
            } else {
                player.sendMessage(ChatColor.RED +"Player not found: " + args[0]);
            }
        } else {
            player.sendMessage(ChatColor.RED +"Usage: /givelorescroll [player]");
        }

        return true;
    }
}
