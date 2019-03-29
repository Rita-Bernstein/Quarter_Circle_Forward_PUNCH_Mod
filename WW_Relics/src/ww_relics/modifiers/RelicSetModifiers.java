package ww_relics.modifiers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.unlock.UnlockTracker;

import ww_relics.relics.chun_li.Handcuffs;
import ww_relics.relics.chun_li.SpikyBracers;
import ww_relics.relics.chun_li.WhiteBoots;
import ww_relics.relics.ryu.DuffelBag;
import ww_relics.relics.ryu.FightingGloves;
import ww_relics.relics.ryu.RedHeadband;

public class RelicSetModifiers {

	//Yes, I know this can be refactored to be a better class, I will do it, bear with me a while.
	
	public static final String WANDERING_WARRIOR_ID = "ww_relics:WanderingWarrior";
	
	public static final String BLUE_JADE_ID = "ww_relics:BlueJade";
	
	public static void AddWanderingWarriorRelicsToCustomRun(ArrayList<String> relics) {
        relics.add(DuffelBag.ID);
        UnlockTracker.markRelicAsSeen(DuffelBag.ID);
        relics.add(FightingGloves.ID);
        UnlockTracker.markRelicAsSeen(FightingGloves.ID);
        relics.add(RedHeadband.ID);
        UnlockTracker.markRelicAsSeen(RedHeadband.ID);
	}
	
	public static void AddBlueJadeRelicsToCustomRun(ArrayList<String> relics) {
        relics.add(SpikyBracers.ID);
        UnlockTracker.markRelicAsSeen(SpikyBracers.ID);
        relics.add(WhiteBoots.ID);
        UnlockTracker.markRelicAsSeen(WhiteBoots.ID);
        relics.add(Handcuffs.ID);
        UnlockTracker.markRelicAsSeen(Handcuffs.ID);
	}
	
	
}
