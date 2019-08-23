package ww_relics.modifiers;

import java.util.*;

import com.megacrit.cardcrawl.screens.custom.CustomMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import ww_relics.QCFPunch_MiscCode;
import ww_relics.relics.chun_li.*;
import ww_relics.relics.guile.*;
import ww_relics.relics.ken.BlackTrainingShirt;
import ww_relics.relics.ken.RedGi;
import ww_relics.relics.ken.UnceasingFlame;
import ww_relics.relics.ryu.*;

public class RelicSetModifiers {

	//Yes, I know this can be refactored to be a better class, I will do it, bear with me a while.
	
	public static final String WANDERING_WARRIOR_ID = QCFPunch_MiscCode.returnPrefix() + "WanderingWarrior";
	public static final String BLUE_JADE_ID = QCFPunch_MiscCode.returnPrefix() + "BlueJade";
	public static final String INDESTRUCTIBLE_FORTRESS_ID = QCFPunch_MiscCode.returnPrefix() + "IndestructibleFortress";
	public static final String BLAZING_FIST_ID = QCFPunch_MiscCode.returnPrefix() + "BlazingFist";
	
	public static void addRelicSetModifiers(List<CustomMod> list) {
		CustomMod wandering_warrior = new CustomMod(RelicSetModifiers.WANDERING_WARRIOR_ID, "y", true);
		CustomMod blue_jade = new CustomMod(RelicSetModifiers.BLUE_JADE_ID, "y", true);
		CustomMod indestructible_fortress = new CustomMod(RelicSetModifiers.INDESTRUCTIBLE_FORTRESS_ID, "y", true);
		CustomMod blazing_fist = new CustomMod(RelicSetModifiers.BLAZING_FIST_ID, "y", true);
		
		list.add(wandering_warrior);
		list.add(blue_jade);
		list.add(indestructible_fortress);
		list.add(blazing_fist);
	}
	
	public static void addWanderingWarriorRelicsToCustomRun(ArrayList<String> relics) {
		addRelicToCustomRunRelicList(DuffelBag.ID, relics);
		addRelicToCustomRunRelicList(FightingGloves.ID, relics);
		addRelicToCustomRunRelicList(RedHeadband.ID, relics);
	}
	
	public static void addBlueJadeRelicsToCustomRun(ArrayList<String> relics) {
		addRelicToCustomRunRelicList(SpikyBracers.ID, relics);
		addRelicToCustomRunRelicList(WhiteBoots.ID, relics);
		addRelicToCustomRunRelicList(Handcuffs.ID, relics);
	}
	
	public static void addIndestructibleFortressToCustomRun(ArrayList<String> relics) {
		addRelicToCustomRunRelicList(ArmyBoots.ID, relics);
		addRelicToCustomRunRelicList(ChainWithNametags.ID, relics);
		addRelicToCustomRunRelicList(CombatFatigues.ID, relics);
	}
	
	public static void addBlazingFistToCustomRun(ArrayList<String> relics) {
		addRelicToCustomRunRelicList(BlackTrainingShirt.ID, relics);
		addRelicToCustomRunRelicList(RedGi.ID, relics);
		addRelicToCustomRunRelicList(UnceasingFlame.ID, relics);
	}
	
	public static void addRelicToCustomRunRelicList(String ID, ArrayList<String> relics) {
		relics.add(ID);
		UnlockTracker.markRelicAsSeen(ID);
	}
	
	
}
