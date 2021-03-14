package me.despical.armorstandeditor.commands;

import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import me.despical.commandframework.CommandFramework;
//import me.despical.commons.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Despical
 * <p>
 * Created at 6.03.2021
 */
public class PentagonCommand {

    public PentagonCommand(CommandFramework commandFramework) {
        commandFramework.registerCommands(this);
    }

    @Command(
            name = "pentagon",
            min = 1,
            senderType = Command.SenderType.PLAYER
    )
    public void pentagonCommand(CommandArguments arguments) {
        Player player = (Player) arguments.getSender();
        String[] args = arguments.getArguments();
        int sides = Integer.parseInt(args[0]);
        boolean horizontal = args.length == 2 && Boolean.parseBoolean(args[1]);

        Location origin = player.getLocation().clone();

        makePolygon(origin.clone(), sides);
    }

    public void makePolygon(Location origin, int sides) {
        for (int i = 0; i < sides; i++) {
            double angle = 360.0 / sides * i;
            double nextAngle = 360.0 / sides * (i + 1);

            angle = Math.toRadians(angle);
            nextAngle = Math.toRadians(nextAngle);

            double x = Math.cos(angle) * sides;
            double z = Math.sin(angle) * sides;
            double x2 = Math.cos(nextAngle) * sides;
            double z2 = Math.sin(nextAngle) * sides;
            double deltaX = x2 - x;
            double deltaZ = z2 - z;
            double distance = Math.sqrt((deltaX - x) * (deltaX - x) + (deltaZ - z) * (deltaZ - z));

            List<ArmorStand> stands = new ArrayList<>();
            for (double d = 0; d < sides / (sides - 1); d += .1) {
                ArmorStand stand = (ArmorStand) origin.getWorld().spawnEntity( origin.clone().add(Math.ceil(x + deltaX * d), 0, Math.ceil(z + deltaZ * d)), EntityType.ARMOR_STAND);
                stand.setVisible(false);
                stand.setGravity(false);
                stand.setSmall(true);
//                stand.setHelmet(new ItemBuilder(Material.SANDSTONE).build());
                stands.add(stand);
            }

            ArmorStand previous = null;
            List<ArmorStand> newStands = new ArrayList<>();

            for (ArmorStand stand : stands) {
                if (previous == null) {
                    previous = stand;
                    continue;
                }

                if (Math.ceil(previous.getLocation().getX()) != Math.ceil(previous.getLocation().getX())) {
                    newStands.add(stand);
                } else {
                    stand.remove();
                }

                previous = stand;
            }

            stands.clear();

            for (ArmorStand stand : newStands) {
                stand.getLocation().getBlock().setType(Material.SANDSTONE);
                stand.remove();
            }
        }
    }
}