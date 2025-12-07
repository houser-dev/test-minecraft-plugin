package me.houserlab.trainingPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntityRightClick(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }


        Material player_holding = event.getPlayer().getInventory().getItemInMainHand().getType();
        Boolean is_cowsplosion = event.getRightClicked().getPersistentDataContainer().get(TrainingPlugin.COWSPLOSION_KEY, PersistentDataType.BOOLEAN);

        TrainingPlugin.getInstance().log("Entity click Detected: " + event.getPlayer().getName() + " Clicked on " + event.getRightClicked().getName());
        //Checks is cow, has cowsplosion metadata, and player holding bucket, then, spawns explosion 10 if adult, 1 if baby
        if (entity instanceof Cow cow && Boolean.TRUE.equals(is_cowsplosion) && player_holding == Material.BUCKET) {
            if (cow.isAdult()) {
                cow.getWorld().createExplosion(cow.getLocation(), 10);
            } else {
                cow.getWorld().createExplosion(cow.getLocation(), 1);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        Boolean is_cowsplosion = event.getEntity().getPersistentDataContainer().get(TrainingPlugin.COWSPLOSION_KEY, PersistentDataType.BOOLEAN);
        Entity entity = event.getEntity();
        if (entity instanceof Cow cow && Boolean.TRUE.equals(is_cowsplosion)) {
            cow.getWorld().createExplosion(cow.getLocation(), 10);

        }

    }

}
