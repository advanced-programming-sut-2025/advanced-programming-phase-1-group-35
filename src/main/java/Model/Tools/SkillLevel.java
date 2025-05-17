package Model.Tools;

import Model.App;
import Model.User;
import Model.enums.CraftingRecipes;
import Model.enums.Skill;

public class SkillLevel {
    private int currentLevel;
    private int xpRate;
    private int XpNeeded ;
    private int currentXp;
    private Skill skill;

    public SkillLevel(int currentLevel , int xpRate, Skill skill) {
        this.currentLevel = currentLevel;
        this.xpRate = xpRate;
        this.XpNeeded = (currentLevel+1)*100 + 50;
        this.skill = skill;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
    public int getCurrentLevel() {
        return currentLevel;
    }
    public int getXpNeeded() {
        return XpNeeded;
    }
    public int getCurrentXp() {
        return currentXp;
    }

    public void gainXp() {
        currentXp += xpRate;
        levelUp();
    }

    public void levelUp() {
        if(currentXp < XpNeeded || currentLevel >= 4) return;
        currentXp -= XpNeeded;
        currentLevel++;
        XpNeeded = (currentLevel+1)*100 + 50;
            User player = App.getCurrentGame().getPlayingUser();
        switch (skill){
            case farming:
                switch (currentLevel){
                    case 1:
                        player.getCraftingRecipes().add(CraftingRecipes.Sprinkler);
                        player.getCraftingRecipes().add(CraftingRecipes.BeeHouse);
                        break;
                    case 2:
                        player.getCraftingRecipes().add(CraftingRecipes.QualitySprinkler);
                        player.getCraftingRecipes().add(CraftingRecipes.CheesePress);
                        player.getCraftingRecipes().add(CraftingRecipes.PreservesJar);
                        break;
                    case 3:
                        player.getCraftingRecipes().add(CraftingRecipes.IridiumSprinkler);
                        player.getCraftingRecipes().add(CraftingRecipes.Keg);
                        player.getCraftingRecipes().add(CraftingRecipes.Loom);
                        player.getCraftingRecipes().add(CraftingRecipes.OilMaker);
                        break;
                }
                break;
            case foraging:
                switch (currentLevel){
                    case 1:
                        player.getCraftingRecipes().add(CraftingRecipes.CharcoalKlin);
                        break;
                    case 4:
                        player.getCraftingRecipes().add(CraftingRecipes.MysticTreeSeed);
                        break;
                }
                    break;
            case mining:
                switch (currentLevel){
                    case 1:
                        player.getCraftingRecipes().add(CraftingRecipes.CherryBomb);
                        break;
                    case 2:
                        player.getCraftingRecipes().add(CraftingRecipes.Bomb);
                        break;
                    case 3:
                        player.getCraftingRecipes().add(CraftingRecipes.MegaBomb);
                        break;
                }
        }
    }
}
