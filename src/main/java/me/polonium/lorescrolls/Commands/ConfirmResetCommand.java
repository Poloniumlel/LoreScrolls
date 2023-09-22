package me.polonium.lorescrolls.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfirmResetCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public ConfirmResetCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // Check if the player has an item in their main hand
        if (itemInHand == null || itemInHand.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You must hold an item in your main hand to reset its lore.");
            return true;
        }
        if (!player.hasPermission("lorescrolls.resetlore")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute this command");
        }

        ItemMeta itemMeta = itemInHand.getItemMeta();

        // Check if the item has lore
        if (itemMeta == null || !itemMeta.hasLore()) {
            player.sendMessage(ChatColor.RED + "The item in your main hand does not have any lore to reset.");
            return true;
        }

        // Check if the Blocktag configuration contains the forbidden lore string
        String blockLore = plugin.getConfig().getString("Blocktag");

        if (blockLore != null && blockLore.equals(itemMeta.getLore().get(0))) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "This item cannot have its lore reset.");
            return true;
        }

        // Reset the lore to an empty list
        itemMeta.setLore(null);
        itemInHand.setItemMeta(itemMeta);

        player.sendMessage(ChatColor.AQUA + "Lore reset successfully.");

        return true;
    }
}
