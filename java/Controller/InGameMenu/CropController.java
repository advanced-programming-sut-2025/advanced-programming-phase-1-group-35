package Controller.InGameMenu;

import models.enums.Crops.Crop;
import java.util.List;
import java.util.stream.Collectors;

public class CropController {
        private List<Crop> cropList;

        public CropController(List<Crop> cropList) {
            this.cropList = cropList;
        }

        public Crop findCropByName(String name) {
            return cropList.stream()
                    .filter(c -> c.getName().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);
        }

        public String getCropInfo(String name) {
            Crop crop = findCropByName(name);
            if (crop == null) return "Crop not found.";

            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(crop.getName()).append("\n");
            sb.append("Source: ").append(crop.getSource()).append("\n");
            sb.append("Stages: ").append(java.util.Arrays.stream(crop.getStages().split("-")).collect(Collectors.joining("-"))).append("\n");
            sb.append("Total Harvest Time: ").append(crop.getTotalHarvestTime()).append("\n");
            sb.append("One Time: ").append(crop.isOneTime()).append("\n");
            sb.append("Regrowth Time: ").append(crop.isOneTime() ? "" : crop.getRegrowthTime()).append("\n");
            sb.append("Base Sell Price: ").append(crop.getBaseSellPrice()).append("\n");
            sb.append("Is Edible: ").append(crop.isEdible()).append("\n");
            sb.append("Base Energy: ").append(crop.getEnergy()).append("\n");
            sb.append("Season: ").append(String.join(", ", crop.getSeasons())).append("\n");
            sb.append("Can Become Giant: ").append(crop.canBecomeGiant()).append("\n");

            return sb.toString();
        }
    }


