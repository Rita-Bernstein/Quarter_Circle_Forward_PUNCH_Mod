package ww_relics.patches;

import java.lang.reflect.Field;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class MakeChallengerCoinALimitedPotionPatch {

	@SpirePatch(
			clz = AbstractDungeon.class,
			method = "returnRandomPotion",
			paramtypez = {AbstractPotion.PotionRarity.class,
						  boolean.class}
			)
	public static class ReturnRandomPotionButNotChallengerCoin {
		
		@SpireInsertPatch(
				rloc=8,
				localvars={"temp", "spamCheck"}
			)
		public static void Insert(AbstractDungeon __instance, AbstractPotion param1, boolean param2){	
			try {
				Field ori_temp = __instance.getClass().getDeclaredField("temp");
				AbstractPotion temp = (AbstractPotion) ori_temp.get(__instance);
				if (temp.ID != "WW_Relics:Challenger_Coin") {
					Field spamCheck = __instance.getClass().getDeclaredField("spamCheck");
					spamCheck.set(__instance, false);
			    }
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
