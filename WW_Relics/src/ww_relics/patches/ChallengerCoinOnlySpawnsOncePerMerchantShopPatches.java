package ww_relics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;
import ww_relics.QCFPunch_MiscelaneaCode;

public class ChallengerCoinOnlySpawnsOncePerMerchantShopPatches {
	
	@SpirePatch(
	        clz=ShopScreen.class,
	        method="initPotions")
	
	public static class ShopScreenPatchAddsVariable{
		
		@SpireInsertPatch(rloc=2)
		public static void Insert(ShopScreen __instance) {
			QCFPunch_MiscelaneaCode.resetNumberOfChallengerCoinPotionsVariable();
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
				public void edit(NewExpr n) throws CannotCompileException
				{				
					if (n.getClassName().toString().equals(StorePotion.class.getName().toString())) {
						try {
							n.replace("$_ = $proceed($$);"
									+ "if ((ww_relics.QCFPunch_MiscelaneaCode.number_of_challenger_coin_potions_at_shop == 0) && "
									+ "($_.potion.ID.equals(ww_relics.potions.ChallengerCoin.ID))){"
									+ "ww_relics.QCFPunch_MiscelaneaCode.incrementNumberOfChallengerCoinPotionsAtShop();"
									+ "} else if ($_.potion.ID.equals(ww_relics.potions.ChallengerCoin.ID)){"
										+ "while ($_.potion.ID.equals(ww_relics.potions.ChallengerCoin.ID)){"
											+ "$_ == new com.megacrit.cardcrawl.shop.StorePotion("
												+ "com.megacrit.cardcrawl.dungeons.AbstractDungeon.returnRandomPotion(), i, this);"
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
