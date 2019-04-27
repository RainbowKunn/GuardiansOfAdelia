package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponRanged;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Bows {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Short Bow";
        Material material = Material.BOW;
        int durability = 2;
        int level = 1;
        RPGClass rpgClass = RPGClass.ARCHER;
        int rangedDamage = 10;
        AttackSpeed attackSpeed = AttackSpeed.SLOW;
        int itemID = 101;

        if (placementNumber == 2) {
            name = "Light Bow";
            durability = 13;
            level = 10;
            rangedDamage = 40;
            itemID = 102;
        } else if (placementNumber == 3) {
            name = "Crossbow";
            durability = 5;
            level = 20;
            rangedDamage = 100;
            itemID = 103;
        } else if (placementNumber == 4) {
            name = "Battle Bow";
            durability = 12;
            level = 30;
            rangedDamage = 250;
            itemID = 104;
        } else if (placementNumber == 5) {
            name = "Satet Bow";
            durability = 3;
            level = 40;
            rangedDamage = 400;
            itemID = 105;
        } else if (placementNumber == 6) {
            name = "Leaf Fairy Bow";
            durability = 6;
            level = 50;
            rangedDamage = 650;
            itemID = 106;
        } else if (placementNumber == 7) {
            name = "Crossbow of Doom";
            durability = 4;
            level = 60;
            rangedDamage = 900;
            itemID = 107;
        } else if (placementNumber == 8) {
            name = "Unicorn Bow";
            durability = 7;
            level = 70;
            rangedDamage = 1200;
            itemID = 108;
        } else if (placementNumber == 9) {
            name = "Zephyr Bow";
            durability = 8;
            level = 80;
            rangedDamage = 1500;
            itemID = 109;
        } else if (placementNumber == 10) {
            name = "Arcade Bow";
            durability = 14;
            level = 90;
            rangedDamage = 2000;
            itemID = 110;
        }

        int damage = rangedDamage / 4;

        final WeaponRanged weapon = new WeaponRanged(name, tier, itemTag, material, durability, level, rpgClass, damage, rangedDamage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats, itemID);
        return weapon.getItemStack();
    }
}
