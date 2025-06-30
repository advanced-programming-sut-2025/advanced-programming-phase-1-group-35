package Model.Tools;

public class SkillLevel {
    private int currentLevel;
    private int xpRate;
    private int XpNeeded ;
    private int currentXp;

    public SkillLevel(int currentLevel , int xpRate) {
        this.currentLevel = currentLevel;
        this.xpRate = xpRate;
        this.XpNeeded = (currentLevel+1)*100 + 50;
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
    }
}
