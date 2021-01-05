package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class RemoveSavedEntities extends MechanicComponent {

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        boolean cast = false;
        for (LivingEntity target : targets) {
            boolean b = SkillDataManager.removeSavedEntities(target, castCounter);
            if (b) {
                cast = true;
            }
        }

        return cast;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        additions.add("Removed saved entities");

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
