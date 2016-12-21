import java.util.*;
public class Card{
	public enum Suit{Club,Diamond,Heart,Spade}; //Definition: 1~4, Clubs=1, Diamonds=2, Hearts=3, Spades=4
	private int rank; //1~13
	
	Suit thisCardSuit;
	public Card(Suit s,int r){
		
		thisCardSuit = s;
		rank = r;
		}
		

	public void printCard(){
		
		System.out.println(thisCardSuit.toString()+","+rank);
	}
	public Suit getSuit(){
		return thisCardSuit;
	}
	public int getRank(){
		return rank;
	}
}