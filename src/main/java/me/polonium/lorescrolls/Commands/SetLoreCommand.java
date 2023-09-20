package me.polonium.lorescrolls.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetLoreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage("Usage: /setlore <message> <line>");
            return true;
        }

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // Check if the player has an item in their main hand
        if (itemInHand == null || itemInHand.getType().isAir()) {
            player.sendMessage("This item isn't a lore scroll! This command only works for lore scrolls.");
            return true;
        }

        if (!itemInHand.getItemMeta().hasCustomModelData()) {
            player.sendMessage("This item isn't a lore scroll! This command only works for lore scrolls.");
            return true;
        }

        // Check if the item has the custom model data of "696969"
        if (itemInHand.getItemMeta().getCustomModelData() != 696969) {
            player.sendMessage("This item isn't a lore scroll! This command only works for lore scrolls.");
            return true;
        }

        int loreLine;
        try {
            loreLine = Integer.parseInt(args[args.length - 1]);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid lore line number.");
            return true;
        }

        if (loreLine <= 0) {
            player.sendMessage("Lore line number must be greater than 0.");
            return true;
        }

        String message = String.join(" ", args);
        message = message.substring(0, message.length() - String.valueOf(loreLine).length()).trim();

        // Apply color formatting
        message = format(message);

        ItemMeta itemMeta = itemInHand.getItemMeta();
        if (itemMeta == null) {
            player.sendMessage("Failed to set lore. Item meta is null.");
            return true;
        }

        // Get the item's current lore or create an empty list if it doesn't exist
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }

        // Make sure there are enough lines in the lore list
        while (lore.size() < loreLine) {
            lore.add("");
        }

        // Set the specified lore line
        lore.set(loreLine - 1, message);
        itemMeta.setLore(lore);

        itemInHand.setItemMeta(itemMeta);

        player.sendMessage("Lore set successfully.");

        return true;
    }

    private final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    private String format(String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            String color = msg.substring(matcher.start(), matcher.end());
            msg = msg.replace(color, ChatColor.of(color) + "");
            matcher = pattern.matcher(msg);
        }

        return msg;
    }
}
