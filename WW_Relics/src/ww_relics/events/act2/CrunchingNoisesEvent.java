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
    
    public static final String TO_WHICH_ACT_ADD = TheCity.ID;
    public static final int WHERE_EVENT_TITLE_STARTS = 0;
    public static final int WHERE_EVENT_TEXT_STARTS = 9;
    
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
        
        this.title = DESCRIPTIONS[0];
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (buttonPressed) {
        
        	case 0:
        		openMap();
        		break;
        		
        	case 1:
        		this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                this.imageEventText.removeDialogOption(0);
                this.imageEventText.setDialogOption(OPTIONS[2]);
                
        		break;
        	case 2:
        		openMap();
        		break;
        	default:
        		
        		break;
        
        }
    	
    	
    	
    }
    
}
