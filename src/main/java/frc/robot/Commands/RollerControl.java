package frc.robot.Commands;

import frc.robot.subsystems.RollerSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class RollerControl extends Command{
    //defines the intake subsystem and speed
    private final RollerSubsystem roller;
    private final double speed;

    //Command for Intake; sets the correct subsystem and speed for the Command
    public RollerControl(RollerSubsystem roller, double speed) {
        this.roller = roller;
        this.speed = speed;
        addRequirements(roller);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        //runs the intake at the designated speed
        roller.setMotorRoller(speed);

    }

    @Override
    public void end(boolean interrupted) {
        //sets the speed of the intake at 0 when not used
        roller.setMotorRoller(0);
    }

    @Override
    public boolean isFinished() {
        //removes the Command IntakeControl from the Command Scheduler
        return false;
    }

}
