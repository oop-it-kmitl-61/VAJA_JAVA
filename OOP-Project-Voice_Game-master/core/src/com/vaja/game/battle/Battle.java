package com.vaja.game.battle;

import java.io.IOException;

import java.net.URL;
import java.util.Random;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.vaja.game.battle.animation.FaintingAnimation;
import com.vaja.game.battle.animation.StartBattleAnimation;
import com.vaja.game.battle.event.*;
import com.vaja.game.battle.move.Move;
import com.vaja.game.model.Monster;
import com.vaja.screen.GameScreen;
import com.vaja.voice.*;

/**
 * i used template pokemon fight
 * haha it look better than any template
 * @author khingbmc
 */
public class Battle implements BattleEventQueuer {




    public enum STATE {
        READY_TO_PROGRESS,
        SELECT_NEW_POKEMON,
        RAN,
        WIN,
        LOSE,
        ;
    }

    @Override
    public void queueEvent(BattleEvent event) {
        this.eventPlayer.queueEvent(event);
    }
    
    GameScreen loadGame;

    private STATE state;
    
    private Random rand;

    private BattleMechanics mechanics;

    private Monster player;
    private Monster opponent;

    private Trainer playerTrainer;
    private Trainer opponentTrainer;
    
    

	private String[] tackle = {"dog", "ant", "box", "school", "bank"}
	, watergun = {"night", "study", "flower", "gun", "clean"}, 
			dragon = {"cloud", "movie", "camera", "pink", "cry"}
	, scratch = {"hotel", "knowledge", "zoo", "danger", "dance"};
	
	private int index;
	private String[] question_t = {"It’s a human loyalty friend, It can bark sound like woof woof."
			, "It’s a small bug when it bites we get hurt a little bit."
			, "It has a square shape we can put things in it."
			, "Students have to go every day and it has a flagstaff."
			, "If you want to deposit money. Where would you go?"};
	
	private String[] question_w = {"How do you call the time when it’s dark?"
			, "We received knowledge from the teacher when we … ?"
			, "When you’re on the date, what do boys like to give you?\n"
			, "When people shoot, what do they use?"
			, "If the room is dirty, what should we do?"};
	
	private String[] question_d = {"You look into the sky in the daytime and what do you see?"
			,"We went to the cinema to see what?"
			,"What do you use to take a picture?"
			,"What is the color that girls like?"
			,"When you feel sad, what’s your reaction?"};
	
	private String[] question_s = {"When you travel, you will stay at ….. ."
			,"We read books so we will have .... ?"
			,"Where do they have lots of animals?"
			,"Walking alone in a dark alley. How do you feel?"
			,"When you enjoy with music, What do you want to do?"
	};

    private BattleEventPlayer eventPlayer;

    public Battle(Trainer player, Monster opponent) {
        this.playerTrainer = player;
        this.player = player.getPokemon(0);
        this.opponent = opponent;
        mechanics = new BattleMechanics();
        this.state = STATE.READY_TO_PROGRESS;
        this.rand = new Random();
   
        
    }

