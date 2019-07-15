package ww_relics.events.act2;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.GoodInstincts;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import ww_relics.relics.mortal_kombat.NeverendingBlood;

public class CrunchingNoisesEvent extends AbstractImageEvent {

    //This isn't technically needed but it becomes useful later
    public static final String ID = "WW_Relics:Crunching_Noises";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    
    public static int last_event_page_visited;
    
    public static final String TO_WHICH_ACT_ADD = TheCity.ID;
    public static final int WHERE_EVENT_TITLE_STARTS = 0;
    public static final int WHERE_EVENT_TEXT_STARTS = 1;
    public static final int WHERE_OPTION_TEXT_STARTS = 0;
    
    public static final int EVENT_STARTING_POINT = 0;
    public static final int ElITE_ENCOUNTER_PART_1 = 1;
    public static final int ElITE_ENCOUNTER_PART_2_FIGHT = 2;
    public static final int ELITE_VICTORIOUS_AFTERMATH = 3;
    public static final int GAINED_BLOOD_RELIC = 4;
    public static final int GAINED_SKELETON_RELIC = 5;
    public static final int GAINED_CHALLENGER_COINS = 6;
    public static final int GAINED_NOPE_NOPE_CANTALOPE_2_GOOD_INSTINCTS_PLUS = 7;
    public static final int THE_SAFER_PATH_GAINED_GOOD_INSTINCTS = 8;
    
    public static final int ElITE_ENCOUNTER_PART_1_OPTION = 0;
    public static final int ElITE_ENCOUNTER_PART_2_FIGHT_OPTION = 1;
    public static final int ELITE_VICTORIOUS_AFTERMATH_OPTION = 2;
    public static final int GAINED_BLOOD_RELIC_OPTION = 3;
    public static final int GAINED_SKELETON_RELIC_OPTION = 4;
    public static final int GAINED_CHALLENGER_COINS_OPTION = 5;
    public static final int GAINED_NOPE_NOPE_CANTALOPE_2_GOOD_INSTINCTS_PLUS_OPTION = 6;
    public static final int THE_SAFER_PATH_GAINED_GOOD_INSTINCTS_OPTION = 7; 
    public static final int FINAL_VICTORIOUS_OPTION = 8;
    public static final int FINAL_SAFER_OPTION = 9;
    
    public static final int NUMBER_OF_GOOD_INSTINCTS_GAINED_AFTER_BATTLE_CHOICE = 2;

	public static final Logger logger = LogManager.getLogger(CrunchingNoisesEvent.class.getName());
    
    public CrunchingNoisesEvent(){
        super(ID, "Crunching Noises", "images/events/cleric.jpg");
        
        SetEventStartingPoint();

    }

    private void SetEventStartingPoint() {
        
    	last_event_page_visited = 0;
        this.title = DESCRIPTIONS[WHERE_EVENT_TITLE_STARTS];
		this.imageEventText.updateBodyText(DESCRIPTIONS[WHERE_EVENT_TEXT_STARTS +
		                                                EVENT_STARTING_POINT]);
        this.imageEventText.setDialogOption(OPTIONS[WHERE_OPTION_TEXT_STARTS +
                                                    ElITE_ENCOUNTER_PART_1_OPTION]);
        this.imageEventText.setDialogOption(OPTIONS[WHERE_OPTION_TEXT_STARTS +
                                                    THE_SAFER_PATH_GAINED_GOOD_INSTINCTS_OPTION]);

		
    }
    
    @Override
    protected void buttonEffect(int button_pressed) {
    
    	int option_chosen = -1;
    	
    	logger.info("A " + button_pressed);
    	logger.info("B " + last_event_page_visited);
    	
    	switch(last_event_page_visited) {
    	
    		case EVENT_STARTING_POINT:
    			CleanEventPage();
        		if (button_pressed == 0) option_chosen = ElITE_ENCOUNTER_PART_1;
        		else if (button_pressed == 1) option_chosen = THE_SAFER_PATH_GAINED_GOOD_INSTINCTS;
    			break;
    		case ElITE_ENCOUNTER_PART_1:
    			CleanEventPage();
    			if (button_pressed == 0) option_chosen = ElITE_ENCOUNTER_PART_2_FIGHT;
    			break;
    		case ElITE_ENCOUNTER_PART_2_FIGHT:
    			CleanEventPage();
    			if (button_pressed == 0) option_chosen = ELITE_VICTORIOUS_AFTERMATH;
    			break;
    		case ELITE_VICTORIOUS_AFTERMATH:
    			CleanEventPage();
    			if (button_pressed == 0) option_chosen = GAINED_BLOOD_RELIC;
    			else if (button_pressed == 1) option_chosen = GAINED_SKELETON_RELIC;
    			else if (button_pressed == 2) option_chosen = GAINED_CHALLENGER_COINS;
    			else if (button_pressed == 3) option_chosen = GAINED_NOPE_NOPE_CANTALOPE_2_GOOD_INSTINCTS_PLUS;
    			break;
    		default:
    			break;
    	}
    	
    	logger.info("C " + option_chosen);
    	
        switch (option_chosen) {
        
        	case ElITE_ENCOUNTER_PART_1:
        		SetEventEliteEncounterPart1();
        		break;
        	case ElITE_ENCOUNTER_PART_2_FIGHT:
        		logger.info("AAAFAD");
        		SetEventEliteEncounterPart2();
        		break;
        	case ELITE_VICTORIOUS_AFTERMATH:
        		logger.info("AAAFADDDDDDDDDDDDDDDDD");
        		SetEventEliteFight();
        		break;
        	case GAINED_BLOOD_RELIC:
        		SetEventGainedBloodRelic();
        		break;
        	case GAINED_SKELETON_RELIC :
        		SetEventGainedSkeletonRelic();
        		break;
        	case GAINED_CHALLENGER_COINS:
        		SetEventGainedChallengeCoinPotions();
        		break;
        	case GAINED_NOPE_NOPE_CANTALOPE_2_GOOD_INSTINCTS_PLUS:
        		SetNopeNopeCantalopeGoodInstincts();
        		break;
        	case THE_SAFER_PATH_GAINED_GOOD_INSTINCTS:
        		
        		break;
        	default:
        		//Add bug message
        		break;
        
        }
    }
    
