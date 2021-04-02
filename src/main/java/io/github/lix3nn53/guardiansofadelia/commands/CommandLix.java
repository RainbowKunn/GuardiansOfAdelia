package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantStone;
import io.github.lix3nn53.guardiansofadelia.Items.list.Eggs;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.RequestHandler;
import io.github.lix3nn53.guardiansofadelia.chat.StaffRank;
import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChest;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChestManager;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChestTier;
import io.github.lix3nn53.guardiansofadelia.rewards.daily.DailyRewardHandler;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class CommandLix implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("admin")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.DARK_PURPLE + "---- ADMIN ----");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin setstaff <player> [NONE|OWNER|ADMIN|DEVELOPER|BUILDER|SUPPORT|YOUTUBER|TRAINEE]");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin setdaily");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin addlootchest [0-3 = tier]");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "---- UTILS ----");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/admin fly");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/admin speed <num>");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/admin tp town <num>");
                player.sendMessage(ChatColor.DARK_BLUE + "---- RPG ----");
                player.sendMessage(ChatColor.DARK_BLUE + "/admin exp <player> <amount>");
                player.sendMessage(ChatColor.DARK_BLUE + "/admin class unlock <player> <newClass>");
                player.sendMessage(ChatColor.BLUE + "---- OTHER ----");
                player.sendMessage(ChatColor.BLUE + "/admin model portal<1-5>");
                player.sendMessage(ChatColor.BLUE + "/admin sound <code> - play custom sounds");
                player.sendMessage(ChatColor.DARK_PURPLE + "---- QUEST ----");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin quest t - turn ins current quests tasks");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin quest a <num> - accept quest tasks");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin quest gui <npcNo> - open quest gui of an npc");
                player.sendMessage(ChatColor.AQUA + "---- ITEMS ----");
                player.sendMessage(ChatColor.AQUA + "/admin coin <num>");
                player.sendMessage(ChatColor.AQUA + "/admin weapon [type] <num> [gearSet]");
                player.sendMessage(ChatColor.AQUA + "/admin armor [slot] [type] <num> [gearSet]");
                player.sendMessage(ChatColor.AQUA + "/admin egg [code] <gearLevel> <petLevel>");
                player.sendMessage(ChatColor.AQUA + "/admin stone <grade> <amount>");
                player.sendMessage(ChatColor.AQUA + "/admin passive [parrot|earring|necklace|glove|ring] <num>");
                player.sendMessage(ChatColor.AQUA + "/admin premium item-id<1-24>");
                player.sendMessage(ChatColor.AQUA + "/admin ingredient id amount");
            } else if (args[0].equals("speed")) {
                int val = Integer.parseInt(args[1]);
                if (val > 10 || val < -1) {
                    player.sendMessage("Speed must be between 1-10");
                    return false;
                }
                float valf = val / 10f;
                player.setFlySpeed(valf);
            } else if (args[0].equals("setdaily")) {
                GuiGeneric guiGeneric = new GuiGeneric(9, ChatColor.YELLOW + "Set Daily Rewards", 0);

                ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS);
                ItemMeta itemMeta = filler.getItemMeta();
                itemMeta.setDisplayName("");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("");
                itemMeta.setLore(lore);
                filler.setItemMeta(itemMeta);
                guiGeneric.setItem(0, filler);
                guiGeneric.setItem(8, filler);

                ItemStack[] rewards = DailyRewardHandler.getRewards();
                int i = 1;
                for (ItemStack itemStack : rewards) {
                    if (itemStack == null) continue;

                    guiGeneric.setItem(i, itemStack);
                    i++;
                }

                guiGeneric.setLocked(false);
                guiGeneric.openInventory(player);
            } else if (args[0].equals("addlootchest")) {
                Block targetBlock = player.getTargetBlock(null, 12);

                Material type = targetBlock.getType();

                if (!type.equals(Material.CHEST)) {
                    player.sendMessage(ChatColor.RED + "You must be looking to a chest");
                    return false;
                }

                int tierIndex = Integer.parseInt(args[1]);

                LootChestTier value = LootChestTier.values()[tierIndex];

                LootChest lootChest = new LootChest(targetBlock.getLocation(), value);

                LootChestManager.addLootChest(lootChest);
            } else if (args[0].equals("exp")) {
                if (args.length == 3) {
                    int expToGive = Integer.parseInt(args[2]);
                    Player player2 = Bukkit.getPlayer(args[1]);
                    if (player2 != null) {
                        if (GuardianDataManager.hasGuardianData(player)) {
                            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                            if (guardianData.hasActiveCharacter()) {
                                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                                activeCharacter.getRpgCharacterStats().giveExp(expToGive);
                            }
                        }
                    }
                }
            } else if (args[0].equals("class")) {
                if (args[1].equals("unlock")) {
                    if (args.length == 4) {
                        Player player2 = Bukkit.getPlayer(args[2]);
                        String newClass = args[3];
                        if (player2 != null) {
                            if (GuardianDataManager.hasGuardianData(player)) {
                                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                                if (guardianData.hasActiveCharacter()) {
                                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                                    activeCharacter.unlockClass(newClass);
                                    player2.sendMessage("Unlocked class: " + newClass);
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equals("setstaff")) {
                if (args.length == 3) {
                    try {
                        StaffRank staffRank = StaffRank.valueOf(args[2]);
                        Player player2 = Bukkit.getPlayer(args[1]);
                        if (player2 != null) {
                            if (GuardianDataManager.hasGuardianData(player)) {
                                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                                guardianData.setStaffRank(staffRank);
                            }
                        }
                    } catch (IllegalArgumentException illegalArgumentException) {
                        player.sendMessage("Unknown staff-rank");
                    }
                }
            } else if (args[0].equals("tp")) {
                if (args.length == 3) {
                    if (args[1].equals("town")) {
                        int no = Integer.parseInt(args[2]);
                        if (no < 6 && no > 0) {
                            Town town = TownManager.getTown(no);
                            player.teleport(town.getLocation());
                        }
                    }
                }
            } else if (args[0].equals("quest")) {
                if (args[1].equals("t")) {
                    if (args.length != 2) return false;
                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                            List<Quest> questList = activeCharacter.getQuestList();

                            for (Quest quest : questList) {
                                activeCharacter.getTurnedInQuests().add(quest.getQuestID());
                                quest.onTurnIn(player);
                            }

                            questList.clear();
                        }
                    }
                } else if (args[1].equals("a")) {
                    if (args.length != 3) return false;
                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                            Quest questCopyById = QuestNPCManager.getQuestCopyById(Integer.parseInt(args[2]));

                            boolean questListIsNotFull = activeCharacter.acceptQuest(questCopyById, player);
                            if (!questListIsNotFull) {
                                player.sendMessage(net.md_5.bungee.api.ChatColor.RED + "Your quest list is full");
                            }
                        }
                    }
                } else if (args[1].equals("gui")) {
                    if (args.length != 3) return false;
                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                        if (guardianData.hasActiveCharacter()) {
                            GuiGeneric questGui = QuestNPCManager.getQuestGui(player, Integer.parseInt(args[2]));
                            questGui.openInventory(player);
                        }
                    }
                }
            } else if (args[0].equals("sound")) {
                if (args.length == 2) {
                    GoaSound goaSound = GoaSound.valueOf(args[1]);
                    CustomSound customSound = goaSound.getCustomSound();
                    customSound.play(player);
                }
            } else if (args[0].equals("fly")) {
                boolean allowFlight = player.getAllowFlight();
                player.setFlying(!allowFlight);
            } else if (args[0].equals("weapon")) {
                if (args.length >= 3) {
                    WeaponGearType weaponGearType = WeaponGearType.valueOf(args[1]);
                    int no = Integer.parseInt(args[2]);
                    GearLevel gearLevel = GearLevel.values()[no];
                    String gearSet = "Command";
                    if (args.length == 4) {
                        gearSet = args[3];
                    }
                    ItemStack weapon = WeaponManager.get(weaponGearType, gearLevel, ItemTier.LEGENDARY, false, gearSet).get(0);
                    InventoryUtils.giveItemToPlayer(player, weapon);
                }
            } else if (args[0].equals("armor")) {
                if (args.length >= 4) {
                    ArmorSlot armorSlot = ArmorSlot.valueOf(args[1]);
                    ArmorGearType armorGearType = ArmorGearType.valueOf(args[2]);
                    int no = Integer.parseInt(args[3]);
                    GearLevel gearLevel = GearLevel.values()[no];
                    String gearSet = "Command";
                    if (args.length == 5) {
                        gearSet = args[4];
                    }
                    ItemStack weapon = ArmorManager.get(armorSlot, armorGearType, gearLevel, ItemTier.LEGENDARY, false, gearSet).get(0);
                    InventoryUtils.giveItemToPlayer(player, weapon);
                }
            } else if (args[0].equals("egg")) {
                if (args.length == 4) {
                    String petCode = args[1];
                    int no = Integer.parseInt(args[2]);
                    GearLevel gearLevel = GearLevel.values()[no];
                    int petLevel = Integer.parseInt(args[3]);

                    ItemStack egg = Eggs.get(petCode, gearLevel, petLevel);
                    InventoryUtils.giveItemToPlayer(player, egg);
                }
            } else if (args[0].equals("stone")) {
                if (args.length == 3) {
                    int grade = Integer.parseInt(args[1]);
                    int amount = Integer.parseInt(args[2]);
                    EnchantStone enchantStone = EnchantStone.TIER_ONE;
                    if (grade == 2) {
                        enchantStone = EnchantStone.TIER_TWO;
                    } else if (grade == 3) {
                        enchantStone = EnchantStone.TIER_THREE;
                    } else if (grade == 4) {
                        enchantStone = EnchantStone.TIER_FOUR;
                    }
                    InventoryUtils.giveItemToPlayer(player, enchantStone.getItemStack(amount));
                }
            } else if (args[0].equals("ingredient")) {
                if (args.length == 3) {
                    int no = Integer.parseInt(args[1]);
                    int amount = Integer.parseInt(args[2]);

                    ItemStack itemStack = GatheringManager.getIngredient(no).getItemStack(amount);

                    InventoryUtils.giveItemToPlayer(player, itemStack);
                }
            } else if (args[0].equals("coin")) {
                if (args.length == 2) {
                    try {
                        int price = Integer.parseInt(args[1]);
                        List<Coin> coins = EconomyUtils.priceToCoins(price);

                        for (Coin coin : coins) {
                            InventoryUtils.giveItemToPlayer(player, coin.getCoin());
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.YELLOW + "(Enter a number)");
                    }
                }
            } else if (args[0].equals("passive")) {
                if (args.length == 3) {
                    RPGSlotType rpgSlotType = RPGSlotType.valueOf(args[1]);
                    int no = Integer.parseInt(args[2]);
                    GearLevel gearLevel = GearLevel.values()[no];
                    ItemStack passive = PassiveManager.get(gearLevel, rpgSlotType, ItemTier.LEGENDARY, false, "Command").get(0);
                    InventoryUtils.giveItemToPlayer(player, passive);
                }
            } else if (args[0].equals("helmet")) {
                PlayerInventory inventory = player.getInventory();
                ItemStack itemInMainHand = inventory.getItemInMainHand();
                ItemStack helmet = inventory.getHelmet();
                if (helmet != null && !helmet.getType().equals(Material.AIR)) {
                    InventoryUtils.giveItemToPlayer(player, helmet);
                }
                inventory.setHelmet(itemInMainHand);
            } else if (args[0].equals("model")) {
                if (args.length == 2) {
                    Location location = player.getLocation();
                    ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                    ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if (args[1].equals("portal1")) {
                        itemMeta.setCustomModelData(6);
                    } else if (args[1].equals("portal2")) {
                        itemMeta.setCustomModelData(7);
                    } else if (args[1].equals("portal3")) {
                        itemMeta.setCustomModelData(8);
                    } else if (args[1].equals("portal4")) {
                        itemMeta.setCustomModelData(9);
                    } else if (args[1].equals("portal5")) {
                        itemMeta.setCustomModelData(10);
                    }
                    itemMeta.setUnbreakable(true);
                    itemStack.setItemMeta(itemMeta);
                    EntityEquipment equipment = armorStand.getEquipment();
                    equipment.setHelmet(itemStack);
                    armorStand.setVisible(false);
                    armorStand.setInvulnerable(true);
                    armorStand.setGravity(false);
                }
            } else if (args[0].equals("premium")) {
                if (args.length == 2) {
                    int itemID = Integer.parseInt(args[1]);

                    RequestHandler.test(itemID, player);
                }
            } else if (args[0].equals("test")) {
                /*double degrees = Double.parseDouble(args[1]);
                double radian = degrees * Math.PI / 180;
                player.sendMessage("radian: " + radian);*/

                Location eyeLocation = player.getEyeLocation();
                float yaw = eyeLocation.getYaw();
                float pitch = eyeLocation.getPitch();

                //ParticleShapes.drawCube(eyeLocation, Particle.SOUL_FIRE_FLAME, null, length, 0.1, true, yaw, pitch);
                double radius = Double.parseDouble(args[1]);
                double angle = Double.parseDouble(args[2]);
                /*
                int amounty = (int) Double.parseDouble(args[3]);
                double phi = Double.parseDouble(args[4]);*/
                //double lengthy = Double.parseDouble(args[3]);
                //double lengthz = Double.parseDouble(args[4]);
                /*yaw += Double.parseDouble(args[3]);
                pitch += Double.parseDouble(args[4]);*/

                // if offsety is set, pitch rotates in y circle

                //ParticleShapes.drawCone(eyeLocation, Particle.SOUL_FIRE_FLAME, height, amount, amounty, phi, null, true, yaw, pitch, new Vector());

                //Vector vector = new Vector(lengthx, lengthy, lengthz);
                ParticleShapes.drawTriangle(eyeLocation, Particle.FLAME, radius, 30, null, 0, true, yaw, pitch, new Vector(), angle);
                //ParticleShapes.drawCube(eyeLocation, Particle.FLAME, null, vector, gap, true, yaw, pitch);
                //ParticleShapes.drawCylinder(eyeLocation, Particle.SOUL_FIRE_FLAME, radius, amount, null, 0, true, yaw, pitchh, add);

                /*Vector dir = eyeLocation.getDirection().normalize();
                Vector side = dir.clone().crossProduct(new Vector(0, 1, 0));
                Vector upyard = dir.clone().crossProduct(side);
                eyeLocation.add(dir.multiply(forward)).subtract(upyard.multiply(upward)).add(side.multiply(right));

                eyeLocation.add(offset);

                ParticleShapes.drawCube(eyeLocation, Particle.SOUL_FIRE_FLAME, null, length, 0.1, true, yaw, pitch);*/
                /*new BukkitRunnable() {

                    int yaww = 0;

                    @Override
                    public void run() {
                        //ParticleShapes.playSingleParticle(center, Particle.FLAME, null);
                        ParticleShapes.drawCube(eyeLocation, Particle.SOUL_FIRE_FLAME, null, length, 0.1, true, yaww, 0, center);
                        yaww += 3;
                        if (yaww >= 360) {
                            cancel();
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 1L);
                new BukkitRunnable() {

                    float pitchh = 0;

                    @Override
                    public void run() {
                        //ParticleShapes.playSingleParticle(center, Particle.FLAME, null);
                        ParticleShapes.drawCube(eyeLocation, Particle.FLAME, null, length, 0.1, true, 0, pitchh, center);
                        pitchh += 3;
                        if (pitchh >= 360) {
                            cancel();
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 1L);*/
            } else if (args[0].equals("reload")) {
                /*ClassConfigurations.loadConfigs();
                player.sendMessage("Reloaded class configs!");
                Set<Player> onlinePlayers = GuardianDataManager.getOnlinePlayers();
                for (Player onlinePlayer : onlinePlayers) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(onlinePlayer);
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                    if (activeCharacter == null) continue;

                    String rpgClassStr = activeCharacter.getRpgClassStr();
                    RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);

                    SkillBar skillBar = activeCharacter.getSkillBar();
                    skillBar.reloadSkillSet(rpgClass.getSkillSet());
                }
                player.sendMessage("Reloaded player skills!");*/
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
