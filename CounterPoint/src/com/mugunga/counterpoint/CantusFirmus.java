package com.mugunga.counterpoint;
import java.io.File;
import java.util.ArrayList;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.pattern.PatternProducer;


public class CantusFirmus extends NoteMelody {
	
	private final  String MIDIdirectory = "MidiFiles/";
	private final MusicUtility mUtility = new MusicUtility();
	//notes in step increment (0 = tonic, 1 = a minor second, 2 =major second, 12 = octave, etc)
	
	private Pattern cantusFirmusPattern;
	public ArrayList<FirstSpecies> firstSpeciesList = new ArrayList<FirstSpecies>();
	private String cfMIDIpattern = "";
	private ArrayList<String> firstSpeciesPatternStrings = new ArrayList<String>();
	public ArrayList<Pattern> firstSpeciesMIDIPatterns = new ArrayList<Pattern>();
	public ArrayList<Pattern> secondSpeciesMIDIPatterns = new ArrayList<Pattern>();
	private static ArrayList<SpeciesBuilder> buildChain = new ArrayList<SpeciesBuilder>();
	
	
	public CantusFirmus (SpeciesBuilder sb, boolean no1S) {
		super(sb.getMelody());
		this.tailorStepIndexes();
		setPattern();
	}

	public void generateSpecies(SpeciesType speciesType) {
		if(null == testChildMelody) {
			log("Generate like normal");
		} else {
			log("Sending 1sTest melody: " + testChildMelody);
		}
		//TODO add something for when 2st species is starting on half note. 
		SpeciesBuilder speciesZero = new SpeciesBuilder(this, speciesType);
		for(int i : speciesZero.getValidNextIndexesRandomized()) {
			SpeciesBuilder childSpecies = new SpeciesBuilder(speciesZero);
			//log("---------Testing child species first note: " + i + "-------");
			if(childSpecies.checkAndSetFirstNote(i)) {
				buildChain.add(childSpecies);
				recursiveMelodySequencer(buildChain);				
			}
		}
	}
	
	private void recursiveMelodySequencer(ArrayList<SpeciesBuilder> buildChain) {
		
		SpeciesBuilder currentSB = buildChain.get(buildChain.size()-1);
		ArrayList<Integer> nextValidIndexes = currentSB.getNextValidIndexArrayRandomized();
		
		for (int i : nextValidIndexes) {
			//log("Current melody: " + currentSB.getNotes().getAll()+ " current testIndex: " + i);
			if (currentSB.testAsNextIndex(i)) {
				SpeciesBuilder newSB = new SpeciesBuilder(currentSB);
				if (newSB.addIntervalAndCheckForCompletion(newSB.nextInterval)) {
					logFirstSpecies(newSB);
						
				} else {
					buildChain.add(newSB);
					recursiveMelodySequencer(buildChain);
				}
			} else {
			}
		}
		buildChain.remove(buildChain.size() - 1);
	}	
	private void logFirstSpecies(SpeciesBuilder newCFB) {
		firstSpeciesList.add(new FirstSpecies(newCFB.getMelody()));
		String patternString = mUtility.getMIDIString(this.getLastFirstSpecies(), getMode(), mUtility.melodyStartIndex);
		firstSpeciesPatternStrings.add(patternString);
		firstSpeciesMIDIPatterns.add(new Pattern(patternString));
	}
	


	private void writeMasterMIDIFile(String prefix) {
		log("Writing Master MIDI!");
		String masterMIDIPattern = cfMIDIpattern + "R ";
		
		for(Pattern p : firstSpeciesMIDIPatterns) {
			masterMIDIPattern += p + "R ";
		}
		
		//System.out.println("masterMIDIPattern:" + masterMIDIPattern);
		Pattern masterPattern = new Pattern(masterMIDIPattern);
		File file = new File(MIDIdirectory + prefix + cantusFirmusPattern.toString() + ".mid" );
		try {
		       MidiFileManager.savePatternToMidi((PatternProducer) masterPattern, file);
		} catch (Exception ex) {
				System.out.println("Could not save midi file");
		        ex.getStackTrace();
		}
	}

	private void setPattern() {
		this.cfMIDIpattern = mUtility.getMIDIString(this, getMode(), mUtility.melodyStartIndex);
		this.cantusFirmusPattern = mUtility.getMIDIPattern(this, getMode(), mUtility.melodyStartIndex);
	}

	public void createMIDIfile(String directory, String filenamePrefix) {
		writeMasterMIDIFile(filenamePrefix);			
	}

	public NoteMelody getLastFirstSpecies() {
		return firstSpeciesList.get(firstSpeciesList.size() - 1);
	}
	
	private void log(String msg) {
		System.out.println("CantusFirmusLog:      " + msg);
	}
	
}
