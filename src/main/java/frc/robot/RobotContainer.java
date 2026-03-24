package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.events.EventTrigger;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.turretShootSubsystem;
import frc.robot.Commands.IntakeControl;
import frc.robot.Commands.TurretControl;

public class RobotContainer {

    boolean fieldcentriccount = true;
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); 
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); 
    private final CommandXboxController joystick = new CommandXboxController(0);
    SlewRateLimiter X_Filter = new SlewRateLimiter(3, MaxSpeed, 0);
    SlewRateLimiter Y_Filter = new SlewRateLimiter(3, MaxSpeed, 0);

    

    // Build an auto chooser. This will use Commands.none() as the default option.
    private SendableChooser<Command> autoChooser;
        
        private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
                .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1)
                .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
    
        private final Telemetry logger = new Telemetry(MaxSpeed);

    
        public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
        
        // Subsystems
        private final IntakeSubsystem intake = new IntakeSubsystem();
        private final turretShootSubsystem turret = new turretShootSubsystem();

        public RobotContainer() {
            configureBindings();

            //Named Commands
            new EventTrigger("runIntake")
                .whileTrue(new IntakeControl(intake, 0.75));

            new EventTrigger("shootTurret")
                .whileTrue(new TurretControl(turret, 20));
                
            // Build an auto chooser. This will use Commands.none() as the default option.

            // Another option that allows you to specify the default auto by its name
            autoChooser = AutoBuilder.buildAutoChooser("Shoot-Only Auto");



            SmartDashboard.putData("Auto Chooser", autoChooser);

        }
        
        private void configureBindings() {
            // DEFAULT DRIVE
            // Note that X is defined as forward according to WPILib convention,
            // and Y is defined as to the left according to WPILib convention.

            drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
                drivetrain.applyRequest(() ->
                    drive.withVelocityX((joystick.getLeftY() * MaxSpeed)) // Drive forward with negative Y (forward)
                        .withVelocityY((joystick.getLeftX() * MaxSpeed)) // Drive left with negative X (left)
                        .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
                )
            );
    
            // BUTTON A: AUTO-ALIGN TO TARGET
            joystick.a().whileTrue(
                drivetrain.driveAndAlign(
                    () -> -joystick.getLeftY() * MaxSpeed,
                    () -> -joystick.getLeftX() * MaxSpeed
                )
            );
    
            drivetrain.registerTelemetry(logger::telemeterize);

    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

}
// i dont want to take so much and give barely any back