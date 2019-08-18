package ww_relics.modifiers;

import java.util.*;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.custom.CustomMod;

public class HarderRunModifiers {

	public static final String FRESH_START_ID = "WW_Relics:FreshStart";
	
	public static final String HALF_HEALTH_BAR_ID = "WW_Relics:HalfHealthBar";
	public static final int HALF_HEALTH_BAR_STARTING_MAX_HP_PERCENTAGE = 50;
	
	public static final String QUARTER_HEALTH_BAR_ID = "WW_Relics:QuarterHealthBar";
	public static final int QUARTER_HEALTH_BAR_STARTING_MAX_HP_PERCENTAGE = 25;
	
	public static final void AddHarderSetModifiers(List<CustomMod> list) {
		CustomMod fresh_start = 
				new CustomMod(HarderRunModifiers.FRESH_START_ID, "y", true);
		CustomMod half_health_bar = 
				new CustomMod(HarderRunModifiers.HALF_HEALTH_BAR_ID, "y", true);
		CustomMod quarter_health_bar = 
				new CustomMod(HarderRunModifiers.QUARTER_HEALTH_BAR_ID, "y", true);
		
		list.add(fresh_start);
		list.add(half_health_bar);
		list.add(quarter_health_bar);
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
