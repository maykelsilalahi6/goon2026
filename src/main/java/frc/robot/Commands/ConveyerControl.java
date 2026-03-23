package frc.robot.Commands;

import frc.robot.subsystems.ConveyerSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class ConveyerControl extends Command{
    //defines the conveyor subsystem and speed
    private final ConveyerSubsystem conveyer;
    private final double speed;

    //Command for Intake; sets the correct subsystem and speed for the Command
    public ConveyerControl(ConveyerSubsystem conveyer, double speed) {
        this.conveyer = conveyer;
        this.speed = speed;
        addRequirements(conveyer);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        //runs the intake at the designated speed
        conveyer.setMotorConveyer(speed);

    }

    @Override
    public void end(boolean interrupted) {
        //sets the speed of the intake at 0 when not used
        conveyer.setMotorConveyer(0);
    }

    @Override
    public boolean isFinished() {
        //removes the Command IntakeControl from the Command Scheduler
        return false;
    }

}
