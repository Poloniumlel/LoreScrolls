package me.polonium.lorescrolls;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class LoreScrollListener implements Listener {

    private final JavaPlugin plugin;

    public LoreScrollListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            Player player = event.getPlayer();
            ItemStack mainHandItem = player.getInventory().getItemInMainHand();
            ItemStack offhandItem = player.getInventory().getItemInOffHand();

            // Check if the offhand item has the correct model data
            if (offhandItem.getType() == Material.PAPER && offhandItem.getItemMeta().hasCustomModelData() && offhandItem.getItemMeta().getCustomModelData() == 696969) {
                // Check if the main hand item exists
                if (mainHandItem == null || mainHandItem.getType() == Material.AIR) {
                    player.sendMessage("You must hold an item in your main hand to apply the lore scroll.");
                    return;
                }

                // Check if the main hand item has lore
                ItemMeta mainHandMeta = mainHandItem.getItemMeta();
                if (mainHandMeta != null && mainHandMeta.hasLore()) {
                    player.sendMessage("You already have lore on the main hand item. To reset it, use /lore reset.");
                    return;
                }

                // Check the configuration for firstlinerename
                FileConfiguration config = plugin.getConfig();
                boolean firstLineRename = config.getBoolean("firstlinerename");

                // Apply the lore or rename the item based on firstLineRename
                // Apply the lore or rename the item based on firstLineRename
                ItemMeta offhandMeta = offhandItem.getItemMeta();
                if (offhandMeta != null && offhandMeta.hasLore()) {
                    if (firstLineRename) {
                        String firstLine = offhandMeta.getLore().get(0);

                        // Set the display name as the first line
                        mainHandMeta.setDisplayName(firstLine);

                        // Set the lore as every line after the first line
                        List<String> loreList = offhandMeta.getLore().subList(1, offhandMeta.getLore().size());
                        mainHandMeta.setLore(loreList);

                        player.sendMessage("Item name changed to: " + firstLine);
                    } else {
                        mainHandMeta.setLore(offhandMeta.getLore());
                        player.sendMessage("Lore applied to the main hand item.");
                    }
                    mainHandItem.setItemMeta(mainHandMeta);
                    player.getInventory().setItemInOffHand(null);
                    spawnParticles(player.getLocation());
                } else {
                    player.sendMessage("The lore scroll in your offhand doesn't have lore to apply.");
                }

                mainHandItem.setItemMeta(mainHandMeta);
                    player.getInventory().setItemInOffHand(null);
                    spawnParticles(player.getLocation());
                } else {
                    player.sendMessage("The lore scroll in your offhand doesn't have lore to apply.");
                }
            }
        }

    public void spawnParticles(Location centerLocation) {
        int particlesSpawned = 0;

        // Ensure you have the correct world instance
        World world = centerLocation.getWorld();

        // Spawn 10 END_ROD particles with the SPARK particle type
        for (int i = 0; i < 10; i++) {
            double offsetX = (Math.random() - 0.5) * 3.0;
            double offsetY = (Math.random() - 0.5) * 3.0;
            double offsetZ = (Math.random() - 0.5) * 3.0;

            Location particleLocation = centerLocation.clone().add(offsetX, offsetY, offsetZ);
            world.spawnParticle(Particle.END_ROD, particleLocation, 1, 0, 0, 0, 0);
            particlesSpawned++;
        }

        // Spawn 20 FIREWORK particles
        for (int i = 0; i < 20; i++) {
            double offsetX = (Math.random() - 0.5) * 3.0;
            double offsetY = (Math.random() - 0.5 + 1) * 3.0;
            double offsetZ = (Math.random() - 0.5) * 3.0;

            Location particleLocation = centerLocation.clone().add(offsetX, offsetY, offsetZ);
            world.spawnParticle(Particle.FIREWORKS_SPARK, particleLocation, 1, 0, 0, 0, 0);
            particlesSpawned++;
        }
    }
}
