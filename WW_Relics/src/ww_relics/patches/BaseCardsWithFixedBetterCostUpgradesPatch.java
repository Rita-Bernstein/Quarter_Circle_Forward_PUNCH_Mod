package ww_relics.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.CreativeAI;
import com.megacrit.cardcrawl.cards.blue.Fusion;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.cards.colorless.Magnetism;
import com.megacrit.cardcrawl.cards.colorless.Mayhem;
import com.megacrit.cardcrawl.cards.green.BulletTime;
import com.megacrit.cardcrawl.cards.green.Nightmare;
import com.megacrit.cardcrawl.cards.green.PhantasmalKiller;
import com.megacrit.cardcrawl.cards.red.Barricade;
import com.megacrit.cardcrawl.cards.red.Corruption;
import com.megacrit.cardcrawl.cards.red.DarkEmbrace;
import com.megacrit.cardcrawl.cards.red.Entrench;
import com.megacrit.cardcrawl.helpers.FontHelper;

public class BaseCardsWithFixedBetterCostUpgradesPatch {
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.colorless.Apotheosis", method = "upgrade")
	public static class ChangeApotheosisUpgrade{
		
		@SpireInsertPatch(rloc = 0)
		public static void Insert(Apotheosis __instance){
			ReduceCostBy(__instance, 1);
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.colorless.Magnetism", method = "upgrade")
	public static class ChangeMagnetismUpgrade{
		
		@SpireInsertPatch(rloc = 0)
		public static void Insert(Magnetism __instance){
			ReduceCostBy(__instance, 1);
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.colorless.Mayhem", method = "upgrade")
	public static class ChangeMayhemUpgrade{
		
		@SpireInsertPatch(rloc = 0)
		public static void Insert(Mayhem __instance){
			ReduceCostBy(__instance, 1);
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.green.BulletTime", method = "upgrade")
	public static class ChangeBulletTimeUpgrade{
		
		@SpireInsertPatch(rloc = 0)
		public static void Insert(BulletTime __instance){
			ReduceCostBy(__instance, 1);
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.green.Nightmare", method = "upgrade")
	public static class ChangeNightmareUpgrade{
		
		@SpireInsertPatch(rloc = 0)
		public static void Insert(Nightmare __instance){
			ReduceCostBy(__instance, 1);
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.green.PhantasmalKiller", method = "upgrade")
	public static class ChangePhantasmalKillerUpgrade{
		
		@SpireInsertPatch(rloc = 0)
		public static void Insert(PhantasmalKiller __instance){
			ReduceCostBy(__instance, 1);
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.red.Barricade", method = "upgrade")
	public static class ChangeBarricadeUpgrade{
		
		@SpireInsertPatch(rloc = 0)
		public static void Insert(Barricade __instance){
			ReduceCostBy(__instance, 1);
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.red.Corruption", method = "upgrade")
	public static class ChangeCorruptionUpgrade{
		
		@SpireInsertPatch(rloc = 0)
		public static void Insert(Corruption __instance){
			ReduceCostBy(__instance, 1);
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.red.DarkEmbrace", method = "upgrade")
	public static class ChangeDarkEmbraceUpgrade{
		
		@SpireInsertPatch(rloc = 0)
		public static void Insert(DarkEmbrace __instance){
			ReduceCostBy(__instance, 1);
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.red.Entrench", method = "upgrade")
	public static class ChangeEntrenchUpgrade{
		
		@SuppressWarnings("static-access")
		@SpireInsertPatch(rloc = 0)
		public static void Insert(Entrench __instance){
			ReduceCostBy(__instance, 1);
			__instance.rawDescription = __instance.UPGRADE_DESCRIPTION;
			__instance.initializeDescription();
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.blue.CreativeAI", method = "upgrade")
	public static class ChangeCreativeAIUpgrade{
		
		@SpireInsertPatch(rloc = 0)
		public static void Insert(CreativeAI __instance){
			ReduceCostBy(__instance, 1);
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.blue.Fusion", method = "upgrade")
	public static class ChangeFusionUpgrade{
		
		@SpireInsertPatch(rloc = 0)
		public static void Insert(Fusion __instance){
			ReduceCostBy(__instance, 1);
		}
	}
	
	private static void ReduceCostBy(AbstractCard __instance, int value) {
		if (!__instance.upgraded) {
			patchedUpgradeName(__instance);
			patchedUpgradeBaseCost(__instance, __instance.cost - value);
		}
	}
	
	private static void patchedUpgradeName(AbstractCard __instance) {
		__instance.timesUpgraded += 1;
		__instance.upgraded = true;
		__instance.name += "+";
		patchedInitializeTitleWithoutSettingUseSmallTitleFont(__instance);
	}
	
	private static void patchedUpgradeBaseCost(AbstractCard __instance, int newBaseCost) {
		int diff = __instance.costForTurn - __instance.cost;
		__instance.cost = newBaseCost;
	    if (__instance.costForTurn > 0) {
	    	__instance.costForTurn = (__instance.cost + diff);
	    }
	    if (__instance.costForTurn < 0) {
	    	__instance.costForTurn = 0;
	    }
	    __instance.upgradedCost = true;
	}
	
	@SuppressWarnings("unused")
	private static void patchedInitializeTitleWithoutSettingUseSmallTitleFont(AbstractCard __instance) {
		FontHelper.cardTitleFont_L.getData().setScale(1.0F);
	    GlyphLayout gl = new GlyphLayout(FontHelper.cardTitleFont_L, __instance.name,
	    		new Color(), 0.0F, 1, false);
	}
	
}
