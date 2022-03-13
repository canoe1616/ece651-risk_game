package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

public class Map implements Serializable{
  private HashSet<Territory> territoryList;
  private HashSet<Player> players;
  private int player_num;

  public Map(){
    this.territoryList = new HashSet<Territory>();
    this.players = new HashSet<Player>();
  }

  public void setPlayerNum(int n){
    this.player_num = n;
  }

  public int getPlayerNum(){
    return this.player_num;
  }

  public HashSet<Territory> getList() {
    return territoryList;
  }

  public HashSet<Player> getPlayer() {
    return players;
  }
  public void addPlayer(Player p){
    players.add(p);
  }

  public void addTerritory(Territory ter){
    territoryList.add(ter);
  }

  public Player findPlayer(String color){
    for (Player p: players){
      if (p.getName().equals(color))
        return p;
    }
    return null;
  }

  public Territory findTerritory(String name){
    for (Territory t: territoryList){
      if (t.getName().equals(name))
        return t;
    }
    return null;
  }

  
}
