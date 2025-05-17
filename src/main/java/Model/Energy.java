package Model;

public class Energy {
    private double energy = 200;
    private double energyCapacity = 200;
    private double CurrentTurnConsumedEnergy; // positive number
    private double CurrentTurnCapacity = 50;
    private boolean fainted = false;

    public void consumeEnergy(double energyAmount) {
        energy -= energyAmount;
        CurrentTurnConsumedEnergy += energyAmount;
    }

    public void endTurn(){
        CurrentTurnConsumedEnergy = 0;
    }

    public boolean TurnEnergyLeft(){
        if(CurrentTurnConsumedEnergy >= CurrentTurnCapacity){
            return false;
        }
        return true;
    }

    public void goToNextDay(){
        energy = energyCapacity ;
        if(fainted) energy *= 0.75;
        fainted = false;
    }

    public void updateEnergyAfterADayPassed() {

    }

    public void checkEnergyWithMaxAmount() {
        if (energy > energyCapacity) energy = energyCapacity;
    }

    public void faint() {
        fainted = true;
    }

    public double getEnergyAmount() {
        return energy;
}

    public void setEnergyAmount(double energyAmount) {
        this.energy = energyAmount;
}

    public double getEnergyCapacity() {
        return energyCapacity;
    }

    public void setEnergyCapacity(double energyCapacity) {
        this.energyCapacity = energyCapacity;
    }

    public double getCurrentTurnConsumedEnergy() {
        return CurrentTurnConsumedEnergy;
    }

    public void setCurrentTurnConsumedEnergy(int currentTurnConsumedEnergy) {
        CurrentTurnConsumedEnergy = currentTurnConsumedEnergy;
    }

    public boolean isFainted() {
        return fainted;
    }

    public void setFainted(boolean fainted) {
        this.fainted = fainted;
    }

    public double getCurrentTurnCapacity() {
        return CurrentTurnCapacity;
    }

    public void setCurrentTurnCapacity(double currentTurnCapacity) {
        CurrentTurnCapacity = currentTurnCapacity;
    }
}
