package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {

		    if(deckRear == null || deckRear.next == null)
			{
		    	throw new NoSuchElementException();
			}
		    
		    boolean jokerB = false, jokerA = false;
		    
		    for (CardNode ptr = deckRear.next;ptr != deckRear; ptr = ptr.next)
		    {
		    	if(deckRear.cardValue == 27)
		    	{
		    		jokerA = true;
		    	}
		    	
		    	if(deckRear.cardValue == 28)
		    	{
		    		jokerB = true;
		    	}
		    	
		    	if (ptr.cardValue == 27)
		    	{
		    		jokerA = true;
		    	}
		    	
		    	if(ptr.cardValue == 28)
		    	{
		    		jokerB = true;
		    	}
		    }
		    
		    if(jokerA == false || jokerB == false)
		    {
		    	throw new NoSuchElementException();
		    }
		    
		    CardNode prev = deckRear;
		
		for (CardNode current = deckRear.next; current != deckRear;current=current.next)
		{
			if (current.cardValue == 27 && current.next == deckRear)
			{
				CardNode temp = current;
				CardNode front = deckRear.next;
				CardNode pointer2 = deckRear;
				prev.next = current.next;
				pointer2.next = temp;
				deckRear = temp;
				temp.next = front;
				return;
				
			}
			else if(current.next.cardValue == 27 && current.next == deckRear)
			{
				CardNode temp = deckRear;
				current.next = deckRear.next;
				deckRear = current.next;
				CardNode temp2 = deckRear.next;
				deckRear.next = temp;
				temp.next = temp2;
				return;
			} 
			else if (current.cardValue == 27)
			{
				CardNode temp = current;
				CardNode temp2 = current.next.next;
				prev.next = current.next;
				prev.next.next = temp;
				temp.next = temp2;
				return;
			}
				prev = prev.next;
			}
		}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {

	    if(deckRear == null)
		{
	    	throw new NoSuchElementException();
		}
	    
		CardNode p2 = deckRear;
		
		for(CardNode current = deckRear.next; current != deckRear; current = current.next)
		{
			if(current.next.cardValue == 28 && current.next == deckRear)
			{
				CardNode temp = deckRear;
				current.next = deckRear.next;
				deckRear = current.next;
				CardNode temp2 = deckRear.next;
				deckRear.next = temp;
				temp.next = temp2;
				CardNode temp3 = temp;
				deckRear.next = temp2;
				CardNode temp4 = temp2.next;
				temp2.next = temp3;
				temp3.next = temp4;
				return;
			}
			else if(current.cardValue == 28 && current.next.next == deckRear)
			{
				CardNode back = deckRear;
				CardNode temp = current;
				CardNode front = deckRear.next;
				p2.next = current.next;
				back.next = temp;
				deckRear = temp;
				temp.next = front;
				return;
				
			}
			else if (current.cardValue == 28 && current.next == deckRear)
			{
				CardNode temp = current;
				p2.next = current.next;
				CardNode temp2 = deckRear.next;
				deckRear.next = temp;
				deckRear = temp;
				deckRear.next = temp2;
				temp = deckRear;
				p2.next.next = deckRear.next;
				deckRear = p2.next.next;
				temp2 = deckRear.next;
				deckRear.next = temp;
				temp.next = temp2;
				return;
			}
			else if (current.cardValue == 28)
			{
				CardNode temp = current;
				CardNode temp2 = current.next.next.next;
				p2.next = current.next;
				p2.next.next.next = temp;
				temp.next = temp2;
				return;
			}
			else
			{
				p2 = p2.next;
			}
		}	
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {

	    if(deckRear == null)
	    	throw new NoSuchElementException();
		
	    
	    CardNode pointer2 = deckRear.next;
	    CardNode current = deckRear.next.next;
		//if there are no cards before the first joker, then the second joker will become the last card in the modified deck
		if(deckRear.next.cardValue == 27 || deckRear.next.cardValue == 28)
		{
			for(current = deckRear.next.next; current != deckRear; current = current.next)
			{
				if (deckRear.cardValue == 27 || deckRear.cardValue == 28)
				{
					return;
				}
				else if (current.cardValue == 27 || current.cardValue == 28)
				{
					CardNode temp = current;
					CardNode temp2 = current.next;
					deckRear = temp;
					deckRear.next = temp2;
					return;
				}
				else 
				{
					pointer2 = pointer2.next;
				}
			}
		}
	
		else if (deckRear.cardValue == 27 || deckRear.cardValue == 28)
		{
			pointer2 = deckRear;
			current = deckRear.next;
			
			for(current = deckRear.next;current != deckRear;current = current.next)
			{
				if(deckRear.next.cardValue == 28 || deckRear.next.cardValue == 27)
				{
					return;
				}
				else if(current.cardValue == 28 || current.cardValue == 27)
				{
					CardNode front = deckRear.next;
					CardNode temp = current;
					CardNode temp2 = pointer2;
					deckRear.next = front;
					deckRear = temp2;
					deckRear.next = temp;
					return;
				}
				else
				{
					pointer2 = pointer2.next;
				}
			}
		}
		else
		{
			
			pointer2 = deckRear;
			current = deckRear.next;
			
			while(current!=deckRear)
			{
				if(current.cardValue == 27 || current.cardValue == 28)
				{
					CardNode firstJoker = current;
					CardNode current2 = current.next;
					while(current2!=deckRear.next)
					{
						if(current2.cardValue == 27 || current2.cardValue == 28)
						{
							CardNode secondJoker = current2;
							CardNode afterSecond = current2.next;
							CardNode head = deckRear.next;
							deckRear.next = firstJoker;
							secondJoker.next = head;
							deckRear = pointer2;
							deckRear.next = afterSecond;
							return;
						}
						else
						{
							current2 = current2.next;
						}
					}
				}
				else
				{
					pointer2 = pointer2.next;
					current = current.next;
				}
			}
		}
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {	
		
	    if(deckRear == null || deckRear.next == null)
	    	throw new NoSuchElementException();
		
		
		int tail;
		if(deckRear.cardValue == 28)
		{
			tail = 27;
		} 
		else 
		{
			tail = deckRear.cardValue;
		}

		CardNode front = deckRear.next;
		CardNode previous = deckRear;
		CardNode current = deckRear.next;
		CardNode middle = new CardNode();
		CardNode endMiddle = new CardNode();
		CardNode endCount = new CardNode();
		
		
		int i = 0;

		while(i<=tail)
		{
			if(i==(tail-1))
			{
				if(current.next==deckRear)
				{
					return;
				}
				
				endCount = current;
				middle = current.next;

				for(CardNode curr2 = current.next; curr2!= deckRear; curr2 = curr2.next)
				{
					if(curr2.next == deckRear)
					{
						endMiddle = curr2;
						endMiddle.next = null;
						curr2.next = front;
						endCount.next = deckRear;
						deckRear.next = middle;
						return;
					}
				}
			}
			current = current.next;
			
			
			
			previous = previous.next;
			i++;
		}
		
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	 private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}
	 
	/**
	 * Implements Step 5 - key = INCLUDING repeating the whole process if the key turns
	 * out to be a joker.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {

		int key = -1;
		int frontValue = deckRear.next.cardValue;

		if(frontValue == 28)
		{
			frontValue = 27;
		}
		
		CardNode current = deckRear.next;
		int i;
		i= 1;
		
		//now loop to count up to frontValue
		while(current != deckRear)
		{
			//if we reached the front node's cardValue, time to get the key which is the next node's cardValue
			if(i==frontValue)
			{
				if(current.next.cardValue == 28 || current.next.cardValue == 27)
				{
					jokerA();
					jokerB();
					tripleCut();
					countCut();
					current=deckRear;
					i=0;
					
					frontValue = deckRear.next.cardValue;

					if(frontValue == 28)
					{
						frontValue = 27;
					}
				}
				else
				{
					key = current.next.cardValue;
					return key;
				}
			}
			current = current.next;
			i++;
		}
		return key;
	}
	
	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
		String keystream = "";
		
		String MSG = message.replaceAll("[^a-zA-Z]","");

		for(int i = 0;i < MSG.length();i++)
		{
			char letter = Character.toUpperCase(MSG.charAt(i));
			System.out.println(letter);
			int Int = letter-'A'+1;	
			jokerA();
			jokerB();
			tripleCut();
			countCut();

			int key = getKey();
			int sum = Int + key;
			if(sum > 26)
			
				sum = sum - 26;
			
			
			letter = (char)(sum-1+'A');
			System.out.println(letter);
			keystream += letter;
		}
		
	    return keystream;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		String keystream = "";
		
		String MSG = message.replaceAll("[^a-zA-Z]","");

		for(int i = 0;i < MSG.length();i++)
		{
			char letter = Character.toUpperCase(MSG.charAt(i));
			int Int = letter-'A'+1;
			
			jokerA();
			jokerB();
			tripleCut();
			countCut();

			int key = getKey();
			int sum = Int - key;

			if(sum <= 0)
				sum += 26;
			
			
			letter = (char)(sum-1+'A');
			keystream += letter;
		}
	    return keystream;
	}
}