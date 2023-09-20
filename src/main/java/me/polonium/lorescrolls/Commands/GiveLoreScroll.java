package me.polonium.lorescrolls.Commands;

import me.polonium.lorescrolls.LoreScroll;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GiveLoreScroll implements CommandExecutor {
    private JavaPlugin plugin; // Field to store the plugin instance

    public GiveLoreScroll(JavaPlugin plugin) {
        this.plugin = plugin; // Initialize the field with the plugin instance
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        // Check for permission to run the command
        if (!player.hasPermission("lorescrolls.give")) {
            player.sendMessage("You don't have permission to use this command.");
            return true;
        }

        // Create a lore scroll item using your LoreScroll class
        ItemStack loreScroll = LoreScroll.createLoreScroll(plugin);

        if (args.length == 0) {
            // Give the lore scroll to the player who ran the command
            player.getInventory().addItem(loreScroll);
            player.sendMessage("You received a lore scroll.");
        } else if (args.length == 1) {
            // Check if the argument is a valid player name
            Player targetPlayer = sender.getServer().getPlayer(args[0]);
            if (targetPlayer != null) {
                // Give the lore scroll to the specified player
                targetPlayer.getInventory().addItem(loreScroll);
                player.sendMessage("You gave a lore scroll to " + targetPlayer.getName() + ".");
                targetPlayer.sendMessage("You received a lore scroll from " + player.getName() + ".");
            } else {
                player.sendMessage("Player not found: " + args[0]);
            }
        } else {
            player.sendMessage("Usage: /givelorescroll [player]");
        }

        return true;
    }
}
