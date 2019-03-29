package ww_relics.modifiers;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.custom.CustomMod;

public class HarderRunModifiers {

	//...that make the run more challenging.
	public static final String WAIT_NO_REST_BETWEEN_ROUNDS_ID = "ww_relics:WaitNoRestBetweenRounds";
	public static final int WAIT_NO_REST_BETWEEN_ROUNDS_STARTING_MAX_HP_PERCENTAGE = 65;
	
	public static final String FRESH_START_ID = "ww_relics:FreshStart";
	
	public static final String HALF_HEALTH_BAR_ID = "ww_relics:HalfHealthBar";
	public static final int HALF_HEALTH_BAR_STARTING_MAX_HP_PERCENTAGE = 50;
	
	public static final String QUARTER_HEALTH_BAR_ID = "ww_relics:QuarterHealthBar";
	public static final int QUARTER_HEALTH_BAR_STARTING_MAX_HP_PERCENTAGE = 25;
	
	public static final void AddHarderSetModifiers(List<CustomMod> list) {
		CustomMod no_rest_between_rounds = 
				new CustomMod(HarderRunModifiers.WAIT_NO_REST_BETWEEN_ROUNDS_ID, "y", true);
		CustomMod fresh_start = 
				new CustomMod(HarderRunModifiers.FRESH_START_ID, "y", true);
		CustomMod half_health_bar = 
				new CustomMod(HarderRunModifiers.HALF_HEALTH_BAR_ID, "y", true);
		CustomMod quarter_health_bar = 
				new CustomMod(HarderRunModifiers.QUARTER_HEALTH_BAR_ID, "y", true);
		
		list.add(no_rest_between_rounds);
		list.add(fresh_start);
		list.add(half_health_bar);
		list.add(quarter_health_bar);
	}
	
	public static final void AddNoRestBetweenRoundsEffectsToRun() {
		multiplyMaxHPByNewPercentage(HarderRunModifiers.WAIT_NO_REST_BETWEEN_ROUNDS_STARTING_MAX_HP_PERCENTAGE);
	}
	
	public static void multiplyMaxHPByNewPercentage(float new_percentage) {
    	float new_startingMaxHP = 
    			AbstractDungeon.player.startingMaxHP *
    			new_percentage / 100;
        AbstractDungeon.player.startingMaxHP = (int)new_startingMaxHP;
        AbstractDungeon.player.maxHealth = (int)new_startingMaxHP;
        AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
    }
	
	public static final void AddFreshStartEffectsToRun() {
		ArrayList<com.megacrit.cardcrawl.relics.AbstractRelic> player_relics;
		
		player_relics = AbstractDungeon.player.relics;

		
		for (int i = player_relics.size() - 1; i >= 0; i--) {
			if (player_relics.get(i).tier == AbstractRelic.RelicTier.STARTER) {
				AbstractDungeon.player.loseRelic(player_relics.get(i).relicId);
			}
		}
	}
	
	public static final void AddHaltHealthBarEffectsToRun() {
		multiplyMaxHPByNewPercentage(HarderRunModifiers.HALF_HEALTH_BAR_STARTING_MAX_HP_PERCENTAGE);
	}
	
	public static final void AddQuarterHealthBarEffectsToRun() {
		multiplyMaxHPByNewPercentage(HarderRunModifiers.QUARTER_HEALTH_BAR_STARTING_MAX_HP_PERCENTAGE);
	}
	
}
