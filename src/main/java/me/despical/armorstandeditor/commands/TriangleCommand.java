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
public class TriangleCommand {

    public TriangleCommand(CommandFramework commandFramework) {
        commandFramework.registerCommands(this);
    }

    @Command(
            name = "triangle",
            min = 1,
            senderType = Command.SenderType.PLAYER
    )
    public void nameCommand(CommandArguments arguments) {
        Player player = (Player) arguments.getSender();
        String[] args = arguments.getArguments();
        int length = moreEquality(Integer.parseInt(args[0]));
        int reversedLength = moreEquality(Integer.parseInt(args[0]));
        boolean horizontal = args.length >= 2 && Boolean.parseBoolean(args[1]);
        boolean reversed = args.length >= 3 && Boolean.parseBoolean(args[2]);

        Location origin = player.getLocation().clone();

        for (int i = 0; length > 0; i++, length -= 2) {
            makeLine(origin.clone().add(i, 0, horizontal ? 0 : i), length, horizontal ? i : 0);
        }

        if (reversed) {
            for (int i = 0; reversedLength > 0; i++, reversedLength -= 2) {
                makeLine(origin.clone().add(i, 0, horizontal ? 0 : i), reversedLength, -i);
            }
        }
    }

    public void makeLine(Location origin, int length, int y) {
        for (int x = 0; x < length; x++) {
            if (x == 0) {
                origin.clone().add(x, y, 0).getBlock().setType(Material.DIAMOND_BLOCK);
                continue;
            }

            if (x == length - 1) {
                origin.clone().add(x, y, 0).getBlock().setType(Material.REDSTONE_BLOCK);
                continue;
            }

            if (x % 2 == 0) {
                origin.clone().add(x, y, 0).getBlock().setType(Material.COAL_BLOCK);
            } else {
                origin.clone().add(x, y, 0).getBlock().setType(Material.IRON_BLOCK);
            }
        }
    }

    public int moreEquality(int i) {
        return i % 2 != 0 ? i : i + 1;
    }
}