package Model.NPCs;

import Model.ItemInterface;

public class Quest {
    private final String request;
    private final String reward;
    private boolean completed = false;

    public Quest(String request, String reward) {
        this.request = request;
        this.reward = reward;
        this.completed = false;
    }

    public String getRequest() {
        return request;
    }

    public String getReward() {
        return reward;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
