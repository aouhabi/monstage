package com.freedomotic.things.impl;

import java.util.logging.Logger;

import com.freedomotic.behaviors.BooleanBehaviorLogic;
import com.freedomotic.behaviors.RangedIntBehaviorLogic;
import com.freedomotic.events.ObjectReceiveClick;
import com.freedomotic.model.ds.Config;
import com.freedomotic.model.object.BooleanBehavior;
import com.freedomotic.model.object.RangedIntBehavior;
import com.freedomotic.reactions.Trigger;
import com.freedomotic.things.EnvObjectLogic;

public class MotionSensor extends EnvObjectLogic {

	
	protected BooleanBehaviorLogic msState ;
	protected boolean msValue=false;
	private final static String BEHAVIOR_MOTION_STATE="msState" ;

	  @Override
	    public void init() {
    //linking this property with the behavior defined in the XML
		  msState = new BooleanBehaviorLogic((BooleanBehavior) getPojo().getBehavior(BEHAVIOR_MOTION_STATE));
		  msState.setValue(msValue);
		  msState.addListener(new BooleanBehaviorLogic.Listener() {
			
			@Override
			public void onTrue(Config params, boolean fireCommand) {
				// TODO Auto-generated method stub
				executePresence(params);
			}
			
			@Override
			public void onFalse(Config params, boolean fireCommand) {
				// TODO Auto-generated method stub
				executeNoPresence(params);
				
			}
		
			  
		  });		 
		
	        //caches hardware level commands and builds user command
		  registerBehavior(msState);
	        super.init();
	 }

	  
	    public void executeNoPresence(Config params) {
	        // when a light is "powered off" its brightness is set to the minValue but the current value is stored
	        msState.setValue(false);
	        getPojo().setCurrentRepresentation(0);
	        setChanged(true);
	        // executeCommand the body of the super implementation. The super call
	        // must be the last call as it executes setChanged(true)
	        
	    }
	    
	  
	    public void executePresence(Config params) {
	        // when a light is "powered on" its brightness is set to the stored value if this is greater than the minValue
	       
	            msState.setValue(true);
	            getPojo().setCurrentRepresentation(1);
	            setChanged(true);
	        
	        // executeCommand the body of the super implementation. The super call
	        // must be the last call as it executes setChanged(true)
	       
	    }
	 
	 @Override
	    protected void createCommands() {
	    }

	    /**
	     *
	     */
	    @Override
	    protected void createTriggers() {
	        Trigger clicked = new Trigger();
	        clicked.setName("When " + this.getPojo().getName() + " is clicked");
	        clicked.setChannel("app.event.sensor.object.behavior.clicked");
	        clicked.getPayload().addStatement("object.name", this.getPojo().getName());
	        clicked.getPayload().addStatement("click", ObjectReceiveClick.SINGLE_CLICK);
	        clicked.setPersistence(false);

	        Trigger login = new Trigger();
	        login.setName("When account " + this.getPojo().getName() + " logs in");
	        login.setChannel("app.event.sensor.account.change");
	        login.getPayload().addStatement("object.action", "LOGIN");
	        login.setPersistence(false);

	        Trigger logout = new Trigger();
	        logout.setName("When account " + this.getPojo().getName() + " logs out");
	        logout.setChannel("app.event.sensor.account.change");
	        logout.getPayload().addStatement("object.action", "LOGOUT");
	        logout.setPersistence(false);

	        triggerRepository.create(clicked);
	        triggerRepository.create(login);
	        triggerRepository.create(logout);
	    }
	    private static final Logger LOG = Logger.getLogger(Person.class.getName());
	}
