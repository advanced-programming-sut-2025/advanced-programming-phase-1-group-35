package Controller.InGameMenu;

import Model.enums.Crops.CropEnum;

import java.util.List;
import java.util.stream.Collectors;

public class CropController {
        private List<CropEnum> cropEnumList;
        public CropController(List<CropEnum> cropEnumList) {
            this.cropEnumList = cropEnumList;
        }

        public CropEnum findCropByName(String name) {
            return cropEnumList.stream()
                    .filter(c -> c.getName().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);
        }

        public String getCropInfo(String name) {
            CropEnum cropEnum = findCropByName(name);
            if (cropEnum == null) return "Crop not found.";

            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(cropEnum.getName()).append("\n");
            sb.append("Source: ").append(cropEnum.getSource()).append("\n");
            sb.append("Stages: ").append(cropEnum.getStages().stream().map(String::valueOf)
                            .collect(Collectors.joining("-")))
                    .append("\n");
            sb.append("Total Harvest Time: ").append(cropEnum.getTotalHarvestTime()).append("\n");
            sb.append("One Time: ").append(cropEnum.isOneTime()).append("\n");
            sb.append("Regrowth Time: ").append(cropEnum.isOneTime() ? "" : cropEnum.getRegrowthTime()).append("\n");
            sb.append("Base Sell Price: ").append(cropEnum.getBaseSellPrice()).append("\n");
            sb.append("Is Edible: ").append(cropEnum.isEdible()).append("\n");
            sb.append("Base Energy: ").append(cropEnum.getEnergy()).append("\n");
            sb.append("Season: ")
                    .append(String.join(", ", cropEnum.getSeasons().stream().map(Enum::name).toList()))
                    .append("\n");
            sb.append("Can Become Giant: ").append(cropEnum.canBecomeGiant()).append("\n");

            return sb.toString();
        }
    }


