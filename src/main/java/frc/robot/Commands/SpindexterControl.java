package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpindexterSubsystem;

public class SpindexterControl extends Command{
    //defines the intake subsystem and speed
    private final SpindexterSubsystem spindexter;
    private final double speed;

    //Command for Intake; sets the correct subsystem and speed for the Command
    public SpindexterControl(SpindexterSubsystem spindexter, double speed) {
        this.spindexter = spindexter;
        this.speed = speed;
        addRequirements(spindexter);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        //runs the intake at the designated speed
        spindexter.setMotorSpindexter(speed);

    }

    @Override
    public void end(boolean interrupted) {
        //sets the speed of the intake at 0 when not used
        spindexter.setMotorSpindexter(0);
    }

    @Override
    public boolean isFinished() {
        //removes the Command IntakeControl from the Command Scheduler
        return false;
    }

}
