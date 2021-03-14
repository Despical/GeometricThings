package me.despical.armorstandeditor.commands;

import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import me.despical.commandframework.CommandFramework;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author Despical
 * <p>
 * Created at 3.03.2021
 */
public class NameCommand {

    private final Font font = new Font("Osaka", Font.PLAIN, 12);

    public NameCommand(CommandFramework commandFramework) {
        commandFramework.registerCommands(this);
    }

    @Command(
            name = "name",
            min = 1,
            senderType = Command.SenderType.PLAYER
    )
    public void nameCommand(CommandArguments arguments) {
        Player player = (Player) arguments.getSender();
        String[] args = arguments.getArguments();

        create(player, args[0], false);
    }

    public void create(Player player, String s, boolean invert) {
        s = String.join(" ", s.split(""));

        float j = 1.5f;
        Location location = player.getLocation().clone();
        int clr;

        try {
            BufferedImage image = stringToBufferedImage(font, s);

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    clr = image.getRGB(x, y);

                    if (!invert && Color.black.getRGB() != clr) {
                        continue;
                    } else if (invert && Color.black.getRGB() == clr) {
                        continue;
                    }

                    Vector v = new Vector((float) image.getWidth() / 2 - x - j, (float) image.getHeight() / 2 - y - j, 0);
                    rotateAroundAxisY(v, -location.getYaw() * (3.1415927f / 180));

                    place(location.add(v));
                    location.subtract(v);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void place(Location loc) {
        loc.getBlock().setType(Material.SANDSTONE);
    }

    public Vector rotateAroundAxisY(Vector v, double angle) {
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    public Vector rotateAroundAxisX(Vector v, double angle) {
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static BufferedImage stringToBufferedImage(Font font, String s) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = img.getGraphics();
        g.setFont(font);

        FontRenderContext frc = g.getFontMetrics().getFontRenderContext();
        Rectangle2D rect = font.getStringBounds(s, frc);
        g.dispose();

        img = new BufferedImage((int) Math.ceil(rect.getWidth()), (int) Math.ceil(rect.getHeight()), BufferedImage.TYPE_4BYTE_ABGR);
        g = img.getGraphics();
        g.setColor(Color.black);
        g.setFont(font);

        FontMetrics fm = g.getFontMetrics();
        int x = 0;
        int y = fm.getAscent();

        g.drawString(s, x, y);
        g.dispose();
        return img;
    }
}