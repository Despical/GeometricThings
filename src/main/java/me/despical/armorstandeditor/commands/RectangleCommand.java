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
 * Created at 6.03.2021
 */
public class RectangleCommand {

    public RectangleCommand(CommandFramework commandFramework) {
        commandFramework.registerCommands(this);
    }

    @Command(
            name = "rectangle",
            min = 2,
            senderType = Command.SenderType.PLAYER
    )
    public void rectangleCommand(CommandArguments arguments) {
        Player player = (Player) arguments.getSender();
        String[] args = arguments.getArguments();
        int sideLength = Integer.parseInt(args[0]), height = Integer.parseInt(args[1]);
        boolean horizontal = args.length == 3 && Boolean.parseBoolean(args[2]);

        Location origin = player.getLocation().clone();

        for (int y = 0; y < height; y++) {
            makeRectangle(origin.clone(), sideLength, height, y, horizontal);
        }
    }

    public void makeRectangle(Location origin, int x, int z, int y, boolean horizontal) {
        if (horizontal) {
            for (int nx = 0; nx < x; nx++) {
                origin.clone().add(nx, y, 0).getBlock().setType(Material.SANDSTONE);
            }

            return;
        }

        for (int nx = 0; nx < x; nx++) {
            for (int nz = 0; nz < z; nz++) {
                origin.clone().add(nx, 0, nz).getBlock().setType(Material.SANDSTONE);
            }
        }
    }
}