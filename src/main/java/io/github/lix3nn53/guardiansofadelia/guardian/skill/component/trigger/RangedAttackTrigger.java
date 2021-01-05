package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class RangedAttackTrigger extends TriggerComponent {

    private final List<Integer> cooldowns;
    LivingEntity caster;
    int skillLevel;
    int castCounter;

    public RangedAttackTrigger(ConfigurationSection configurationSection) {
        if (configurationSection.contains("cooldowns")) {
            this.cooldowns = configurationSection.getIntegerList("cooldowns");
        } else {
            this.cooldowns = new ArrayList<>();
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        this.caster = caster;
        this.skillLevel = skillLevel;
        this.castCounter = castCounter;

        RangedAttackTrigger rangedAttackTrigger = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    if (target instanceof Player) {
                        TriggerListener.startListeningRangedAttack((Player) target, rangedAttackTrigger);
                    }
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 10L);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    /**
     * The callback when player lands that applies child components
     */
    public boolean callback(Player attacker, LivingEntity target) {
        ArrayList<LivingEntity> targets = new ArrayList<>();
        targets.add(target);
        boolean cast = executeChildren(caster, skillLevel, targets, castCounter);

        if (!cast) return false;

        RangedAttackTrigger trigger = this;

        if (cooldowns.isEmpty()) {
            TriggerListener.startListeningRangedAttack(attacker, trigger);
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    TriggerListener.startListeningRangedAttack(attacker, trigger);
                }
            }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldowns.get(skillLevel - 1));
        }

        return true;
    }
}
