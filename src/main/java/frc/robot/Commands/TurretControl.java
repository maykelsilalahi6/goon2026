package frc.robot.Commands;

//imports the necessary files/folders needed
import frc.robot.subsystems.turretShootSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class TurretControl extends Command{
    
//defines the intake subsystem and speed
    private final turretShootSubsystem turret;
    private final double speed;

    //Command for Intake; sets the correct subsystem and speed for the Command
    public TurretControl(turretShootSubsystem turret, double speed) {
        this.turret = turret;
        this.speed = speed;
        addRequirements(turret);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        //runs the intake at the designated speed
        turret.setMotorShot(speed);

    }

    @Override
    public void end(boolean interrupted) {
        //sets the speed of the intake at 0 when not used
        turret.setMotorShot(0);
    }

    @Override
    public boolean isFinished() {
        //removes the Command IntakeControl from the Command Scheduler
        return false;
    }


}
