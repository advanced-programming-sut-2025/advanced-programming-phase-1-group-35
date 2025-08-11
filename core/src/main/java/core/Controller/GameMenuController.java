package core.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import core.Model.*;
import core.Model.Serializables.SerializableMap;
import core.Model.enums.*;
import core.Model.enums.Crops.*;
import core.Model.enums.Shops.Products.*;
import core.Controller.InGameMenu.CropController;
import core.Controller.InGameMenu.FarmingController;
import core.Controller.InGameMenu.ShopMenuController;
import core.GraphicView.GameMenuUI;
import core.Model.CropClasses.Crop;
import core.Model.CropClasses.Tree;
import core.Model.FarmStuff.Greenhouse;
import core.Model.Tools.BackPack;
import core.Model.enums.machines.ArtisanProductDetails;
import core.Model.machines.ArtisanProduct;
import core.Model.enums.animal.AnimalProductDetails;
import core.Model.enums.animal.FishType;
import core.View.InGameMenu.ShopMenu;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import core.Model.enums.Colors;
import core.Model.enums.Menu;
import core.Model.enums.Shops.Products.GeneralStoreProducts;
import core.Model.enums.TileType;
import com.StardewValley.Main;

import java.io.IOException;
import java.util.*;
import java.util.Map;

import static core.Model.enums.Colors.RESET;

public class GameMenuController {
    CropController cropController = new CropController();
    FarmingController farmingController;
    public Game CurrentGame = null;
    public GameMenuUI gameMenu;

    public GameMenuController() {
    }

    public String showCropInfo(String cropName) {
        return cropController.getCropInfo(cropName);
    }

    public Result plantSeed(String seedName, String direction) {
        return farmingController.plantSeed(seedName, direction);
    }

    public Result pickUpSeed(String direction) {
        Tile tile = findTile(direction);
        Optional<SeedEnum> matchingSeed = Arrays.stream(SeedEnum.values())
                .filter(seed -> tile.getContents().contains(seed))
                .findFirst();

        boolean hasAnySeed = Arrays.stream(SeedEnum.values())
                .anyMatch(seed -> tile.getContents().contains(seed));

        if (!hasAnySeed) {
            return new Result(false, "tile doesn't have any seed");
        }
        if (!App.getCurrentGame().getPlayingUser().getBackPack().doesBackPackHasSpace()) {
            return new Result(false, "your backpack is full");
        }
        App.getCurrentGame().getPlayingUser().getBackPack().items.put(matchingSeed.get(), 1);
        return new Result(true, "picked up " + matchingSeed.get().getName());
    }

    public Result fertilize(String fertilizerName, String direction) {
        if (!direction.toLowerCase().matches("up|down|left|right")) {
            return new Result(false, "Invalid direction");
        }
        List<ItemInterface> fertilizers = new ArrayList<>();
        fertilizers.add(GeneralStoreProducts.SPEED_GRO);
        fertilizers.add(GeneralStoreProducts.DELUXE_RETAINING_SOIL);
        fertilizers.add(GeneralStoreProducts.BASIC_RETAINING_SOIL);
        fertilizers.add(GeneralStoreProducts.QUALITY_RETAINING_SOIL);
        Fertilizer fertilizer = null;
        for (ItemInterface item : fertilizers) {
            if (item.getName().toLowerCase().equals(fertilizerName.toLowerCase())) {
                fertilizer = (Fertilizer) item;
                break;
            }
        }
        if (fertilizer == null) {
            return new Result(false, "Fertilizer not found");
        }
        if (!App.getCurrentGame().getPlayingUser().getBackPack().items.containsKey(fertilizer)) {
            return new Result(false, "you don't have the fertilizer in your inventory");
        }

        if (findTile(direction).getPlanted() == null) {
            if (!findTile(direction).isPlowed()) {
                return new Result(false, "the selected tile is not plowed");
            }
        } else {
            if (findTile(direction).getPlanted().getClass().equals(Crop.class)) {
                Crop crop = (Crop) findTile(direction).getPlanted();
                if (crop.getDaysSincePlanted() != 0)
                    return new Result(false, "you can't fertilize a plant that's older than 1 day");
                crop.setFertilized(true);
                findTile(direction).setFertilized(true);
                crop.setFertilizer(fertilizer);
                return new Result(true, "you fertilized " + crop.getName());
            }
            if (findTile(direction).getPlanted().getClass().equals(Tree.class)) {
                Tree tree = (Tree) findTile(direction).getPlanted();
                if (tree.getDaysSincePlanted() != 0)
                    return new Result(false, "you can't fertilize a plant that's older than 1 day");
                tree.setFertilized(true);
                findTile(direction).setFertilized(true);
                tree.setFertilizer(fertilizer);
                return new Result(true, "you fertilized " + tree.getName());
            }
        }
        findTile(direction).setFertilized(true);
        return new Result(true, "tile fertilized successfully");
    }

