package ww_relics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;


public class AddOutOfCombatPotionInterfacePatch {

	@SpirePatch(clz = PotionPopUp.class, method = "updateInput")
	public static class UpdateInputUsable {
		
		public static ExprEditor Instrument()
		{
			return new ExprEditor() {
				@Override
				public void edit(Instanceof i) throws CannotCompileException
				{				
					try {
						
						if (i.getType().getName().toString().equals("com.megacrit.cardcrawl.potions.FruitJuice"))
							
							i.replace("$_ = $proceed($$) || this.potion instanceof ww_relics.potions.OutOfCombatPotion;");
						
					} catch (NotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}
		
	}
	
	@SpirePatch(clz = PotionPopUp.class, method = "render")
	public static class RenderUsable {
		
		public static ExprEditor Instrument()
		{
			return new ExprEditor() {
				
				@Override
				public void edit(Instanceof i) throws CannotCompileException
				{				
					try {
						
						if (i.getType().getName().toString().equals("com.megacrit.cardcrawl.potions.FruitJuice"))
							
							i.replace("$_ = $proceed($$) || (this.potion instanceof ww_relics.potions.OutOfCombatPotion && this.potion.canUse());");
						
					} catch (NotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}
		
	}
	
}
