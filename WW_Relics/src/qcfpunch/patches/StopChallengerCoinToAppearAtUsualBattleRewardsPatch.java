package qcfpunch.patches;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import qcfpunch.potions.ChallengerCoin;

@SpirePatch(clz= AbstractRoom.class, method = "addPotionToRewards",
			paramtypez = {})
public class StopChallengerCoinToAppearAtUsualBattleRewardsPatch {

	/*public static final Logger logger =
			LogManager.getLogger(StopChallengerCoinToAppearAtUsualBattleRewardsPatch.class.getName());*/
	
	@SpireInsertPatch(rloc=30)
	public static void Insert(AbstractRoom __instance) {
		while(__instance.rewards.get(__instance.rewards.size() - 1).potion.ID ==
				ChallengerCoin.ID) {
			__instance.rewards.remove(__instance.rewards.size() - 1);
			__instance.rewards.add(new RewardItem(AbstractDungeon.returnRandomPotion()));
		}
	}
}
