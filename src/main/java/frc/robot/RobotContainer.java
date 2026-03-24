// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot;

// import static edu.wpi.first.units.Units.*;

// import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
// import com.ctre.phoenix6.swerve.SwerveRequest;

// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
// //import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
// //import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

// import frc.robot.generated.TunerConstants;
// import frc.robot.subsystems.CommandSwerveDrivetrain;
// import frc.robot.subsystems.ConveyerSubsystem;
// import frc.robot.subsystems.IntakeSubsystem;
// import frc.robot.Commands.ConveyerControl;
// import frc.robot.Commands.IntakeControl;

// public class RobotContainer {

//     boolean fieldcentriccount = true;

//     private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
//     private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

//     /* Setting up bindings for necessary control of the swerve drive platform */
//     private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
//             .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
//             .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
//     //private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
//     //private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

//     private final Telemetry logger = new Telemetry(MaxSpeed);

//     private final CommandXboxController joystick = new CommandXboxController(0);

//     public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

//     public RobotContainer() {

//         configureBindings();
//     }

//     private void configureBindings() {

//         // Note that X is defined as forward according to WPILib convention,
//         // and Y is defined as to the left according to WPILib convention.
//         drivetrain.setDefaultCommand(
//             // Drivetrain will execute this command periodically
//             drivetrain.applyRequest(() ->
//                 drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
//                     .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
//                     .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
//             )
//         );

//         // Idle while the robot is disabled. This ensures the configured
//         // neutral mode is applied to the drive motors while disabled.
//         // final var idle = new SwerveRequest.Idle();
//         // RobotModeTriggers.disabled().whileTrue(
//         //     drivetrain.applyRequest(() -> idle).ignoringDisable(true)
//         // );

//         // joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
//         // joystick.b().whileTrue(drivetrain.applyRequest(() ->
//         //     point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
//         // ));

//         // Run SysId routines when holding back/start and X/Y.
//         // Note that each routine should be run exactly once in a single log.
//         //joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
//         //joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
//         //joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
//         //joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

//         // Reset the field-centric heading on left bumper press.
//         // joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

//         drivetrain.registerTelemetry(logger::telemeterize);

//         final IntakeSubsystem intake = new IntakeSubsystem();
//         joystick.rightBumper().whileTrue(new IntakeControl(intake, -0.75));
//         joystick.rightBumper().whileTrue(new IntakeControl(intake, 0.75));

//         final ConveyerSubsystem conveyer = new ConveyerSubsystem();
//         joystick.rightBumper().whileTrue(new ConveyerControl(conveyer, 0.5));


//     }

//     public Command getAutonomousCommand() {
//         // Simple drive forward auton
//         final var idle = new SwerveRequest.Idle();
//         return Commands.sequence(
//             // Reset our field centric heading to match the robot
//             // facing away from our alliance station wall (0 deg).
//             drivetrain.runOnce(() -> drivetrain.seedFieldCentric(Rotation2d.kZero)),
//             // Then slowly drive forward (away from us) for 5 seconds.
//             drivetrain.applyRequest(() ->
//                 drive.withVelocityX(0.5)
//                     .withVelocityY(0)
//                     .withRotationalRate(0)
//             )
//             .withTimeout(5.0),
//             // Finally idle for the rest of auton
//             drivetrain.applyRequest(() -> idle)
//         );
//     }


// }


package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.events.EventTrigger;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ConveyerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.RollerSubsystem;
import frc.robot.subsystems.turretShootSubsystem;
import pabeles.concurrency.IntOperatorTask.Max;
import frc.robot.Commands.ConveyerControl;
import frc.robot.Commands.IntakeControl;
import frc.robot.Commands.RollerControl;
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
        private final ConveyerSubsystem conveyer = new ConveyerSubsystem();
        private final turretShootSubsystem turret = new turretShootSubsystem();
        private final RollerSubsystem roller = new RollerSubsystem();

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
    
            // INTAKE & CONVEYOR (Right Bumper)
            joystick.rightBumper().whileTrue(
                Commands.parallel(
                    new IntakeControl(intake, 0.8),
                    new ConveyerControl(conveyer, 0.65)
                )
            );

            joystick.leftBumper().whileTrue(
                Commands.parallel(
                    new IntakeControl(intake, -0.8),
                    new ConveyerControl(conveyer, -0.65)
                )
            );

            // TURRET (Right Trigger)
            joystick.rightTrigger().whileTrue(
                Commands.parallel(
                    new RollerControl(roller, -0.75),
                    new TurretControl(turret, 18)
                )
                
            );

            joystick.leftTrigger().whileTrue(
                Commands.parallel(
                    new RollerControl(roller, 0.75)
                )
                
            );
    
            drivetrain.registerTelemetry(logger::telemeterize);

    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

}
// i dont want to take so much and give barely any back