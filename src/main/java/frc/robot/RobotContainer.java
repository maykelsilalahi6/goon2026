// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.*;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
//import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Constants.TunerConstants;
import frc.robot.Subsystems.CommandSwerveDrivetrain;
import frc.robot.Subsystems.SpindexterSubsystem;
import frc.robot.Subsystems.Turret.TurretShooter;




public class RobotContainer {

    // private SendableChooser<Command> autoChooser;
    boolean fieldcentriccount = true;

    //Subsystems
    private final SpindexterSubsystem m_spindexter = new SpindexterSubsystem();
    private final TurretShooter m_turretShooter = new TurretShooter();

    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    //private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    //private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    //Joysticks
    private final CommandXboxController m_joystick = new CommandXboxController(0);
    //private final CommandXboxController joystick2 = new CommandXboxController(1);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public RobotContainer() {

        // NamedCommands.registerCommand("exampleCommand", m_turretShooter.setMotorShooter());

        configureBindings();

        // For convenience a programmer could change this when going to competition.
    //     boolean isCompetition = true;

    //     // Build an auto chooser. This will use Commands.none() as the default option.
    //     // As an example, this will only show autos that start with "comp" while at
    //     // competition as defined by the programmer
    //     autoChooser = AutoBuilder.buildAutoChooserWithOptionsModifier(
    //         (stream) -> isCompetition
    //             ? stream.filter(auto -> auto.getName().startsWith("comp"))
    //             : stream
    // );

    // SmartDashboard.putData("Auto Chooser", autoChooser);

  }

    private void configureBindings() {

        
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-m_joystick.getLeftY() * MaxSpeed/2) // Drive forward with negative Y (forward)
                    .withVelocityY(-m_joystick.getLeftX() * MaxSpeed/2) // Drive left with negative X (left)
                    .withRotationalRate(-m_joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        //brakes swerve when a is pressed
        //joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        //turns swerve modules based on left joystick input when b is pressed
        //joystick.b().whileTrue(drivetrain.applyRequest(() ->
            //point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        m_joystick.back().and(m_joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        m_joystick.back().and(m_joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        m_joystick.start().and(m_joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        m_joystick.start().and(m_joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on y press
        //joystick.y().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        m_joystick.leftTrigger().whileTrue(
            
            m_spindexter.runEnd(
                () -> m_spindexter.setMotorIndexer(1),
                () -> m_spindexter.setMotorIndexer(0)    
            )
        );

        drivetrain.registerTelemetry(logger::telemeterize);
        
        }

    

    public Command getAutonomousCommand() {
        return new PathPlannerAuto("");
    }
}

