package Controller;

import Model.App;
import Model.Game;
import Model.Result;
import Model.Tools.FishingPole;
import Model.User;
import Model.enums.GameMenuCommands;
import Model.enums.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenuController {
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
    public Result createNewGame(String username1, String username2, String username3 , Scanner scanner) {
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
            if(isUserInOtherGame(user)) return new Result(false, playerName + "is already in a game");
            players.add(user);
        }
        Game game = new Game(players , App.getLoggedInUser());
        App.games.add(game);
        App.setCurrentGame(game);
        for (User player : players) {
            player.setCurrentGame(game);
        }

        game.getMap().buildMap();
        return new Result(true, "You have created a new game . now redirecting to the game .");
    }

    private boolean isUserInOtherGame(User user) {
        if(user.getCurrentGame() == null) return false;
        return true;
    }

    public void chooseMap(Game game , ArrayList<User> players , Scanner scanner) {
        for (int i = 0; i < players.size();) { // choosing maps
            System.out.println("choosing map for " + players.get(i).getUsername());
            String input = scanner.nextLine();
            Matcher matcher = GameMenuCommands.chooseMap.getMatcher(input);
            if(matcher == null) {
                System.out.println("invalid input");
                continue;
            }
            int number = Integer.parseInt(matcher.group("number"));
            if(number < 1 || number > 4) {
                System.out.println("invalid number");
                continue;
            }
            if(game.getMap().getFarms().get(number - 1).getOwner() != null) {
                System.out.println("this farm is taken");
                continue;
            }
            game.getMap().getFarms().get(number - 1).setOwner(players.get(i));
            System.out.println("farm number " + number + " has been chosen by " + players.get(i).getUsername());
            i++;
        }
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

    public Result deleteCurrentGame(Scanner scanner) {
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
                String input = scanner.nextLine().trim();
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

    public void goToNextTurn() {
        int i = App.getCurrentGame().getPlayers().indexOf(App.getCurrentGame().getPlayingUser());
        i = i + 1 == App.getCurrentGame().getPlayers().size() ? 0 : i;
        App.getCurrentGame().setPlayingUser(App.getCurrentGame().getPlayers().get(i));
        System.out.println("going to next turn . now turn of : " + App.getCurrentGame().getPlayingUser().getUsername());
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

    public void walk(int x, int y) {

    }

    private boolean isThereAnyWayToGetToTheDestination() {
        return false;
    }

    private void findTheBestWayToGetToTheDestination() {

    }

    public Result printMap() {
        return null;
    }

    public Result helpReadingTheMap() {
        return null;
    }

    public Result showEnergy() {
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
