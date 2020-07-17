package com.mugunga.counterpoint;
import java.util.ArrayList;


public class NoteIndexCollection extends MusicIntCollection{
	
	public NoteIndexCollection() {
		
	}
	
	//This constructor deliberately does not use the add() method of parent class because it will trigger a bunch of uneeded logic
	public NoteIndexCollection(ArrayList<Integer> notes) {
		for(int i : notes) {
			items.add(i);
		}
	}


 }
