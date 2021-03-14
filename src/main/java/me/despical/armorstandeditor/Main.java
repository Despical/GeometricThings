package me.despical.armorstandeditor;

import me.despical.armorstandeditor.commands.*;
import me.despical.armorstandeditor.maze.MazeCommand;
import me.despical.commandframework.CommandFramework;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Despical
 * <p>
 * Created at 8.02.2021
 */
public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        CommandFramework commandFramework = new CommandFramework(this);

        new PyramidCommand(commandFramework);
        new NameCommand(commandFramework);
        new TowerCommand(commandFramework);
        new TempleCommand(commandFramework);
        new TriangleCommand(commandFramework);
        new RectangleCommand(commandFramework);
        new PentagonCommand(commandFramework);

        new MazeCommand(commandFramework);
    }
}