    private void CleanEventPage() {
    	
    	this.imageEventText.clearAllDialogs();
    	this.imageEventText.clearRemainingOptions();
    	
    }
    
    private void SetEventEliteEncounterPart1() {
    	last_event_page_visited = ElITE_ENCOUNTER_PART_1;
        this.imageEventText.setDialogOption(OPTIONS[WHERE_OPTION_TEXT_STARTS +
                                                    ElITE_ENCOUNTER_PART_2_FIGHT_OPTION]);
		this.imageEventText.updateBodyText(DESCRIPTIONS[WHERE_EVENT_TEXT_STARTS +
		                                                ElITE_ENCOUNTER_PART_1]);
    }
    
    private void SetEventEliteEncounterPart2() {
    	last_event_page_visited = ElITE_ENCOUNTER_PART_2_FIGHT;
        this.imageEventText.setDialogOption(OPTIONS[WHERE_OPTION_TEXT_STARTS +
                                                    ELITE_VICTORIOUS_AFTERMATH_OPTION]);
		this.imageEventText.updateBodyText(DESCRIPTIONS[WHERE_EVENT_TEXT_STARTS +
		                                                ElITE_ENCOUNTER_PART_2_FIGHT]);
    	
    	
    	
    }
    
    private void SetEventEliteFight() {
        last_event_page_visited = ElITE_ENCOUNTER_PART_2_FIGHT;

        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Colosseum Slavers");
        AbstractDungeon.getCurrRoom().rewards.clear();
        AbstractDungeon.getCurrRoom().rewardAllowed = false;
        AbstractDungeon.getCurrRoom().combatEvent = true;
        enterCombatFromImage();
    }
    
    private void SetEventVictoriousAftermath() {
    	last_event_page_visited = ELITE_VICTORIOUS_AFTERMATH;
        this.imageEventText.setDialogOption(OPTIONS[WHERE_OPTION_TEXT_STARTS +
                                                    GAINED_BLOOD_RELIC_OPTION]);
        this.imageEventText.setDialogOption(OPTIONS[WHERE_OPTION_TEXT_STARTS +
                                                    GAINED_SKELETON_RELIC_OPTION]);
        this.imageEventText.setDialogOption(OPTIONS[WHERE_OPTION_TEXT_STARTS +
                                                    GAINED_CHALLENGER_COINS_OPTION]);
        this.imageEventText.setDialogOption(OPTIONS[WHERE_OPTION_TEXT_STARTS +
                                                    GAINED_NOPE_NOPE_CANTALOPE_2_GOOD_INSTINCTS_PLUS_OPTION]);
		this.imageEventText.updateBodyText(DESCRIPTIONS[WHERE_EVENT_TEXT_STARTS +
		                                                ELITE_VICTORIOUS_AFTERMATH]);
    }
    
    private void SetEventGainedBloodRelic() {
    	last_event_page_visited = GAINED_BLOOD_RELIC;
        this.imageEventText.setDialogOption(OPTIONS[WHERE_OPTION_TEXT_STARTS +
                                                    FINAL_VICTORIOUS_OPTION]);
		this.imageEventText.updateBodyText(DESCRIPTIONS[WHERE_EVENT_TEXT_STARTS +
		                                                GAINED_BLOOD_RELIC]);
		AbstractRelic relic = new NeverendingBlood();
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, relic);
    }
    
    private void SetEventGainedSkeletonRelic() {
    	
    }
    
    private void SetEventGainedChallengeCoinPotions() {
    	
    }
    
    private void SetNopeNopeCantalopeGoodInstincts() {
    	for (int i = 0; i < NUMBER_OF_GOOD_INSTINCTS_GAINED_AFTER_BATTLE_CHOICE; i++) {   		
    		AbstractCard c = new GoodInstincts();
    		c.upgrade();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));	
    	}
    }
    
    
    
    @Override
    public void reopen() {
		SetEventVictoriousAftermath();
        AbstractDungeon.resetPlayer();
        AbstractDungeon.player.drawX = Settings.WIDTH * 0.25F;
        AbstractDungeon.player.preBattlePrep();
        enterImageFromCombat();
    }

    
}
