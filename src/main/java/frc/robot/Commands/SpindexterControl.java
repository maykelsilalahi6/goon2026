package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpindexterSubsystem;

public class SpindexterControl extends Command{
    //defines the Spindexter subsystem and speed
    private final SpindexterSubsystem spindexter;
    private final double speed;

    //defines the Tunnel subsystem and speed
    private final SpindexterSubsystem tunnel;

    //Command for Intake; sets the correct subsystem and speed for the Command
    public SpindexterControl(SpindexterSubsystem spindexter, SpindexterSubsystem tunnel, double speed) {
        this.spindexter = spindexter;
        this.tunnel = tunnel;
        this.speed = speed;
        addRequirements(spindexter);
        addRequirements(tunnel);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        //runs the intake at the designated speed
        spindexter.setMotorSpindexter(speed);
        tunnel.setMotorSpindexter(speed);
    }

    @Override
    public void end(boolean interrupted) {
        //sets the speed of the intake at 0 when not used
        spindexter.setMotorSpindexter(0);
        tunnel.setMotorSpindexter(0);
    }

    @Override
    public boolean isFinished() {
        //removes the Command IntakeControl from the Command Scheduler
        return false;
    }

}
