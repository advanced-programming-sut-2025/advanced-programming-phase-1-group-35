package models;

import models.NPCs.NPC;
import models.NPCs.Quest;
import models.TradeAndGift.Gift;
import models.TradeAndGift.Trade;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private ArrayList<User> lv1Friends;
    private ArrayList<User> lv2Friends;
    private ArrayList<User> lv3Friends;
    private ArrayList<Gift> Gifts;
    private HashMap<NPC , Integer> npcFriendship;

    public void talk (User user){

    }
    public void talkHistory(User user){

    }
    public Result gift (User user , Item item){

    }
    public Result RateGift(Gift gift){

    }
    public void showGiftList(User user){

    }
    public Result hug(User user){

    }
    public Result flower(User user){

    }
    public Result askMarriage(User user){

    }
    public Result respondToMarriageRequest(User user){

    }
    public Result trade(User user , String type , Item item , int amount , int price , Item item2){

    }
    public void listTradeRequests(){

    }
    public Result respondToTrade(Trade trade){

    }
    public void tradeHistory(){

    }
    public Result meetNPC(NPC npc){

    }
    public Result giftNPC(NPC npc){

    }
    public void npcFriendshipList(){

    }
    public void questList(){

    }
    public Result completeQuest(Quest quest){

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
}
