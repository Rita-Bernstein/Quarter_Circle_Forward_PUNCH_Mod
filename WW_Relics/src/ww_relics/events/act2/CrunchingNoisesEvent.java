package ww_relics.events.act2;

import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.AbstractImageEvent;

public class CrunchingNoisesEvent extends AbstractImageEvent {

    //This isn't technically needed but it becomes useful later
    public static final String ID = "WW_Relics:Crunching_Noises";
    
    public static final String TO_WHICH_ACT_ADD = TheCity.ID;

    public CrunchingNoisesEvent(){
        super(ID, "Crunching Noises", "images/events/cleric.jpg");
        
        //This is where you would create your dialog options
        this.imageEventText.setDialogOption("My Dialog Option"); //This adds the option to a list of options
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        //It is best to look at examples of what to do here in the basegame event classes, but essentially when you click on a dialog option the index of the pressed button is passed here as buttonPressed.
    	openMap();
    }
    
}
