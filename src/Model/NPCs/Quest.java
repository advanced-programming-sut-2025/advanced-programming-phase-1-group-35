package Model.NPCs;

import Model.Item;

public class Quest {
    private Item item;
    private Item reward;
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

    public Item getReward() {
        return reward;
    }

    public Item getItem() {
        return item;
    }
}
