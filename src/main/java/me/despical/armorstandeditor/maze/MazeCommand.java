package me.despical.armorstandeditor.maze;

import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import me.despical.commandframework.CommandFramework;
import org.bukkit.entity.Player;

/**
 * @author Despical
 * <p>
 * Created at 7.03.2021
 */
public class MazeCommand {

    public MazeCommand(CommandFramework commandFramework) {
        commandFramework.registerCommands(this);
    }

    @Command(
            name = "maze",
            min = 3,
            senderType = Command.SenderType.PLAYER
    )
    public void mazeCommand(CommandArguments arguments) {
        Player player = (Player) arguments.getSender();
        String[] args = arguments.getArguments();
        int radius = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        boolean solve = Boolean.parseBoolean(args[2]);

        Maze maze = new Maze(radius);
        if (solve) maze.solve();
        maze.build(player.getLocation().clone(), height);
    }
}