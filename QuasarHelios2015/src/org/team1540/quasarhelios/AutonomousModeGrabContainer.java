package org.team1540.quasarhelios;

import ccre.channel.FloatInputPoll;
import ccre.instinct.AutonomousModeOverException;
import ccre.log.Logger;

public class AutonomousModeGrabContainer extends AutonomousModeBase {
    @Tunable(2.0f)
    private FloatInputPoll attachWaitingTime;
    @Tunable(1.0f)
    private FloatInputPoll strafeTime1;
    @Tunable(0.5f)
    private FloatInputPoll strafeTime2;
    @Tunable(-0.5f)
    private FloatInputPoll strafeSpeed1;
    @Tunable(0.5f)
    private FloatInputPoll strafeSpeed2;
    @Tunable(0.0f)
    private FloatInputPoll forwardSpeed1;
    @Tunable(0.0f)
    private FloatInputPoll forwardSpeed2;

    public AutonomousModeGrabContainer() {
        super("Grab Container");
    }

    @Override
    protected void runAutonomous() throws InterruptedException, AutonomousModeOverException {
        ContainerGrabber.containerGrabberSolenoid.set(true);
        waitForTime(attachWaitingTime);
        straightening.set(false);
        Logger.info("Motion 1: " + strafeTime1.get() + " in " + strafeSpeed1.get() + ", " + forwardSpeed1.get());
        if (strafeTime1.get() != 0) {
            DriveCode.angularStrafeForAuto(strafeSpeed1.get(), forwardSpeed1.get());
            waitForTime(strafeTime1);
        }
        Logger.info("Motion 2: " + strafeTime2.get() + " in " + strafeSpeed2.get() + ", " + forwardSpeed2.get());
        ContainerGrabber.containerGrabberSolenoid.set(false);
        if (strafeTime2.get() != 0) {
            DriveCode.angularStrafeForAuto(strafeSpeed2.get(), forwardSpeed2.get());
            waitForTime(strafeTime2);
        }
        Logger.info("Motion Done.");
        DriveCode.strafe.set(0.0f);
    }
}
