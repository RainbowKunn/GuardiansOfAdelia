package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ImmunityMechanic extends MechanicComponent {

    private final EntityDamageEvent.DamageCause damageCause;
    private final List<Integer> ticks;

    /**
     * @param damageCause
     * @param ticks       empty list for infinite
     */
    public ImmunityMechanic(EntityDamageEvent.DamageCause damageCause, List<Integer> ticks) {
        this.damageCause = damageCause;
        this.ticks = ticks;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            ImmunityListener.addImmunity(ent, damageCause);
        }

        if (!ticks.isEmpty()) {
            new BukkitRunnable() { //remove buffs from buffed players

                @Override
                public void run() {
                    for (LivingEntity ent : targets) {
                        ImmunityListener.removeImmunity(ent, damageCause);
                    }
                }
            }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), ticks.get(skillLevel - 1));
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        if (skillLevel == 0 || skillLevel == ticks.size()) {
            lore.add(damageCause.toString() + " immunity duration: " + (ticks.get(skillLevel) / 20));
        } else {
            lore.add(damageCause.toString() + " immunity duration: " + (ticks.get(skillLevel - 1) / 20) + " -> " + (ticks.get(skillLevel) / 20));
        }
        return lore;
    }
}