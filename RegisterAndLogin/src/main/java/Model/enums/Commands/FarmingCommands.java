package Model.enums.Commands;

public enum FarmingCommands {
    CraftInfo("\\s*craftinfo\\s*-n\\s*(?<craft_name>\\S+)"),
    
    ;
    private String pattern;
    FarmingCommands(String pattern) {
        this.pattern = pattern;
    }
}