    public static Tile findTile(String direction) {
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
        if (!App.isStayLoggedIn()) {
            App.setLoggedInUser(null);
            App.setCurrentMenu(Menu.LoginMenu);
        } else App.setCurrentMenu(Menu.MainMenu);
        App.setCurrentGame(null);
        App.serializeApp();
        App.setCurrentMenu(Menu.ExitMenu);
    }

    public Result createNewGame(String username0 ,String username1, String username2, String username3, int[] mapNumbers) {
        LoginMenuController loginMenuController = new LoginMenuController();
        ArrayList<String> playerNames = new ArrayList<>();
        ArrayList<User> players = new ArrayList<>();
        User host = loginMenuController.getUser(username0);
        players.add(host);

        if (username1 != null) playerNames.add(username1);
        if (username2 != null) playerNames.add(username2);
        if (username3 != null) playerNames.add(username3);
        for (String playerName : playerNames) {
            User user = loginMenuController.getUser(playerName);
            if (user == null) return new Result(false, playerName + "does not exist");
            if (isUserInOtherGame(user)) return new Result(false, playerName + "is already in a game");
            players.add(user);
        }
        User playingUser = App.getLoggedInUser();
        System.out.println("playingUser = " + playingUser.getUsername());
        System.out.println("players = " + players);
        Game game = new Game(players, playingUser);
        App.games.add(game);
        App.setCurrentGame(game);
        game.npcController.init();
        CurrentGame = game;
        setFarmingController();
        for (User player : players) {
            player.setCurrentGame(game);
        }
        for (int mapNumber : mapNumbers) {
            System.out.println(mapNumber);
        }
        chooseMap(mapNumbers);
        return new Result(true, "You have created a new game . now redirecting to the game .");
    }

    private boolean isUserInOtherGame(User user) {
        if (user.getCurrentGame() == null) return false;
        return true;
    }

    public void chooseMap(int[] mapNumbers) {
        Game game = App.getCurrentGame();
        ArrayList<User> players = game.getPlayers();
        User[] users = new User[4];
        for (int i = 0; i < players.size(); i++) {
            users[i] = players.get(i);
        }
        game.getMap().buildMap(users, mapNumbers);
        core.Model.Map.users = users;
        core.Model.Map.types = mapNumbers;
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
        return new Result(false, "nuh uh");
    }

    public Result pickItem(String itemName, String direction) throws IOException {
        ItemConstant item = getItemConstantByName(itemName);
        Tile tile = findTile(direction);
        if (item == null) {
            return new Result(false, "Item " + itemName + " does not exist");
        }
        if (!tile.getContents().contains(item)) {
            return new Result(false, "tile is empty");
        }
        if (!App.getCurrentGame().getPlayingUser().getBackPack().doesBackPackHasSpace()) {
            return new Result(false, "your backpack is full");
        }
        App.getCurrentGame().getPlayingUser().getBackPack().items.put(item, 1);
        tile.getContents().remove(item);
        return new Result(true, "Item " + itemName + " has been picked up");
    }

    public Result useScareCrow(String direction) {
        Tile tile = findTile(direction);
        if (tile.getPlanted() != null || tile.getContents() != null) {
            return new Result(false, "can't place it here");
        }
        if (!App.getCurrentGame().getPlayingUser().getBackPack().items.containsKey(CraftingItems.Scarecrow)) {
            return new Result(false, "you don't have any scarecrows");
        }
        tile.getContents().add(CraftingItems.Scarecrow);
        App.getCurrentGame().getPlayingUser().getBackPack().items.put(CraftingItems.Scarecrow, App.getCurrentGame().getPlayingUser().getBackPack().items.get(CraftingItems.Scarecrow) - 1);
        if (App.getCurrentGame().getPlayingUser().getBackPack().items.get(CraftingItems.Scarecrow) == 0) {
            App.getCurrentGame().getPlayingUser().getBackPack().items.remove(CraftingItems.Scarecrow);
        }
        return new Result(true, "scare crow planted");
    }

