package me.polonium.lorescrolls.Commands;

import me.polonium.lorescrolls.ColorFormatter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RenameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "Usage: /rename <name>");
                return true;
            }
            if (p.getInventory().getItemInMainHand() == (null) || p.getInventory().getItemInMainHand().getType().isAir()) {
                p.sendMessage(ChatColor.RED + "This command only woprks on items!");
                return true;
            }
            if (p.hasPermission("lorescrolls.rename")) {
                StringBuilder messageBuilder = new StringBuilder();
                for (String arg : args) {
                    messageBuilder.append(arg).append(" ");
                }
                String message = messageBuilder.toString().trim();
                String formattedmessage = ColorFormatter.format(message);
                ItemStack item = p.getInventory().getItemInMainHand();
                item.getItemMeta().setDisplayName(formattedmessage);
                p.getInventory().setItemInMainHand(item);
                p.sendMessage(ChatColor.AQUA + "Item renamed to:" + ChatColor.RESET + formattedmessage);
            } else {
                p.sendMessage(ChatColor.RED + "You do not have permission to execute this command");
            }
        }
        return true;
    }
}
