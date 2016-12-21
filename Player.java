import java.util.ArrayList;
import java.util.Scanner;
public class Player extends Person {
	Scanner sc = new Scanner(System.in);
	private String name; 
	private int chips; 
	private int bet; 
	//private int total_value;
	//boolean ace = false;
	//private ArrayList<Card> oneRoundCard; 
	
	public Player(String name, int chips) {
		this.name=name;
		this.chips=chips;
		//oneRoundCard=new ArrayList<Card>();
	}
	
	public String get_name() {
	return name; 
	}
	
	public int make_bet() {
		bet = 1;
		if (chips >= 1) {
			return bet;
		} else {
			System.out.println("run out of chips,can't make bet");
			return 0;
		}
	}
	/*public void setOneRoundCard(ArrayList<Card> cards) {
		for(Card c:cards){
	oneRoundCard.add(c); 
	}*/
	
	@Override
	public boolean hit_me(Table table) {
		int total_value = getTotalValue();
		if (total_value < 17)
			return true;
		else if (total_value == 17 && hasAce()) {
			return true;
		} 
		else {
			if (total_value >= 21)
				return false;
			/*else {
				Player[] players = table.get_player();
				int lose_count = 0;
				int v_count = 0;
				int[] betArray = table.get_palyers_bet();
				for (int i = 0; i < players.length; i++) {
					if (players[i] == null) {
						continue;
					}
					if (players[i].getTotalValue() != 0) {
						if (total_value < players[i].getTotalValue()) {
							lose_count += betArray[i];
						} else if (total_value > players[i].getTotalValue()) {
							v_count += betArray[i];
						}
					}
				}
				if (v_count < lose_count)
					return true;*/
				else
					return false;
			}
		}

	
	/*public int getTotalValue() {
		for(Card c:oneRoundCard){
		int rankc=c.getRank();
		
		if (rankc==11||rankc==12||rankc==13){
			rankc=10;
		}
		else if(rankc==1){
			 ace=true;
			}
		total_value=total_value+rankc;
		}
		
	return total_value ;
	}*/
	
	public void increase_chips (int diff) {
		chips= chips+diff;
	}
	public int get_current_chips() {
		return chips;
	}
	public void say_hello() {
	System.out.println("Hello, I am " + name + "."); 
	System.out.println("I have " + chips + " chips."); 
	}

}
