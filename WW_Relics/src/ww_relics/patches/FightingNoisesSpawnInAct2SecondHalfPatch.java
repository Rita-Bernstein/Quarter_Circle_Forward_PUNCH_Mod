package ww_relics.patches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;

import ww_relics.events.act2.FightingNoisesEvent;

public class FightingNoisesSpawnInAct2SecondHalfPatch {

	public static final Logger logger =
			LogManager.getLogger(FightingNoisesSpawnInAct2SecondHalfPatch.class.getName());
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "getEvent")
	public static class GetEventPatch {

	    @SpirePostfixPatch
	    public static AbstractEvent FightingNoisesSpawnInAct2SecondHalf(
	        AbstractEvent _retVal,
	        com.megacrit.cardcrawl.random.Random rng) {
			    if ((_retVal instanceof FightingNoisesEvent) &&
			    	(AbstractDungeon.currMapNode.y > AbstractDungeon.map.size() / 2)) {
			    	logger.info("ASASDASFAF Here");
			    	return _retVal;
			    	
			    }
			    		
			    else {
			    	return AbstractDungeon.getEvent(AbstractDungeon.eventRng);
			    }
	    
	    	}

	}
}
