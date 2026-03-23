package frc.robot.Commands;

//imports the necessary files/folders needed
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class IntakeControl extends Command{
    
//defines the intake subsystem and speed
    private final IntakeSubsystem intake;
    private final double speed;

    //Command for Intake; sets the correct subsystem and speed for the Command
    public IntakeControl(IntakeSubsystem intake, double speed) {
        this.intake = intake;
        this.speed = speed;
        addRequirements(intake);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        //runs the intake at the designated speed
        intake.setMotorIntake(speed);

    }

    @Override
    public void end(boolean interrupted) {
        //sets the speed of the intake at 0 when not used
        intake.setMotorIntake(0);
    }

    @Override
    public boolean isFinished() {
        //removes the Command IntakeControl from the Command Scheduler
        return false;
    }


}
