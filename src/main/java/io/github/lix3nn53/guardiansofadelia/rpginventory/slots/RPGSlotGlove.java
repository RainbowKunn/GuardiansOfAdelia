package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class RPGSlotGlove extends RPGSlotPassive implements RPGSlot {

    private final RPGSlotType passiveType = RPGSlotType.GLOVE;

    public boolean doesFit(ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasString(itemStack, "passive")) {
            String typeStr = PersistentDataContainerUtil.getString(itemStack, "passive");
            return RPGSlotType.valueOf(typeStr).equals(this.passiveType);
        }
        return false;
    }

    public ItemStack getFillItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_AXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Gloves Slot");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(6);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
