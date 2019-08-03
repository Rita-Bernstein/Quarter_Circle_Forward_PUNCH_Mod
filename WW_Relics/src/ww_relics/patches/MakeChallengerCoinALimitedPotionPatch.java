package ww_relics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

public class MakeChallengerCoinALimitedPotionPatch {

	@SpirePatch(clz = AbstractDungeon.class, method = "returnRandomPotion")
	public static class ReturnRandomPotionButNotChallengerCoin {
		
		public static ExprEditor Instrument()
		{
			return new ExprEditor() {
				@Override
				public void edit(FieldAccess fi) throws CannotCompileException
				{				
						if ((fi.getClassName().equals("com.megacrit.cardcrawl.potions.AbstractPotion")) &&
								(fi.getFieldName().equals("ID"))) 
													
							fi.replace("$_ = $proceed($$) || this.potion instanceof ww_relics.potions.ChallengeCoin;");
						
				}
			};
		}
		
	}
	
}
