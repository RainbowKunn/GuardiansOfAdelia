package io.github.lix3nn53.guardiansofadelia.jobs.crafting;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantStone;
import io.github.lix3nn53.guardiansofadelia.Items.list.Ingredient;
import io.github.lix3nn53.guardiansofadelia.utilities.ItemPoolGenerator;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum CraftingType {
    WEAPON_MELEE,
    WEAPON_RANGED,
    ARMOR_HEAVY,
    ARMOR_LIGHT,
    POTION,
    FOOD,
    JEWEL,
    ENCHANT_STONE;

    public String getName() {
        switch (this) {
            case WEAPON_RANGED:
                return "Ranged Weapon";
            case ARMOR_HEAVY:
                return "Heavy Armor";
            case ARMOR_LIGHT:
                return "Light Armor";
            case POTION:
                return "Potion";
            case FOOD:
                return "Food";
            case JEWEL:
                return "Jewel";
            case ENCHANT_STONE:
                return "Enchant Stone";
        }
        return "Melee Weapon";
    }

    public List<ItemStack> getItemsToCraft(GearLevel gearLevel) {
        ItemTier tier = ItemTier.MYSTIC;
        String itemTag = "Weaponsmith's";
        List<ItemStack> itemStackList = ItemPoolGenerator.generateRangedWeapons(tier, itemTag, gearLevel);

        switch (this) {
            case WEAPON_RANGED:
                itemStackList = ItemPoolGenerator.generateMeleeWeapons(tier, itemTag, gearLevel);
                break;
            case ARMOR_HEAVY:
                itemTag = "Armorsmith's";
                itemStackList = ItemPoolGenerator.generateHeavyArmors(tier, itemTag, gearLevel);
                break;
            case ARMOR_LIGHT:
                itemTag = "Armorsmith's";
                itemStackList = ItemPoolGenerator.generateLightArmors(tier, itemTag, gearLevel);
                break;
            case POTION:
                itemStackList = ItemPoolGenerator.generatePotions(gearLevel.getConsumableNo());
                break;
            case FOOD:
                itemStackList = ItemPoolGenerator.generateFoods(gearLevel.getConsumableNo());
                break;
            case JEWEL:
                itemTag = "Jeweller's";
                itemStackList = ItemPoolGenerator.generatePassives(tier, itemTag, gearLevel);
                break;
            case ENCHANT_STONE:
                itemStackList = new ArrayList<>();
                itemStackList.add(EnchantStone.TIER_ONE.getItemSTack(1));
                itemStackList.add(EnchantStone.TIER_TWO.getItemSTack(1));
                itemStackList.add(EnchantStone.TIER_THREE.getItemSTack(1));
                itemStackList.add(EnchantStone.TIER_FOUR.getItemSTack(1));
                break;
        }
        return itemStackList;
    }

    public List<ItemStack> getIngredients(GearLevel gearLevel) {
        List<ItemStack> itemStackList = new ArrayList<>();

        switch (this) {
            case WEAPON_MELEE:
                if (gearLevel.equals(GearLevel.TWO)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(4));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.THREE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(8));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.FOUR)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(12));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.FIVE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.SIX)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(4));
                } else if (gearLevel.equals(GearLevel.SEVEN)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(6));
                } else if (gearLevel.equals(GearLevel.EIGHT)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(28));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(10));
                } else if (gearLevel.equals(GearLevel.NINE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(40));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                }
                break;
            case WEAPON_RANGED:
                if (gearLevel.equals(GearLevel.TWO)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(4));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.THREE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(8));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.FOUR)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(12));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.FIVE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.SIX)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(4));
                } else if (gearLevel.equals(GearLevel.SEVEN)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(6));
                } else if (gearLevel.equals(GearLevel.EIGHT)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(28));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(10));
                } else if (gearLevel.equals(GearLevel.NINE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(40));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                }
                break;
            case ARMOR_HEAVY:
                if (gearLevel.equals(GearLevel.TWO)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(4));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.THREE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(8));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.FOUR)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(12));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.FIVE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.SIX)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(4));
                } else if (gearLevel.equals(GearLevel.SEVEN)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(6));
                } else if (gearLevel.equals(GearLevel.EIGHT)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(28));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(10));
                } else if (gearLevel.equals(GearLevel.NINE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(40));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                }
                break;
            case ARMOR_LIGHT:
                if (gearLevel.equals(GearLevel.TWO)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(4));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.THREE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(8));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.FOUR)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(12));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.FIVE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.SIX)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(4));
                } else if (gearLevel.equals(GearLevel.SEVEN)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(6));
                } else if (gearLevel.equals(GearLevel.EIGHT)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(28));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(10));
                } else if (gearLevel.equals(GearLevel.NINE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(40));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                }
                break;
            case POTION:
                if (gearLevel.equals(GearLevel.TWO)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(4));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.THREE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(8));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.FOUR)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(12));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.FIVE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.SIX)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(4));
                } else if (gearLevel.equals(GearLevel.SEVEN)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(6));
                } else if (gearLevel.equals(GearLevel.EIGHT)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(28));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(10));
                } else if (gearLevel.equals(GearLevel.NINE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(40));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                }
                break;
            case FOOD:
                if (gearLevel.equals(GearLevel.TWO)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(4));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.THREE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(8));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.FOUR)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(12));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.FIVE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.SIX)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(4));
                } else if (gearLevel.equals(GearLevel.SEVEN)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(6));
                } else if (gearLevel.equals(GearLevel.EIGHT)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(28));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(10));
                } else if (gearLevel.equals(GearLevel.NINE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(40));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                }
                break;
            case JEWEL:
                if (gearLevel.equals(GearLevel.TWO)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(4));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.THREE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(8));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.FOUR)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(12));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.FIVE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.SIX)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(4));
                } else if (gearLevel.equals(GearLevel.SEVEN)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(6));
                } else if (gearLevel.equals(GearLevel.EIGHT)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(28));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(10));
                } else if (gearLevel.equals(GearLevel.NINE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(40));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                }
                break;
            case ENCHANT_STONE:
                if (gearLevel.equals(GearLevel.TWO)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(4));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.THREE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(8));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(8));
                } else if (gearLevel.equals(GearLevel.FOUR)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(12));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.FIVE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                } else if (gearLevel.equals(GearLevel.SIX)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(4));
                } else if (gearLevel.equals(GearLevel.SEVEN)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(16));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(6));
                } else if (gearLevel.equals(GearLevel.EIGHT)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(28));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(20));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(10));
                } else if (gearLevel.equals(GearLevel.NINE)) {
                    itemStackList.add(Ingredient.WOODCUTTING_PLANK.getItemStack(40));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(24));
                    itemStackList.add(Ingredient.MINING_ORE_COPPER.getItemStack(12));
                }
                break;
        }
        return itemStackList;
    }
}
