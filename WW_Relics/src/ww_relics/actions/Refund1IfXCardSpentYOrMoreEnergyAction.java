package ww_relics.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.actions.common.RefundAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Refund1IfXCardSpentYOrMoreEnergyAction extends AbstractGameAction {

	public AbstractCard card;
	public int energy_spent_limit;
	public int energy_before;
	public int energy_to_refund;
	
	static Logger logger = LogManager.getLogger(Refund1IfXCardSpentYOrMoreEnergyAction.class.getName());
	
	public Refund1IfXCardSpentYOrMoreEnergyAction (AbstractCard card,
			int energy_spent_limit, int energy_before, int energy_to_refund) {
		this.card = card;
		this.energy_spent_limit = energy_spent_limit;
		this.energy_before = energy_before;
		this.energy_to_refund = energy_to_refund;
		
		logger.info("card " + card);
		logger.info("energy_spent_limit " + energy_spent_limit);
		logger.info("energy_before " + energy_before);
		logger.info("energy_to_refund " + energy_to_refund);
	}
	
	@Override
	public void update() {
		if (!this.isDone) {
			
			int energy_now = AbstractDungeon.player.energy.energy;
			
			if (energy_before - energy_now >= energy_spent_limit) {
				 AbstractDungeon.actionManager.addToTop(new RefundAction(card, energy_to_refund));
			}
			
			this.isDone = true;
			
		}

	}

}
