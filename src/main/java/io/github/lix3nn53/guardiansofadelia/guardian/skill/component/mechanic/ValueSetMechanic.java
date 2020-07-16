package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class ValueSetMechanic extends MechanicComponent {

    private final String key;
    private final int value;

    public ValueSetMechanic(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public ValueSetMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("key")) {
            configLoadError("key");
        }

        if (!configurationSection.contains("value")) {
            configLoadError("value");
        }

        this.key = configurationSection.getString("key");
        this.value = configurationSection.getInt("value");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            SkillDataManager.setValue(ent, key, value);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
