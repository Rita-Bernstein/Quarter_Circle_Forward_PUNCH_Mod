package qcfpunch.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;

import qcfpunch.events.act2.FightingNoisesEvent;

public class FightingNoisesSpawnInAct2SecondHalfPatch {
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "getEvent")
	public static class GetEventPatch {

	    @SpirePostfixPatch
	    public static AbstractEvent FightingNoisesSpawnInAct2SecondHalf(
	        AbstractEvent _retVal,
	        com.megacrit.cardcrawl.random.Random rng) {
	    		if ((_retVal instanceof FightingNoisesEvent) &&
	    				(AbstractDungeon.currMapNode.y > AbstractDungeon.map.size() / 2)) {
	    			return _retVal;
	    		} else if (_retVal instanceof FightingNoisesEvent) {
	    				return AbstractDungeon.getEvent(AbstractDungeon.eventRng);
	    		} else return _retVal;
	    	}

	}
}
