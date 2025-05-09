package Model.enums.Shops.Products;

import Model.enums.Seasons;

public enum BlackSmithProducts implements ShopProduct{
    COPPER_ORE("Mineral","Copper Ore", "A common ore that can be smelted into bars.", 75, Double.POSITIVE_INFINITY),
    IRON_ORE("Mineral","Iron Ore", "A fairly common ore that can be smelted into bars.", 150, Double.POSITIVE_INFINITY),
    COAL("Mineral","Coal", "A combustible rock that is useful for crafting and smelting.", 150, Double.POSITIVE_INFINITY),
    GOLD_ORE("Mineral","Gold Ore", "A precious ore that can be smelted into bars.", 400, Double.POSITIVE_INFINITY),
    COPPER_TOOL("ToolUpgrade", "Copper Tool", "Upgrade Tools", 2000, 1.0),
    STEEL_TOOL("ToolUpgrade","Steel Tool", "Upgrade Tools", 5000, 1.0),
    GOLD_TOOL("ToolUpgrade","Gold Tool", "Upgrade Tools", 10000, 1.0),
    IRIDIUM_TOOL("ToolUpgrade","Iridium Tool", "Upgrade Tools", 25000, 1.0),
    COPPER_TRASH_CAN("ToolUpgrade","Copper Trash Can", "Upgrade Tools", 1000, 1.0),
    STEEL_TRASH_CAN("ToolUpgrade","Steel Trash Can", "Upgrade Tools", 2500, 1.0),
    GOLD_TRASH_CAN("ToolUpgrade","Gold Trash Can", "Upgrade Tools", 5000, 1.0),
    IRIDIUM_TRASH_CAN("ToolUpgrade","Iridium Trash Can", "Upgrade Tools", 12500, 1.0);

    private String type;
    private String name , description;
    private int price;
    private Double dailyLimit;
    BlackSmithProducts(String type , String name, String description, int price, Double dailyLimit) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.type = type;
    }

    @Override
    public String getName() {
        return this.toString();
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getDailyLimit() {
        double dl = 0;
        if(dailyLimit == Double.POSITIVE_INFINITY) return 1000000;
        dl = dailyLimit;
        return (int) dl;
    }

    @Override
    public Seasons getSeason() {
        return null;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
