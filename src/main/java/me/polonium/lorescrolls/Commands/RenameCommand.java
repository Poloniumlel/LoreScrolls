package me.polonium.lorescrolls.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenameCommand implements CommandExecutor {
    private JavaPlugin plugin;

    public RenameCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p) {
            if (plugin.getConfig().getBoolean("renaming")) {
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
                    ItemStack item = p.getInventory().getItemInMainHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(format(message));
                    item.setItemMeta(itemMeta);
                    p.getInventory().setItemInMainHand(item);
                    p.sendMessage(ChatColor.AQUA + "Item renamed to:" + ChatColor.RESET + format(message));
                } else {
                    p.sendMessage(ChatColor.RED + "You do not have permission to execute this command");
                }
            } else {
                p.sendMessage(ChatColor.RED + "this command has been disabled by an administrator.");
            }
        }
            return true;
        }
        private final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        private String format (String msg){
            msg = net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg);
            Matcher matcher = pattern.matcher(msg);
            while (matcher.find()) {
                String color = msg.substring(matcher.start(), matcher.end());
                msg = msg.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                matcher = pattern.matcher(msg);
            }
            return msg;
        }
    }

