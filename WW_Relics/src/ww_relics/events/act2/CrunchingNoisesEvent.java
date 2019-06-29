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

    public CrunchingNoisesEvent(){
        super(ID, "Crunching Noises", "images/events/cleric.jpg");
        
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
