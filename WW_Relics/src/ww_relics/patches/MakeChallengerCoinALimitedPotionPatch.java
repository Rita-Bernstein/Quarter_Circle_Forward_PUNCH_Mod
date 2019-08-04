package ww_relics.patches;

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
	public static class ReturnRandomPotion {
		
		public static AbstractPotion Postfix(AbstractPotion __result, AbstractPotion.PotionRarity rarity,
												 boolean limited){	
			if (!limited) return __result;
			else if (__result.ID != "WW_Relics:Challenger_Coin") return __result;
			else return AbstractDungeon.returnRandomPotion(rarity, limited);

		}
	}
	
}
