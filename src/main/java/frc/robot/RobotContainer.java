// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Constants.TunerConstants;
import frc.robot.Subsystems.CommandSwerveDrivetrain;
//import frc.robot.Subsystems.IntakeSubsystem;




public class RobotContainer {

    private final SendableChooser<Command> autoChooser;
    boolean fieldcentriccount = true;

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

    private final SlewRateLimiter m_accelLimiter = new SlewRateLimiter(1.0);
        private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(0.25);
    private double m_lastJoystickMag = 0.0;

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    private Translation2d getLimitedTranslation(double xInput, double yInput) {

        double currentMag = Math.hypot(xInput, yInput);

        double limitedMag;
        if (currentMag < m_lastJoystickMag) {

            m_accelLimiter.reset(currentMag);
            limitedMag = currentMag;

        } else {

            limitedMag = m_accelLimiter.calculate(currentMag);

        }

        m_lastJoystickMag = limitedMag;

        if (currentMag == 0.0) {
            return new Translation2d(0.0, 0.0);
            };

            double ratio = limitedMag / currentMag;
            return new Translation2d(xInput * ratio, yInput * ratio);
        }

private Rotation2d getLimitedRotation(double aInput) {
    // 1. Get the true magnitude (0.0 to 1.0)
    double currentMag = Math.abs(aInput);
    double limitedMag;

    // 2. Compare magnitudes to see if we are decelerating
    if (currentMag < m_lastJoystickMag) {
        // We are letting go of the stick. Instantly drop speed and reset limiter.
        m_accelLimiter.reset(currentMag);
        limitedMag = currentMag;
    } else {
        limitedMag = m_rotLimiter.calculate(currentMag);
    }

    m_lastJoystickMag = limitedMag;

    double limitedInput = limitedMag * Math.signum(aInput);

    return new Rotation2d(limitedInput * Math.PI); 
}

    public RobotContainer() {

        //NamedCommands.registerCommand("sweepRunIntake", m_IntakeSubsystem.runIntakeCommand(200).withTimeout(5));

        configureBindings();

        // For convenience a programmer could change this when going to competition.
        boolean isCompetition = true;

        // Build an auto chooser. This will use Commands.none() as the default option.
        // As an example, this will only show autos that start with "comp" while at
        // competition as defined by the programmer
        autoChooser = AutoBuilder.buildAutoChooserWithOptionsModifier(
            (stream) -> isCompetition
                ? stream.filter(auto -> auto.getName().startsWith("comp"))
                : stream
    );

    SmartDashboard.putData("Auto Chooser", autoChooser);

  }

    private void configureBindings() {

        
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->

                drive.withVelocityX(getLimitedTranslation(-m_joystick.getLeftY(), -m_joystick.getLeftX()).getX() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(getLimitedTranslation(-m_joystick.getLeftY(), -m_joystick.getLeftX()).getY() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(getLimitedRotation(-m_joystick.getRightX()).getRadians() * MaxAngularRate) // Drive counterclockwise with negative X (left)
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
        m_joystick.y().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);

        }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}