    public Result loadGame() {
        Game game = App.getLoggedInUser().getCurrentGame();
        if (game == null) {
            return new Result(false, "You have no ongoing game");
        }
        App.setCurrentGame(game);
        return new Result(true, "You have successfully loaded game");
    }

    public Result exitGame() {
        Game game = App.getLoggedInUser().getCurrentGame();
        if (game == null) return new Result(false, "You have no ongoing game");
        if (!game.getPlayers().get(0).equals(game.getPlayingUser())) {
            return new Result(false, game.getPlayingUser() + " is not the creator");
        }
        App.setCurrentGame(null);
        return new Result(true, "You have successfully exited the game , you may create or load another game .");
    }

    public Result deleteCurrentGame() throws IOException {
        Game game = App.getCurrentGame();
        User requester = game.getPlayingUser();
        if (game == null) return new Result(false, "You have no ongoing game");
        HashMap<User, Boolean> terminationVotes = new HashMap<>();
        int positiveVotes = 1;
        terminationVotes.put(requester, true);
        for (User player : game.getPlayers()) {
            if (player.equals(requester)) continue;
            System.out.println(player.getUsername() + " must vote about termination : (y/n)");
        }
        if (positiveVotes == game.getPlayers().size()) {
            App.setCurrentGame(null);
            for (User player : game.getPlayers()) {
                player.setCurrentGame(null);
            }
            return new Result(true, "You have successfully deleted the current game");
        } else {
            return new Result(false, "insufficient amount of votes , game continues");
        }
    }

    public Result ShowRecipes() {
        if (App.getCurrentGame().getPlayingUser().getCraftingRecipes() == null) {
            return new Result(false, "You have no crafting recipes");
        }
        StringBuilder st = new StringBuilder();
        for (CraftingRecipes craftingRecipes : App.getCurrentGame().getPlayingUser().getCraftingRecipes()) {
            st.append(craftingRecipes.toString());
        }
        return new Result(true, st.toString());
    }

    public Result CraftItem(String itemName) {
        CraftingRecipes craftingRecipes = null;
        if (!App.getCurrentGame().getPlayingUser().getCurrentTile().getTileType().equals(TileType.BuildingTile)) {//TODO:house tile?
            return new Result(false, "you can only build in your house");
        }
        System.out.println("crafting recipes: ");
        for (CraftingRecipes craftingRecipe : App.getCurrentGame().getPlayingUser().getCraftingRecipes()) {
            System.out.println(craftingRecipe.toString());
        }
        for (CraftingRecipes craftingRecipes1 : CraftingRecipes.values()) {
            if (craftingRecipes1.name().equals(itemName)) {
                craftingRecipes = craftingRecipes1;
                break;
            }
        }
        if (craftingRecipes == null) {
            return new Result(false, "no item found by given name");
        }
        if (!App.getCurrentGame().getPlayingUser().getCraftingRecipes().contains(craftingRecipes)) {
            return new Result(false, "you haven't discovered the given crafting recipe");
        }
        //TODO:need help implementing this part
        CraftingItems item = (CraftingItems) craftingRecipes.getItem();
        HashMap ingredients = item.getIngredients();
        for (Object item1 : ingredients.keySet()) {
            if (!App.getCurrentGame().getPlayingUser().getBackPack().items.containsKey(item1) ||
                    App.getCurrentGame().getPlayingUser().getBackPack().items.get(item1) < (int) ingredients.get(item1)) {
                return new Result(false, "you don't have the required ingredients");
            }
        }
        if (!App.getCurrentGame().getPlayingUser().getBackPack().doesBackPackHasSpace()) {
            return new Result(false, "your backpack is full");
        }
        App.getCurrentGame().getPlayingUser().getEnergy().consumeEnergy(10);
        for (Object item1 : ingredients.keySet()) {
            CraftingItems item2 = (CraftingItems) item1;
            App.getCurrentGame().getPlayingUser().getBackPack().items.put(item2, App.getCurrentGame().getPlayingUser().getBackPack().items.get(item2) - item2.getIngredients().get(item2));
        }
        App.getCurrentGame().getPlayingUser().getBackPack().items.put(craftingRecipes.getItem(), 1);
        return new Result(true, itemName + " has been crafted");
    }

    public Result CraftUsingMachine() {
        return null;
    }


