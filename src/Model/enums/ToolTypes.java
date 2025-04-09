package Model.enums;

public enum ToolTypes {
    HOE(Skill.farming,ToolMaterial.Basic,5),
    PICKAXE(Skill.mining,ToolMaterial.Basic,5),
    AXE(Skill.foraging,ToolMaterial.Basic,5),
    WATERING_CAN(Skill.farming,ToolMaterial.Basic,5),
    FISHING_ROD(Skill.fishing,null,8), /*it would be better to implement different types of rods in other ways*/
    SCYTHE(Skill.farming,null,2),
    MILK_PAIL(null,null,4),
    SHEARS(null,null,4),
    BACKPACK(null,null,0),   /*different types of backpack will be implemented in inventory class*/
    TRASH_CAN(null,null,0);

    final Skill skill;
    ToolMaterial material;
    int energyConsumed;
    ToolTypes(Skill skill, ToolMaterial material,int energyConsumed) {
        this.skill = skill;
        this.material = material;
        this.energyConsumed = energyConsumed;
    }
}
