package me.polonium.lorescrolls;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class LoreScroll {
    public static ItemStack createLoreScroll(Plugin plugin) {
        FileConfiguration config = plugin.getConfig();
        boolean firstLineRename = config.getBoolean("firstlinerename");

        ItemStack loreScroll = new ItemStack(Material.PAPER); // You can change the material to your preference
        ItemMeta meta = loreScroll.getItemMeta();
        meta.setDisplayName("Lore Scroll");
        meta.setCustomModelData(696969);

        // Lore explaining how to use the scroll
        List<String> lore = new ArrayList<>();
        lore.add("Right-click an item while holding this item in your offhand to set its lore");
        lore.add("The item must be in your hand when using the scroll.");
        lore.add("To edit the lore of the scroll, use /loreset <message> <line>, to reset the lore /lorereset");
        lore.add("whilst the scroll is in your main hand");

        // Check the config for firstlinerename and add the bonus line if it is true
        if (firstLineRename) {
            lore.add(0, "The first line of the lore will dictate the item's name on apply");
        }

        meta.setLore(lore);

        loreScroll.setItemMeta(meta);
        return loreScroll;
    }
}
