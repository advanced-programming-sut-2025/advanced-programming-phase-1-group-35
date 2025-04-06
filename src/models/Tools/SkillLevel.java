package models;

public class SkillLevel {
    private int currentLevel;
    private int XpNeeded ;
    private int currentXp;

    public SkillLevel(int currentLevel){
        this.currentLevel = currentLevel;
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

    public void gainXp(int xp) {}
}
