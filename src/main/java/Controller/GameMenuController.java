package Controller;

import Controller.InGameMenu.CropController;
import Controller.InGameMenu.FarmingController;
import Controller.InGameMenu.ShopMenuController;
import Model.*;
import Model.CropClasses.Crop;
import Model.CropClasses.Seed;
import Model.CropClasses.Tree;
import Model.Tools.BackPack;
import Model.Tools.FishingPole;
import Model.TradeAndGift.Gift;
import Model.enums.Colors;
import Model.enums.GameMenuCommands;
import Model.enums.Menu;
import Model.enums.Shops.Products.GeneralStoreProducts;
import Model.enums.TileType;
import Model.enums.TileType;
import Model.enums.machines.ArtisanProductDetails;
import Model.machines.ArtisanProduct;
import Model.enums.*;
import Model.enums.Crops.*;
import Model.enums.Shops.Products.*;
import Model.enums.animal.AnimalProductDetails;
import Model.enums.animal.FishType;
import Model.enums.machines.ArtisanProductDetails;
import View.GameMenu;
import View.InGameMenu.ShopMenu;

import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.util.*;
import java.util.Map;
import java.util.regex.Matcher;

import static Model.enums.Colors.RESET;

public class GameMenuController {
    CropController cropController = new CropController() ;
    FarmingController farmingController;
    Game CurrentGame = null ;

    public String showCropInfo(String cropName) {
        return cropController.getCropInfo(cropName);
    }

    public Result plantSeed(String seedName, String direction){
        return farmingController.plantSeed(seedName, direction);
    }
    public Result pickUpSeed(String direction){
        Tile tile = findTile(direction);
        Optional<SeedEnum> matchingSeed = Arrays.stream(SeedEnum.values())
                .filter(seed -> tile.getContents().contains(seed))
                .findFirst();

        boolean hasAnySeed = Arrays.stream(SeedEnum.values())
                .anyMatch(seed -> tile.getContents().contains(seed));

        if(!hasAnySeed){
            return new Result(false, "tile doesn't have any seed");
        }
        if(!App.getCurrentGame().getPlayingUser().getBackPack().doesBackPackHasSpace()){
            return new Result(false, "your backpack is full");
        }
        App.getCurrentGame().getPlayingUser().getBackPack().items.put(matchingSeed.get(),1);
        return new Result(true, "picked up "+ matchingSeed.get().getName());
    }

