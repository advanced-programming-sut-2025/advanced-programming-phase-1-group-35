package Model.enums;

import models.SkillLevel;

public enum Skill {
    farming(new SkillLevel(1),5),
    mining(new SkillLevel(1),10),
    foraging(new SkillLevel(1), 10),
    fishing(new SkillLevel(1), 5);

    SkillLevel skillLevel;
    int gainedXP;

    Skill(SkillLevel skillLevel, int gainedXP) {
        this.skillLevel = skillLevel;
        this.gainedXP = gainedXP;
    }

}
