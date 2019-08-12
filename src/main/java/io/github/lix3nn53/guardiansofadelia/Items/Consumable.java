package io.github.lix3nn53.guardiansofadelia.Items;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.HealMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.ManaMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.RepeatMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;

public enum Consumable {
    BUFF_PHYSICAL_DAMAGE,
    BUFF_PHYSICAL_DEFENSE,
    BUFF_MAGICAL_DAMAGE,
    BUFF_MAGICAL_DEFENSE,
    POTION_INSTANT_HEALTH,
    POTION_INSTANT_MANA,
    POTION_INSTANT_HYBRID,
    POTION_REGENERATION_HEALTH;

    public void consume(Player player, int skillLevel, ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "reqLevel")) {
            int reqLevel = PersistentDataContainerUtil.getInteger(itemStack, "reqLevel");
            if (player.getLevel() < reqLevel) {
                player.sendMessage(ChatColor.RED + "Required level to use this item is " + reqLevel);
                return;
            }
        }

        List<LivingEntity> targets = new ArrayList<>();
        targets.add(player);
        List<SkillComponent> componentList = getSkillComponents();
        for (SkillComponent component : componentList) {
            component.execute(player, skillLevel, targets);
        }

        if (PersistentDataContainerUtil.hasInteger(itemStack, "consumableUsesLeft")) {
            int usesLeft = PersistentDataContainerUtil.getInteger(itemStack, "consumableUsesLeft");
            if (usesLeft > 1) {
                PersistentDataContainerUtil.putInteger("consumableUsesLeft", usesLeft - 1, itemStack);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta.hasDisplayName()) {
                    String displayName = itemMeta.getDisplayName();
                    String replace = displayName.replace("(" + usesLeft + " Uses left)", "(" + (usesLeft - 1) + " Uses left)");
                    itemMeta.setDisplayName(replace);
                    itemStack.setItemMeta(itemMeta);
                }
            } else {
                itemStack.setAmount(0);
            }
        } else {
            itemStack.setAmount(0);
        }
    }

    public List<SkillComponent> getSkillComponents() {
        List<SkillComponent> list = new ArrayList<>();
        switch (this) {
            case BUFF_PHYSICAL_DAMAGE:
                List<Integer> ticks = new ArrayList<>();
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                List<Double> multipliers = new ArrayList<>();
                multipliers.add(0.08);
                multipliers.add(0.1);
                multipliers.add(0.12);
                multipliers.add(0.14);
                multipliers.add(0.16);
                multipliers.add(0.2);
                list.add(new BuffMechanic(BuffType.PHYSICAL_DAMAGE, multipliers, ticks));
                break;
            case BUFF_PHYSICAL_DEFENSE:
                ticks = new ArrayList<>();
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                multipliers = new ArrayList<>();
                multipliers.add(0.08);
                multipliers.add(0.1);
                multipliers.add(0.12);
                multipliers.add(0.14);
                multipliers.add(0.16);
                multipliers.add(0.2);
                list.add(new BuffMechanic(BuffType.MAGIC_DEFENSE, multipliers, ticks));
                break;
            case BUFF_MAGICAL_DAMAGE:
                ticks = new ArrayList<>();
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                multipliers = new ArrayList<>();
                multipliers.add(0.08);
                multipliers.add(0.1);
                multipliers.add(0.12);
                multipliers.add(0.14);
                multipliers.add(0.16);
                multipliers.add(0.2);
                list.add(new BuffMechanic(BuffType.MAGIC_DEFENSE, multipliers, ticks));
                break;
            case BUFF_MAGICAL_DEFENSE:
                ticks = new ArrayList<>();
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                ticks.add(60);
                multipliers = new ArrayList<>();
                multipliers.add(0.08);
                multipliers.add(0.1);
                multipliers.add(0.12);
                multipliers.add(0.14);
                multipliers.add(0.16);
                multipliers.add(0.2);
                list.add(new BuffMechanic(BuffType.MAGIC_DEFENSE, multipliers, ticks));
                break;
            case POTION_INSTANT_HEALTH:
                List<Integer> amounts = new ArrayList<>();
                amounts.add(80);
                amounts.add(200);
                amounts.add(500);
                amounts.add(800);
                amounts.add(1200);
                amounts.add(1700);
                amounts.add(2400);
                amounts.add(3000);
                amounts.add(3400);
                amounts.add(4000);
                list.add(new HealMechanic(amounts, new ArrayList<>()));
                break;
            case POTION_INSTANT_MANA:
                amounts = new ArrayList<>();
                amounts.add(40);
                amounts.add(60);
                amounts.add(80);
                amounts.add(100);
                amounts.add(120);
                amounts.add(140);
                amounts.add(160);
                amounts.add(180);
                amounts.add(200);
                amounts.add(240);
                list.add(new ManaMechanic(amounts, new ArrayList<>()));
                break;
            case POTION_INSTANT_HYBRID:
                List<Integer> amountsSecond = new ArrayList<>();
                amountsSecond.add(200);
                amountsSecond.add(200);
                amountsSecond.add(200);
                amountsSecond.add(200);
                amountsSecond.add(200);
                amountsSecond.add(200);
                amountsSecond.add(200);
                amountsSecond.add(200);
                amountsSecond.add(200);
                amountsSecond.add(200);
                list.add(new HealMechanic(amountsSecond, new ArrayList<>()));
                amounts = new ArrayList<>();
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                list.add(new ManaMechanic(amounts, new ArrayList<>()));
                break;
            case POTION_REGENERATION_HEALTH:
                List<Integer> repetitions = new ArrayList<>();
                repetitions.add(5);
                repetitions.add(6);
                repetitions.add(7);
                repetitions.add(8);
                repetitions.add(9);
                repetitions.add(10);
                repetitions.add(11);
                repetitions.add(12);
                repetitions.add(14);
                repetitions.add(18);
                RepeatMechanic repeatMechanic = new RepeatMechanic(10L, repetitions);
                amounts = new ArrayList<>();
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                amounts.add(200);
                repeatMechanic.addChildren(new HealMechanic(amounts, new ArrayList<>()));
                list.add(repeatMechanic);
                break;
        }
        return list;
    }

    public Material getMaterial() {
        switch (this) {
            case BUFF_PHYSICAL_DAMAGE:
                return Material.COOKED_BEEF;
            case BUFF_PHYSICAL_DEFENSE:
                return Material.RABBIT_STEW;
            case BUFF_MAGICAL_DAMAGE:
                return Material.COOKED_SALMON;
            case BUFF_MAGICAL_DEFENSE:
                return Material.SUSPICIOUS_STEW;
        }
        return Material.POTION;
    }

    private int getReqLevel(int skillLevel) {
        if (skillLevel == 1) {
            return 1;
        }
        return (skillLevel * 10) - 10;
    }

    public ItemStack getItemStack(int skillLevel, int uses) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemStack.getType().equals(Material.POTION)) {
            PotionMeta potionMeta = (PotionMeta) itemMeta;
            potionMeta.setColor(getPotionColor());
        }
        itemMeta.setDisplayName(getName() + " Tier-" + skillLevel + " (" + uses + " Uses left)");
        int reqLevel = getReqLevel(skillLevel);

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Required Level: " + reqLevel);
        lore.add("");
        lore.add(ChatColor.GRAY + "Hold right click while this item");
        lore.add(ChatColor.GRAY + "is in your hand to use");
        lore.add("");
        lore.addAll(getDescription());
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);
        PersistentDataContainerUtil.putString("customConsumable", toString(), itemStack);
        PersistentDataContainerUtil.putInteger("consumableLevel", skillLevel, itemStack);
        PersistentDataContainerUtil.putInteger("consumableUsesLeft", uses, itemStack);
        PersistentDataContainerUtil.putInteger("reqLevel", reqLevel, itemStack);
        return itemStack;
    }

    private Color getPotionColor() {
        switch (this) {
            case POTION_INSTANT_HEALTH:
                return Color.RED;
            case POTION_INSTANT_MANA:
                return Color.AQUA;
            case POTION_INSTANT_HYBRID:
                return Color.YELLOW;
            case POTION_REGENERATION_HEALTH:
                return Color.FUCHSIA;
        }
        return Color.RED;
    }

    public String getName() {
        switch (this) {
            case BUFF_PHYSICAL_DAMAGE:
                return ChatColor.RED + "Steak (Physical-Damage Buff)";
            case BUFF_PHYSICAL_DEFENSE:
                return ChatColor.AQUA + "Beef Stew (Physical-Defense Buff)";
            case BUFF_MAGICAL_DAMAGE:
                return ChatColor.LIGHT_PURPLE + "Cooked Fish (Magical-Damage Buff)";
            case BUFF_MAGICAL_DEFENSE:
                return ChatColor.GREEN + "Highland Soup (Magical-Defense Buff)";
            case POTION_INSTANT_HEALTH:
                return ChatColor.RED + "Health Potion";
            case POTION_INSTANT_MANA:
                return ChatColor.AQUA + "Mana Potion";
            case POTION_INSTANT_HYBRID:
                return ChatColor.LIGHT_PURPLE + "Hybrid Potion";
            case POTION_REGENERATION_HEALTH:
                return ChatColor.GOLD + "Health Regeneration Potion";
        }
        return "";
    }

    public List<String> getDescription() {
        List<String> lore = new ArrayList<>();
        switch (this) {
            case BUFF_PHYSICAL_DAMAGE:
                lore.add(ChatColor.GRAY + "Increases physical damage for 20 minutes");
                break;
            case BUFF_PHYSICAL_DEFENSE:
                lore.add(ChatColor.GRAY + "Increases physical defense for 20 minutes");
                break;
            case BUFF_MAGICAL_DAMAGE:
                lore.add(ChatColor.GRAY + "Increases magical damage for 20 minutes");
                break;
            case BUFF_MAGICAL_DEFENSE:
                lore.add(ChatColor.GRAY + "Increases magical defense for 20 minutes");
                break;
            case POTION_INSTANT_HEALTH:
                lore.add(ChatColor.GRAY + "Restores health");
                break;
            case POTION_INSTANT_MANA:
                lore.add(ChatColor.GRAY + "Restores mana");
                break;
            case POTION_INSTANT_HYBRID:
                lore.add(ChatColor.GRAY + "Restores health & mana");
                break;
            case POTION_REGENERATION_HEALTH:
                lore.add(ChatColor.GRAY + "Restores health every 2 seconds for 20 seconds");
                break;
        }
        return lore;
    }
}
