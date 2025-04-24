package Model;

import Model.NPCs.NPC;
import Model.NPCs.Quest;
import Model.Tools.Tool;
import Model.enums.Gender;
import Model.TradeAndGift.Gift;
import Model.enums.SecurityQuestions;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Gender gender;
    private SecurityQuestions securityQuestion ;
    private String securityAnswer;
    private int highScore = 0;
    private int gamesPlayed = 0;
    private int energyConsumedInTurn = 0;

    private Game currentGame = null;
    private Tile currentTile = null;
    private Map map;
    public Inventory inventory;
    private ArrayList<User> lv1Friends;
    private ArrayList<User> lv2Friends;
    private ArrayList<User> lv3Friends;
    private ArrayList<Gift> Gifts;
    private HashMap<NPC, Integer> npcFriendship;
    protected Tool currentTool;

    public User(String username, String password, String nickname, String email,
                Gender gender , SecurityQuestions securityQuestion , String securityAnswer) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public void talk (User user){

    }
    public void talkHistory(User user){

    }
    public Result gift (User user , Item item){
        return null;
    }
    public Result RateGift(Gift gift){
        return null;
    }
    public void showGiftList(User user){

    }
    public Result hug(User user){
        return null;
    }
    public Result flower(User user){
        return null;
    }
    public Result askMarriage(User user){
        return null;
    }
    public Result respondToMarriageRequest(User user){
        return null;
    }
    public Result trade(User user , String type , Item item , int amount , int price , Item item2){
        return null;
    }
    public void listTradeRequests(){

    }
    public Result respondToTrade(Model.TradeAndGift.Trade trade){
        return null;
    }
    public void tradeHistory(){

    }
    public Result meetNPC(NPC npc){
        return null;
    }
    public Result giftNPC(NPC npc){
        return null;
    }
    public void npcFriendshipList(){

    }
    public void questList(){

    }
    public Result completeQuest(Quest quest){
        return null;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
    public ArrayList<User> getLv1Friends() {
        return lv1Friends;
    }

    public void setLv1Friends(ArrayList<User> lv1Friends) {
        this.lv1Friends = lv1Friends;
    }

    public ArrayList<User> getLv2Friends() {
        return lv2Friends;
    }

    public void setLv2Friends(ArrayList<User> lv2Friends) {
        this.lv2Friends = lv2Friends;
    }

    public ArrayList<User> getLv3Friends() {
        return lv3Friends;
    }

    public void setLv3Friends(ArrayList<User> lv3Friends) {
        this.lv3Friends = lv3Friends;
    }

    public ArrayList<Gift> getGifts() {
        return Gifts;
    }

    public void setGifts(ArrayList<Gift> gifts) {
        Gifts = gifts;
    }

    public HashMap<NPC, Integer> getNpcFriendship() {
        return npcFriendship;
    }

    public void setNpcFriendship(HashMap<NPC, Integer> npcFriendship) {
        this.npcFriendship = npcFriendship;
    }

    public SecurityQuestions getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(SecurityQuestions securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public int getEnergyConsumedInTurn() {
        return energyConsumedInTurn;
    }

    public void setEnergyConsumedInTurn(int energyConsumedInTurn) {
        this.energyConsumedInTurn = energyConsumedInTurn;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public Energy getEnergy() {
        return energy;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }
}
