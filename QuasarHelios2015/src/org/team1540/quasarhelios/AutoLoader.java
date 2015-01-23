package org.team1540.quasarhelios;

import ccre.channel.BooleanInputPoll;
import ccre.channel.BooleanStatus;
import ccre.channel.EventOutput;
import ccre.ctrl.BooleanMixing;
import ccre.igneous.Igneous;
import ccre.instinct.AutonomousModeOverException;
import ccre.instinct.InstinctModule;

public class AutoLoader extends InstinctModule {
	private static final BooleanInputPoll crateInPosition = Igneous.makeDigitalInput(6);
	
	public static BooleanStatus create() {
		BooleanStatus b = new BooleanStatus(false);
		AutoLoader a = new AutoLoader();
		
		a.setShouldBeRunning(b);
		a.updateWhen(Igneous.globalPeriodic);
		
		Elevator.elevatorControl.setFalseWhen(BooleanMixing.onRelease(b));
		
		return b;
	}
	
	@Override
	public void autonomousMain() throws AutonomousModeOverException, InterruptedException {
		Elevator.elevatorControl.set(true);
		
		waitUntil(Elevator.topLimitSwitch);
		
		boolean r = Rollers.running.get();
		boolean d = Rollers.direction.get();
		
		Rollers.direction.set(true);
		Rollers.running.set(true);
		
		waitUntil(crateInPosition);
		
		Rollers.running.set(r);
		Rollers.direction.set(d);
		
		Elevator.elevatorControl.set(false);
	}
}

