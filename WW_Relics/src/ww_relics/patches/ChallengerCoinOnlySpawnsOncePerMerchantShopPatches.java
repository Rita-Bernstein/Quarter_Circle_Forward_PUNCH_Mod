package ww_relics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.shop.ShopScreen;

import javassist.CannotCompileException;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import ww_relics.WW_Relics_MiscelaneaCode;

public class ChallengerCoinOnlySpawnsOncePerMerchantShopPatches {

	@SpirePatch(
	        clz=ShopScreen.class,
	        method="initPotions")
	
	public static class ShopScreenPatchAddsVariable{
		
		@SpireInsertPatch(rloc=2)
		public static void Insert(ShopScreen __instance) {
			WW_Relics_MiscelaneaCode.resetNumberOfChallengerCoinPotionsVariable();
		}
		
	}
	
	@SpirePatch(
	        cls="com.megacrit.cardcrawl.shop.ShopScreen",
	        method="initPotions")
	
	public static class ShopScreenPatchUsesVariable{
		
		public static ExprEditor Instrument()
		{
			return new ExprEditor() {
				@Override
				public void edit(ConstructorCall c)
				{				
					if (c.getClassName().toString().equals("com.megacrit.cardcrawl.shop.StorePotion")) {
						try {
							c.replace("$_ = $proceed($$);"
									+ "if ((ww_relics.WW_Relics.MiscelaneaCode.number_of_challenger_coin_potions_at_shop == 0) && "
									+ "($0.potion.ID.equals(ww_relics.potions.OutOfCombatPotion.ID)){"
									+ "ww_relics.WW_Relics.MiscelaneaCode.incrementNumberOfChallengerCoinPotionsAtShop();"
									+ "} else if ($0.potion.ID.equals(ww_relics.potions.OutOfCombatPotion.ID)){"
										+ "while ($0.potion.ID.equals(ww_relics.potions.OutOfCombatPotion.ID)){"
											+ "$0 = = new StorePotion(AbstractDungeon.returnRandomPotion(), i, this);"
										+ "}"
									+ "}");
						} catch (CannotCompileException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
			
		}
	}
	
}
