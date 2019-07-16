package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MeleeAttackTrigger extends TriggerComponent {

    private final List<Integer> cooldown;
    LivingEntity caster;
    int skillLevel;

    public MeleeAttackTrigger(List<Integer> cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        this.caster = caster;
        this.skillLevel = skillLevel;

        MeleeAttackTrigger meleeAttackTrigger = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    if (target instanceof Player) {
                        TriggerListener.startListeningMeleeAttack((Player) target, meleeAttackTrigger);
                    }
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 10L);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }

    /**
     * The callback when player lands that applies child components
     */
    public boolean callback(Player attacker, LivingEntity target) {
        ArrayList<LivingEntity> targets = new ArrayList<>();
        targets.add(target);
        boolean cast = executeChildren(caster, skillLevel, targets);

        if (!cast) return false;

        MeleeAttackTrigger trigger = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                TriggerListener.startListeningMeleeAttack(attacker, trigger);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldown.get(skillLevel - 1));

        return true;
    }
}