package ww_relics.patches;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;

public class AddOutOfCombatPotionInterfacePatch {

	public static ExprEditor Instrument()
	{
		return new ExprEditor() {
			public void edit(Instanceof i) throws CannotCompileException
			{
				try {
					
					if (i.getClass().equals("PotionPopUp")
					        && i.getType().equals("FruitJuice"))
						
						i.replace("{ $1 instanceof $r || $1 instanceof OutOfCombatPotion }");
					
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}
	
}
