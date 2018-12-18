package com.vaja.game.battle.event;
import com.vaja.voice.*;

public class VoiceEvent extends BattleEvent{
	private boolean finished = false;
	
	private Recognizer recognize;
	private String word;
	
	
	
	@Override
	public void begin(BattleEventPlayer player) {
		
		super.begin(player);
		recognize = new Recognizer();
		word = recognize.getWord();
	}

	public String getWord() {
		return word;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean finished() {
		// TODO Auto-generated method stub
		return finished;
	}

	

}
