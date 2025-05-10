package Model;

import Model.FarmStuff.Farm;
import Model.FarmStuff.Farm;
import Model.FarmStuff.Home.Cabin;
import Model.FarmStuff.Home.Cabin;
import Model.NPCs.NPC;
import Model.NPCs.Quest;
import Model.Tools.BackPack;
import Model.Tools.SkillLevel;
import Model.Tools.Tool;
import Model.enums.CookingRecipes;
import Model.enums.Gender;
import Model.TradeAndGift.Gift;
import Model.enums.SecurityQuestions;
import Model.enums.Skill;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private int ID ;
    private int IDCounter = 1;
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
    private int money = 0;
    public Farm farm; // TODO : find the farm of this player and set it to this object

    private Game currentGame = null;
    private int currentGameFarmIndex = -1;
    private int currentGameID = 0;
    private Tile currentTile = null;
    private Point currentPoint = null;
    private char symbol;
    private Map map;
    public Inventory inventory;
    private SkillLevel farmingSkill = Skill.farming.getSkillLevel();
    private SkillLevel miningSkill = Skill.mining.getSkillLevel();
    private SkillLevel foragingSkill = Skill.foraging.getSkillLevel();
    private SkillLevel fishingSkill = Skill.fishing.getSkillLevel();
    public BackPack backPack;
    private ArrayList<User> lv1Friends;
    private ArrayList<User> lv2Friends;
    private ArrayList<User> lv3Friends;
    private ArrayList<Gift> Gifts;
    private HashMap<NPC, Integer> npcFriendship;
    protected Tool currentTool;
    private Energy energy = new Energy();
    public Cabin cabin;
    public ArrayList<CookingRecipes> learnedRecipes = new ArrayList<>();

    public User(String username, String password, String nickname, String email,
                Gender gender , SecurityQuestions securityQuestion , String securityAnswer) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.ID = IDCounter++;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public BackPack getBackPack() {
        return backPack;
    }

    public void setBackPack(BackPack backPack) {
        this.backPack = backPack;
    }

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }

    public ArrayList<CookingRecipes> getLearnedRecipes() {
        return learnedRecipes;
    }

    public void setLearnedRecipes(ArrayList<CookingRecipes> learnedRecipes) {
        this.learnedRecipes = learnedRecipes;
    }

    public void talk (User user){

    }
    public void talkHistory(User user){

    }
    public Result gift (User user , ItemInterface itemInterface){
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
    public Result trade(User user , String type , ItemInterface itemInterface, int amount , int price , ItemInterface itemInterface2){
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
        currentGameID = 0;
        if(currentGame != null)currentGameID = currentGame.getGameID();
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

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public SkillLevel getFarmingSkill() {
        return farmingSkill;
    }

    public void setFarmingSkill(SkillLevel farmingSkill) {
        this.farmingSkill = farmingSkill;
    }

    public SkillLevel getMiningSkill() {
        return miningSkill;
    }

    public void setMiningSkill(SkillLevel miningSkill) {
        this.miningSkill = miningSkill;
    }

    public SkillLevel getForagingSkill() {
        return foragingSkill;
    }

    public void setForagingSkill(SkillLevel foragingSkill) {
        this.foragingSkill = foragingSkill;
    }

    public SkillLevel getFishingSkill() {
        return fishingSkill;
    }

    public void setFishingSkill(SkillLevel fishingSkill) {
        this.fishingSkill = fishingSkill;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCurrentGameID() {
        return currentGameID;
    }

    public void setCurrentGameID(int currentGameID) {
        this.currentGameID = currentGameID;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = currentPoint;
    }

    public Farm getFarm(){
        if(currentGame == null) return null;
        if(App.getCurrentGame() == null) return null;
        return App.getCurrentGame().getMap().getFarms().get(currentGameFarmIndex);
    }

    public int getCurrentGameFarmIndex() {
        return currentGameFarmIndex;
    }

    public void setCurrentGameFarmIndex(int currentGameFarmIndex) {
        this.currentGameFarmIndex = currentGameFarmIndex;
    }
}