    /**
     * Plays appropiate animation for starting a battle
     */
    public void beginBattle() {
        queueEvent(new MonsterSpriteEvent(opponent.getSprite(), BATTLE_PARTY.OPPONENT));
        queueEvent(new TextEvent("I'm "+player.getName()+"!!!", 1f));
        queueEvent(new MonsterSpriteEvent(player.getSprite(), BATTLE_PARTY.PLAYER));
        queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new StartBattleAnimation()));
    }

    public void setState(STATE state) {
        this.state = state;
    }
    
   
    

    /**
     * Progress the battle one turn.
     * @param input		Index of the move used by the player
     */
    public void progress(int input) {
        if (state != STATE.READY_TO_PROGRESS) {
            return;
        }
        if (mechanics.goesFirst(player, opponent)) {
        	
            playTurn(BATTLE_PARTY.PLAYER, input);
            
            if (state == STATE.READY_TO_PROGRESS) {
            	
                playTurn(BATTLE_PARTY.OPPONENT, 0);
            }
        } else {
            playTurn(BATTLE_PARTY.OPPONENT, 0);
            if (state == STATE.READY_TO_PROGRESS) {
            	
                playTurn(BATTLE_PARTY.PLAYER, input);
     
            }
        }
        /*
         * XXX: Status effects go here.
         */
    }

    /**
     * Sends out a new Pokemon, in the case that the old one fainted.
     * This will NOT take up a turn.
     * @param monster	Pokemon the trainer is sending in
     */
    public void chooseMons(Monster monster) {
        this.player = monster;
        queueEvent(new HPAnimationEvent(
                BATTLE_PARTY.PLAYER,
                monster.getCurrentHitpoints(),
                monster.getCurrentHitpoints(),
                monster.getStat(STAT.HITPOINTS),
                0f));
        queueEvent(new MonsterSpriteEvent(monster.getSprite(), BATTLE_PARTY.PLAYER));
        queueEvent(new NameChangeEvent(monster.getName(), BATTLE_PARTY.PLAYER));
        queueEvent(new TextEvent(" "+monster.getName()+" สุดเท่ไงล่ะ!"));
        queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new StartBattleAnimation()));
        this.state = STATE.READY_TO_PROGRESS;
    }

    /**
     * Attempts to run away
     */
    public void attemptRun() {
        queueEvent(new TextEvent("Got away successfully...", true));
        this.state = STATE.RAN;
    }

    private void playTurn(BATTLE_PARTY user, int input) {
    	int num = 0, check = 0;

        BATTLE_PARTY target = BATTLE_PARTY.getOpposite(user);

        Monster battleUser = null;
        Monster monsTarget = null;
        if (user == BATTLE_PARTY.PLAYER) {
            battleUser = player;
            monsTarget = opponent;
            
        } else if (user == BATTLE_PARTY.OPPONENT) {
            battleUser = opponent;
            monsTarget = player;
        }
        
        Move move = battleUser.getMove(input);

        /* Broadcast the text graphics */
       
        //        mechanics.attemptHit(move, battleUser, monsTarget)
        
        if (battleUser.getName().equals("Brian")) {

            /* Broadcast the text graphics */
        	if(move.getName().toUpperCase().equals("TACKLE")) {
        		
        			
        				queueEvent(new TextEvent(battleUser.getName()+" used\n"+move.getName().toUpperCase()+"!", 0.5f));
                		this.index = rand.nextInt(5);
                		
                		
                		JOptionPane.showMessageDialog(null, "Question:"+this.question_t[index]);
                		JOptionPane.showMessageDialog(null, "Choice:"+" dog | ant | box | school | bank ");
                		check++;
                		
        			
        		
        		if(check == 1) {
        			Recognizer recog = new Recognizer();
        			queueEvent(new TextEvent("This is Your answer:"+recog.getWord(), 0.8f));
        			queueEvent(new TextEvent("This is True answer:"+tackle[index], 0.8f));
            		if(recog.getWord().equals(tackle[index])) num = 1;
            		else num = -1;
        		}
        	}
        	
        	if(move.getName().equals("Fus Ro Dah!")) {
        		
    			
				queueEvent(new TextEvent(battleUser.getName()+" used\n"+move.getName().toUpperCase()+"!", 0.5f));
        		this.index = rand.nextInt(5);
        		
        		
        		JOptionPane.showMessageDialog(null, "Question:"+this.question_w[index]);
        		JOptionPane.showMessageDialog(null, "Choice:"+" night | study | flower | gun | clean");
        		check++;
        		
			
		
		if(check == 1) {
			Recognizer recog = new Recognizer();
			queueEvent(new TextEvent("This is Your answer:"+recog.getWord(), 0.8f));
			queueEvent(new TextEvent("This is True answer:"+watergun[index], 0.8f));
    		if(recog.getWord().equals(watergun[index])) num = 1;
    		else num = -1;
		}
	}
        if(move.getName().equals("Ha do Ken!")) {
        		
    			
				queueEvent(new TextEvent(battleUser.getName()+" used\n"+move.getName().toUpperCase()+"!", 0.5f));
        		this.index = rand.nextInt(5);
        		
        		
        		JOptionPane.showMessageDialog(null, "Question:"+this.question_d[index]);
        		JOptionPane.showMessageDialog(null, "Choice:"+" cloud | movie | camera | pink | cry");
        		check++;
        		
			
		
		if(check == 1) {
			Recognizer recog = new Recognizer();
			queueEvent(new TextEvent("This is Your answer:"+recog.getWord(), 0.8f));
			queueEvent(new TextEvent("This is True answer:"+dragon[index], 0.8f));
    		if(recog.getWord().equals(dragon[index])) num = 1;
    		else num = -1;
		}
	}
        if(move.getName().equals("Khame Khame Ha!")) {
    		
			
			queueEvent(new TextEvent(battleUser.getName()+" used\n"+move.getName().toUpperCase()+"!", 0.5f));
    		this.index = rand.nextInt(5);
    		
    		
    		JOptionPane.showMessageDialog(null, "Question:"+this.question_s[index]);
    		JOptionPane.showMessageDialog(null, "Choice:"+" hotel | knowledge | zoo | danger | dance");
    		check++;
    		
		
	
	if(check == 1) {
		Recognizer recog = new Recognizer();
		queueEvent(new TextEvent("This is Your answer:"+recog.getWord(), 0.8f));
		queueEvent(new TextEvent("This is True answer:"+scratch[index], 0.8f));
		if(recog.getWord().equals(scratch[index])) num = 1;
		else num = -1;
	}
}
            if (num == 1) {
                move.useMove(mechanics, battleUser, monsTarget, user, this);
            } else { // miss
                /* Broadcast the text graphics */
                queueEvent(new TextEvent(battleUser.getName()+"'s\nattack missed!", 0.5f));
            }
//        	
//        	queueEvent(new TextEvent("You Said:", 0.5f));
//        	recognizer();
//            if(getResultText().equals("dog") || getResultText().equals("ant")) {
//            	num++;
//            	move.useMove(mechanics, battleUser, monsTarget, user, this);
//            }else { // miss
//                /* Broadcast the text graphics */
//                queueEvent(new TextEvent(battleUser.getName()+"'s\nattack missed!", 0.5f));
//            }
            
        } else {
        	if(mechanics.attemptHit(move, battleUser, monsTarget)) {
            	move.useMove(mechanics, battleUser, monsTarget, user, this);
            }else { // miss
                /* Broadcast the text graphics */
                queueEvent(new TextEvent(battleUser.getName()+"'s\nattack missed!", 0.5f));
            }
        }

        if (player.isFainted()) {
            queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new FaintingAnimation()));
            boolean anyoneAlive = false;
            for (int i = 0; i < getPlayerTrainer().getTeamSize(); i++) {
                if (!getPlayerTrainer().getPokemon(i).isFainted()) {
                    anyoneAlive = true;
                    break;
                }
            }
            if (!anyoneAlive) {
            	queueEvent(new TextEvent("You're Die ", true));
                this.state = STATE.LOSE;
                System.out.println(this.state);
//                loadGame.setState("Con/tinue");
            } 
        } else if (opponent.isFainted()) {
            queueEvent(new AnimationBattleEvent(BATTLE_PARTY.OPPONENT, new FaintingAnimation()));
            queueEvent(new TextEvent("Congratulations! You Win!", true));
            this.state = STATE.WIN;
            this.player.setLevel((int) (this.player.getLevel()+this.opponent.getLevel()));
        }
    }

  
    public Monster getPlayerPokemon() {
        return player;
    }

    public Monster getOpponentPokemon() {
        return opponent;
    }

    public Trainer getPlayerTrainer() {
        return playerTrainer;
    }

    public Trainer getOpponentTrainer() {
        return opponentTrainer;
    }

    public STATE getState() {
        return state;
    }

    public void setEventPlayer(BattleEventPlayer player) {
        this.eventPlayer = player;
    }


}