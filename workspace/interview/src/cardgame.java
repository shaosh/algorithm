import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;


public class cardgame {
	static final String[] suittype = {"hearts", "clubs", "diamonds", "spades"};
	static final int CARDNUM = 52;
	static final int SUITNUM = 13;
	static final int PINONUM = 48;
	static card[] poker;
	static card[] removed = new card[CARDNUM];
	static int removednum = 0;
	public static void main(String[] args){
		poker = createCards();
		for(int i = 0; i < CARDNUM; i++){
			System.out.println(poker[i].suitname + " " + poker[i].cardname + " " + poker[i].id);
			removed[i] = new card();
		}
		
		System.out.println("Shuffle==========================================");
		shuffle(poker);
		for(int i = 0; i < CARDNUM; i++){
			System.out.println(poker[i].suitname + " " + poker[i].cardname);
		}
	
		System.out.println("Cut=========================================");
		cut(poker, 27);
		for(int i = 0; i < CARDNUM; i++){
			System.out.println(poker[i].suitname + " " + poker[i].cardname);
		}
		
		System.out.println("Turnover========================================");
		card retrieve = turnover(poker);
		System.out.println(retrieve.suitname + " " + retrieve.cardname);
		for(int i = 0; i < CARDNUM; i++){
			System.out.println(poker[i].suitname + " " + poker[i].cardname);
		}
		
		System.out.println("Search========================================");
		int position = search(poker, 13, "hearts");
		System.out.println("" + position);
		
		System.out.println("Deal======================================");
		retrieve = deal(poker);
		System.out.println("retrieved card: " + retrieve.suitname + " " + retrieve.cardname);
		retrieve = deal(poker);
		System.out.println("retrieved card: " + retrieve.suitname + " " + retrieve.cardname);
		retrieve = deal(poker);
		System.out.println("retrieved card: " + retrieve.suitname + " " + retrieve.cardname);
		for(int i = 0; i < CARDNUM; i++){
			if(poker[i] == null)
				System.out.println("null");
			else
				System.out.println(poker[i].suitname + " " + poker[i].cardname);
		}
		
		System.out.println("NewOrder======================================");
		newOrder(poker);
		for(int i = 0; i < CARDNUM; i++){
			if(poker[i] == null)
				System.out.println("null");
			else
				System.out.println(poker[i].suitname + " " + poker[i].cardname);
		}
		
		System.out.println("Pinochle======================================");
		poker = createCards();
		card[] poker2 = createCards();
		card[] pokerPino = new card[PINONUM];
		for(int i = 0; i < PINONUM; i++){
			pokerPino[i] = new card();
		}
		pinochle(pokerPino, poker, poker2);
		for(int i = 0; i < PINONUM; i++){
			System.out.println(pokerPino[i].suitname + " " + pokerPino[i].cardname);
		}
	}
	
	public static card[] createCards(){
		card[] poker = new card[CARDNUM]; 
		
		for(int suitnum = 0; suitnum < 4; suitnum++){				
			for(int i = 0; i < SUITNUM; i++){
				poker[suitnum * SUITNUM + i] = new card();
				
				poker[suitnum * SUITNUM + i].suit = i;
				poker[suitnum * SUITNUM + i].suitname = new String(suittype[suitnum]);					
				
				poker[suitnum * SUITNUM + i].id = suitnum * SUITNUM + i;
				poker[suitnum * SUITNUM + i].cardname = i + 1;
			}
		}
		return poker;		
	}
	
	public static void shuffle(card[] poker){
		List<card> newPoker = Arrays.asList(poker); 
		Collections.shuffle(newPoker);
		poker = (card[])newPoker.toArray();
	}
	
	public static void cut(card[] poker, int split){
		if (split < 1 || split > CARDNUM){
			System.out.println("Invalid split position");
			return;
		}
		split = split - 1;
		card[] newPoker = new card[CARDNUM];
		for (int i = 0; i < CARDNUM; i++){
			newPoker[i] = new card();
		}
		for(int i = 0; i < CARDNUM; i++){
			if(i < split)
				newPoker[CARDNUM - split + i].cloneCard(poker[i]);		
			else
				newPoker[i - split].cloneCard(poker[i]);
		}
		for(int i = 0; i < CARDNUM; i++){
			poker[i].cloneCard(newPoker[i]);
		}
		return;		
	}
	
