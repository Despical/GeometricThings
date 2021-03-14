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
public class TempleCommand {

    public TempleCommand(CommandFramework commandFramework) {
        commandFramework.registerCommands(this);
    }

    @Command(
            name = "temple",
            min = 1,
            senderType = Command.SenderType.PLAYER
    )
    public void templeCommand(CommandArguments arguments) {
        Player player = (Player) arguments.getSender();
        String[] args = arguments.getArguments();
        int sideLength = moreEquality(Integer.parseInt(args[0]));

        Location origin = player.getLocation().clone();

        for (int y = 0; sideLength > 0; y++, sideLength -= 2) {
            fillArea(origin.clone().add(y + 1, 0, y + 1), sideLength, sideLength, y);
        }
    }

    public void fillArea(Location origin, int x, int z, int y) {
        Location newOrigin = origin.add(0, y, 0);

        for (int nx = 0; nx < x; nx++) {
            for (int nz = 0; nz < z; nz++) {
                if (x == 1 && z == 1) {
                    newOrigin.clone().add(nx, 0, nz).getBlock().setType(Material.DIAMOND_BLOCK);
                    continue;
                }

                if (nx == 0 && nz == z - 1) {
                    newOrigin.clone().add(nx, 0, nz).getBlock().setType(Material.REDSTONE_BLOCK);
                    continue;
                }

                if (nz == 0 && nx == x - 1) {
                    newOrigin.clone().add(nx, 0, nz).getBlock().setType(Material.REDSTONE_BLOCK);
                    continue;
                }

                if (nx == nz && nz == z - 1) {
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