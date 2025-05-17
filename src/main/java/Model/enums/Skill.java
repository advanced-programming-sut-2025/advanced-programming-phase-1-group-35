package Model.enums;

import Model.Tools.SkillLevel;

;

public enum Skill {
    farming(5),
    mining(10),
    foraging(10),
    fishing(5);

    int gainedXp;

    Skill(int gainedXp) {
        this.gainedXp = gainedXp;
    }

    public SkillLevel getSkillLevel() {
        return new SkillLevel(1, this.gainedXp);
    }
}
