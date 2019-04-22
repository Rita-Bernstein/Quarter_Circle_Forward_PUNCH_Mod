package ww_relics.modifiers;

import java.util.*;

import com.megacrit.cardcrawl.screens.custom.CustomMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import ww_relics.relics.chun_li.*;
import ww_relics.relics.guile.*;
import ww_relics.relics.ryu.*;

public class RelicSetModifiers {

	//Yes, I know this can be refactored to be a better class, I will do it, bear with me a while.
	
	public static final String WANDERING_WARRIOR_ID = "ww_relics:WanderingWarrior";
	
	public static final String BLUE_JADE_ID = "ww_relics:BlueJade";
	
	public static final String INDESTRUCTIBLE_FORTRESS_ID = "ww_relics:IndestructibleFortress";
	
	public static void AddRelicSetModifiers(List<CustomMod> list) {
		CustomMod wandering_warrior = new CustomMod(RelicSetModifiers.WANDERING_WARRIOR_ID, "y", true);
		CustomMod blue_jade = new CustomMod(RelicSetModifiers.BLUE_JADE_ID, "y", true);
		CustomMod indestructible_fortress = new CustomMod(RelicSetModifiers.INDESTRUCTIBLE_FORTRESS_ID, "y", true);
		
		list.add(wandering_warrior);
		list.add(blue_jade);
		list.add(indestructible_fortress);
	}
	
	public static void AddWanderingWarriorRelicsToCustomRun(ArrayList<String> relics) {
		AddRelicToCustomRun(relics, DuffelBag.ID);
		AddRelicToCustomRun(relics, FightingGloves.ID);
		AddRelicToCustomRun(relics, RedHeadband.ID);
	}
	
	public static void AddBlueJadeRelicsToCustomRun(ArrayList<String> relics) {
        relics.add(SpikyBracers.ID);
        UnlockTracker.markRelicAsSeen(SpikyBracers.ID);
        relics.add(WhiteBoots.ID);
        UnlockTracker.markRelicAsSeen(WhiteBoots.ID);
        relics.add(Handcuffs.ID);
        UnlockTracker.markRelicAsSeen(Handcuffs.ID);
	}
	
	public static void AddIndestructibleFortressToCustomRun(ArrayList<String> relics) {
		relics.add(ArmyBoots.ID);
		UnlockTracker.markRelicAsSeen(ArmyBoots.ID);
		relics.add(ChainWithNametags.ID);
		UnlockTracker.markRelicAsSeen(ChainWithNametags.ID);
		relics.add(CombatFatigues.ID);
		UnlockTracker.markRelicAsSeen(CombatFatigues.ID);
	}
	
	public static void AddRelicToCustomRun(ArrayList<String> relics, String ID) {
		relics.add(ID);
		UnlockTracker.markRelicAsSeen(ID);
	}
	
	
}
