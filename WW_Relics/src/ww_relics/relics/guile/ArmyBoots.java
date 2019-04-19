package ww_relics.relics.guile;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseBlockRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class ArmyBoots extends CustomRelic implements OnLoseBlockRelic  {
	public static final String ID = "WW_Relics:Army_Boots";
	
	public static final Logger logger = LogManager.getLogger(ArmyBoots.class.getName());
	
	private static ArrayList<String> powers_affected_by_relic;
	private static boolean relic_effect_activated = false;
	
	private static final int AMOUNT_OF_HP_HEALED = 1;

	public ArmyBoots() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.COMMON, LandingSound.SOLID);
		
		powers_affected_by_relic = new ArrayList<String>();
		powers_affected_by_relic.add("Frail");
		powers_affected_by_relic.add("Vulnerable");
	}
	
	public String getUpdatedDescription() {
		return "test";
	}
	
	@Override
	public void atPreBattle() {
		relic_effect_activated = false;
	}
	
	@Override
	public int onLoseBlock(DamageInfo info, int damage_amount) {
		
		boolean found_power = false;
		boolean showed_relic_image = false;
		if ((!relic_effect_activated) && (info.type == DamageType.NORMAL)) {
			flash();
			
			AbstractPlayer player = AbstractDungeon.player;
			
			for (String power: powers_affected_by_relic){
				if (player.hasPower(power)) {
					if (!relic_effect_activated) {
						found_power = true;
						relic_effect_activated = true;
						
					}
					
					if ((found_power) && (!showed_relic_image)) {
						AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
						showed_relic_image = true;
					}
					
					
					RemoveSpecificPowerAction remove_power_action =
							new RemoveSpecificPowerAction(player, player, player.getPower(power));

					AbstractDungeon.actionManager.addToTop(remove_power_action);
				}
			}
			
			if ((found_power == false) && (damage_amount > player.currentBlock)) {
				AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
				AbstractDungeon.actionManager.addToBottom(
						new HealAction(player, player, AMOUNT_OF_HP_HEALED));
				relic_effect_activated = true;
			}
		}
		
		return damage_amount;
	}	
	
	public AbstractRelic makeCopy() {
		return new ArmyBoots();
	}

}
