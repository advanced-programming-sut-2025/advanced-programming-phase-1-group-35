    package Model.enums.Crops;


    //to do: change stages and seasons into Lists//


    import Model.enums.Seasons;

    import java.util.List;

    import static Model.enums.Seasons.*;

    public enum CropEnum{
        BLUE_JAZZ("Blue Jazz", SeedEnum.JAZZ, List.of(1, 2, 2, 2), 7, true, -1, 50, true, 45, List.of(Spring), false),
        CARROT("Carrot", SeedEnum.CARROT, List.of(1,1,1), 3, true, -1, 35, true, 75, List.of(Spring), false),
        CAULIFLOWER("Cauliflower", SeedEnum.CAULIFLOWER , List.of(1,2,4,4,1), 12, true, -1, 175, true, 75, List.of(Spring), true),
        COFFEE_BEAN("Coffee Bean", SeedEnum.COFFEE, List.of(1,2,2,3,2), 10, false, 2, 15, false, -1, List.of(Spring,Summer), false),
        GARLIC("Garlic", SeedEnum.GARLIC , List.of(1,1,1,1), 4, true, -1, 60, true, 20, List.of(Spring), false),
        GREEN_BEAN("Green Bean", SeedEnum.BEAN, List.of(1,1,1,3,4), 10, false, 3, 40, true, 25, List.of(Spring), false),
        KALE("Kale", SeedEnum.KALE , List.of(1,2,2,1), 6, true, -1, 110, true, 50, List.of(Spring), false),
        PARSNIP("Parsnip", SeedEnum.PARSNIP , List.of(1,1,1,1), 4, true, -1, 35, true, 25, List.of(Spring), false),
        POTATO("Potato", SeedEnum.POTATO , List.of(1,1,1,2,1), 6, true, -1, 80, true, 25, List.of(Spring), false),
        RHUBARB("Rhubarb", SeedEnum.RHUBARB , List.of(2,2,2,3,4), 13, true, -1, 220, false, -1, List.of(Spring), false),
        STRAWBERRY("Strawberry", SeedEnum.STRAWBERRY , List.of(1,1,2,2,2), 8, false, 4, 120, true, 50, List.of(Spring), false),
        TULIP("Tulip", SeedEnum.TULIP, List.of(1,1,2,2), 6, true, -1, 30, true, 45, List.of(Spring), false),
        UNMILLED_RICE("Unmilled Rice", SeedEnum.RICE, List.of(1,2,2,3), 8, true, -1, 30, true, 3, List.of(Spring), false),
        BLUEBERRY("Blueberry", SeedEnum.BLUEBERRY , List.of(1,3,3,4,2), 13, false, 4, 50, true, 25, List.of(Summer), false),
        CORN("Corn", SeedEnum.CORN , List.of(2,3,3,3,3), 14, false, 4, 50, true, 25, List.of(Summer , Fall), false),
        HOPS("Hops", SeedEnum.HOPS, List.of(1,1,2,3,4), 11, false, 1, 25, true, 45, List.of(Summer), false),
        HOT_PEPPER("Hot Pepper", SeedEnum.PEPPER, List.of(1,1,1,1,1), 5, false, 3, 40, true, 13, List.of(Summer), false),
        MELON("Melon", SeedEnum.MELON , List.of(1,2,3,3,3), 12, true, -1, 250, true, 113, List.of(Summer), true),
        POPPY("Poppy", SeedEnum.POPPY , List.of(1,2,2,2), 7, true, -1, 140, true, 45, List.of(Summer), false),
        RADISH("Radish", SeedEnum.RADISH , List.of(2,1,2,1), 6, true, -1, 90, true, 45, List.of(Summer), false),
        RED_CABBAGE("Red Cabbage", SeedEnum.RED_CABBAGE , List.of(2,1,2,2,2), 9, true, -1, 260, true, 75, List.of(Summer), false),
        STARFRUIT("Starfruit", SeedEnum.STARFRUIT , List.of(2,3,2,3,3), 13, true, -1, 750, true, 125, List.of(Summer), false),
        SUMMER_SPANGLE("Summer Spangle", SeedEnum.SPANGLE , List.of(1,2,3,1), 8, true, -1, 90, true, 45, List.of(Summer), false),
        SUMMER_SQUASH("Summer Squash", SeedEnum.SUMMER_SQUASH , List.of(1,1,1,2,1), 6, false, 3, 45, true, 63, List.of(Summer), false),
        SUNFLOWER("Sunflower", SeedEnum.SUNFLOWER , List.of(1,2,3,2), 8, true, -1, 80, true, 45, List.of(Summer , Fall), false),
        TOMATO("Tomato", SeedEnum.TOMATO , List.of(2,2,2,2,3), 11, false, 4, 60, true, 20, List.of(Summer), false),
        WHEAT("Wheat", SeedEnum.WHEAT , List.of(1,1,1,1), 4, true, -1, 25, false, -1, List.of(Summer , Fall), false),
        AMARANTH("Amaranth", SeedEnum.AMARANTH , List.of(1,2,2,2), 7, true, -1, 150, true, 50, List.of(Fall), false),
        ARTICHOKE("Artichoke", SeedEnum.ARTICHOKE , List.of(2,2,1,2,1), 8, true, -1, 160, true, 30, List.of(Fall), false),
        BEET("Beet", SeedEnum.BEET , List.of(1,1,2,2), 6, true, -1, 100, true, 30, List.of(Fall), false),
        BOK_CHOY("Bok Choy", SeedEnum.BOK_CHO , List.of(1,1,1,1), 4, true, -1, 80, true, 25, List.of(Fall), false),
        BROCCOLI("Broccoli", SeedEnum.BROCCOLI , List.of(2,2,2,2), 8, false, 4, 70, true, 63, List.of(Fall), false),
        CRANBERRIES("Cranberries", SeedEnum.CRANBERRY , List.of(1,2,1,1,2), 7, false, 5, 75, true, 38, List.of(Fall), false),
        EGGPLANT("Eggplant", SeedEnum.EGGPLANT , List.of(1,1,1,1), 5, false, 5, 60, true, 20, List.of(Fall), false),
        FAIRY_ROSE("Fairy Rose", SeedEnum.FAIRY , List.of(1,4,4,3), 12, true, -1, 290, true, 45, List.of(Fall), false),
        GRAPE("Grape", SeedEnum.GRAPE, List.of(1,1,2,3,3), 10, false, 3, 80, true, 38, List.of(Fall), false),
        PUMPKIN("Pumpkin", SeedEnum.PUMPKIN , List.of(1,2,3,4,3), 13, true, -1, 320, false, -1, List.of(Fall), true),
        YAM("Yam", SeedEnum.YAM, List.of(1,3,3,3), 10, true, -1, 160, true, 45, List.of(Fall), false),
        SWEET_GEM_BERRY("Sweet Gem Berry", SeedEnum.SWEET_GEM_BERRY, List.of(2,4,6,6,6), 24, true, -1, 3000, false, -1, List.of(Fall), false),
        POWDERMELON("Powdermelon", SeedEnum.POWDERMELON , List.of(1,2,1,2,1), 7, true, -1, 60, true, 63, List.of(Winter), true),
        ANCIENT_FRUIT("Ancient Fruit", SeedEnum.ANCIENT , List.of(2,7,7,7,5), 28, false, 7, 550, false, -1, List.of(Spring , Summer , Fall ), false);
        private final String name;
        private final SeedEnum source;
        private final List<Integer> stages;
        private final int totalHarvestTime;
        private final boolean oneTime;
        private final int regrowthTime;
        private final int baseSellPrice;
        private final boolean isEdible;
        private final int energy;
        private final List<Seasons> season;
        private final boolean canBecomeGiant;
        private boolean isForaging;
        private int currentState;
        private int daysSinceLastGrowth;
        CropEnum(String name , SeedEnum source, List<Integer> stages, int totalHarvestTime, boolean oneTime, int regrowthTime,
                 int baseSellPrice, boolean isEdible, int energy, List<Seasons> season, boolean canBecomeGiant) {
            this.name = name;
            this.source = source;
            this.stages = stages;
            this.totalHarvestTime = totalHarvestTime;
            this.oneTime = oneTime;
            this.regrowthTime = regrowthTime;
            this.baseSellPrice = baseSellPrice;
            this.isEdible = isEdible;
            this.energy = energy;
            this.season = season;
            this.canBecomeGiant = canBecomeGiant;
            this.currentState = 1;
            this.daysSinceLastGrowth = 0;
        }

        public String getName() { return name; }
        public SeedEnum getSource() { return source; }
        public List<Integer> getStages() { return stages; }
        public int getTotalHarvestTime() { return totalHarvestTime; }
        public boolean isOneTime() { return oneTime; }
        public int getRegrowthTime() { return regrowthTime; }
        public int getBaseSellPrice() { return baseSellPrice; }
        public boolean isEdible() { return isEdible; }
        public int getEnergy() { return energy; }
        public List<Seasons> getSeasons() { return season; }
        public boolean canBecomeGiant() { return canBecomeGiant; }
      


    }
