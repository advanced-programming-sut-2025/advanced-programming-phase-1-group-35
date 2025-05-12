package Model.NPCs;

import Model.ItemInterface;

public class Quest {
    private ItemInterface itemInterface;
    private ItemInterface reward;
    private int rewardMoney;
    private boolean completed = false;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getRewardMoney() {
        return rewardMoney;
    }

    public ItemInterface getReward() {
        return reward;
    }

    public ItemInterface getItem() {
        return itemInterface;
    }
}
