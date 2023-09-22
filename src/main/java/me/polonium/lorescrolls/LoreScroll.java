package me.polonium.lorescrolls;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class LoreScroll {
    public static ItemStack createLoreScroll(Plugin plugin) {
        ItemStack loreScroll = new ItemStack(Material.PAPER); // You can change the material to your preference
        ItemMeta meta = loreScroll.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Lore Scroll");
        meta.setCustomModelData(696969);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Right-click an item while holding this item in your offhand to set its lore");
        lore.add(ChatColor.GREEN + "The item must be in your hand when using the scroll.");
        lore.add(ChatColor.GREEN + "To edit the lore of the scroll, use /setlore <message> <line>, to reset the lore /resetlore");
        lore.add(ChatColor.GREEN + "whilst the scroll is in your main hand");
        meta.setLore(lore);
        loreScroll.setItemMeta(meta);
        return loreScroll;
    }
}
