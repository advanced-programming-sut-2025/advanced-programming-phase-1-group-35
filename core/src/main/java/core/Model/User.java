package core.Model;

import core.GameUpdater;
import core.Model.FarmStuff.Farm;
import core.Model.FarmStuff.Home.Cabin;
import core.Model.NPCs.NPC;
import core.Model.NPCs.Quest;
import core.Model.Tools.BackPack;
import core.Model.Tools.SkillLevel;
import core.Model.Tools.Tool;
import core.Model.TradeAndGift.Gift;
import core.Model.TradeAndGift.Trade;
import core.Model.enums.*;
import core.Model.enums.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Model.enums.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    private int ID ;
    private static int IDCounter = 1;
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

    private int money = 10000;
    private int income = 0;
    private ArrayList<CraftingRecipes> craftingRecipes = new ArrayList<>();

    private int currentGame = 0;
    private int currentGameFarmIndex = -1;
    private Pair<Integer, Integer> currentTile = null;
    private ArrayList<Gift> Gifts = new ArrayList<>();
    private ArrayList<Trade> trades = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();

    private Pair<Float, Float> currentPoint = new Pair<>(10f, 10f);
    private char symbol;
    private SkillLevel farmingSkill = Skill.farming.getSkillLevel();
    private SkillLevel miningSkill = Skill.mining.getSkillLevel();
    private SkillLevel foragingSkill = Skill.foraging.getSkillLevel();
    private SkillLevel fishingSkill = Skill.fishing.getSkillLevel();
    public BackPack backPack = new BackPack();
    private HashMap<Integer , Integer> friendshipXPs = new HashMap<>();
    private ArrayList<Integer> lvl3FriendsID = new ArrayList<>();
    private boolean hasNewMessages = false;
    private boolean hasNewGift = false;
    private boolean hasNewTradeRequest = false;
    private String spouse = null;
    private String askedMarriage = null;
    private HashMap<NPC, Integer> npcFriendship = new HashMap<>();
    protected Tool currentTool;
    private Energy energy = new Energy();
    public Cabin cabin;
    public ArrayList<CookingRecipes> learnedRecipes;
    private int selectedSlot = -1;
    private int maxInventorySize = 9;
    private int movingDirection = 0;
    private float speed = 20f;
    private float vx , vy ;
    private int votes = 0;

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
        this.learnedRecipes = new ArrayList<>();
    }

    public User(String username, String password, String nickname, String email,
                Gender gender , SecurityQuestions securityQuestion , String securityAnswer,
                int ID ) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.ID = ID ;
        this.learnedRecipes = new ArrayList<>();
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public ArrayList<CraftingRecipes> getCraftingRecipes() {
        return craftingRecipes;
    }

    public void AddCraftingRecipes(CraftingRecipes Recipes) {
        craftingRecipes.add(Recipes);
    }

    public String toString(){
        return "username: " + username + "\nfarm number: " + (currentGameFarmIndex+1) + "\n" ;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        GameUpdater.sendUpdate("money", money);
    }

    public BackPack getBackPack() {
        return backPack;
    }

    public void setBackPack(BackPack backPack) {
        this.backPack = backPack;
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
        return App.findGameByID(currentGame);
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame.getGameID();
    }

    public Tile getCurrentTile() {
        return App.getCurrentGame().getMap().getTiles()[currentTile.first][currentTile.second];
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = new Pair<>(currentTile.coordination.x, currentTile.coordination.y);
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

    public Pair<Float, Float> getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Pair<Float, Float> currentPoint) {
        this.currentPoint = currentPoint;
    }

    public Farm getFarm(){
        if(App.findGameByID(currentGame) == null) return null;
        if(App.getCurrentGame() == null) return null;
        for (Farm farm1 : App.getCurrentGame().getMap().getFarms()) {
            if(farm1.getOwner().equals(this)) return farm1;
        }
        return null;
    }

    public int getCurrentGameFarmIndex() {
        return currentGameFarmIndex;
    }

    public void setCurrentGameFarmIndex(int currentGameFarmIndex) {
        this.currentGameFarmIndex = currentGameFarmIndex;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
        GameUpdater.sendUpdate("income", income);
    }

    public HashMap<Integer, Integer> getFriendshipXPs() {
        return friendshipXPs;
    }

    public void setFriendshipXPs(HashMap<Integer, Integer> friendshipXPs) {
        this.friendshipXPs = friendshipXPs;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public boolean isHasNewMessages() {
        return hasNewMessages;
    }

    public void setHasNewMessages(boolean hasNewMessages) {
        this.hasNewMessages = hasNewMessages;
    }

    public boolean isHasNewGift() {
        return hasNewGift;
    }

    public void setHasNewGift(boolean hasNewGift) {
        this.hasNewGift = hasNewGift;
    }

    public boolean isHasNewTradeRequest() {
        return hasNewTradeRequest;
    }

    public void setHasNewTradeRequest(boolean hasNewTradeRequest) {
        this.hasNewTradeRequest = hasNewTradeRequest;
    }

    public ArrayList<Integer> getLvl3FriendsID() {
        return lvl3FriendsID;
    }

    public void setLvl3FriendsID(ArrayList<Integer> lvl3FriendsID) {
        this.lvl3FriendsID = lvl3FriendsID;
    }

    public int getEnergyConsumedInTurn() {
        return energyConsumedInTurn;
    }

    public void setEnergyConsumedInTurn(int energyConsumedInTurn) {
        this.energyConsumedInTurn = energyConsumedInTurn;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
    }

    public User getSpouse() {
        return App.findUserByUsername(spouse);
    }

    public void setSpouse(User spouse) {
        this.spouse = spouse.getUsername();
        GameUpdater.sendUpdate("spouse", spouse);
    }

    public User getAskedMarriage() {
        return App.findUserByUsername(askedMarriage);
    }

    public void setAskedMarriage(User askedMarriage) {
        this.askedMarriage = askedMarriage.getUsername();
        GameUpdater.sendUpdate("askedMarriage", askedMarriage);
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public int getMaxInventorySize() {
        return maxInventorySize;
    }

    public int getMovingDirection() {
        return movingDirection;
    }

    public void setMovingDirection(int movingDirection) {
        this.movingDirection = movingDirection;
        GameUpdater.sendUpdate("movingDirection", movingDirection);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void update(float delta, Tile[][] tiles) {
        tryMove(vx * delta, vy * delta, tiles);
    }
    public boolean tryMove(float dx, float dy, Tile[][] tiles) {

        int newX = (int) (currentPoint.first + dx);
        int newY = (int) (currentPoint.second + dy);

        if (newX < 0 || newX >= tiles.length || newY < 0 || newY >= tiles[0].length) return false;
        setCurrentTile(tiles[newX][newY]);
        if (getCurrentTile().isWalkable() && (getCurrentTile().getOwner() == null || getCurrentTile().getOwner().equals(this)) ) {
            currentPoint.first += dx;
            currentPoint.second += dy;
            GameUpdater.sendPositionUpdate(currentPoint.first, currentPoint.second);
            return true;
        }
        return false;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

}
