package ww_relics.patches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import ww_relics.potions.OutOfCombatPotion;

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
				final Logger logger = LogManager.getLogger(AddOutOfCombatPotionInterfacePatch.class.getName());
				
				try {
					logger.info(i.getType().getName().toString());
					logger.info(i.getType().getName().toString().equals("com.megacrit.cardcrawl.potions.FruitJuice"));
					
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
