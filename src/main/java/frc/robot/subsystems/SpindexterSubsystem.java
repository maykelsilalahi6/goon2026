package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Constants.SpindexterConstants.*;

public class SpindexterSubsystem extends SubsystemBase {
    
    //  Master configs for Indexer
    private final TalonFX m_indexerTalonFX = new TalonFX(kIndexerMotorID);
    private final DutyCycleOut m_indexerMotorRequest = new DutyCycleOut(0.0);
    private TalonFXConfiguration m_indexerConfigs = new TalonFXConfiguration();

    //  Master configs for Tunnel
    private final TalonFX m_tunnelTalonFX = new TalonFX(kTunnelMotorID);
    private final DutyCycleOut m_tunnelMotorRequest = new DutyCycleOut(0.0);
    private TalonFXConfiguration m_tunnelConfigs = new TalonFXConfiguration();

    //  Motor configs
    public SpindexterSubsystem() {

        //  Indexer configs
        m_indexerConfigs.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        m_indexerConfigs.CurrentLimits.StatorCurrentLimit = kIndexerStatorCurrentLimit;
        m_indexerConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
        m_indexerConfigs.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = kIndexerRampTime;

        //  Tunnel configs
        m_tunnelConfigs.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        m_tunnelConfigs.CurrentLimits.StatorCurrentLimit = kIndexerStatorCurrentLimit;
        m_tunnelConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
        m_tunnelConfigs.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = kTunnelRampTime;

        //  Applies configs
        m_indexerTalonFX.getConfigurator().apply(m_indexerConfigs);
        m_tunnelTalonFX.getConfigurator().apply(m_tunnelConfigs);

    }

    //  Sets the Indexer Motor speed
    public void setMotorIndexer(double speed) {

        //  Applies indexer speed multiplier
        double calculatedSpeed = speed * -kIndexerSpeedMultiplier;

        //  Apply only the requested speed to the motor
        m_indexerTalonFX.setControl(m_indexerMotorRequest.withOutput(calculatedSpeed));
    }

    //  Sets the Tunnel Motor speed
    public void setMotorTunnel(double speed) {

        //  Applies tunnel speed multiplier
        double calculatedSpeed = speed * -kTunnelSpeedMultiplier;

        //  Apply only the requested speed to the motor
        m_tunnelTalonFX.setControl(m_tunnelMotorRequest.withOutput(calculatedSpeed));
    }

}
