package me.polonium.lorescrolls;

import me.polonium.lorescrolls.Commands.ConfirmResetCommand;
import me.polonium.lorescrolls.Commands.GiveLoreScroll;
import me.polonium.lorescrolls.Commands.RenameCommand;
import me.polonium.lorescrolls.Commands.SetLoreCommand;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;

public class LoreScrolls extends JavaPlugin {

    private final Map<Character, Material> ingredientMap = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadIngredientsFromConfig();

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new LoreScrollListener(this), this);

        getCommand("setlore").setExecutor(new SetLoreCommand());
        getCommand("resetlore").setExecutor(new ConfirmResetCommand(this));
        getCommand("givelorescroll").setExecutor(new GiveLoreScroll(this));
        getCommand("rename").setExecutor(new RenameCommand());

        setupCraftingRecipe();
    }

    private void loadIngredientsFromConfig() {
        FileConfiguration config = getConfig();
        ConfigurationSection ingredientsSection = config.getConfigurationSection("recipe.ingredients");

        if (ingredientsSection != null) {
            for (String key : ingredientsSection.getKeys(false)) {
                Material material = Material.matchMaterial(ingredientsSection.getString(key, "AIR"));
                if (material != null) {
                    ingredientMap.put(key.charAt(0), material);
                }
            }
        }
    }

    private void setupCraftingRecipe() {
        FileConfiguration config = getConfig();
        String pattern = config.getString("recipe.pattern");

        if (pattern != null && !pattern.isEmpty()) {
            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "lore_scroll"), LoreScroll.createLoreScroll(this));

            // Set the shape first
            recipe.shape(
                    "ABC",
                    "DEF",
                    "GHI"
            );

            // Set the ingredients
            for (char key : pattern.toCharArray()) {
                Material material = ingredientMap.get(key);
                if (material != null) {
                    recipe.setIngredient(key, material);
                }
            }
            if (config.getBoolean("recipeenabled")) {
                getServer().addRecipe(recipe);
            }
        }
    }
}