	public static card turnover(card[] poker){
		return poker[0];
	}
	
	public static card deal(card[] poker){
		if (removednum == CARDNUM){
			System.out.println("No card in the deck");
			return null;
		}
		removed[removednum].cloneCard(poker[0]);
		removednum++;
		for(int i = 1; i < CARDNUM; i++){
			if(poker[i] != null)
				poker[i - 1].cloneCard(poker[i]);
		}
		poker[CARDNUM - 1] = null;
		return removed[removednum - 1];
	}
	
	public static int search(card[] poker, int cardnum, String suit){
		List<Integer> idlist = new ArrayList<Integer>(CARDNUM);
		for(int i = 0; i < CARDNUM; i++){
			idlist.add(poker[i].id);
		}
		List suitlist = Arrays.asList(suittype);
		int suitindex = suitlist.indexOf(suit);
		int targetid = suitindex * SUITNUM + cardnum - 1;
		int position = idlist.indexOf(targetid);
		return position + 1;
	}
	
	public static void newOrder(card[] poker){
		List<Integer> removedIdList = new ArrayList<Integer>(removednum);
		for(int i = 0; i < removednum; i++){
			removedIdList.add(removed[i].id);
		}
		
		for(int i = 0; i < CARDNUM; i++){
			poker[i] = new card();
			poker[i].suit = i / SUITNUM;
			poker[i].suitname = new String(suittype[poker[i].suit]);
			if(i / SUITNUM < 2){
				poker[i].id = i;				
				poker[i].cardname = i % SUITNUM + 1;
			}
			else if(i / SUITNUM >= 2 && i / SUITNUM < 3){
				poker[i].id = SUITNUM * 2 + SUITNUM - i % SUITNUM - 1;
				poker[i].cardname = SUITNUM - i % SUITNUM;
			}
			else{
				poker[i].id = SUITNUM * 3 + SUITNUM - i % SUITNUM - 1;
				poker[i].cardname = SUITNUM - i % SUITNUM;
			}
		}		
		for(int i = 0; i < CARDNUM; i++){
			if(removedIdList.contains(poker[i].id)){
				for(int j = i; j < CARDNUM - 1; j++){
					if(poker[j + 1] != null)
						poker[j].cloneCard(poker[j + 1]);
				}
			} 
		}
		for(int i = 0; i < removednum; i++){
			poker[CARDNUM - i - 1] = null;
		}
		return;		
	}
	
	public static void pinochle(card[] pino, card[] card1, card[] card2){
		card[] card3 = new card[2 * CARDNUM];	
		int[] singleCounter = new int[6];
		int totalCounter = 0;
		for(int i = 0; i < 2 * CARDNUM; i++){
			card3[i] = new card();
			if (i < CARDNUM)
				card3[i].cloneCard(card1[i]);
			else{
				card3[i].cloneCard(card2[i % CARDNUM]); 
				card3[i].id = i;
			}
		}
		int num;
		for(int i = 0; i < 2 * CARDNUM && totalCounter <= 48; i++){
			num = card3[i].cardname;
			if(num >= 9){
				pino[8 * (num % 9) + singleCounter[num % 9]].cloneCard(card3[i]);
				singleCounter[num % 9]++;
				totalCounter++;
			}
			else if(num == 1){
				pino[8 * ((1 + SUITNUM) % 9) + singleCounter[(1 + SUITNUM) % 9]].cloneCard(card3[i]);
				singleCounter[(1 + SUITNUM) % 9]++;
				totalCounter++;
			}
		}
		return;
	}
}

class card{
	int suit;
	String suitname = new String();
	int cardname;
	int id;
	
	public void cloneCard(card card1){
		this.suit = card1.suit;
		this.id = card1.id;
		this.cardname = card1.cardname;
		this.suitname = new String(card1.suitname);	
	}
}