package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SilenceMechanic extends MechanicComponent {

    private final List<Integer> duration;

    public SilenceMechanic(List<Integer> duration) {
        this.duration = duration;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            StatusEffectManager.setSilenced(target);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    StatusEffectManager.removeSilenced(target);
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), duration.get(skillLevel - 1));

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        if (skillLevel == 0 || skillLevel == duration.size()) {
            lore.add("Silence duration: " + (int) ((duration.get(skillLevel) / 20) + 0.5) + " seconds");
        } else {
            lore.add("Silence duration: " + (int) ((duration.get(skillLevel - 1) / 20) + 0.5) + " seconds -> " + (int) ((duration.get(skillLevel) / 20) + 0.5));
        }

        return lore;
    }
}