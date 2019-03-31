package ww_relics.relics.chun_li;

import java.io.IOException;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.CardGroup.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class WhiteBoots extends CustomRelic implements ClickableRelic {
	public static final String ID = "WW_Relics:White_Boots";
	private static final int NUMBER_OF_USES_PER_FIGHT = 1;
	private static final int NUMBER_OF_CHOSEN_ATTACKS = 1;
	private static final int MAXIMUM_COST = 1;
	private static final int NUMBER_OF_COPIES = 3;
	private static final int EFFECT_ON_COST_OF_GENERATED_CARDS = -1;
	private static final int EFFECT_ON_COST_READABLE = EFFECT_ON_COST_OF_GENERATED_CARDS * -1;
	
	public int number_of_uses_left_in_this_fight;
	public int number_of_copies_left_to_use;
	public boolean player_activated;
	public boolean card_is_selected;
	public AbstractCard card_selected;
	public AbstractCard card_copied;
	
	public int original_cost;
	
	public int[] copied_cards_x_position = {100, 120, 120, 100};
	public int[] copied_cards_y_position = {100, 80, -80, -100};
	
	public boolean effect_happened = false;
	
	public static final Logger logger = LogManager.getLogger(WhiteBoots.class.getName());
	
	private boolean have_uses_left = false;
	private boolean is_on_combat = false;
	private boolean is_alive = false;
	private boolean turn_wont_end_soon = false;
	private boolean player_isnt_ending_turn = false;
	private boolean turn_havent_ended = false;
	private boolean has_an_attack_in_hand = false;
	
	private Texture white_boots_texture;
	private Texture spent_white_boots_texture;
	
	public WhiteBoots() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
			RelicTier.UNCOMMON, LandingSound.SOLID);
		
		white_boots_texture = GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png");
		spent_white_boots_texture = GraphicResources.LoadRelicImage("White_Boots spent - steeltoe-boots - Lorc - CC BY 3.0.png");

	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[2] + NUMBER_OF_CHOSEN_ATTACKS +
				DESCRIPTIONS[3] + DESCRIPTIONS[4] + NUMBER_OF_COPIES + DESCRIPTIONS[5] +
				EFFECT_ON_COST_READABLE + DESCRIPTIONS[6];
	}
	
	public void atBattleStartPreDraw() {

		number_of_uses_left_in_this_fight = NUMBER_OF_USES_PER_FIGHT;

		logger.info("... " + AbstractDungeon.getCurrRoom().phase);
		card_is_selected = false;
		card_selected = null;
		card_copied = null;
		player_activated = false;
		
		AbstractDungeon.gridSelectScreen.selectedCards.clear();
		
		setBooleansOfValidUseToFalse();
		
		checkAndSetNotUsedRelic();
		checkAndSetUsedRelic();

	}
	
	private void setBooleansOfValidUseToFalse() {
		have_uses_left = false;
		is_on_combat = false;
		is_alive = false;
		turn_wont_end_soon = false;
		player_isnt_ending_turn = false;
		turn_havent_ended = false;
		has_an_attack_in_hand = false;
	}
	
	private void checkAndSetNotUsedRelic() {
		
		if (number_of_uses_left_in_this_fight > 0) {
			this.usedUp = false;
			this.img = white_boots_texture;
		}

	}
	
	@Override
	public void onRightClick() {
		
		updateBooleansOfValidUse();
		
		if (isValidToUse()) {
			
			CardGroup attacks_in_hand = AbstractDungeon.player.hand.getAttacks();
			
			if (AValidAttackIsInHand(attacks_in_hand)) {
				
				number_of_copies_left_to_use = NUMBER_OF_COPIES;
				
				CardGroup list_of_all_hand_attacks = AbstractDungeon.player.hand.getPurgeableCards().
						getAttacks();
				CardGroup list_of_attacks = new CardGroup(CardGroupType.UNSPECIFIED);
				
				addValidAttacksToList(list_of_all_hand_attacks, list_of_attacks);

				if (list_of_attacks.size() >= 1) {
					player_activated = true;
				}
				
				if (list_of_attacks.size() == 1) {
					setSelectedCard(list_of_attacks.getTopCard());
				} else if (list_of_attacks.size() > 1){
					AbstractDungeon.gridSelectScreen.open(
						list_of_attacks, 1,
						this.DESCRIPTIONS[7] +
							NUMBER_OF_CHOSEN_ATTACKS + this.DESCRIPTIONS[3],
						false, false, false, false);
			    }
			}
		}
	}
	
	private void updateBooleansOfValidUse() {
		
		have_uses_left = number_of_uses_left_in_this_fight > 0;
		is_on_combat = AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
		is_alive = !AbstractDungeon.player.isDead;
		turn_wont_end_soon = !AbstractDungeon.player.endTurnQueued;
		player_isnt_ending_turn = !AbstractDungeon.player.isEndingTurn;
		turn_havent_ended = !AbstractDungeon.actionManager.turnHasEnded;
		has_an_attack_in_hand = AbstractDungeon.player.hand.getAttacks().
			size() > 0;
	}
	
	private boolean AValidAttackIsInHand(CardGroup attacks) {
		
		for (int i = 0; i < attacks.size(); i++) {
			int cost = attacks.getNCardFromTop(i).cost;
			if (cost <= MAXIMUM_COST) return true;
		}
		return false;
		
	}
	
	private boolean isValidToUse() {
		return have_uses_left && is_on_combat && is_alive && turn_wont_end_soon &&
				player_isnt_ending_turn && turn_havent_ended && has_an_attack_in_hand;
	}
	
	private void addValidAttacksToList(CardGroup list_of_all_hand_attacks, CardGroup list_of_attacks) {
		for (int i = 0; i < list_of_all_hand_attacks.size(); i++) {
			
			AbstractCard card = list_of_all_hand_attacks.getNCardFromTop(i);
			if (card.cost <= MAXIMUM_COST) {
				list_of_attacks.addToTop(card);
			}
			
		}
	}
	
	private void setSelectedCard(AbstractCard selected) {
		card_is_selected = true;
		card_selected = selected;
	}
	
	public void update()
	{
		super.update();

		if ((have_uses_left) && (player_activated)){
			
			if ((!card_is_selected) && (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())) {
				number_of_uses_left_in_this_fight--;
				
				setSelectedCard((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
			}
			
			if (card_is_selected) {
				
				number_of_uses_left_in_this_fight--;
				
				createBaseCopyOfSelectedCard();
				
				createCopiesAndAddThemToDrawPile();
				
				AbstractDungeon.player.hand.moveToExhaustPile(card_selected);
				
				AbstractDungeon.gridSelectScreen.selectedCards.clear();
				
				have_uses_left = number_of_uses_left_in_this_fight > 0;
				
				checkAndSetUsedRelic(); 
			}
		}
	}
	
	private void createBaseCopyOfSelectedCard() {
		card_copied = card_selected.makeCopy();
		if (card_selected.upgraded == true) card_copied.upgrade();
		
		original_cost = card_copied.cost;
		if (card_copied.cost > 0) card_copied.updateCost(- 1); 
	}
	
	private void createCopiesAndAddThemToDrawPile() {
		for (int i = 0; i < NUMBER_OF_COPIES; i++) {
			AbstractDungeon.actionManager.addToBottom(
					new MakeTempCardInDrawPileAction(
							card_copied, 1, true, true, false,
							copied_cards_x_position[i],
							copied_cards_y_position[i]));
		}
	}

	private void checkAndSetUsedRelic() {
		
		if (number_of_uses_left_in_this_fight <= 0) {
			this.usedUp = true;
			this.img = spent_white_boots_texture;
		}

	}
	
	public void onPlayCard(AbstractCard c, AbstractMonster m)  {
		
		if ((card_is_selected) && (c.compareTo(card_copied) == 0)
				&& (c.isCostModified)){
			
			if (original_cost > c.cost) c.cost += 1;
			c.isCostModified = false;
			number_of_copies_left_to_use--;
			
			if (number_of_copies_left_to_use == 0) {
				card_copied = null;
				card_selected = null;
				card_is_selected = false;
			}
			
		}
		
	}
	
	public boolean canSpawn()
	{
		return AbstractDungeon.player.masterDeck.getAttacks().size() > 0;
	}
	
	public static void save(final SpireConfig config) {

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
    		logger.info("Started saving White Boots information");
        	final WhiteBoots relic = (WhiteBoots)AbstractDungeon.player.getRelic(ID);

            config.setInt("White_Boots_number_of_uses",
            		relic.number_of_uses_left_in_this_fight);
            
            try {
				config.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
            logger.info("Finished saving White Boots info.");
        }
        else {
        	clear(config);
        }

    }
	
	public static void load(final SpireConfig config) {
		
		logger.info("Loading White Boots info.");
		if (AbstractDungeon.player.hasRelic(ID) && config.has("White_Boots_number_of_uses")){

            final WhiteBoots relic = (WhiteBoots)AbstractDungeon.player.getRelic(ID);
            final int number_of_uses = config.getInt("White_Boots_number_of_uses");
                     
        	relic.number_of_uses_left_in_this_fight = number_of_uses;
            
        	relic.checkAndSetNotUsedRelic();
        	relic.checkAndSetUsedRelic();   
            
            try {
				config.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("Finished loading White Boots info.");
        }
		
		else
		{
			logger.info("There's no info, setting variables accordingly.");

			logger.info("Finished setting White Boots variables.");
		}
		
		
    }
		
	public static void clear(final SpireConfig config) {
		logger.info("Clearing White Boots variables.");      
        config.remove("White_Boots_number_of_uses");
        logger.info("Finished clearing White Boots variables.");
	}

	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new WhiteBoots();
	}
}