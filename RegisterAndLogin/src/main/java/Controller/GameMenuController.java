package Controller;

import Controller.InGameMenu.CropController;
import Controller.InGameMenu.FarmingController;
import Model.*;
import Model.Tools.FishingPole;
import Model.enums.GameMenuCommands;
import Model.enums.Menu;
import View.GameMenu;
import View.LoginMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

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
        return new Result(true , "going to next turn . now turn of : " + App.getCurrentGame().getPlayingUser().getUsername());
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
        for (int i = y; i < Math.min(y + size , 250); i++) {
            for (int j = x; j < Math.min(x + size , 300); j++) {
                System.out.print(tiles[j][i].getSymbol());
            }
            System.out.println();
        }
        return new Result(true, "here is your map Arbab");
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
                "# : cabin floorTiles" +
                "@ : greenhouse floorTiles" +
                "0 : not walkable";
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
    public void talk (){

    }
    public void talkHistory(){

    }
    public Result hug(){
        return null;
    }
    public Result flower(){
        return null;
    }
    public Result askMarriage(){
        return null;
    }
    public Result respondToMarriageRequest(){
        return null;
    }
    public void goToTradeMenu(){

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
    public Result Sell(){
        return null;
    }

}
