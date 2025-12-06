package me.houserlab.trainingPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CowCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be executed by players.");
            TrainingPlugin.getInstance().getLogger().warning(sender.getName() + " Just attempted to run the command: '" + label + "' But they are not a player!");
            return true;
        }

        if (args.length != 1) {
            TrainingPlugin.getInstance().getLogger().warning("Command issue :(");
            sender.sendMessage("Please specify an adult/baby cow B-)");
            return false;
        }

        Cow cow = player.getWorld().spawn(player.getLocation(), Cow.class);


        if (args[0].equalsIgnoreCase("baby")) {
            sender.sendMessage("Spawning special Baby cow ;)");
            cow.setBaby();

        } else if (args[0].equalsIgnoreCase("adult")) {
            sender.sendMessage("Spawned big daddy cow ;)");

        }

        // Sets cows persistent data container for COWSPLOSION_KEY to true, marking the cow as of origin of COWSPLOSION command
        cow.getPersistentDataContainer().set(TrainingPlugin.COWSPLOSION_KEY, PersistentDataType.BOOLEAN, true);

        cow.setCustomName(ChatColor.RED + "MILKY ME");
        cow.setCustomNameVisible(true);

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
