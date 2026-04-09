// package frc.robot.Subsystems;

// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
// import com.revrobotics.spark.config.SparkMaxConfig;

// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import com.revrobotics.PersistMode;
// import com.revrobotics.ResetMode;

// import static frc.robot.Constants.Constants.IntakeConstants.*;

// public class IntakeSubsystem extends SubsystemBase {
    
//     // Motor for Intake
//     private final SparkMax m_IntakeNeo = new SparkMax(kIntakeMotorID, MotorType.kBrushless);
//     SparkMaxConfig m_IntakeConfig = new SparkMaxConfig();

//     public IntakeSubsystem() {

//         //  ------Intake Configs------
//         m_IntakeConfig
//             .inverted(true)
//             .idleMode(IdleMode.kBrake)
//             .smartCurrentLimit(20);
        
//         m_IntakeNeo.configure(m_IntakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

//     }

//     public void setMotorIntake(double RPM) {

//         //  Applies intake speed multiplier
//         double calculatedSpeedPercent = (RPM * kIntakeSpeedMultiplier) / kMaxRPM;

//         m_IntakeNeo.set(calculatedSpeedPercent);

//     }

//     /**
//      * Runs the intake continuously at the specified speed.
//      */
//     public Command runIntakeCommand(double speed) {

//         return this.run(() -> {
//             setMotorIntake(speed);
//         });
//     }

//     /**
//      * Stops the intake instantly.
//      */
//     public Command stopIntakeCommand() {

//         return this.runOnce(() -> {
//             setMotorIntake(0.0);
//         });
//     }

// }
