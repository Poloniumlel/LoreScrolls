package me.polonium.lorescrolls;
import org.bukkit.*;
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
            if (offhandItem.getType() == Material.PAPER && offhandItem.getItemMeta().hasCustomModelData() && offhandItem.getItemMeta().getCustomModelData() == 696969) {
                if (!player.hasPermission("lorescrolls.usescroll")) {
                    player.sendMessage(ChatColor.RED + "You do not have permission use this scroll!");
                }
                if (mainHandItem == null || mainHandItem.getType() == Material.AIR) {
                    player.sendMessage(ChatColor.RED + "You must hold an item in your main hand to apply the lore scroll.");
                    return;
                }
                ItemMeta mainHandMeta = mainHandItem.getItemMeta();
                if (mainHandMeta != null && mainHandMeta.hasLore()) {
                    player.sendMessage(ChatColor.RED + "You already have lore on the main hand item. To reset it, use /resetlore.");
                    return;
                }
                FileConfiguration config = plugin.getConfig();
                ItemMeta offhandMeta = offhandItem.getItemMeta();
                if (offhandMeta != null && offhandMeta.hasLore()) {
                        mainHandMeta.setLore(offhandMeta.getLore());
                        player.sendMessage(ChatColor.AQUA + "Lore applied to the main hand item.");
                        mainHandItem.setItemMeta(mainHandMeta);
                        player.getInventory().setItemInOffHand(null);
                        spawnParticles(player.getLocation());
                    } else {
                    player.sendMessage(ChatColor.RED + "The lore scroll in your offhand doesn't have lore to apply.");
                }
                }
            }
        }


    public void spawnParticles(Location centerLocation) {
        int particlesSpawned = 0;

        World world = centerLocation.getWorld();

        for (int i = 0; i < 20; i++) {
            double offsetX = (Math.random() - 0.5) * 3.0;
            double offsetY = (Math.random() - 0.5 + 1) * 3.0;
            double offsetZ = (Math.random() - 0.5) * 3.0;

            Location particleLocation = centerLocation.clone().add(offsetX, offsetY, offsetZ);
            world.spawnParticle(Particle.ELECTRIC_SPARK, particleLocation, 1, 0, 0, 0, 0);
            particlesSpawned++;
        }
        for (int i = 0; i < 20; i++) {
            double offsetX = (Math.random() - 0.5) * 3.0;
            double offsetY = (Math.random() - 0.5 + 1) * 3.0;
            double offsetZ = (Math.random() - 0.5) * 3.0;
            Location particleLocation = centerLocation.clone().add(offsetX, offsetY, offsetZ);
            world.spawnParticle(Particle.FIREWORKS_SPARK, particleLocation, 1, 0, 0, 0, 0);
            particlesSpawned++;
        }
        for (int i = 0; i < 20; i++) {
            double offsetX = (Math.random() - 0.5) * 3.0;
            double offsetY = (Math.random() - 0.5 + 1) * 3.0;
            double offsetZ = (Math.random() - 0.5) * 3.0;
            Location particleLocation = centerLocation.clone().add(offsetX, offsetY, offsetZ);
            world.spawnParticle(Particle.SPELL_WITCH, particleLocation, 1, 0, 0, 0, 0);
            particlesSpawned++;
        }
    }
}
