package com.freedomotic.things.impl;

import com.freedomotic.behaviors.RangedIntBehaviorLogic;
import com.freedomotic.model.ds.Config;
import com.freedomotic.model.object.Behavior;
import com.freedomotic.model.object.RangedIntBehavior;
import com.freedomotic.reactions.Command;

public class Curtain  extends ElectricDevice{
	
	private RangedIntBehaviorLogic opening ;
	private int openingStoredValue=0 ;
	protected final static String BEHAVIOR_CURTAIN_STATE="storeState" ;
	
	  @Override
	    public void init() {
	        //linking this property with the behavior defined in the XML
		  opening = new RangedIntBehaviorLogic((RangedIntBehavior) getPojo().getBehavior(BEHAVIOR_CURTAIN_STATE));
		  opening.setValue(openingStoredValue);
	      opening.addListener(new RangedIntBehaviorLogic.Listener() {
	            
	            @Override
	            public void onLowerBoundValue(Config params, boolean fireCommand) {
	            	openingStoredValue = opening.getMin();
	                executePowerOff(params);
	            }
	            
	            @Override
	            public void onUpperBoundValue(Config params, boolean fireCommand) {
	            	openingStoredValue = opening.getMax();
	                executePowerOn(params);
	            }
	            
	            @Override
	            public void onRangeValue(int rangeValue, Config params, boolean fireCommand) {
	                executeBrightness(rangeValue, params);
	            }
	        });
	        //register this behavior to the superclass to make it visible to it
	        registerBehavior(opening);
	        super.init();
	    }
	
	  @Override
	    public void executePowerOff(Config params) {
	        // when a light is "powered off" its brightness is set to the minValue but the current value is stored
	        opening.setValue(opening.getMin());
	        // executeCommand the body of the super implementation. The super call
	        // must be the last call as it executes setChanged(true)
	        super.executePowerOff(params);
	    }
	    
	  @Override
	    public void executePowerOn(Config params) {
	        // when a light is "powered on" its brightness is set to the stored value if this is greater than the minValue
	        if (openingStoredValue > opening.getMin()) {
	            opening.setValue(openingStoredValue);
	        } else {
	            opening.setValue(opening.getMax());
	        }
	        // executeCommand the body of the super implementation. The super call
	        // must be the last call as it executes setChanged(true)
	        super.executePowerOn(params);
	    }
	    
	    public void executeBrightness(int rangeValue, Config params) {
	        boolean executed = executeCommand("set storeState", params); //executes the developer level command associated with 'set brightness' action

	        if (executed) {
	            powered.setValue(true);
	            opening.setValue(rangeValue);
	            openingStoredValue = opening.getValue();
	            //set the light graphical representation
	            getPojo().setCurrentRepresentation(1); //points to the second element in the XML views array (light on image)
	            setChanged(true);
	        }
	    }
	    
	    @Override
	    protected void createCommands() {
	        super.createCommands();
	        
	        Command a = new Command();
	        // a.setName(I18n.msg("set_X_brightness_to_50", new Object[]{this.getPojo().getName()}));
	        a.setName("Set " + getPojo().getName() + " opening to 50%");
	        a.setDescription("the Store " + getPojo().getName() + " changes its state");
	        a.setReceiver("app.events.sensors.behavior.request.objects");
	        a.setProperty("object",
	                getPojo().getName());
	        a.setProperty("behavior", BEHAVIOR_CURTAIN_STATE);
	        a.setProperty("value", "50");
	        
	        Command b = new Command();
	        // b.setName(I18n.msg("increase_X_brightness", new Object[]{this.getPojo().getName()}));
	        b.setName("Increase " + getPojo().getName() + " store state");
	        b.setDescription("increases " + getPojo().getName() + " opening of one step");
	        b.setReceiver("app.events.sensors.behavior.request.objects");
	        b.setProperty("object",
	                getPojo().getName());
	        b.setProperty("behavior", BEHAVIOR_CURTAIN_STATE);
	        b.setProperty("value", Behavior.VALUE_NEXT);
	        
	        Command c = new Command();
	        // c.setName(I18n.msg("decrease_X_brightness", new Object[]{this.getPojo().getName()}));
	        c.setName("Decrease " + getPojo().getName() + " brightness");
	        c.setDescription("decreases " + getPojo().getName() + " brightness of one step");
	        c.setReceiver("app.events.sensors.behavior.request.objects");
	        c.setProperty("object",
	                getPojo().getName());
	        c.setProperty("behavior", BEHAVIOR_CURTAIN_STATE);
	        c.setProperty("value", Behavior.VALUE_PREVIOUS);
	        
	        Command d = new Command();
	        // d.setName(I18n.msg("set_its_brightness_to_50"));
	        d.setName("Set its brightness to 50%");
	        // d.setDescription(I18n.msg("set_its_brightness_to_50"));
	        d.setDescription("Set its brightness to 50%");
	        d.setReceiver("app.events.sensors.behavior.request.objects");
	        d.setProperty("object", "@event.object.name");
	        d.setProperty("behavior", BEHAVIOR_CURTAIN_STATE);
	        d.setProperty("value", "50");
	        
	        Command e = new Command();
	        // e.setName(I18n.msg("increase_its_brightness"));
	        e.setName("Increase its brightness");
	        e.setDescription("increases its brightness of one step");
	        e.setReceiver("app.events.sensors.behavior.request.objects");
	        e.setProperty("object", "@event.object.name");
	        e.setProperty("behavior", BEHAVIOR_CURTAIN_STATE);
	        e.setProperty("value", Behavior.VALUE_NEXT);
	        
	        Command f = new Command();
	        // f.setName(I18n.msg("decrease_its_brightness"));
	        f.setName("Decrease its brightness");
	        f.setDescription("decreases its brightness of one step");
	        f.setReceiver("app.events.sensors.behavior.request.objects");
	        f.setProperty("object", "@event.object.name");
	        f.setProperty("behavior", BEHAVIOR_CURTAIN_STATE);
	        f.setProperty("value", Behavior.VALUE_PREVIOUS);
	        
	        Command g = new Command();
	        // g.setName(I18n.msg("set_brightness_from_event_value"));
	        g.setName("Set its brightness to the value in the event");
	        g.setDescription("set its brightness to the value in the event");
	        g.setReceiver("app.events.sensors.behavior.request.objects");
	        g.setProperty("object", "@event.object.name");
	        g.setProperty("behavior", BEHAVIOR_CURTAIN_STATE);
	        g.setProperty("value", "@event.value");
	        
	        commandRepository.create(a);
	        commandRepository.create(b);
	        commandRepository.create(c);
	        commandRepository.create(d);
	        commandRepository.create(e);
	        commandRepository.create(f);
	        commandRepository.create(g);
	    }
	    
	    @Override
	    protected void createTriggers() {
	        super.createTriggers();
	    }
}
