package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.mugunga.counterpoint.*;


class TestSingleCounterpointMelodies {
	
	FuxianCounterPointSingleMelodyTest cpt;
	
	/*
	 * TODO Should be able to run 1 cantus firmis and generate 1s, not continue trying 
	 * to create more cantus firmus.
	 * 
	 * TODO when 1S and CF start on a unison, they should not progress to an octave because 
	 * there is no melodic movement
	 */
	@Test
	void unisonOctaveViolationTest() {
		FuxianCounterPointSingleMelodyTest cpt = new FuxianCounterPointSingleMelodyTest();
		
		int[] testCFMelody =   {0, 5, 4, 3, 4, 8, 9, 7, 6, 2, 1, 0}; 
		
		cpt.setTestCantusFirmus(testCFMelody);
		//int[] test1SMelody  =  {0,-2, -5, -4, 2, 1, 0, -2, -3, -2, -1, 0}; 
		//cpt.setTestFirstSpecies(test1SMelody);
		cpt.setMode(Mode.LOCRIAN);
		cpt.testMelody();
		assertTrue(cpt.validCantusFirmus());
		assertTrue(cpt.validFirstSpecies());
	}
	
	//TODO should not have 3 steps of parallel motion
	@Test
	void parallelMotionViolationTest() {
		FuxianCounterPointSingleMelodyTest cpt = new FuxianCounterPointSingleMelodyTest();
		
		int[] testCFMelody =   {0, -2, 0, 1, 2, 4, 3, 2, 1, 2, 3, 1, 0}; //Andrew's cantus firmus, gets melodic minor
		
		cpt.setTestCantusFirmus(testCFMelody);
		int[] test1SMelody  =  {4, 3, 2, 3, 7, 6, 8, 7, 3, 4, 5, 6, 7};  //also need to raise leading tone in final bar.
		cpt.setTestFirstSpecies(test1SMelody);
		cpt.setMode(Mode.PHYRGIAN);
		cpt.testMelody();
		assertTrue(cpt.validCantusFirmus());
		assertTrue(cpt.validFirstSpecies());
	}
	
	//TODO Melody shouldn't jump down a third and right back up to the tonic
	@Test
	void repeatThirdPatternTest() {
		FuxianCounterPointSingleMelodyTest cpt = new FuxianCounterPointSingleMelodyTest();
		
		int[] testCFMelody =   {0, -2, 0, 1, -2, -3, -4, -5, 2, 1, 0}; 
		cpt.setTestCantusFirmus(testCFMelody);
		cpt.setMode(Mode.PHYRGIAN);
		cpt.testMelody();
		assertTrue(cpt.validCantusFirmus());
	}
	
	//TODO This melody needs to fail because the pentultimate note is not the leading tone
	@Test
	void phyrgianIllegalEndWithLeapTest() {
		FuxianCounterPointSingleMelodyTest cpt = new FuxianCounterPointSingleMelodyTest();
		
		int[] testCFMelody =   {0, 3, 5, 4, 3, 1, 0, -1, -2, 0}; 
		cpt.setTestCantusFirmus(testCFMelody);
		cpt.setMode(Mode.PHYRGIAN);
		cpt.testMelody();
		assertTrue(cpt.validCantusFirmus());
	}
	
	@Test
	void andrewStevensonsAeolianMelodicTest() {
		FuxianCounterPointSingleMelodyTest cpt = new FuxianCounterPointSingleMelodyTest();
		
		int[] testCFMelody =   {0, 2, 1, 3, 2, 4, 5, 4, 3, 1, 0 }; //Andrew's cantus firmus, gets melodic minor
		cpt.setTestCantusFirmus(testCFMelody);
		int[] test1SMelody  =  {0, 4, 6, 5, 6, 9, 7, 6, 5, 6, 7};  //also need to raise leading tone in final bar.
		cpt.setTestFirstSpecies(test1SMelody);
		cpt.setMode(Mode.AEOLIAN);
		cpt.testMelody();
		assertTrue(cpt.validCantusFirmus());
		assertTrue(cpt.validFirstSpecies());
	}
	
	@Test
	void ionianClimaxBugTest() {
		FuxianCounterPointSingleMelodyTest cpt = new FuxianCounterPointSingleMelodyTest();
		
		int[] testCFMelody =   {0, 2, 1, 3, 2, 4, 5, 4, 3, 2, 1, 0 }; 
		cpt.setTestCantusFirmus(testCFMelody);
		int[] test1SMelody  =  {7, 6, 8, 7, 9, 8, 7, 6, 5, 6, 6,  7,  };  
		cpt.setTestFirstSpecies(test1SMelody);
		cpt.setMode(Mode.IONIAN);
		cpt.testMelody();
		assertTrue(cpt.validCantusFirmus());
		assertTrue(cpt.validFirstSpecies());
	}
	
	@Test
	void leapCheckTest() {
		FuxianCounterPointSingleMelodyTest cpt = new FuxianCounterPointSingleMelodyTest();
		
		int[] testCFMelody =   {0, -2, -5, -4, 0, -1, 2, 0, -1, 1, 0}; 
		cpt.setTestCantusFirmus(testCFMelody);
		cpt.setMode(Mode.IONIAN);
		cpt.testMelody();
		assertTrue(cpt.validCantusFirmus());
	}
	
	@Test
	void lydianVanillaTest() {
		FuxianCounterPointSingleMelodyTest cpt = new FuxianCounterPointSingleMelodyTest();
		
		int[] testCFMelody =   {0, -2, 1, 2, -1, 0, 2, 1, 0}; 
		cpt.setTestCantusFirmus(testCFMelody);
		cpt.setMode(Mode.LYDIAN);
		cpt.testMelody();
		assertTrue(cpt.validCantusFirmus());
	}
	
	/*
	 * Need to add a fail code to the Melody Test so we can verify that it failed for the reason we thought it would
	 */
	@Test
	void aeolianVoiceCrossingTest() {
		FuxianCounterPointSingleMelodyTest cpt = new FuxianCounterPointSingleMelodyTest();
		
		int[] testCFMelody =   {0, 5, 4, 1,  0, 3,  4,  5,  -2, -1, 0, 1, 0}; 
		cpt.setTestCantusFirmus(testCFMelody);
		int[] test1SMelody  =  {4, 7, 8, 10, 9, 12, 11, 10, 7, 6, 5, 6, 7 };  
		cpt.setTestFirstSpecies(test1SMelody);
		cpt.setMode(Mode.AEOLIAN);
		cpt.testMelody();
		assertTrue(cpt.validCantusFirmus());
		assertTrue(cpt.validFirstSpecies());
		//TODO assertEquals(cpy.failCode,FailCode.ILLEGAL_VOICE_CROSS), etc
	}
	
	@Test
	void weirdAeolianTest() {
		FuxianCounterPointSingleMelodyTest cpt = new FuxianCounterPointSingleMelodyTest();
		
		int[] testCFMelody =   {0, -2, 0, 1, 4, 3, 2, 1, 2, 3, 1, 0}; 
		cpt.setTestCantusFirmus(testCFMelody);
		int[] test1SMelody  =  {4, 5, 5, 10, 9, 8, 11, 10, 9, 5, 6, 7 };  
		cpt.setTestFirstSpecies(test1SMelody);
		cpt.setMode(Mode.AEOLIAN);
		cpt.testMelody();
		assertTrue(cpt.validCantusFirmus());
		assertTrue(cpt.validFirstSpecies());
		//TODO assertEquals(cpy.failCode,FailCode.ILLEGAL_VOICE_CROSS), etc
	}
	
	

}