    public Result fertilize(String fertilizerName, String direction){
        if(!direction.toLowerCase().matches("up|down|left|right")){
            return new Result(false, "Invalid direction");
        }
        List<ItemInterface> fertilizers = new ArrayList<>();
        fertilizers.add(GeneralStoreProducts.SPEED_GRO);
        fertilizers.add(GeneralStoreProducts.DELUXE_RETAINING_SOIL);
        fertilizers.add(GeneralStoreProducts.BASIC_RETAINING_SOIL);
        fertilizers.add(GeneralStoreProducts.QUALITY_RETAINING_SOIL);
        Fertilizer fertilizer = null;
        for(ItemInterface item : fertilizers){
            if(item.getName().toLowerCase().equals(fertilizerName.toLowerCase())){
                fertilizer = (Fertilizer) item;
                break;
            }
        }
        if(fertilizer == null){
            return new Result(false, "Fertilizer not found");
        }
        if(!App.getCurrentGame().getPlayingUser().backPack.items.containsKey(fertilizer)){
            return new Result(false, "you don't have the fertilizer in your inventory");
        }

        if(findTile(direction).getPlanted()== null) {
            if (!findTile(direction).isPlowed()){
                return new Result(false, "the selected tile is not plowed");
            }
        }
        else{
            if(findTile(direction).getPlanted().getClass().equals(Crop.class)){
                Crop crop = (Crop) findTile(direction).getPlanted();
                if(crop.getDaysSincePlanted() != 0) return new Result(false, "you can't fertilize a plant that's older than 1 day");
                crop.setFertilized(true);
                findTile(direction).setFertilized(true);
                crop.setFertilizer(fertilizer);
                return new Result(true, "you fertilized " + crop.getName());
            }
            if(findTile(direction).getPlanted().getClass().equals(Tree.class)){
                Tree tree = (Tree) findTile(direction).getPlanted();
                if(tree.getDaysSincePlanted() != 0) return new Result(false, "you can't fertilize a plant that's older than 1 day");
                tree.setFertilized(true);
                findTile(direction).setFertilized(true);
                tree.setFertilizer(fertilizer);
                return new Result(true, "you fertilized " + tree.getName());
            }
        }
        findTile(direction).setFertilized(true);
        return new Result(true, "tile fertilized successfully");
    }
    public static Tile findTile(String direction){
        Tile tile = null;
        Tile[][] map = App.getCurrentGame().getMap().getTiles();
        switch (direction) {
            case "up":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getX()]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY() - 1];
                break;
            case "down":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY() + 1];
                break;
            case "left":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x - 1]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()];
                break;
            case "right":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x + 1]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()];
                break;
            case "here":
                tile = App.getCurrentGame().getPlayingUser().getCurrentTile();
                break;
            default:
                String[] parts = direction.split(" ");
                tile = map[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])];
        }
        return tile;
    }
    public void setFarmingController() {
        cropController = new CropController();
        farmingController = new FarmingController(App.getCurrentGame().getMap().getTiles());
    }
    public void exitMenu() throws IOException {
        if(!App.isStayLoggedIn()) {
            App.setLoggedInUser(null);
            App.setCurrentMenu(Menu.LoginMenu);
        }
        else App.setCurrentMenu(Menu.MainMenu);
        App.setCurrentGame(null);
        App.serializeApp();
        App.setCurrentMenu(Menu.ExitMenu);
    }
    public Result createNewGame(String username1, String username2, String username3) throws IOException {
        LoginMenuController loginMenuController = new LoginMenuController();
        ArrayList<String> playerNames = new ArrayList<>();
        ArrayList<User> players = new ArrayList<>();
        if(username1 != null) playerNames.add(username1);
        if(username2 != null) playerNames.add(username2);
        if(username3 != null) playerNames.add(username3);
        players.add(App.getLoggedInUser());
        if(playerNames.isEmpty()) return new Result(false, "You have to choose at least one player");
        for (String playerName : playerNames) {
            User user = loginMenuController.getUser(playerName);
            if(user == null) return new Result(false, playerName + "does not exist");
            if(user.equals(App.getLoggedInUser())) return new Result(false, "your bipolar shit doesn't make sense");
            if(isUserInOtherGame(user)) return new Result(false, playerName + "is already in a game");
            players.add(user);
        }
        Game game = new Game(players , App.getLoggedInUser());
        App.games.add(game);
        App.setCurrentGame(game);
        CurrentGame = game;
        setFarmingController();
        for (User player : players) {
            player.setCurrentGame(game);
        }
        chooseMap();
        return new Result(true, "You have created a new game . now redirecting to the game .");
    }

    private boolean isUserInOtherGame(User user) {
        if(user.getCurrentGame() == null) return false;
        return true;
    }

    public void chooseMap() throws IOException {
        Game game = App.getCurrentGame();
        ArrayList<User> players = game.getPlayers();
        User[] users = new User[4];
        int[] types = new int[4];
        for (int i = 0; i < players.size();) { // choosing maps
            GameMenu.print("choosing map for " + players.get(i).getUsername());
            String input = GameMenu.scan();
            Matcher matcher = GameMenuCommands.chooseMap.getMatcher(input);
            if(matcher == null) {
                GameMenu.print("invalid input");
                continue;
            }
            int number = Integer.parseInt(matcher.group("number"));
            int type = Integer.parseInt(matcher.group("type"));
            if(number < 1 || number > 4) {
                GameMenu.print("invalid number");
                continue;
            }
            if(type < 0 || type > 3) {
                GameMenu.print("invalid type");
                continue;
            }
            if(users[number - 1] != null) {
                GameMenu.print("this farm is taken");
                continue;
            }
            users[number - 1] = players.get(i);
            types[number - 1] = type;
            GameMenu.print("farm number " + number + " has been chosen by " + players.get(i).getUsername());
            i++;
        }
        game.getMap().buildMap(users , types);
    }
    public Result harvest(String direction) throws IOException {
        farmingController = new FarmingController(App.getCurrentGame().getMap().getTiles());
        return farmingController.harvestCrop(findTile(direction));
    }
    public Result ShowCrop(int x, int y) throws IOException {
        farmingController = new FarmingController(App.getCurrentGame().getMap().getTiles());
        return farmingController.ShowCrop(x, y);
    }

    public Result giveSeed(String seedName) throws IOException {

        for (SeedEnum seedEnum : SeedEnum.values()) {
            if (seedEnum.name().equalsIgnoreCase(seedName)) {
                    App.getCurrentGame().getPlayingUser().getBackPack().items.put(seedEnum, 1);
                return new Result(true, "Seed: " + seedEnum.getName() + " was given to player " + App.getCurrentGame().getPlayingUser().getUsername());
            }
        }
            return new Result(false,"nuh uh");
    }

    public Result pickItem(String itemName, String direction) throws IOException {
        ItemConstant item = getItemConstantByName(itemName);
        Tile tile = findTile(direction);
        if(item == null) {
            return new Result(false, "Item " + itemName + " does not exist");
        }
        if(!tile.getContents().contains(item)) {
            return new Result(false, "tile is empty");
        }
        if(!App.getCurrentGame().getPlayingUser().getBackPack().doesBackPackHasSpace()){
            return new Result(false, "your backpack is full");
        }
        App.getCurrentGame().getPlayingUser().getBackPack().items.put(item,1);
        tile.getContents().remove(item);
        return new Result(true, "Item " + itemName + " has been picked up");
    }

    public Result useScareCrow(String direction){
        Tile tile = findTile(direction);
        if(tile.getPlanted() != null || tile.getContents() != null){
            return new Result(false, "can't place it here");
        }
        if(!App.getCurrentGame().getPlayingUser().getBackPack().items.containsKey(CraftingItems.Scarecrow)){
            return new Result(false, "you don't have any scarecrows");
        }
        tile.getContents().add(CraftingItems.Scarecrow);
        App.getCurrentGame().getPlayingUser().getBackPack().items.put(CraftingItems.Scarecrow, App.getCurrentGame().getPlayingUser().backPack.items.get(CraftingItems.Scarecrow)-1);
        if(App.getCurrentGame().getPlayingUser().getBackPack().items.get(CraftingItems.Scarecrow) == 0){
            App.getCurrentGame().getPlayingUser().getBackPack().items.remove(CraftingItems.Scarecrow);
        }
        return new Result(true, "scare crow planted");
    }

    public Result loadGame() {
        Game game = App.getLoggedInUser().getCurrentGame();
        if(game == null) {
            return new Result(false, "You have no ongoing game");
        }
        App.setCurrentGame(game);
        return new Result(true, "You have successfully loaded game");
    }

    public Result exitGame() {
        Game game = App.getLoggedInUser().getCurrentGame();
        if(game == null) return new Result(false, "You have no ongoing game");
        if(!game.getPlayers().get(0).equals(game.getPlayingUser())) {
            return new Result(false, game.getPlayingUser() + " is not the creator");
        }
        App.setCurrentGame(null);
        return new Result(true, "You have successfully exited the game , you may create or load another game .");
    }

    public Result deleteCurrentGame() throws IOException {
        Game game = App.getCurrentGame();
        User requester = game.getPlayingUser();
        if(game == null) return new Result(false, "You have no ongoing game");
        HashMap<User , Boolean> terminationVotes = new HashMap<>();
        int positiveVotes = 1;
        terminationVotes.put(requester, true);
        for (User player : game.getPlayers()) {
            if(player.equals(requester)) continue;
            System.out.println(player.getUsername() + " must vote about termination : (y/n)");
            while(true) {
                String input = GameMenu.scan();
                if (input.equalsIgnoreCase("y")) {
                    terminationVotes.put(player, true);
                    positiveVotes++;
                    break;
                } else if (input.equalsIgnoreCase("n")) {
                    terminationVotes.put(player, false);
                    break;
                }
                System.out.println("invalid input");
            }
        }
        if(positiveVotes == game.getPlayers().size()) {
            App.setCurrentGame(null);
            for (User player : game.getPlayers()) {
                player.setCurrentGame(null);
            }
            return new Result(true, "You have successfully deleted the current game");
        }
        else {
            return new Result(false, "insufficient amount of votes , game continues");
        }
    }

    public Result ShowRecipes(){
        if(App.getCurrentGame().getPlayingUser().getCraftingRecipes() == null){
            return new Result(false, "You have no crafting recipes");
        }
        StringBuilder st = new StringBuilder();
        for(CraftingRecipes craftingRecipes : App.getCurrentGame().getPlayingUser().getCraftingRecipes()){
            st.append(craftingRecipes.toString());
        }
        return new Result(true, st.toString());
    }
    public Result CraftItem(String itemName){
        CraftingRecipes craftingRecipes = null;
        if(!App.getCurrentGame().getPlayingUser().getCurrentTile().getTileType().equals(TileType.BuildingTile)){//TODO:house tile?
            return new Result(false,"you can only build in your house");
        }
        System.out.println("crafting recipes: ");
        for(CraftingRecipes craftingRecipe : App.getCurrentGame().getPlayingUser().getCraftingRecipes()){
            System.out.println(craftingRecipe.toString());
        }
        for(CraftingRecipes craftingRecipes1:CraftingRecipes.values()){
            if(craftingRecipes1.name().equals(itemName)){
                craftingRecipes = craftingRecipes1;
                break;
            }
        }
        if(craftingRecipes == null) {
            return new Result(false, "no item found by given name");
        }
        if(!App.getCurrentGame().getPlayingUser().getCraftingRecipes().contains(craftingRecipes)) {
            return new Result(false, "you haven't discovered the given crafting recipe");
        }
        //TODO:need help implementing this part
        CraftingItems item = (CraftingItems)craftingRecipes.getItem();
        HashMap ingredients = item.getIngredients();
        for(Object item1 : ingredients.keySet()){
            if(!App.getCurrentGame().getPlayingUser().backPack.items.containsKey(item1) ||
            App.getCurrentGame().getPlayingUser().backPack.items.get(item1) < (int)ingredients.get(item1)) {
                return new Result(false, "you don't have the required ingredients");
            }
        }
        if(!App.getCurrentGame().getPlayingUser().getBackPack().doesBackPackHasSpace()){
            return new Result(false, "your backpack is full");
        }
        App.getCurrentGame().getPlayingUser().getEnergy().consumeEnergy(10);
        for(Object item1 : ingredients.keySet()){
            CraftingItems item2 = (CraftingItems)item1;
            App.getCurrentGame().getPlayingUser().backPack.items.put(item2,App.getCurrentGame().getPlayingUser().backPack.items.get(item2)-item2.getIngredients().get(item2));
        }
            App.getCurrentGame().getPlayingUser().backPack.items.put(craftingRecipes.getItem(),1);
        return new Result(true, itemName + " has been crafted");
    }

    public Result CraftUsingMachine(){return null;}



    public Result goToNextTurn(User forceUser) throws IOException {
        User user ;
        String notifications = "";
        if(forceUser == null) {
            int i = App.getCurrentGame().getPlayers().indexOf(App.getCurrentGame().getPlayingUser());
            i = i + 1 == App.getCurrentGame().getPlayers().size() ? 0 : i + 1;
            App.getCurrentGame().setPlayingUser(App.getCurrentGame().getPlayers().get(i));
            if (i == 0) {
                App.getCurrentGame().getGameCalender().updateTimeAndDateAndSeasonAfterTurns();
            }
             user = App.getCurrentGame().getPlayingUser();
        }
        else {
            App.getCurrentGame().setPlayingUser(forceUser);
            user = forceUser;
        }
        if(user.getAskedMarriage() != null) {
            GameMenu.print(user.getAskedMarriage().getUsername() + " has asked you to marry him, you should respond now");
            while(true) {
                String input = GameMenu.scan();
                Matcher matcher = GameMenuCommands.respondToMarriageRequest.getMatcher(input);
                if(matcher == null) {
                    GameMenu.print("invalid input");
                    continue;
                }
                if(matcher.group("answer").equalsIgnoreCase("accept")){
                    return acceptMarriageRequest(user);
                }
                if(matcher.group("answer").equalsIgnoreCase("reject")){
                    return rejectMarriageRequest(user);
                }
                break;
            }
        }
        if(user.isHasNewMessages()){
            notifications += "\nyou have new message(s), look your message history for more info";
        }
        if(user.isHasNewGift()){
            notifications += "\nyou have new gift(s), look your gift history for more info";
        }
        if(user.isHasNewTradeRequest()){
            notifications += "\nyou have new trade request(s), look your trade history for more info";
        }
        user.setHasNewMessages(false);
        user.setHasNewGift(false);
        user.setHasNewTradeRequest(false);
        return new Result(true , "going to next turn . now turn of : " +
                user.getUsername() + notifications);
    }

    public Result UseArtisan(String ArtisanName, List<String> Ingredients) {
        if(!App.getCurrentGame().getPlayingUser().getCurrentTile().getTileType().equals(TileType.BuildingTile)) {
            return new Result(false, "You are not allowed to use artisan here");
        }
        ArtisanProduct artisanProduct = null;
        for(ArtisanProductDetails artisan : ArtisanProductDetails.values()) {
            if (artisan.getName().equals(ArtisanName)) {
                artisanProduct = new ArtisanProduct(artisan);
                break;
            }
        }
        if(artisanProduct == null) {
            return new Result(false, "enter a valid artisan name");
        }
        return null;
    }

    private Result acceptMarriageRequest(User user) {
        Map.Entry<ItemInterface, Integer> ring = getItemFromBackPack("WEDDING_RING", user.getAskedMarriage().backPack);
        if(ring == null) {
            return new Result(false, "that stupid boy forgot the ring");
        }
        user.setSpouse(user.getAskedMarriage());
        user.getSpouse().setSpouse(user);
        user.setAskedMarriage(null);
        removeFromBackPack(ring,user.getSpouse().backPack , 1);
        addToBackPack(ring,user.backPack , 1);
        return new Result(true, "You have successfully accepted the marriage");
    }

    private Result rejectMarriageRequest(User user) {
        int xp = user.getFriendshipXPs().get(user.getAskedMarriage().getID());
        increaseMutualXP(user, user.getAskedMarriage(), -xp);
        user.setAskedMarriage(null);
        return new Result(true, "damn , so we breaking hearts now ?");
    }

    public Result showTime() {
        return null;
    }

    public Result showDate() {
        return null;
    }

    public Result showTimeAndDate() {
        return null;
    }

    public Result showDayOfTheWeek() {
        return null;
    }

    public Result showSeason() {
        return null;
    }

    public Result showWeather() {
        return null;
    }

    public Result showWeatherForecast() {
        return null;
    }

    public void goToNextDay() {

    }

    public Result walk(User user , String xString, String yString) throws IOException {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        User player = user;
        Tile startTile = player.getCurrentTile();
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        Tile destTile = tiles[x][y];
        PathFinder p = new PathFinder(tiles);
        PathFinder.Path path = p.walk(startTile.coordination.x , startTile.coordination.y , x, y, player.getEnergy());
        if(!path.reachable()) {
            return new Result(false, path.message());
        }
        for (Point point : path.path()) {
            if(player.getEnergy().getEnergyAmount() <= 0) {
                player.getEnergy().faint();
                return new Result(false, "you have no energy left");
            }
            if(!player.getEnergy().TurnEnergyLeft()){
                goToNextTurn(null);
                player.getEnergy().endTurn();
                return new Result(true, "next turn");
            }
            player.getCurrentTile().setContentSymbol('0');
            player.setCurrentTile(tiles[point.x][point.y]);
            tiles[point.x][point.y].setContentSymbol(player.getSymbol());
            player.getEnergy().consumeEnergy(point.energy);
        }
        return new Result(true, path.message());
    }

    public Result printMap(String xString , String yString , String sizeString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        int size = Integer.parseInt(sizeString);
        Result validate = validateCoordinates(x , y);
        if(!validate.isSuccess())return validate;
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        StringBuilder map = new StringBuilder();
        for (int i = -1 + x ; i < -1 + x + size ; i++){
            map.append(String.format("%4d", i));
        }
        map.append("\n");
        for (int i = y; i < Math.min(y + size , 250); i++) {
            map.append(String.format("%4d", i));
            for (int j = x; j < Math.min(x + size , 300); j++) {
                if(tiles[j][i].getTileType().equals(TileType.BuildingWall)){
                    map.append(String.format("%s%4c%s", Colors.YELLOW ,tiles[j][i].getSymbol(), RESET));
                }
                else if(tiles[j][i].getTileType().equals(TileType.Water)){
                    map.append(String.format("%s%4c%s", Colors.BLUE ,tiles[j][i].getSymbol(), RESET));
                }
                else if(tiles[j][i].getTileType().equals(TileType.Pathway)){
                    map.append(String.format("%s%s%4c%s", Colors.YELLOW_UNDERLINED, Colors.GREEN ,tiles[j][i].getSymbol(), RESET));
                }
                else if(tiles[j][i].getSymbol() == 'X'){
                    map.append(String.format("%s%4c%s", Colors.RED ,tiles[j][i].getSymbol(), RESET));
                }
                else{
                    map.append(String.format("%s%4c%s", Colors.WHITE, tiles[j][i].getSymbol(), RESET));
                }
            }
            map.append("\n");
        }
        return new Result(true, map + "here is your map Arbab");
    }

    public Result validateCoordinates(int x, int y) {
        if(x < 0 || y < 0 || x > 299 || y > 249) {
            return new Result(false, "invalid coordinates");
        }
        return new Result(true, "coordinates good to go");
    }

    public Result helpReadingTheMap() {
        String message = ". : ground\n" +
                "numbers(1-4) : players" +
                "color yellow : walls" +
                "# : cabin floorTiles" +
                "@ : greenhouse floorTiles" +
                "R : rock" +
                "~ : water" +
                "0 : not walkable" +
                "B : black smith" +
                "C : carpenter's shop" +
                "S : star drop saloon" +
                "M : Marnie's ranch" +
                "G : general store" +
                "F : fish shop" +
                "J : Joja market";
        return new Result(true, message);
    }

    public Result cheatEnergySet(String energyString){
        User player = App.getCurrentGame().getPlayingUser();
        int energy = Integer.parseInt(energyString);
        if(energy > player.getEnergy().getEnergyCapacity()){
            player.getEnergy().setEnergyCapacity(energy);
        }
        player.getEnergy().setEnergyAmount(energy);
        return new Result(true, "cheat energy set");
    }

    public Result cheatEnergyUnlimited(){
        User player = App.getCurrentGame().getPlayingUser();
        Energy energy = player.getEnergy();
        energy.setEnergyCapacity(Double.POSITIVE_INFINITY);
        energy.setEnergyAmount(Double.POSITIVE_INFINITY);
        energy.setCurrentTurnCapacity(Double.POSITIVE_INFINITY);
        return new Result(true, "cheat energy unlimited");
    }

    public Result showEnergy() {
        Energy energy = App.getCurrentGame().getPlayingUser().getEnergy();
        return new Result(true, "" +
                "energy left: " + energy.getEnergyAmount() +
                "energy left in this turn: " + (energy.getCurrentTurnCapacity() - energy.getCurrentTurnConsumedEnergy()) +
                "energy capacity: " + energy.getEnergyCapacity());
    }

    public Result goToShopMenu(){
        ShopMenuController controller = new ShopMenuController();
        if(controller.shop == null)
            return new Result(false, "you are not in a shop");
        App.setCurrentMenu(Menu.ShopMenu);
        ((ShopMenu)Menu.ShopMenu.getMenu()).setShop(controller.shop);
        return new Result(true, "redirecting to shop menu ...");
    }

    public Result cheatAddItemToBackPack(String itemName, String amountString){
        return null;
    }

    public Result showInventory() {
        StringBuilder output = new StringBuilder();
        output.append("Inventory: ");

        for (Map.Entry<ItemInterface, Integer> entry : App.getCurrentGame().getPlayingUser().getBackPack().items.entrySet()) {
            ItemInterface item = entry.getKey();
            int quantity = entry.getValue();

            output.append(item.getName());
            output.append(": ");
            output.append(quantity);
            output.append(", ");
        }

        // Remove the last comma and space if needed
        if (output.length() > 11) { // "Inventory: " is 11 characters
            output.setLength(output.length() - 2);
        }

        return new Result(true, output.toString());
    }


    public Result deleteAnItemFromInventory() {
        return null;
    }
    public Result buyAnimal(String animalType ,String animalName) {
        return null;
    }
    public String petAnimal(String animalName){
        return null;
    }
    public String AnimalsDetails(){
        return null;
    }
    public Result shepherdAnimal(String animalName){
        return null;
    }
    public Result feedHay(String animalName){
        return null;
    }
    public String produces(){
        return null;
    }
    public Result collectProducts(String animalName){
        return null;
    }
    public Result sellAnimal(String animalName){
        return null;
    }
    public Result fishing(FishingPole fishingPole){
        return null;
    }
    public Result useArtisan(String ArtisanName , String productName){
        return null;
    }

    public Result getFromArtisan(String ArtisanName){
        return null;
    }

    public Result talk (String username, String message){
        User sender = App.getCurrentGame().getPlayingUser();
        User receiver = getUserBYName(username);
        if(receiver == null){
            return new Result(false, "user not found");
        }
        if(notCloseEnough(sender, receiver)){
            return new Result(false, "you are not close enough, somehow you don't have a cell phone either");
        }
        Message m = new Message(sender.getID(), message, receiver.getID());
        sender.getMessages().add(m);
        receiver.getMessages().add(m);
        receiver.setHasNewMessages(true);
        increaseMutualXP(sender , receiver, 20);
        return new Result(true, "your message was sent");
    }

    public boolean notCloseEnough(User sender, User receiver){
        return !(Math.abs(receiver.getCurrentTile().coordination.x - sender.getCurrentTile().coordination.x) > 2 ||
                Math.abs(receiver.getCurrentTile().coordination.y - sender.getCurrentTile().coordination.y) > 2);
    }

    public void increaseMutualXP(User sender, User receiver, int i) {
        sender.getFriendshipXPs().put(receiver.getID(), sender.getFriendshipXPs().getOrDefault(receiver.getID(), 100) + i);
        receiver.getFriendshipXPs().put(sender.getID(), receiver.getFriendshipXPs().getOrDefault(sender.getID(), 100) + i);
    }
    public Result talkHistory(String username){
        User me = App.getCurrentGame().getPlayingUser();
        User friend = getUserBYName(username);
        StringBuilder m = new StringBuilder();
        m.append("talk history with ").append(username).append(": \n═════════════════════════════════\n");
        for (Message message : me.getMessages()) {
            if(message.getSenderID() == friend.getID()){
                m.append("received: \n").append(message.getMessage()).append("\n═════════════════════════════════\n");
            }
            else if(message.getReceiverID() == friend.getID()){
                m.append("sent: \n").append(message.getMessage()).append("\n═════════════════════════════════\n");
            }
        }
        return new Result(true, m.toString());
    }
    public Result friendShipStatus(String username){
        User me = App.getCurrentGame().getPlayingUser();
        User friend = getUserBYName(username);
        int xp = me.getFriendshipXPs().getOrDefault(friend.getID(), 100);
        int level = xp/100 -1;
        return new Result(true, "friendship status for " + username+
                "\nfriendship level: " + level + "\nfriendship xp: " + xp);
    }
    public Result giftPlayer(String username, String ItemName, String amountString){
        User user = App.getCurrentGame().getPlayingUser();
        User receiver = getUserBYName(username);
        int amount = Integer.parseInt(amountString);
        Map.Entry<ItemInterface, Integer> item = getItemFromBackPack(ItemName);
        if(receiver == null){
            return new Result(false, "user not found");
        }
        if(item == null){
            return new Result(false, "item not found");
        }
        if(item.getValue() < amount){
            return new Result(false, "you don't have enough of this item");
        }
        if(!receiver.backPack.doesBackPackHasSpace()){
            return new Result(false, "this player doesn't have enough space");
        }
        if(user.getFriendshipXPs().getOrDefault(receiver.getID(), 100)/100 -1 < 1){
            return new Result(false, "you should be at least level one friends");
        }
        Gift gift = new Gift(user.getID(), receiver.getID(), item.getKey(), amount);
        user.getGifts().add(gift);
        receiver.getGifts().add(gift);
        addToBackPack(item, receiver.backPack, amount);
        removeFromBackPack(item, user.backPack, amount);
        return new Result(true, "gift has been sent");
    }
    public Result giftList(){
        User user = App.getCurrentGame().getPlayingUser();
        StringBuilder m = new StringBuilder();
        String rating;
        m.append("gift list:\n═════════════════════════════════\n");
        for (Gift gift : user.getGifts()) {
            if(gift.getSenderID() == user.getID()){
                rating = gift.getRate() == -1 ? "not rated yet" : String.format("%d",gift.getRate());
                m.append("you sent :").append(gift.getAmount()).append(" of ").append(gift.getItemInterface().getName())
                        .append("\nrating: ").append(rating).append("\nID: ").append(gift.getID()).append("\n═════════════════════════════════\n");
            }
            else{
                rating = gift.getRate() == -1 ? "not rated yet" : String.format("%d",gift.getRate());
                m.append("you received :").append(gift.getAmount()).append(" of ").append(gift.getItemInterface().getName())
                        .append("\nrating: ").append(rating).append("\nID: ").append(gift.getID()).append("\n═════════════════════════════════\n");
            }
        }
        return new Result(true, m.toString());
    }
    public Result giftHistory(String username){
        User me = App.getCurrentGame().getPlayingUser();
        User friend = getUserBYName(username);
        StringBuilder m = new StringBuilder();
        String rating;
        m.append("gift history with ").append(username).append(": \n═════════════════════════════════\n");
        for (Gift gift : me.getGifts()) {
            if(gift.getRecipientID() == me.getID()){
                rating = gift.getRate() == -1 ? "not rated yet" : String.format("%d",gift.getRate());
                m.append("you received :").append(gift.getAmount()).append(" of ").append(gift.getItemInterface().getName())
                        .append("\nrating: ").append(rating).append("\nID: ").append(gift.getID()).append("\n═════════════════════════════════\n");
            }
            else if(gift.getSenderID() == me.getID()){
                rating = gift.getRate() == -1 ? "not rated yet" : String.format("%d",gift.getRate());
                m.append("you sent :").append(gift.getAmount()).append(" of ").append(gift.getItemInterface().getName())
                        .append("\nrating: ").append(rating).append("\nID: ").append(gift.getID()).append("\n═════════════════════════════════\n");
            }
        }
        friend.setHasNewGift(true);
        return new Result(true, m.toString());
    }

    public Result rateGift(String giftIDString, String ratingString){
        Gift gift = getGiftByID(Integer.parseInt(giftIDString));
        if(gift == null){
            return new Result(false, "gift not found");
        }
        int rate = Integer.parseInt(ratingString);
        User sender = getUserByID(gift.getSenderID());
        User receiver = getUserByID(gift.getRecipientID());
        if(App.getCurrentGame().getPlayingUser().getID() != receiver.getID()){
            return new Result(false, "you are not the one who got the gift");
        }
        if(rate < 1 || rate > 5){
            return new Result(false, "rating should be between 1 and 5");
        }
        gift.setRate(rate);
        int xp = (rate - 3) * 10 + 15;
        increaseMutualXP(sender, receiver, xp);
        return new Result(true, "rating has been set");
    }

    public User getUserByID(int senderID) {
        for (User player : App.getCurrentGame().getPlayers()) {
            if(player.getID() == senderID){
                return player;
            }
        }
        return null;
    }
    public void addToBackPack(Map.Entry<ItemInterface, Integer> item, BackPack backPack, int amount){
        backPack.items.compute(item.getKey(), (k, v) -> v == null ? amount : v + amount);
    }

    public void removeFromBackPack(Map.Entry<ItemInterface, Integer> item, BackPack backPack, int amount){
        backPack.items.compute(item.getKey(), (k, v) -> v - amount);
        if(item.getValue() < 0){
            backPack.items.remove(item.getKey());
        }
    }
    public Result hug(String username){
        User me = App.getCurrentGame().getPlayingUser();
        User friend = getUserBYName(username);
        if(friend == null){
            return new Result(false, "user not found");
        }
        if(notCloseEnough(me, friend)){
            return new Result(false, "you are not close enough");
        }
        if(me.getFriendshipXPs().getOrDefault(friend.getID(), 100)/100 - 1 < 2){
            return new Result(false, "you should be at least level two friends");
        }
        increaseMutualXP(friend, me, 60);
        return new Result(true, "awww that's totally platonic");
    }
    public Result flower(String username){
        User me = App.getCurrentGame().getPlayingUser();
        User friend = getUserBYName(username);
        if(friend == null){
            return new Result(false, "user not found");
        }
        if(notCloseEnough(me, friend)){
            return new Result(false, "you are not close enough");
        }
        if(me.getFriendshipXPs().getOrDefault(friend.getID(), 100)/100 - 1 < 3){
            return new Result(false, "you should finish level two friendship");
        }
        Map.Entry<ItemInterface, Integer> item = getItemFromBackPack("Bouquete");
        if(item == null){
            return new Result(false, "you are not a magician you can't summon flowers");
        }
        if(!friend.backPack.doesBackPackHasSpace()){
            return new Result(false, "your friend's backpack is full");
        }
        me.getLvl3FriendsID().add(friend.getID());
        friend.getLvl3FriendsID().add(me.getID());
        addToBackPack(item , friend.backPack,1);
        removeFromBackPack(item, me.backPack, 1);
        return new Result(true, "wow you are really making a move don't you ?");
    }
    public Result askMarriage(String username){
        User me = App.getCurrentGame().getPlayingUser();
        User friend = getUserBYName(username);
        if(friend == null){
            return new Result(false, "user not found");
        }
        if(notCloseEnough(me, friend)){
            return new Result(false, "you are not close enough");
        }
        Map.Entry<ItemInterface, Integer> ring = getItemFromBackPack("WEDDING_RING");
        if(ring == null){
            return new Result(false , "you don't have a wedding ring");
        }
        if(!me.getGender().equals(Gender.male)){
            return new Result(false, "you are not a male");
        }
        if(!friend.getGender().equals(Gender.female)){
            return new Result(false, "that's fucking gay");
        }
        if(me.getFriendshipXPs().getOrDefault(friend.getID(), 100) < 400){
            return new Result(false, "you are not intimate enough");
        }
        friend.setAskedMarriage(me);
        return new Result(true, "we are all rooting for you");
    }
    public Result respondToMarriageRequest(){
        return null;
    }
    public Result goToTradeMenu(){
        App.setCurrentMenu(Menu.TradeMenu);
        return new Result(true, "redirecting to trade menu ...");
    }
    public Result meetNPC(){
        return null;
    }
    public void npcFriendshipList(){

    }
    public void questList(){

    }
    public Result completeQuest(){
        return null;
    }

    public Result Sell(String productName , String countString){
        boolean isNearBin = false;
        User player = App.getCurrentGame().getPlayingUser();
        Point point = player.getCurrentPoint();
        Tile[][]tiles = App.getCurrentGame().getMap().getTiles();
        for(int i = point.x -1 ; i <= point.x + 1 ; i++){
            for(int j = point.y -1 ; j <= point.y + 1 ; j++){
                if(tiles[i][j].getTileType().equals(TileType.ShippingBin)){
                    isNearBin = true;
                    break;
                }
            }
        }
        if(!isNearBin){
            return new Result(false, "you are not near a shipping bin");
        }
        Map.Entry<ItemInterface, Integer> product = getItemFromBackPack(productName);
        if(product == null){
            return new Result(false, "product not found");
        }
        int count = countString == null ? product.getValue() : Integer.parseInt(countString);
        if(count > product.getValue()){
            return new Result(false, "product exceeds maximum quantity");
        }
        player.setIncome(player.getIncome() + count*product.getKey().getPrice());
        player.getBackPack().items.replace(product.getKey(), product.getValue() - count);
        if(player.getBackPack().items.get(product.getKey()) == 0){
            player.getBackPack().items.remove(product.getKey());
        }
        return new Result(true, "you have successfully sold " + count + " of " + product.getKey().getName());
    }
    public Map.Entry<ItemInterface, Integer> getItemFromBackPack(String productName) {
        for (Map.Entry<ItemInterface, Integer> e : App.getCurrentGame().getPlayingUser().getBackPack().items.entrySet()) {
            if(e.getKey().getName().equalsIgnoreCase(productName)){
                return e;
            }
        }
        return null;
    }

    public Map.Entry<ItemInterface, Integer> getItemFromBackPack(String productName, BackPack backPack) {
        for (Map.Entry<ItemInterface, Integer> e : backPack.items.entrySet()) {
            if(e.getKey().getName().equalsIgnoreCase(productName)){
                return e;
            }
        }
        return null;
    }

    public User getUserBYName(String userName) {
        for (User player : App.getCurrentGame().getPlayers()) {
            if (player.getUsername().equals(userName)) {
                return player;
            }
        }
        return null;
    }

    public Gift getGiftByID(int giftID) {
        for (Gift gift : App.getCurrentGame().getPlayingUser().getGifts()) {
            if(giftID==gift.getID()){
                return gift;
            }
        }
        return null;
    }
    public ItemConstant getItemConstantByName(String itemName) throws IOException {
        Class<? extends ItemConstant>[] enumClasses = new Class[]{
                AnimalProductDetails.class,
                ArtisanProductDetails.class,
                BlackSmithProducts.class,
                CarpenterShopProducts.class,
                GeneralStoreProducts.class,
                FishShopProducts.class,
                RanchProducts.class,
                SaloonProducts.class,
                JojaMartProducts.class,
                CookingRecipes.class,
                CraftingRecipes.class,
                CropEnum.class,
                FishType.class,
                ForagingSeeds.class,
                Fruit.class,
                Minerals.class,
                MixedSeeds.class,
                SaplingEnum.class,
                SeedEnum.class,
                ToolTypes.class
        };
        for (Class<? extends ItemConstant> enumClass : enumClasses) {
            for (ItemConstant constant : enumClass.getEnumConstants()) {
                if (constant.getItem().getName().equalsIgnoreCase(itemName)) {
                    return constant;
                }
            }
        }
        return null;
    }
}
