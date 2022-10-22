package dev.cosmics.pigthingy;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobEvents implements Listener {
    private static EntityType mob = null;
    private static float explosionPower = 4f;

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getType().equals(mob)) {
            event.getEntity().getLocation().getWorld().createExplosion(event.getEntity().getLocation(), explosionPower);
        }
    }

    public static void setMob(EntityType mob) {
        MobEvents.mob = mob;
    }

    public static void addExplosionPower() {
        MobEvents.explosionPower = MobEvents.explosionPower + 1f;
    }

    public static void subtractExplosionPower() {
        MobEvents.explosionPower = MobEvents.explosionPower - 1f;
    }

}
