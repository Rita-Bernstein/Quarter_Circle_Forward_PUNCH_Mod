package ww_relics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import ww_relics.potions.ChallengerCoin;

@SpirePatch(clz= AbstractRoom.class, method = "addPotionToRewards",
			paramtypez = {})
public class StopChallengerCoinToAppearAtUsualBattleRewardsPatch {

	@SpireInsertPatch(rloc=25)
	public static void Insert(AbstractRoom __instance) {
		while(__instance.rewards.get(__instance.rewards.size() - 1).potion.ID ==
				ChallengerCoin.ID) {
			__instance.rewards.remove(__instance.rewards.size() - 1);
			__instance.rewards.add(new RewardItem(AbstractDungeon.returnRandomPotion()));
		}
	}
}
