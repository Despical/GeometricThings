package me.despical.armorstandeditor.commands;

import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import me.despical.commandframework.CommandFramework;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author Despical
 * <p>
 * Created at 5.03.2021
 */
public class TowerCommand {

    public TowerCommand(CommandFramework commandFramework) {
        commandFramework.registerCommands(this);
    }

    @Command(
            name = "tower",
            min = 2,
            senderType = Command.SenderType.PLAYER
    )
    public void nameCommand(CommandArguments arguments) {
        Player player = (Player) arguments.getSender();
        String[] args = arguments.getArguments();
        int radius = Integer.parseInt(args[0]), height = Integer.parseInt(args[1]);
        boolean pyramid = args.length == 3 && Boolean.parseBoolean(args[2]);

        Location origin = player.getLocation().clone();

        for (int y = 0; y < height; y++) {
            makeSquare(origin.clone(), radius, radius, y);

            if (pyramid && y == height - 1) {
                makeRoof(origin.clone().add(-2, y + 1, -2), moreEquality(radius + 2));
            }
        }
    }

    public void makeSquare(Location origin, int x, int z, int y) {
        origin = origin.add(0, y, 0);

        for (int nx = 0; nx < x; nx++) {
            for (int nz = 0; nz < z; nz++) {
                origin.clone().add(nx, 0, nz).getBlock().setType(Material.SANDSTONE);
            }
        }
    }

    public void makeRoof(Location origin, int sideLength) {
        for (int y = 0; sideLength > 0; y++, sideLength -= 2) {
            fillArea(origin.clone().add(y + 1, 0, y + 1), sideLength, sideLength, y); // y pozitive = normal, y negative = reverse
        }
    }

    public void fillArea(Location origin, int x, int z, int y) {
        Location newOrigin = origin.add(0, y, 0);

        for (int nx = 0; nx < x; nx++) {
            for (int nz = 0; nz < z; nz++) {
                if (x + z == 2) {
                    newOrigin.clone().add(nx, 0, nz).getBlock().setType(Material.DIAMOND_BLOCK);
                    continue;
                }

                if (nx == 0 && nz == 0) {
                    newOrigin.clone().add(nx, 0, nz).getBlock().setType(Material.REDSTONE_BLOCK);
                    continue;
                }

                if (nx == x - 1 && nz == 0) {
                    newOrigin.clone().add(nx, 0, nz).getBlock().setType(Material.REDSTONE_BLOCK);
                    continue;
                }

                if (nx == 0 && nz == x - 1) {
                    newOrigin.clone().add(nx, 0, nz).getBlock().setType(Material.REDSTONE_BLOCK);
                    continue;
                }

                if (nx == nz) {
                    newOrigin.clone().add(nx, 0, nz).getBlock().setType(Material.REDSTONE_BLOCK);
                    continue;
                }

                newOrigin.clone().add(nx, 0, nz).getBlock().setType(Material.SANDSTONE);
            }
        }
    }

    public int moreEquality(int i) {
        return i % 2 != 0 ? i : i + 1;
    }
}