# Lore Scrolls
Lore Scrolls is a Spigot plugin for Minecraft servers that allows players to enhance their gameplay by adding custom lore to their items. This plugin provides the following features:

Craftable Lore Scrolls: Craft lore scrolls using in-game materials or obtain them through admin commands.

Lore Modification: Apply custom lore to items using lore scrolls, supporting chat colors and hex colors.

Lore Protection: Prevent lore modification by specifying a protection string.

Item renaming: rename all your items using the /rename command! supports both hex and chatcolor colors!

Lore Reset: Reset an item's lore using /lorereset, unless it contains the specified protection string.
# Usage
Download the latest release from the Releases page.
Place the JAR file into your Spigot server's plugins folder.
Start or reload your server to enable the plugin.
# Commands
/resetlore - resets the lore of an item unless it has a protection string specified in the config

/givelorescroll - give yourself or another player a lore scroll.

/rename - rename your mainhand item. Supports hex colors and chat colors ( example: #00BFFF for a blue hex , &d for a chatcolor pink)

/setlore - set the lore of an item.
# Config 
default config:

Blocktag: "UnLoreAble"

recipeenabled: true

recipe:

pattern: "ABCDEFHGI"

ingredients:

A: PAPER
B: PAPER
C: PAPER
D: PAPER
E: DIAMOND
F: PAPER
G: PAPER
H: PAPER
I: PAPER
## Config info
blocktag - the string to put in the lore of an item to prevent its lore from being reset or changed

recipeenabled - whenever the recipe should be usable

pattern - leave as is , only change ingredients.

ingedients - the materials used in the crafting recipe , AIR for empty slot.

ingredient layout:

A B C

D E F

G H I

# Support
For issues, questions, or assistance, please open an issue on the GitHub repository.

# License
This plugin is open-source and distributed under the MIT License.