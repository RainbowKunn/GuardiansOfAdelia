package io.github.lix3nn53.guardiansofadelia.commands.admin;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonInstance;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoom;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomDoor;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomSpawner;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandAdminDungeon implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("admindungeon")) {
            return false;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "/admindungeon add room [theme] <roomNo>");
                player.sendMessage(ChatColor.YELLOW + "/admindungeon add door [theme] <roomNo> <material>" + ChatColor.GOLD + " !!select WorldEdit region first!!");
                player.sendMessage(ChatColor.YELLOW + "/admindungeon add wave [theme] <roomNo> <waveNo> <mobCode> <mobLevel> <amount>" + ChatColor.GOLD + " !!look at spawner location block!!");
                player.sendMessage(ChatColor.YELLOW + "/admindungeon add checkpoint" + ChatColor.GOLD + " !!look at spawner location block!!");
            } else if (args[0].equals("add")) {
                switch (args[1]) {
                    case "room": {
                        String key = args[2];
                        int roomNo = Integer.parseInt(args[3]);

                        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                        DungeonTheme dungeonTheme = dungeonThemes.get(key);

                        List<DungeonRoomDoor> doors = new ArrayList<>();
                        HashMap<Integer, List<DungeonRoomSpawner>> waves = new HashMap<>();
                        List<Integer> nextRooms = new ArrayList<>();

                        DungeonRoom dungeonRoom = new DungeonRoom(doors, waves, nextRooms);

                        dungeonTheme.addDungeonRoom(roomNo, dungeonRoom);
                        player.sendMessage("Added new room");
                        break;
                    }
                    case "door": {
                        String key = args[2];
                        int roomNo = Integer.parseInt(args[3]);
                        Material material = Material.valueOf(args[4]);

                        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                        DungeonTheme dungeonTheme = dungeonThemes.get(key);

                        DungeonRoom room = dungeonTheme.getDungeonRoom(roomNo);

                        BukkitPlayer bPlayer = BukkitAdapter.adapt(player);
                        try {
                            Region region = WorldEdit.getInstance().getSessionManager().get(bPlayer).getSelection(bPlayer.getWorld());

                            BlockVector3 min = region.getMinimumPoint();
                            BlockVector3 max = region.getMaximumPoint();

                            BoundingBox boundingBox = new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());

                            Location start = MiniGameManager.getDungeonInstance(key, 1).getStartLocation(1);
                            Location multiply = start.multiply(-1);

                            BoundingBox shift = boundingBox.shift(multiply);

                            DungeonRoomDoor dungeonRoomDoor = new DungeonRoomDoor(material, shift);
                            room.addDoor(dungeonRoomDoor);
                            player.sendMessage("Added new door");

                            remakeHolograms(key);
                        } catch (IncompleteRegionException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "spawner": {
                        String key = args[2];
                        int roomNo = Integer.parseInt(args[3]);
                        int waveNo = Integer.parseInt(args[4]);
                        String mobCode = args[5];
                        int mobLevel = Integer.parseInt(args[6]);
                        int amount = Integer.parseInt(args[7]);

                        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                        DungeonTheme dungeonTheme = dungeonThemes.get(key);

                        Block targetBlock = player.getTargetBlock(null, 12);
                        Location add = targetBlock.getLocation().add(0.5, 1, 0.5);

                        Location start = MiniGameManager.getDungeonInstance(key, 1).getStartLocation(1);
                        Vector subtract = start.toVector().subtract(add.toVector());
                        DungeonRoomSpawner spawner = new DungeonRoomSpawner(mobCode, mobLevel, amount, subtract);

                        DungeonRoom dungeonRoom = dungeonTheme.getDungeonRoom(roomNo);
                        dungeonRoom.addSpawner(waveNo, spawner);
                        player.sendMessage("Added new spawner");
                        remakeHolograms(key);
                        break;
                    }
                    case "checkpoint": {
                        String key = args[2];

                        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                        DungeonTheme dungeonTheme = dungeonThemes.get(key);

                        Block targetBlock = player.getTargetBlock(null, 12);
                        Location add = targetBlock.getLocation().add(0.5, 1, 0.5);

                        Location start = MiniGameManager.getDungeonInstance(key, 1).getStartLocation(1);
                        Vector offset = start.toVector().subtract(add.toVector());

                        dungeonTheme.addCheckpointOffset(offset);

                        Checkpoint checkpoint = new Checkpoint(start.clone().add(offset));

                        for (int i = 0; i < 999; i++) {
                            if (MiniGameManager.dungeonInstanceExists(key, i)) {
                                DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(key, i);

                                dungeonInstance.addCheckpoint(checkpoint);
                            } else {
                                break;
                            }
                        }

                        player.sendMessage("Added new checkpoint");
                        break;
                    }
                    case "startroom": {
                        String key = args[2];
                        int roomNo = Integer.parseInt(args[3]);

                        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                        DungeonTheme dungeonTheme = dungeonThemes.get(key);

                        boolean b = dungeonTheme.addStartingRooms(roomNo);

                        if (!b) {
                            player.sendMessage("Room does not exist");
                            return false;
                        }

                        player.sendMessage("Added new startroom");
                        break;
                    }
                }
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }

    private void remakeHolograms(String themeKey) {
        for (int i = 0; i < 999; i++) {
            if (MiniGameManager.dungeonInstanceExists(themeKey, i)) {
                DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(themeKey, i);

                dungeonInstance.remakeDebugHolograms();
            } else {
                break;
            }
        }
    }
}