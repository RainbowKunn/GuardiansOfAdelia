package io.github.lix3nn53.guardiansofadelia.quests.list.mainstory;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.actions.GiveItemAction;
import io.github.lix3nn53.guardiansofadelia.quests.actions.GiveWeaponAction;
import io.github.lix3nn53.guardiansofadelia.quests.actions.SendTitleAction;
import io.github.lix3nn53.guardiansofadelia.quests.task.Task;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskInteract;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskKill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class QuestsPhaseOne {

    public static void createQuests() {
        createQuestOne();
        createQuestTwo();
        createQuestThree();
        createQuestFour();
        createQuestFive();
        createQuestSix();
    }

    private static void createQuestOne() {
        List<String> story = new ArrayList<>();
        story.add("You need to master power of elements and become");
        story.add("a Guardian of Adelia to fight against darkness.");
        story.add("But this will be a long journey, let's start");
        story.add("with meeting villagers in Roumen.");
        List<Task> tasks = new ArrayList<>();

        Task task = new TaskInteract(8);
        ItemStack boat = OtherItems.getBoat();
        GiveItemAction giveItemAction = new GiveItemAction(boat);
        SendTitleAction sendTitleAction = new SendTitleAction("", ChatColor.GREEN + "Obtained Boat");
        task.addOnCompleteAction(giveItemAction);
        task.addOnCompleteAction(sendTitleAction);
        tasks.add(task);

        Task task1 = new TaskInteract(13);
        GiveWeaponAction giveWeaponAction = new GiveWeaponAction(1, ItemTier.RARE, "Newbie", 1, 5, 2);
        SendTitleAction sendTitleAction1 = new SendTitleAction("", ChatColor.GREEN + "Obtained Weapon");
        task1.addOnCompleteAction(giveWeaponAction);
        task1.addOnCompleteAction(sendTitleAction1);
        tasks.add(task1);

        Task task2 = new TaskInteract(22);
        ItemStack hpPotion = Consumable.POTION_INSTANT_HEALTH.getItemStack(1, 3);
        ItemStack manaPotion = Consumable.POTION_INSTANT_MANA.getItemStack(1, 3);
        GiveItemAction giveItemAction1 = new GiveItemAction(hpPotion);
        GiveItemAction giveItemAction2 = new GiveItemAction(manaPotion);
        task2.addOnCompleteAction(giveItemAction1);
        task2.addOnCompleteAction(giveItemAction2);
        SendTitleAction sendTitleAction2 = new SendTitleAction("", ChatColor.GREEN + "Obtained Potions");
        task2.addOnCompleteAction(sendTitleAction2);
        tasks.add(task2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String obj = "Talk to Item Merchant TASK_PROGRESS_1/1\n" + "Talk to Blacksmith TASK_PROGRESS_2/1\n" +
                "Talk to Magical Item Merchant TASK_PROGRESS_3/1\n"
                + "Then meet Sergeant Armin in building near city entrance\n";
        Quest quest = new Quest(6, "Another newbie?", story,
                "Time to meet villagers of Roumen!", obj,
                "", tasks, itemPrizes, 10, 10, 0, 5,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 31, 32);
    }

    private static void createQuestTwo() {
        List<String> story = new ArrayList<>();
        story.add("Forest of Slumber is most important resource");
        story.add("for Hobbit Village.");
        story.add("Hobbits are having difficulties because of");
        story.add("monsters in Forest of Slumber.");
        story.add("This is your first task as a Guardian!");
        story.add("Can you save the Hobbit Village?");
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.\nDon't forget to use your boat!";
        Quest quest = new Quest(7, "Lets meet the hobbits", story,
                startMsg, startMsg + "\nTalk with hobbit Village Elder Odo",
                "", tasks, itemPrizes, 10, 10, 0, 6,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 32, 33);
    }

    private static void createQuestThree() {
        List<String> story = new ArrayList<>();
        story.add("Hobbit Adventurer Milo is fighting alone in");
        story.add("Forest of Slumber. Help him to restore the");
        story.add("peace in the forest.");

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.GREEN + "Wild Lizard", 7);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.DARK_GREEN + "Poisonous Lizard", 5);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Lets enter Forest of Slumber!\nKill TASK_PROGRESS_1/7 Wild Lizard and TASK_PROGRESS_2/5 Poisonous Lizard\nThen talk to Adventurer Milo";
        Quest quest = new Quest(8, "First combat", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 10, 0, 7,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 33, 34);
    }

    private static void createQuestFour() {
        List<String> story = new ArrayList<>();
        story.add("Sticky monsters appeared in the woods,");
        story.add("lets deal with them and learn where they");
        story.add("came from.");

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.GREEN + "Baby Slime", 14);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.GREEN + "Sticky Slime", 9);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Kill TASK_PROGRESS_1/14 Baby Slime and TASK_PROGRESS_2/9 Sticky Slime\nThen talk to Adventurer Milo";
        Quest quest = new Quest(9, "Sticky situation", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 10, 0, 8,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 34, 34);
    }

    private static void createQuestFive() {
        List<String> story = new ArrayList<>();
        story.add("We have found the magical portal that");
        story.add("slimes comes from. Lets go in and stop them!");

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.GOLD + "King Slime", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "<Dungeon>\nKill King Slime TASK_PROGRESS_1/1\nThen talk to Adventurer Milo";
        Quest quest = new Quest(10, "Arcade dungeon", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 10, 0, 9,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 34, 34);
    }

    private static void createQuestSix() {
        List<String> story = new ArrayList<>();
        story.add("Lets bring the good news to hobbit elder then");
        story.add("report to Sergeant Armin.");

        List<Task> tasks = new ArrayList<>();
        Task task = new TaskInteract(33);
        tasks.add(task);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Bring good news about Forest of Slumber to Village Elder Odo TASK_PROGRESS_1/1\nThen report back to Sergeant Armin";
        Quest quest = new Quest(11, "Good news", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 10, 0, 10,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 34, 32);
    }
}
