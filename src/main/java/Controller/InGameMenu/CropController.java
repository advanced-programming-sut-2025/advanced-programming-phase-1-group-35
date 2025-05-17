package Controller.InGameMenu;

import Model.enums.Crops.CropEnum;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CropController {

    public CropEnum findCropByName(String name) {
        return Arrays.stream(CropEnum.values())
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }


        public String getCropInfo(String name) {
            CropEnum cropEnum = findCropByName(name);
            if (cropEnum == null) return "Crop not found,try something valid!";

            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(cropEnum.getName()).append("\n");
            if(cropEnum.getSource() == null) sb.append("Source: null\n");
            else sb.append("Source: ").append(cropEnum.getSource().getName()).append("\n");
            sb.append("Stages: ").append(cropEnum.getStages().stream().map(String::valueOf)
                            .collect(Collectors.joining("-")))
                    .append("\n");
            sb.append("Total Harvest Time: ").append(cropEnum.getTotalHarvestTime()).append("\n");
            sb.append("One Time: ").append(cropEnum.isOneTime()).append("\n");
            if (!cropEnum.isOneTime()) {
                sb.append("Regrowth Time: ").append(cropEnum.getRegrowthTime()).append("\n");
            }
            sb.append("Base Sell Price: ").append(cropEnum.getBaseSellPrice()).append("\n");
            sb.append("Is Edible: ").append(cropEnum.isEdible()).append("\n");
            sb.append("Base Energy: ").append(cropEnum.getEnergy()).append("\n");
            sb.append("Season: ")
                    .append(String.join(", ", cropEnum.getSeasons().stream().map(Enum::name).toList()))
                    .append("\n");
            sb.append("Can Become Giant: ").append(cropEnum.canBecomeGiant()).append("\n");

            return sb.toString();
        }
//        public boolean CheckSideTilesForMixedSeeds(Tile tile) {
//        boolean result = false;
//        if()
//        }


    }