    public Result goToNextTurn(User forceUser) throws IOException {
        User user;
        String notifications = "";
        if (forceUser == null) {
            int i = App.getCurrentGame().getPlayers().indexOf(App.getCurrentGame().getPlayingUser());
            i = i + 1 == App.getCurrentGame().getPlayers().size() ? 0 : i + 1;
            if (i == 0) {
                App.getCurrentGame().getGameCalender().updateTimeAndDateAndSeasonAfterTurns();
            }
            user = App.getCurrentGame().getPlayingUser();
        } else {
            user = forceUser;
        }
        if (user.isHasNewMessages()) {
            notifications += "\nyou have new message(s), look your message history for more info";
        }
        if (user.isHasNewGift()) {
            notifications += "\nyou have new gift(s), look your gift history for more info";
        }
        if (user.isHasNewTradeRequest()) {
            notifications += "\nyou have new trade request(s), look your trade history for more info";
        }
        return new Result(true, "going to next turn . now turn of : " +
                user.getUsername() + notifications);
    }

    public Result UseArtisan(String ArtisanName, List<String> Ingredients) {
        if (!App.getCurrentGame().getPlayingUser().getCurrentTile().getTileType().equals(TileType.BuildingTile)) {
            return new Result(false, "You are not allowed to use artisan here");
        }
        ArtisanProduct artisanProduct = null;
        for (ArtisanProductDetails artisan : ArtisanProductDetails.values()) {
            if (artisan.getName().equals(ArtisanName)) {
                artisanProduct = new ArtisanProduct(artisan);
                break;
            }
        }
        if (artisanProduct == null) {
            return new Result(false, "enter a valid artisan name");
        }
        return null;
    }


    public Result buildGreenHouse() {
        User user = App.getCurrentGame().getPlayingUser();
        Greenhouse greenhouse = user.getFarm().getGreenhouse();
        if (user.getMoney() < 10000) {
            return new Result(false, "you do not have enough money");
        }
        if (greenhouse.isFixed()) {
            return new Result(false, "already fixed");
        }
        user.setMoney(user.getMoney() - 10000);
        greenhouse.setFixed(true);
        return new Result(true, "You have successfully built the greenhouse");
    }

