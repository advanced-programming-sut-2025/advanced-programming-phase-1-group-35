package Controller.InGameMenu;

import Model.*;
import Model.NPCs.NPC;
import Model.NPCs.Quest;
import Model.Tools.BackPack;
import Model.Tools.Tool;
import Model.enums.Seasons;
import Model.enums.WeatherCondition;

public class NPCController {
    public Result meetNPC(String npcName) {
        Game game = App.getCurrentGame();
        NPC npc = null;
        for (NPC eachNPC : game.getNpcs()) {
            if (eachNPC.name.equals(npcName)) {
                npc = eachNPC;
            }
        }
        if (npc == null) {
            return new Result(false, "NPC not found");
        } else if (false) {
            // TODO : check that if you are near to that npc or not
            return new Result(false, "You are not close to the npc");
        }
        npc.friendshipPoint += 20;
        return new Result(true, generateDialogue(npc.friendshipLevel,
                game.getGameCalender().getGameDateTime().getHour(),
                game.getSeason(), game.getWeather().getWeatherCondition()));
    }

    private String generateDialogue(int friendshipLevel, int hour, Seasons season, WeatherCondition weatherCondition) {
        return getGreetingDialogue(friendshipLevel) + "\n" +
                getTimeDialogue(hour) + "\n" +
                getSeasonDialogue(season) + "\n" +
                getWeatherDialogue(weatherCondition);
    }

    private String getGreetingDialogue(int friendshipLevel) {
        return switch (friendshipLevel) {
            case 1 -> "Hey there!";
            case 2 -> "Hey buddy!";
            case 3 -> "Hey dude!";
            default -> "Hey";
        };
    }

    private String getTimeDialogue(int hour) {
        if (hour <= 11) {
            return "good morning!";
        } else if (hour <= 18) {
            return "good afternoon!";
        } else {
            return "good evening!";
        }
    }

    private String getSeasonDialogue(Seasons season) {
        return "It's a very lovely day in " + season.toString() + "!";
    }

    private String getWeatherDialogue(WeatherCondition weather) {
        String weatherCondition = null;
        if (weather != WeatherCondition.sunny) {
            weatherCondition = weather.toString() + "y";
        }
        return "Did you notice that it's " + weatherCondition + " today?";
    }

    public Result sendGift(String npcName, String itemName) {
        Game game = App.getCurrentGame();
        BackPack backPack = App.getCurrentGame().getPlayingUser().backPack;
        NPC npc = null;
        for (NPC eachNPC : game.getNpcs()) {
            if (eachNPC.name.equals(npcName)) {
                npc = eachNPC;
            }
        }
        if (npc == null) {
            return new Result(false, "NPC not found");
        } else if (backPack.findItem(itemName) == null) {
            return new Result(false, "You don't have a " + itemName + " in your backpack!");
        } else if (backPack.findItem(itemName) instanceof Tool) {
            return new Result(false, "You can't gift a tool!");
        } else if (false) {
            // TODO : if not near to npc
            return new Result(false, "You are not close to the npc");
        }
        ItemInterface item = backPack.findItem(itemName);
        backPack.items.put(item, backPack.items.get(item) - 1);
        if (backPack.items.get(item) <= 0) {
            backPack.items.remove(item);
        }
        npc.friendshipPoint += 50;
        return new Result(true, "you gifted a " + itemName + " to " + npcName);
    }

    public Result seeFriendshipWithNPCs() {
        StringBuilder output = new StringBuilder();
        for (NPC npc : App.getCurrentGame().getNpcs()) {
            output.append(npc.name).append("-->  friendship points: ").append(npc.friendshipPoint);
            output.append("  friendship level: ").append(npc.friendshipLevel).append("\n");
        }
        return new Result(true, output.toString());
    }

    public Result seeQuestList(String npcName) {
        Game game = App.getCurrentGame();
        NPC npc = null;
        for (NPC eachNPC : game.getNpcs()) {
            if (eachNPC.name.equals(npcName)) {
                npc = eachNPC;
            }
        }
        if (npc == null) {
            return new Result(false, "NPC not found");
        }
        StringBuilder output = new StringBuilder();
        for (Quest quest : npc.quests) {
            output.append("request: ").append(quest.getRequest()).append("   ");
            output.append("reward: ").append(quest.getReward()).append("\n");
        }
        return new Result(true, output.toString());
    }

    public Result finishQuest(String questIndex, String npcName) {
        Game game = App.getCurrentGame();
        NPC npc = null;
        for (NPC eachNPC : game.getNpcs()) {
            if (eachNPC.name.equals(npcName)) {
                npc = eachNPC;
            }
        }
        try {
            Integer.parseInt(questIndex);
        } catch (NumberFormatException e) {
            return new Result(false, "Invalid quest index");
        }
        if (npc == null) {
            return new Result(false, "NPC not found");
        } else if (false) {
            // TODO : if not close to npc
            return new Result(false, "You are not close to the npc");
        }
        return new Result(false, "you can't do this mission right now!");
    }

}
