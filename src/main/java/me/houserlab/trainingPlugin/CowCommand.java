package me.houserlab.trainingPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CowCommand implements CommandExecutor, TabExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        TrainingPlugin plugin = TrainingPlugin.getInstance();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be executed by players.");
            TrainingPlugin.getInstance().getLogger().warning(sender.getName() + " Just attempted to run the command: '" + label + "' But they are not a player!");
            return true;
        }

        UUID id = player.getUniqueId();

        long now = System.currentTimeMillis();
        long cooldown = 15000;

        if (plugin.commandCooldown.containsKey(id)) {
            long expires = plugin.commandCooldown.get(id);

            if (now < expires) {
                long left = (expires - now) / 1000;
                player.sendMessage("Cooldown left: " + left + "secs remaining");
                return true;
            }
        }


        if (args.length != 2) {
            TrainingPlugin.getInstance().getLogger().warning("Command issue :(");
            sender.sendMessage("Please specify an adult/baby cow B-), and quantity ");
            return false;
        }

        try {
            Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid Number: " + args[1] + ", please enter valid number.");
            return true;
        }

        if (Integer.parseInt(args[1]) > 500) {
            sender.sendMessage("Bro too many, no more than 100 please");
            return false;
        }

        int count = Integer.parseInt(args[1]);

        plugin.commandCooldown.put(id, now + cooldown);

        new BukkitRunnable() {

            int i = 1;

            @Override
            public void run() {

                if (i > count) {
                    cancel();
                    return;
                }

                Location loc = player.getLocation();
                Cow cow = player.getWorld().spawn(loc, Cow.class);

                if (args[0].equalsIgnoreCase("baby")) {
                    sender.sendMessage("Spawning special Baby cow ;)" + " " + i);
                    cow.setBaby();

                } else if (args[0].equalsIgnoreCase("adult")) {
                    sender.sendMessage("Spawned big daddy cow ;)" + " " + i);

                }

                AttributeInstance attr = cow.getAttribute(Attribute.MAX_HEALTH);
                if (attr != null) {
                    attr.setBaseValue(40.0);
                    cow.setHealth(40.0);
                }


                cow.getPersistentDataContainer().set(TrainingPlugin.COWSPLOSION_KEY, PersistentDataType.BOOLEAN, true);


                cow.setCustomName(ChatColor.RED + "MILKY ME");
                cow.setCustomNameVisible(true);

                i++;


            }


        }.runTaskTimer(TrainingPlugin.getInstance(), 0L, 0L);


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            return Arrays.asList("baby", "adult");
        }
        return new ArrayList<>(); // null = all player names for some reason
    }
}
