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

  public Map(HashSet<Territory> list) {
    this.territoryList = list;
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

  public Player findPlayer(String color){
    Iterator<Player> playerIterator = players.iterator();
    while(playerIterator.hasNext()){
      Player p = playerIterator.next();
      if (p.getName().equals(color)){
        return p;
      }
    }
    throw new IllegalArgumentException("No such player.");
  }

  public Territory findTerritory(String name){
     Iterator<Territory> terIterator = territoryList.iterator();
     while(terIterator.hasNext()){
       Territory ter = terIterator.next();
       if (ter.getName().equals(name)){
         return ter;
       }
     }
     throw new IllegalArgumentException("No such territory.");
   }

  public void buildMap(int player_num){
    MapFactory m = new MapFactory();
    if (player_num == 2){
      territoryList = m.makeMapForTwo().getList();
      
    }
  }

  
  public void addTerritory(Territory ter){
    territoryList.add(ter);
  }
}
