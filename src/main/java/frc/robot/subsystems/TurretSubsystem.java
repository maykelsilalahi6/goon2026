package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Constants.TurretConstants.*;

public class TurretSubsystem extends SubsystemBase{
    
    //  Motor for Turret Shooter
    private final TalonFX m_turretShooterTalonFX = new TalonFX(kTurretShooterMotorID);
    private final DutyCycleOut m_turretShooterMotorRequest = new DutyCycleOut(0.0);
    private TalonFXConfiguration m_turretShooterConfigs = new TalonFXConfiguration();

    //  Motor for Turret Base

    //  Motor configs
    public TurretSubsystem() {

        //  Turret Shooter configs
        m_turretShooterConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        m_turretShooterConfigs.CurrentLimits.StatorCurrentLimit = kTurretShooterStatorCurrentLimit;
        m_turretShooterConfigs.CurrentLimits.StatorCurrentLimitEnable = true;

        //  Turret Base configs

        //  Applies configs
        m_turretShooterTalonFX.getConfigurator().apply(m_turretShooterConfigs);  


    }

    //  Sets the Turret Shooter Motor speed
    public void setMotorShooter(double speed) {

        //  Applies turret shooter speed multiplier
        double calculatedSpeed = speed * -kTurretShooterSpeedMultiplier;

        //  Apply only the requested speed to the motor
        m_turretShooterTalonFX.setControl(m_turretShooterMotorRequest.withOutput(calculatedSpeed));
    }


}


