// package frc.robot.Subsystems.Turret;

// import edu.wpi.first.math.filter.Debouncer;
// import edu.wpi.first.math.filter.Debouncer.DebounceType;
// import edu.wpi.first.networktables.NetworkTable;
// import edu.wpi.first.networktables.NetworkTableInstance;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import com.revrobotics.spark.SparkBase.ControlType;
// import com.revrobotics.spark.SparkClosedLoopController;
// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.spark.config.SparkMaxConfig;
// import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

// import static frc.robot.Constants.Constants.TurretConstants.*;
// import static frc.robot.Constants.Constants.FieldConstants.*;
// import static frc.robot.Constants.Constants.LimelightConstants.*;

// import com.revrobotics.PersistMode;
// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.ResetMode;

// public class TurretHood extends SubsystemBase{
    
//     //  Motor for Turret Hood
//     private final SparkMax m_TurretHoodNeo = new SparkMax(kTurretHoodMotorID, MotorType.kBrushless);
//     SparkMaxConfig m_TurretHoodConfig = new SparkMaxConfig();

//     //  PID Controller for Turret Hood
//     private final SparkClosedLoopController m_HoodPIDController = m_TurretHoodNeo.getClosedLoopController();
//     private final RelativeEncoder m_TurretHoodEncoder = m_TurretHoodNeo.getEncoder();

//     //  Limelight Table Set-Up
//     private final NetworkTable m_limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

//     public TurretHood() {

//         //  Gear Ratio Logic
//         m_TurretHoodConfig.encoder.positionConversionFactor(kHoodConversionFactor);

//         //  Hood Soft Limits
//         m_TurretHoodConfig.softLimit
//             .forwardSoftLimit(60)
//             .reverseSoftLimit(0)
//             .forwardSoftLimitEnabled(true)
//             .reverseSoftLimitEnabled(true);

//         m_TurretHoodConfig.closedLoop
//             .pid(kHoodP, kHoodI, kHoodD);

//         m_TurretHoodConfig
//             .inverted(false)
//             .idleMode(IdleMode.kBrake);

//         m_TurretHoodNeo.configure(m_TurretHoodConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

//     }

//     /**
//      * Calculates the horizontal distance to the target in inches.
//      * Returns -1.0 if no target is currently in view.
//      */
//     public double getDistanceToTarget() {

//         double tv = m_limelightTable.getEntry("tv").getDouble(0.0);
        
//         if (tv == 0.0) {
//             return -1.0; 
//         }

//         // Add mount and limelight offset angle
//         double ty = m_limelightTable.getEntry("ty").getDouble(0.0);
//         double totalAngleDegrees = kLimelightAngleinDegrees + ty;
//         double angleRadians = Math.toRadians(totalAngleDegrees);

//         //  Distance Formula
//         double distanceInches = (kTowerHeightInInches - kLimelightHeightInInches) / Math.tan(angleRadians);
//         return distanceInches;

//     }

//     public Command homeHoodCommand() {
        
//         //  Debouncer to check if current spikes in 0.1 seconds
//         Debouncer spikeDebouncer = new Debouncer(0.1, DebounceType.kRising);

//         return run(() -> {
//             //  Run motor backwards into hard stop
//             m_TurretHoodNeo.setVoltage(-1.5); 
//         })
//         .until(() -> {
//             //  Check if  motor is pulling more than 20 Amps
//             boolean isSpiking = m_TurretHoodNeo.getOutputCurrent() > 20.0;
//             return spikeDebouncer.calculate(isSpiking);
//         })
//         .finallyDo((interrupted) -> {
//             //  When the command ends (because it hit the wall), stop the motor
//             m_TurretHoodNeo.set(0);
            
//             //  Only reset the encoder if the command finished naturally (not canceled)
//             if (!interrupted) {
//                 m_TurretHoodEncoder.setPosition(0);
//             }
//         });
//     }

//     // --- AIMING METHOD ---
//     public void setTargetAngle(double targetDegrees) {
//         // Tells the Spark Max to use PID to glide to the target position
//         m_HoodPIDController.setSetpoint(targetDegrees, ControlType.kPosition);
//     }

//     public void stopHood() {
//         m_TurretHoodNeo.set(0.0);
//     }

    

// }