    public Result walk(User user, String xString, String yString) throws IOException {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        User player = user;
        Tile startTile = player.getCurrentTile();
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        Tile destTile = tiles[x][y];
        PathFinder p = new PathFinder(tiles);
        PathFinder.Path path = p.walk(startTile.coordination.x, startTile.coordination.y, x, y, player.getEnergy(), player);
        if (!path.reachable()) {
            return new Result(false, path.message());
        }
        for (Point point : path.path()) {
            if (player.getEnergy().getEnergyAmount() <= 0) {
                player.getEnergy().faint();
                return new Result(false, "you have no energy left");
            }
            if (!player.getEnergy().TurnEnergyLeft()) {
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

    public Result printMap(String xString, String yString, String sizeString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        int size = Integer.parseInt(sizeString);
        Result validate = validateCoordinates(x, y);
        if (!validate.isSuccess()) return validate;
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        StringBuilder map = new StringBuilder();
        for (int i = -1 + x; i < -1 + x + size; i++) {
            map.append(String.format("%4d", i));
        }
        map.append("\n");
        for (int i = y; i < Math.min(y + size, 250); i++) {
            map.append(String.format("%4d", i));
            for (int j = x; j < Math.min(x + size, 300); j++) {
                if (tiles[j][i].getTileType().equals(TileType.BuildingWall)) {
                    map.append(String.format("%s%4c%s", Colors.YELLOW, tiles[j][i].getSymbol(), RESET));
                } else if (tiles[j][i].getTileType().equals(TileType.Water)) {
                    map.append(String.format("%s%4c%s", Colors.BLUE, tiles[j][i].getSymbol(), RESET));
                } else if (tiles[j][i].getTileType().equals(TileType.Pathway)) {
                    map.append(String.format("%s%s%4c%s", Colors.YELLOW_UNDERLINED, Colors.GREEN, tiles[j][i].getSymbol(), RESET));
                } else if (tiles[j][i].getSymbol() == 'X') {
                    map.append(String.format("%s%4c%s", Colors.RED, tiles[j][i].getSymbol(), RESET));
                } else {
                    map.append(String.format("%s%4c%s", Colors.WHITE, tiles[j][i].getSymbol(), RESET));
                }
            }
            map.append("\n");
        }
        return new Result(true, map + "here is your map Arbab");
    }

    public Result validateCoordinates(int x, int y) {
        if (x < 0 || y < 0 || x > 299 || y > 249) {
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

    public Result cheatEnergySet(String energyString) {
        User player = App.getCurrentGame().getPlayingUser();
        int energy = Integer.parseInt(energyString);
        if (energy > player.getEnergy().getEnergyCapacity()) {
            player.getEnergy().setEnergyCapacity(energy);
        }
        player.getEnergy().setEnergyAmount(energy);
        return new Result(true, "energy set to " + energy);
    }

    public Result cheatEnergyUnlimited() {
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

    public Result goToShopMenu() {
        ShopMenuController controller = new ShopMenuController();
        if (controller.shop == null)
            return new Result(false, "you are not in a shop");
        App.setCurrentMenu(Menu.ShopMenu);
        ((ShopMenu) Menu.ShopMenu.getMenu()).setShop(controller.shop);
        return new Result(true, "redirecting to shop menu ...");
    }

    public Result cheatAddItemToBackPack(String itemName, String amountString) throws IOException {
        ItemInterface item = getItemConstantByName(itemName).getItem();
        if (item == null) return new Result(false, "no item found via name " + itemName);
        int amount = Integer.parseInt(amountString);
        App.getCurrentGame().getPlayingUser().getBackPack().items.put(item, amount);
        return new Result(true, amount + " of " + itemName + " was given to player");
    }

    public Result chopTree(String direction) {
        Tile tile = findTile(direction);
        if (!App.getCurrentGame().getPlayingUser().getCurrentTool().getToolType().equals(ToolTypes.AXE)) {
            return new Result(false, "you need to equip an axe first");
        }
        if (!(tile.getPlanted() instanceof Tree)) {
            return new Result(false, "no tree found");
        }
        Tree tree = (Tree) tile.getPlanted();
        App.getCurrentGame().getPlayingUser().getBackPack().items.put(CarpenterShopProducts.WOOD, 1);
        App.getCurrentGame().getPlayingUser().getForagingSkill().gainXp();
//        App.getCurrentGame().getMap().getTrees().remove(tree);
//        App.getCurrentGame().getPlayingUser().getFarm().getTrees().remove(tree);
        tree.setChopped(true);
        tile.setSymbol('.');
        tile.setPlanted(null);
        tile.getContents().remove(tree);
        tile.setContentSymbol('.');
        return new Result(true, "tree chopped down");
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

    public User getUserByID(int senderID) {
        for (User player : App.getCurrentGame().getPlayers()) {
            if (player.getID() == senderID) {
                return player;
            }
        }
        return null;
    }

    public void addToBackPack(Map.Entry<ItemInterface, Integer> item, BackPack backPack, int amount) {
        backPack.items.compute(item.getKey(), (k, v) -> v == null ? amount : v + amount);
    }

    public void removeFromBackPack(Map.Entry<ItemInterface, Integer> item, BackPack backPack, int amount) {
        backPack.items.compute(item.getKey(), (k, v) -> v - amount);
        if (item.getValue() < 0) {
            backPack.items.remove(item.getKey());
        }
    }

    public Result respondToMarriageRequest() {
        return null;
    }

    public Result goToTradeMenu() {
        App.setCurrentMenu(Menu.TradeMenu);
        return new Result(true, "redirecting to trade menu ...");
    }

    public Result Sell(String productName, String countString) {
        boolean isNearBin = false;
        User player = App.getCurrentGame().getPlayingUser();
        Point point = new Point(player.getCurrentTile().coordination.x, player.getCurrentTile().coordination.y);
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        for (int i = point.x - 1; i <= point.x + 1; i++) {
            for (int j = point.y - 1; j <= point.y + 1; j++) {
                if (tiles[i][j].getTileType().equals(TileType.ShippingBin)) {
                    isNearBin = true;
                    break;
                }
            }
        }
        if (!isNearBin) {
            return new Result(false, "you are not near a shipping bin");
        }
        Map.Entry<ItemInterface, Integer> product = getItemFromBackPack(productName);
        if (product == null) {
            return new Result(false, "product not found");
        }
        int count = countString == null ? product.getValue() : Integer.parseInt(countString);
        if (count > product.getValue()) {
            return new Result(false, "product exceeds maximum quantity");
        }
        player.setIncome(player.getIncome() + count * product.getKey().getPrice());
        player.getBackPack().items.replace(product.getKey(), product.getValue() - count);
        if (player.getBackPack().items.get(product.getKey()) == 0) {
            player.getBackPack().items.remove(product.getKey());
        }
        return new Result(true, "you have successfully sold " + count + " of " + product.getKey().getName());
    }

    public Map.Entry<ItemInterface, Integer> getItemFromBackPack(String productName) {
        for (Map.Entry<ItemInterface, Integer> e : App.getCurrentGame().getPlayingUser().getBackPack().items.entrySet()) {
            if (e.getKey().getName().equalsIgnoreCase(productName)) {
                return e;
            }
        }
        return null;
    }

    public Map.Entry<ItemInterface, Integer> getItemFromBackPack(String productName, BackPack backPack) {
        for (Map.Entry<ItemInterface, Integer> e : backPack.items.entrySet()) {
            if (e.getKey().getName().equalsIgnoreCase(productName)) {
                return e;
            }
        }
        return null;
    }
    public void increaseMutualXP(User sender, User receiver, int i) {
        if (sender.getSpouse().equals(receiver)) {
            sender.getEnergy().setEnergyAmount(sender.getEnergy().getEnergyAmount() + 50);
            receiver.getEnergy().setEnergyAmount(receiver.getEnergy().getEnergyAmount() + 50);
        }
        sender.getFriendshipXPs().put(receiver.getID(), sender.getFriendshipXPs().getOrDefault(receiver.getID(), 100) + i);
        receiver.getFriendshipXPs().put(sender.getID(), receiver.getFriendshipXPs().getOrDefault(sender.getID(), 100) + i);
    }

    public User getUserBYName(String userName) {
        for (User player : App.getCurrentGame().getPlayers()) {
            if (player.getUsername().equals(userName)) {
                return player;
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
    public SerializableMap getSerializableMap() {
        if (App.getCurrentGame() == null || App.getCurrentGame().getMap() == null) {
            System.err.println("Current game or map is null, cannot serialize map.");
            return null;
        }
        return new SerializableMap(App.getCurrentGame().getMap());
    }
    public boolean isCloseToObject(String objectName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        FarmingController controller = new FarmingController(game.getMap().getTiles());
        Tile[] closeTiles = controller.findCloseTiles(player.getCurrentTile());
        for (Tile tile : closeTiles) {
            for (ItemInterface content : tile.getContents()) {
                if (content.getName().equalsIgnoreCase(objectName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCloseToSea() {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        FarmingController controller = new FarmingController(game.getMap().getTiles());
        Tile[] closeTiles = controller.findCloseTiles(player.getCurrentTile());
        for (Tile tile : closeTiles) {
            if (tile.getTileType().equals(TileType.Water)) {
                return true;
            }
        }
        return false;
    }
    public void showNotification(String message) {
        Label notificationLabel = new Label(message, GameAssetManager.getDefaultSkin());
        notificationLabel.setWrap(true);
        notificationLabel.setAlignment(Align.center);

        Table notificationTable = new Table(GameAssetManager.getDefaultSkin());
        notificationTable.setBackground(GameAssetManager.getDefaultSkin().newDrawable("white", Color.DARK_GRAY));
        notificationTable.add(notificationLabel).width(300).pad(10);
        notificationTable.pack();

        // Center top
        notificationTable.setPosition(
            Gdx.graphics.getWidth() / 2f - notificationTable.getWidth() / 2f,
            Gdx.graphics.getHeight() - notificationTable.getHeight() - 20
        );

        notificationTable.getColor().a = 0;
        gameMenu.getStage().addActor(notificationTable);

        notificationTable.addAction(Actions.sequence(
            Actions.fadeIn(0.3f),
            Actions.delay(2f),
            Actions.fadeOut(0.5f),
            Actions.removeActor()
        ));
    }


    public void init() {
        gameMenu = new GameMenuUI(this, CurrentGame);
//        farmingController = farmingController = new FarmingController(App.getCurrentGame().getMap().getTiles());
        try {
            farmingController.generateStartingPlants();
        Crop crop = new Crop(CropEnum.COFFEE_BEAN,App.getCurrentGame().getPlayingUser().getCurrentTile());
        crop.setCurrentState(crop.getStages().size());
        App.getCurrentGame().getPlayingUser().getCurrentTile().setPlanted(crop);
        App.getCurrentGame().getMap().getCrops().add(crop);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }


        Main.getGame().setScreen(gameMenu);
    }

    public Game getGame() {
        return CurrentGame;
    }
}
