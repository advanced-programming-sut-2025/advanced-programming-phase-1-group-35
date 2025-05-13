package Controller;

import Controller.InGameMenu.CropController;
import Controller.InGameMenu.FarmingController;
import Controller.InGameMenu.ShopMenuController;
import Model.*;
import Model.Tools.BackPack;
import Model.Tools.FishingPole;
import Model.TradeAndGift.Gift;
import Model.enums.*;
import Model.enums.Crops.*;
import Model.enums.Shops.Products.*;
import Model.enums.animal.AnimalProductDetails;
import Model.enums.animal.FishType;
import Model.enums.machines.ArtisanProductDetails;
import View.GameMenu;
import View.InGameMenu.ShopMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import static Model.enums.Colors.RESET;

public class GameMenuController {
    CropController cropController ;
    FarmingController farmingController ;
    Game CurrentGame = null ;

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

    public Result goToNextTurn() {
        int i = App.getCurrentGame().getPlayers().indexOf(App.getCurrentGame().getPlayingUser());
        i = i + 1 == App.getCurrentGame().getPlayers().size() ? 0 : i;
        App.getCurrentGame().setPlayingUser(App.getCurrentGame().getPlayers().get(i));
        if(i == 0){
            App.getCurrentGame().getGameCalender().updateTimeAndDateAndSeasonAfterTurns();
        }
        String notifications = "";
        User user = App.getCurrentGame().getPlayingUser();
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

    public Result walk(String xString, String yString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        User player = App.getCurrentGame().getPlayingUser();
        Tile startTile = App.getCurrentGame().getPlayingUser().getCurrentTile();
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
                goToNextTurn();
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
        for (int i = -1 ; i < 300 ; i++){
            map.append(String.format("%4d", i));
        }
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
        return null;
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
        increaseMutualXP(sender , receiver, 20);
        return new Result(true, "your message was sent");
    }

    public boolean notCloseEnough(User sender, User receiver){
        return Math.abs(receiver.getCurrentPoint().x - sender.getCurrentPoint().x) > 2 ||
                Math.abs(receiver.getCurrentPoint().y - sender.getCurrentPoint().y) > 2;
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
            if(gift.getRecipientID() == friend.getID()){
                rating = gift.getRate() == -1 ? "not rated yet" : String.format("%d",gift.getRate());
                m.append("you sent :").append(gift.getAmount()).append(" of ").append(gift.getItemInterface().getName())
                        .append("\nrating: ").append(rating).append("\nID: ").append(gift.getID()).append("\n═════════════════════════════════\n");
            }
            else if(gift.getSenderID() == friend.getID()){
                rating = gift.getRate() == -1 ? "not rated yet" : String.format("%d",gift.getRate());
                m.append("you received :").append(gift.getAmount()).append(" of ").append(gift.getItemInterface().getName())
                        .append("\nrating: ").append(rating).append("\nID: ").append(gift.getID()).append("\n═════════════════════════════════\n");
            }
        }
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
        backPack.items.compute(item.getKey(), (k, v) -> v + amount);
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
    public Result askMarriage(){
        return null;
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
