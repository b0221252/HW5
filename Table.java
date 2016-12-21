import java.util.ArrayList;
public class Table {


	static final int MAXPLAYER = 4;
	private Deck deck;
	private Player[] players;
	private Dealer dealer = new Dealer();
	int[] pos_betArray = new int[MAXPLAYER];
	//private int nDecks;
	public static void main(String[] args) {

	}

	public Table(int nDeck) {

		//this.nDecks = nDecks;
		deck = new Deck(nDeck);
		players = new Player[MAXPLAYER];

}
	public void set_player(int pos, Player p) {
		if ((pos >= 0) && (pos < MAXPLAYER)) {
			players[pos] = p;
		}

}

	public Player[] get_player(){
return players;
	}

	public void set_dealer(Dealer d){
dealer=d;
	}

	public Card get_face_up_card_of_dealer(){
		return dealer.getOneRoundCard().get(1);// 2nd card idx=1
	}

	private void ask_each_player_about_bets() {
		for (int p = 0; p < players.length; p++) {
			if (this.players[p] != null) {
				players[p].say_hello();
				int bet = players[p].make_bet();
				if (bet > players[p].get_current_chips()) {//no more chips
					pos_betArray[p] = 0;
				} 
				else {
					pos_betArray[p] = players[p].make_bet();
				}
			}
		}
}

	
	private void distribute_cards_to_dealer_and_players() {
		for (int i = 0; i < players.length; i++) {
			if ((players[i] != null) && (pos_betArray[i] != 0)) {
				ArrayList<Card> playerCard = new ArrayList();
				playerCard.add(deck.getOneCard(true));//player all cards open
				playerCard.add(deck.getOneCard(true));
				players[i].setOneRoundCard(playerCard);
			}
		}
		ArrayList<Card> dealerCard = new ArrayList();
		dealerCard.add(deck.getOneCard(false)); // dealer 1st close
		dealerCard.add(deck.getOneCard(true)); // 2nd open
		dealer.setOneRoundCard(dealerCard);
		System.out.print("Dealer's face up card is ");
		Card dealerFaceUpCard = get_face_up_card_of_dealer();
		dealerFaceUpCard.printCard();
	}


	private void ask_each_player_about_hits() {
		for (int i = 0; i < players.length; i++) {
			ArrayList<Card> playerCard = new ArrayList();
			boolean hit = false;
			do {
				hit = players[i].hit_me(this);
				if (hit) {
					playerCard = players[i].getOneRoundCard();
					playerCard.add(deck.getOneCard(true));
					players[i].setOneRoundCard(playerCard);
					System.out.print("Hit! ");
					System.out.println(players[i].get_name() + "'s Cards now:");
					for (Card c : playerCard) {
						c.printCard();
					}
				} 
				else {
					System.out.println(players[i].get_name() + ", Pass hit!");
					System.out.println(players[i].get_name() + ", Final Card:");
					for (Card c : players[i].getOneRoundCard()) {
						c.printCard();
					}
				}
			} 
			while (hit);
		}

}
	private void ask_dealer_about_hits(){
		ArrayList<Card> dealerCard = new ArrayList();
		boolean hit = false;
		do {
			hit = dealer.hit_me(this);
			if (hit) {

				dealerCard = dealer.getOneRoundCard();
				dealerCard.add(deck.getOneCard(true));
				dealer.setOneRoundCard(dealerCard);
				System.out.print("Hit! ");
			}
			if (dealer.getTotalValue() > 21) {
				hit = false;
			}
		} 
		while (hit);
		System.out.println("Dealer's hit is over!");


	}

	private void calculate_chips() {
		int dealerValue = dealer.getTotalValue();
		System.out.print("Dealer's card value is " + dealerValue + " ,Cards:");
		dealer.printAllCard();//dealer  cards and totalvalue
		
		for (int i = 0; i < players.length; i++) {
			if ((players[i] != null) && (pos_betArray[i] != 0)) {
				int playerValue = players[i].getTotalValue();
				System.out.print(players[i].get_name() + " card value is " + playerValue);
				
				
				if (players[i].getTotalValue() > 21) {
					if (dealer.getTotalValue() > 21) {//dealer also>21
						System.out.println(", chips have no change!, the Chips now is: " 
											+ players[i].get_current_chips());
					} 
				
					else {	//dealer also>21 dealer win
						players[i].increase_chips(-pos_betArray[i]);
						System.out.println(", Loss " + pos_betArray[i] 
											+ " Chips, the Chips now is: "
											+ players[i].get_current_chips());
					}
				} 
				
				else if (players[i].getTotalValue() == 21) { //player ==21
					//1st.2nd ==10+Ace >>>black jack
					if ((players[i].getOneRoundCard().size() == 2) && (players[i].hasAce())) 
					{
						//dealer!=21 Player win 2*bet return
						if (dealer.getTotalValue() != 21) {
							players[i].increase_chips(pos_betArray[i]*2);
							System.out.println("Get " + pos_betArray[i]
									+ " Chips, the Chips now is: " 
									+ players[i].get_current_chips());
						} 
						//dealer has same cards, tie
						else if ((dealer.getOneRoundCard().size() == 2) && (dealer.hasAce())){
							
							System.out.println("chips have no change!, the Chips now is: "
									+ players[i].get_current_chips());
						} 
						//otherwise player  win 2*bet return
						else {
							players[i].increase_chips(pos_betArray[i]*2);
							System.out.println("Get " + pos_betArray[i]
												+ " Chips, the Chips now is: " 
												+ players[i].get_current_chips());
						}
					}
					//player totalvalue=21,dealer!=21,player win
					else if (dealer.getTotalValue() != 21) {
						players[i].increase_chips(pos_betArray[i] );
						System.out.println(",Get " + pos_betArray[i] 
								+ " Chips, the Chips now is: "
								+ players[i].get_current_chips());
					} 
					//tie? no win or loss
					else {          
						System.out.println(
								",chips have no change!The Chips now is: " 
						+ players[i].get_current_chips());
					}
				} 
				//dealer >21 player win
				else if (dealer.getTotalValue() > 21) {
					players[i].increase_chips(pos_betArray[i]);
					System.out.println(", Get " + pos_betArray[i] 
										+ " Chips, the Chips now is: "
										+ players[i].get_current_chips());
				} 
				//player>dealer ,player win
				else if (dealer.getTotalValue() < players[i].getTotalValue()) {
					players[i].increase_chips(pos_betArray[i]);
					System.out.println(", Get " + pos_betArray[i] 
										+ " Chips, the Chips now is: "
										+ players[i].get_current_chips());
				} 
				//player<dealer ,dealer win
				else if (dealer.getTotalValue() > players[i].getTotalValue()) {
					players[i].increase_chips(-pos_betArray[i]);
					System.out.println(", Loss " + pos_betArray[i] 
										+ " Chips, the Chips now is: "
										+ players[i].get_current_chips());
				} 
				//player==dealer
				else {
					System.out.println(", chips have no change! The Chips now is: " 
										+ players[i].get_current_chips());
				}

			}
		}

}

	public int[] get_palyers_bet(){
		return pos_betArray;
	}

	public void play(){
		ask_each_player_about_bets();
		distribute_cards_to_dealer_and_players();
		ask_each_player_about_hits();
		ask_dealer_about_hits();
		calculate_chips();
	}


}
