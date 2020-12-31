package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ManaMechanic extends MechanicComponent {

    private final List<Integer> manaAmount;
    private final List<Double> manaPercent;
    private final String multiplyWithValue;

    public ManaMechanic(List<Integer> manaAmount, List<Double> manaPercent, @Nullable String multiplyWithValue) {
        this.manaAmount = manaAmount;
        this.manaPercent = manaPercent;
        this.multiplyWithValue = multiplyWithValue;
    }

    public ManaMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("healAmountList")) {
            configLoadError("healAmountList");
        }

        if (!configurationSection.contains("healPercentList")) {
            configLoadError("healPercentList");
        }

        if (configurationSection.contains("multiplyWithValue")) {
            this.multiplyWithValue = configurationSection.getString("multiplyWithValue");
        } else {
            this.multiplyWithValue = null;
        }

        this.manaAmount = configurationSection.getIntegerList("healAmountList");
        this.manaPercent = configurationSection.getDoubleList("healPercentList");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        boolean manaFilled = false;

        for (LivingEntity ent : targets) {
            if (ent instanceof Player) {
                Player player = (Player) ent;
                UUID uuid = player.getUniqueId();
                if (GuardianDataManager.hasGuardianData(uuid)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                        int currentMana = rpgCharacterStats.getCurrentMana();

                        double maxMana = rpgCharacterStats.getTotalMaxMana();

                        if (currentMana == maxMana) continue;

                        int fillAmount = 0;
                        if (!manaPercent.isEmpty()) {
                            fillAmount = manaAmount.get(skillLevel - 1);
                        }
                        if (!manaPercent.isEmpty()) {
                            fillAmount = (int) (maxMana * manaPercent.get(skillLevel - 1) + 0.5);
                        }
                        if (multiplyWithValue != null) {
                            int multiply = SkillDataManager.getValue(caster, multiplyWithValue);
                            fillAmount *= multiply;
                        }

                        int nextMana = currentMana + fillAmount;

                        if (nextMana > maxMana) {
                            nextMana = (int) maxMana;
                        }

                        rpgCharacterStats.setCurrentMana(nextMana);
                        manaFilled = true;
                    }
                }
            }
        }

        return manaFilled;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!manaAmount.isEmpty()) {
            if (skillLevel == 0) {
                additions.add(ChatColor.AQUA + "Mana regen: " + manaAmount.get(skillLevel));
            } else if (skillLevel == manaAmount.size()) {
                additions.add(ChatColor.AQUA + "Mana regen: " + manaAmount.get(skillLevel - 1));
            } else {
                additions.add(ChatColor.AQUA + "Mana regen: " + manaAmount.get(skillLevel - 1) + " -> " + manaAmount.get(skillLevel));
            }
        }
        if (!manaPercent.isEmpty()) {
            if (skillLevel == 0) {
                additions.add(ChatColor.AQUA + "Mana regen: " + manaPercent.get(skillLevel) + "%");
            } else if (skillLevel == manaPercent.size()) {
                additions.add(ChatColor.AQUA + "Mana regen: " + manaPercent.get(skillLevel - 1) + "%");
            } else {
                additions.add(ChatColor.AQUA + "Mana regen: " + manaPercent.get(skillLevel - 1) + "%" + " -> " + manaPercent.get(skillLevel) + "%");
            }
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
