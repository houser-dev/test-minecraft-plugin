package me.houserlab.trainingPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntityRightClick(PlayerInteractEntityEvent event) {

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        TrainingPlugin.getInstance().log("Entity click Detected: " + event.getPlayer().getName() + " Clicked on " + event.getRightClicked().getName());
        //This is freaking ugly but i wanted it on one line, checks it's cow and checks the PDC key to make sure it is cowsplosion and holding bucket
        if (event.getRightClicked() instanceof Cow cow && Boolean.TRUE.equals(event.getRightClicked().getPersistentDataContainer().get(TrainingPlugin.COWSPLOSION_KEY, PersistentDataType.BOOLEAN)) && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BUCKET) {
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
