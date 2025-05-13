    package Model.enums.Crops;


    //to do: change stages and seasons into Lists//


    import Model.CropClasses.Crop;
    import Model.ItemInterface;
    import Model.enums.ItemConstant;
    import Model.enums.Seasons;

    import java.util.List;
    import java.util.Random;
    import java.util.stream.Collectors;

    import static Model.enums.Seasons.*;

    public enum CropEnum implements ItemConstant {
        BLUE_JAZZ("Blue Jazz", SeedEnum.JAZZ, List.of(1, 2, 2, 2), 7, true, -1, 50, true, 45, List.of(Spring), false, false),
        CARROT("Carrot", SeedEnum.CARROT, List.of(1,1,1), 3, true, -1, 35, true, 75, List.of(Spring), false, false),
        CAULIFLOWER("Cauliflower", SeedEnum.CAULIFLOWER , List.of(1,2,4,4,1), 12, true, -1, 175, true, 75, List.of(Spring), true, false),
        COFFEE_BEAN("Coffee Bean", SeedEnum.COFFEE, List.of(1,2,2,3,2), 10, false, 2, 15, false, -1, List.of(Spring,Summer), false, false),
        GARLIC("Garlic", SeedEnum.GARLIC , List.of(1,1,1,1), 4, true, -1, 60, true, 20, List.of(Spring), false, false),
        GREEN_BEAN("Green Bean", SeedEnum.BEAN, List.of(1,1,1,3,4), 10, false, 3, 40, true, 25, List.of(Spring), false, false),
        KALE("Kale", SeedEnum.KALE , List.of(1,2,2,1), 6, true, -1, 110, true, 50, List.of(Spring), false, false),
        PARSNIP("Parsnip", SeedEnum.PARSNIP , List.of(1,1,1,1), 4, true, -1, 35, true, 25, List.of(Spring), false, false),
        POTATO("Potato", SeedEnum.POTATO , List.of(1,1,1,2,1), 6, true, -1, 80, true, 25, List.of(Spring), false, false),
        RHUBARB("Rhubarb", SeedEnum.RHUBARB , List.of(2,2,2,3,4), 13, true, -1, 220, false, -1, List.of(Spring), false, false),
        STRAWBERRY("Strawberry", SeedEnum.STRAWBERRY , List.of(1,1,2,2,2), 8, false, 4, 120, true, 50, List.of(Spring), false, false),
        TULIP("Tulip", SeedEnum.TULIP, List.of(1,1,2,2), 6, true, -1, 30, true, 45, List.of(Spring), false, false),
        UNMILLED_RICE("Unmilled Rice", SeedEnum.RICE, List.of(1,2,2,3), 8, true, -1, 30, true, 3, List.of(Spring), false, false),
        BLUEBERRY("Blueberry", SeedEnum.BLUEBERRY , List.of(1,3,3,4,2), 13, false, 4, 50, true, 25, List.of(Summer), false, false),
        CORN("Corn", SeedEnum.CORN , List.of(2,3,3,3,3), 14, false, 4, 50, true, 25, List.of(Summer , Fall), false, false),
        HOPS("Hops", SeedEnum.HOPS, List.of(1,1,2,3,4), 11, false, 1, 25, true, 45, List.of(Summer), false, false),
        HOT_PEPPER("Hot Pepper", SeedEnum.PEPPER, List.of(1,1,1,1,1), 5, false, 3, 40, true, 13, List.of(Summer), false, false),
        MELON("Melon", SeedEnum.MELON , List.of(1,2,3,3,3), 12, true, -1, 250, true, 113, List.of(Summer), true, false),
        POPPY("Poppy", SeedEnum.POPPY , List.of(1,2,2,2), 7, true, -1, 140, true, 45, List.of(Summer), false, false),
        RADISH("Radish", SeedEnum.RADISH , List.of(2,1,2,1), 6, true, -1, 90, true, 45, List.of(Summer), false, false),
        RED_CABBAGE("Red Cabbage", SeedEnum.RED_CABBAGE , List.of(2,1,2,2,2), 9, true, -1, 260, true, 75, List.of(Summer), false, false),
        STARFRUIT("Starfruit", SeedEnum.STARFRUIT , List.of(2,3,2,3,3), 13, true, -1, 750, true, 125, List.of(Summer), false, false),
        SUMMER_SPANGLE("Summer Spangle", SeedEnum.SPANGLE , List.of(1,2,3,1), 8, true, -1, 90, true, 45, List.of(Summer), false, false),
        SUMMER_SQUASH("Summer Squash", SeedEnum.SUMMER_SQUASH , List.of(1,1,1,2,1), 6, false, 3, 45, true, 63, List.of(Summer), false, false),
        SUNFLOWER("Sunflower", SeedEnum.SUNFLOWER , List.of(1,2,3,2), 8, true, -1, 80, true, 45, List.of(Summer , Fall), false, false),
        TOMATO("Tomato", SeedEnum.TOMATO , List.of(2,2,2,2,3), 11, false, 4, 60, true, 20, List.of(Summer), false, false),
        WHEAT("Wheat", SeedEnum.WHEAT , List.of(1,1,1,1), 4, true, -1, 25, false, -1, List.of(Summer , Fall), false, false),
        AMARANTH("Amaranth", SeedEnum.AMARANTH , List.of(1,2,2,2), 7, true, -1, 150, true, 50, List.of(Fall), false, false),
        ARTICHOKE("Artichoke", SeedEnum.ARTICHOKE , List.of(2,2,1,2,1), 8, true, -1, 160, true, 30, List.of(Fall), false, false),
        BEET("Beet", SeedEnum.BEET , List.of(1,1,2,2), 6, true, -1, 100, true, 30, List.of(Fall), false, false),
        BOK_CHOY("Bok Choy", SeedEnum.BOK_CHO , List.of(1,1,1,1), 4, true, -1, 80, true, 25, List.of(Fall), false, false),
        BROCCOLI("Broccoli", SeedEnum.BROCCOLI , List.of(2,2,2,2), 8, false, 4, 70, true, 63, List.of(Fall), false, false),
        CRANBERRIES("Cranberries", SeedEnum.CRANBERRY , List.of(1,2,1,1,2), 7, false, 5, 75, true, 38, List.of(Fall), false, false),
        EGGPLANT("Eggplant", SeedEnum.EGGPLANT , List.of(1,1,1,1), 5, false, 5, 60, true, 20, List.of(Fall), false, false),
        FAIRY_ROSE("Fairy Rose", SeedEnum.FAIRY , List.of(1,4,4,3), 12, true, -1, 290, true, 45, List.of(Fall), false, false),
        GRAPE("Grape", SeedEnum.GRAPE, List.of(1,1,2,3,3), 10, false, 3, 80, true, 38, List.of(Fall), false, true),
        PUMPKIN("Pumpkin", SeedEnum.PUMPKIN , List.of(1,2,3,4,3), 13, true, -1, 320, false, -1, List.of(Fall), true, false),
        YAM("Yam", SeedEnum.YAM, List.of(1,3,3,3), 10, true, -1, 160, true, 45, List.of(Fall), false, false),
        SWEET_GEM_BERRY("Sweet Gem Berry", SeedEnum.SWEET_GEM_BERRY, List.of(2,4,6,6,6), 24, true, -1, 3000, false, -1, List.of(Fall), false, false),
        POWDERMELON("Powdermelon", SeedEnum.POWDERMELON , List.of(1,2,1,2,1), 7, true, -1, 60, true, 63, List.of(Winter), true, false),
        ANCIENT_FRUIT("Ancient Fruit", SeedEnum.ANCIENT , List.of(2,7,7,7,5), 28, false, 7, 550, false, -1, List.of(Spring , Summer , Fall ), false, false),

        COMMON_MUSHROOM("Common Mushroom", null, null, 0, true, 0, 40, true, 38, List.of(Seasons.Winter, Spring, Summer, Fall), false, true),
        DAFFODIL("Daffodil", null, null, 0, true, 0, 30, true, 0, List.of(Spring), false, true),
        DANDELION("Dandelion", null, null, 0, true, 0, 40, true, 25, List.of(Spring), false, true),
        LEEK("Leek", null, null, 0, true, 0, 60, true, 40, List.of(Spring), false, true),
        MOREL("Morel", null, null, 0, true, 0, 150, true, 20, List.of(Spring), false, true),
        SALMONBERRY("Salmonberry", null, null, 0, true, 0, 5, true, 25, List.of(Spring), false, true),
        SPRING_ONION("Spring Onion", null, null, 0, true, 0, 8, true, 13, List.of(Spring), false, true),
        WILD_HORSERADISH("Wild Horseradish", null, null, 0, true, 0, 50, true, 13, List.of(Spring), false, true),
        FIDDLEHEAD_FERN("Fiddlehead Fern", null, null, 0, true, 0, 90, true, 25, List.of(Summer), false, true),
        RED_MUSHROOM("Red Mushroom", null, null, 0, true, 0, 75, true, -50, List.of(Summer), false, true),
        SPICE_BERRY("Spice Berry", null, null, 0, true, 0, 80, true, 25, List.of(Summer), false, true),
        SWEET_PEA("Sweet Pea", null, null, 0, true, 0, 50, true, 0, List.of(Summer), false, true),
        BLACKBERRY("Blackberry", null, null, 0, true, 0, 25, true, 25, List.of(Fall), false, true),
        CHANTERELLE("Chanterelle", null, null, 0, true, 0, 160, true, 75, List.of(Fall), false, true),
        HAZELNUT("Hazelnut", null, null, 0, true, 0, 40, true, 38, List.of(Fall), false, true),
        PURPLE_MUSHROOM("Purple Mushroom", null, null, 0, true, 0, 90, true, 30, List.of(Fall), false, true),
        WILD_PLUM("Wild Plum", null, null, 0, true, 0, 80, true, 25, List.of(Fall), false, true),
        CROCUS("Crocus", null, null, 0, true, 0, 60, true, 0, List.of(Winter), false, true),
        CRYSTAL_FRUIT("Crystal Fruit", null, null, 0, true, 0, 150, true, 63, List.of(Winter), false, true),
        HOLLY("Holly", null, null, 0, true, 0, 80, true, -37, List.of(Winter), false, true),
        SNOW_YAM("Snow Yam", null, null, 0, true, 0, 100, true, 30, List.of(Winter), false, true),
        WINTER_ROOT("Winter Root", null, null, 0, true, 0, 70, true, 25, List.of(Winter), false, true)

        ;
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
                 int baseSellPrice, boolean isEdible, int energy, List<Seasons> season, boolean canBecomeGiant, boolean isForaging) {
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
            this.isForaging = isForaging;
        }

        public String getName() { return name; }
        //TODO: what does the foraging crops drop as seeds?
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
        public CropEnum getCrop() {return this;}
        public boolean isForaging() {return isForaging;}
        public List<Seasons> getSeason() {return season;}
        public boolean isCanBecomeGiant() {return canBecomeGiant;}
        public int getCurrentState() {return currentState;}
        public int getDaysSinceLastGrowth() {return daysSinceLastGrowth;}

        public static CropEnum getRandomForagingCrop() {
            List<CropEnum> foragingCrops = List.of(CropEnum.values()).stream()
                    .filter(CropEnum::isForaging)
                    .collect(Collectors.toList());

            if (foragingCrops.isEmpty()) {
                return null; // or throw an exception if you prefer
            }

            Random random = new Random();
            return foragingCrops.get(random.nextInt(foragingCrops.size()));
        }

        @Override
        public ItemInterface getItem() {
            return new Crop(this);
        }
    }
