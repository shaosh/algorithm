import java.util.LinkedHashSet;
import java.util.Set;


public class cardgame {
	public static void main(String[] args){
		card[] poker = createCards();
		card[] newPoker = new card[52];
		for(int i = 0; i < 52; i++){
			System.out.println(poker[i].suitname + " " + poker[i].cardname);
			newPoker[i] = new card(); 
		}
		
		System.out.println("================================================");
		newPoker = shuffle(poker);
		for(int i = 0; i < 52; i++){
			System.out.println(newPoker[i].suitname + " " + newPoker[i].cardname);
		}
		
		System.out.println("================================================");
		cut(newPoker, 27);
		for(int i = 0; i < 52; i++){
			System.out.println(newPoker[i].suitname + " " + newPoker[i].cardname);
		}
		
	}
	
	public static card[] createCards(){
		card[] poker = new card[52]; 
		
		int[] suit = {0, 1, 2, 3};
		int suitnum = suit[0];
		for(int cardnum = 0; cardnum < 4; cardnum++){				
			for(int i = 0; i < 13; i++){
				poker[cardnum * 13 + i] = new card();
				if(suitnum == 0){
					poker[cardnum * 13 + i].suit = 0;
					poker[cardnum * 13 + i].suitname = "hearts";					
				}
				else if(suitnum == 1){
					poker[cardnum * 13 + i].suit = 1;
					poker[cardnum * 13 + i].suitname = "clubs";					
				}
				else if(suitnum == 2){
					poker[cardnum * 13 + i].suit = 2;
					poker[cardnum * 13 + i].suitname = "diamonds";					
				}
				else if(suitnum == 3){
					poker[cardnum * 13 + i].suit = 3;
					poker[cardnum * 13 + i].suitname = "spades";					
				}
				poker[cardnum * 13 + i].number = i;
				poker[cardnum * 13 + i].cardname = i + 1;
			}
			suitnum++;
		}
		return poker;		
	}
	
	public static card[] shuffle(card[] poker){
		card[] newPoker = new card[52];
		int[] numlist = new int[52];
		for (int i = 0; i < 52; i++){
			newPoker[i] = new card();
			int num;
			while(true){
				num = (int)(Math.random()*52);
				if(poker[num].shuffled == false){
					newPoker[i].cloneCard(poker[num]);
					poker[num].shuffled = true;
					break;
				}
			}
		}
		return newPoker;
	}
	
	public static void cut(card[] poker, int split){
		if (split < 1 || split > 52){
			System.out.println("Invalid split position");
			return;
		}
		split = split - 1;
		card[] newPoker = new card[52];
		for (int i = 0; i < 52; i++){
			newPoker[i] = new card();
		}
		for(int i = 0; i < 52; i++){
			if(i < split)
				newPoker[52 - split + i].cloneCard(poker[i]);		
			else
				newPoker[i - split].cloneCard(poker[i]);
		}
		for(int i = 0; i < 52; i++){
			poker[i].cloneCard(newPoker[i]);
		}
		return;
		
	}
}

class card{
	int suit;
	String suitname = new String();
	int number;
	int cardname;
	boolean shuffled = false;
	
	public void cloneCard(card card1){
		this.suit = card1.suit;
		this.number = card1.number;
		this.cardname = card1.cardname;
		this.suitname = new String(card1.suitname);
			
	}
}