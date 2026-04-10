// package frc.robot.Subsystems.Turret;
// import com.ctre.phoenix6.configs.TalonFXConfiguration;
// import com.ctre.phoenix6.controls.VelocityVoltage;
// import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.signals.NeutralModeValue;

// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import static frc.robot.Constants.Constants.TurretConstants.*;

// public class TurretShooter extends SubsystemBase{
    
//     //  Motor for Turret Shooter
//     private final TalonFX m_turretShooterTalonFX = new TalonFX(kTurretShooterMotorID);
//     private final VelocityVoltage m_turretShooterVelocityRequest = new VelocityVoltage(0.0);
//     private TalonFXConfiguration m_turretShooterConfigs = new TalonFXConfiguration();

//     //  Motor configs
//     public TurretShooter() {

//         //  Turret Shooter configs
//         m_turretShooterConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
//         m_turretShooterConfigs.CurrentLimits.StatorCurrentLimit = kTurretShooterStatorCurrentLimit;
//         m_turretShooterConfigs.CurrentLimits.StatorCurrentLimitEnable = true;

//         //  Turret Shooter PV configs
//         m_turretShooterConfigs.Slot0.kP = kShooterP;
//         m_turretShooterConfigs.Slot0.kV = kShooterV;

//         //  Applies configs
//         m_turretShooterTalonFX.getConfigurator().apply(m_turretShooterConfigs);  

//     }

//     //  Sets the Turret Shooter Motor speed
//     public void setMotorShooter(double RPS) {

//         //  Applies turret shooter speed multiplier
//         double calculatedRPS = RPS * -kTurretShooterSpeedMultiplier;

//         //  Apply only the requested speed to the motor
//         m_turretShooterTalonFX.setControl(m_turretShooterVelocityRequest.withVelocity(calculatedRPS));
//     }

//     /**
//      * Runs the indexer continuously at the specified speed.
//      */
//     public Command runShooterCommand(double speed) {

//         return this.run(() -> {
//             setMotorShooter(speed);
//         });
//     }

//     /**
//      * Stops the indexer instantly.
//      */
//     public Command stopShooterCommand() {

//         return this.runOnce(() -> {
//             setMotorShooter(0.0);
//         });
//     }

// }


