package me.houserlab.trainingPlugin;

import org.bukkit.entity.Cow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntityRightClick(PlayerInteractEntityEvent event) {
        TrainingPlugin.getInstance().log("Entity click Detected: " + event.getPlayer().getName() + " Clicked on " + event.getRightClicked().getName());
        if (event.getRightClicked() instanceof Cow cow) {
            cow.getWorld().createExplosion(cow.getLocation(), 10);
        }
    }

    @EventHandler
    public void onCowDie(EntityDeathEvent event) {
        if (event.getEntity() instanceof Cow cow) {
            cow.getWorld().createExplosion(cow.getLocation(), 10);
        }
    }

}
