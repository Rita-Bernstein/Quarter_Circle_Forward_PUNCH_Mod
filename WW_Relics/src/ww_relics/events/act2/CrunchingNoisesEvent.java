package ww_relics.events.act2;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;

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
    public static final int WHERE_EVENT_TEXT_STARTS = 9;
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

    public CrunchingNoisesEvent(){
        super(ID, "Crunching Noises", "images/events/cleric.jpg");
        
        SetEventStartingPoint();

    }

    private void SetEventStartingPoint() {
        
    	last_event_page_visited = 0;
        this.title = DESCRIPTIONS[WHERE_EVENT_TITLE_STARTS];
        this.imageEventText.setDialogOption(OPTIONS[WHERE_OPTION_TEXT_STARTS]);
        this.imageEventText.setDialogOption(OPTIONS[WHERE_OPTION_TEXT_STARTS + THE_SAFER_PATH_GAINED_GOOD_INSTINCTS]);
		this.imageEventText.updateBodyText(DESCRIPTIONS[WHERE_EVENT_TEXT_STARTS]);
		
    }
    
    @Override
    protected void buttonEffect(int button_pressed) {
    
    	int option_chosen = -1;
    	
    	if (last_event_page_visited == EVENT_STARTING_POINT) {
    		
    		if (button_pressed == 0) option_chosen = ElITE_ENCOUNTER_PART_1;
    		else if (button_pressed == 1) option_chosen = THE_SAFER_PATH_GAINED_GOOD_INSTINCTS;
    		
    	}
    	
        switch (option_chosen) {
        
        	case ElITE_ENCOUNTER_PART_1:
        		break;
        	case ElITE_ENCOUNTER_PART_2_FIGHT:
        		break;
        	case ELITE_VICTORIOUS_AFTERMATH:
        		break;
        	case GAINED_BLOOD_RELIC:
        		break;
        	case GAINED_SKELETON_RELIC :
        		break;
        	case GAINED_CHALLENGER_COINS:
        		break;
        	case GAINED_NOPE_NOPE_CANTALOPE_2_GOOD_INSTINCTS_PLUS:
        		break;
        	case THE_SAFER_PATH_GAINED_GOOD_INSTINCTS:
        		break;
        	default:
        		//Add bug message
        		break;
        
        }
    }
    

    
}
