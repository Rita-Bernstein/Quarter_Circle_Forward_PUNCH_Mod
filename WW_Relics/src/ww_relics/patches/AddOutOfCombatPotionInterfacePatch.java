package ww_relics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;

@SpirePatch(clz = PotionPopUp.class, method = "updateInput")
public class AddOutOfCombatPotionInterfacePatch {

	public static ExprEditor Instrument()
	{
		return new ExprEditor() {
			@Override
			public void edit(Instanceof i) throws CannotCompileException
			{
				try {
					
					if (i.getType().equals("FruitJuice"))
						
						i.replace("{ $1 instanceof $r || $1 instanceof OutOfCombatPotion }");
					
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}
	
}
