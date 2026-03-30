package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Constants.SpindexterConstants.*;

public class SpindexterSubsystem extends SubsystemBase {
    
    //motor for Indexer
    private final TalonFX m_indexerTalonFX = new TalonFX(kIndexerMotorID);
    private TalonFXConfiguration m_indexerConfigs = new TalonFXConfiguration();

    //motor for Tunnel
    private final TalonFX m_tunnelTalonFX = new TalonFX(kTunnelMotorID);
    private TalonFXConfiguration m_tunnelConfigs = new TalonFXConfiguration();

    //DutyCycleOutputs for both Indexer and Tunnel
    private final DutyCycleOut m_indexerMotorRequest = new DutyCycleOut(0.0);
    private final DutyCycleOut m_tunnelMotorRequest = new DutyCycleOut(0.0);

    public SpindexterSubsystem() {

        //Indexer configs
        m_indexerConfigs.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        m_indexerConfigs.CurrentLimits.StatorCurrentLimit = kIndexerStatorCurrentLimit;
        m_indexerConfigs.CurrentLimits.StatorCurrentLimitEnable = true;

        //Tunnel configs
        m_tunnelConfigs.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        m_tunnelConfigs.CurrentLimits.StatorCurrentLimit = kIndexerStatorCurrentLimit;
        m_tunnelConfigs.CurrentLimits.StatorCurrentLimitEnable = true;

        //Applies configs
        m_indexerTalonFX.getConfigurator().apply(m_indexerConfigs);
        m_tunnelTalonFX.getConfigurator().apply(m_tunnelConfigs);

    }

    //sets the Indexer Motor's speed
    public void setMotorIndexer(double speed) {
        double calculatedSpeed = speed * -kIndexerSpeedMultiplier;

        // Apply only the requested speed to the motor
        m_indexerTalonFX.setControl(m_indexerMotorRequest.withOutput(calculatedSpeed));
    }

    public void setMotorTunnel(double speed) {
        double calculatedSpeed = speed * -kTunnelSpeedMultiplier;

        // Apply only the requested speed to the motor
        m_tunnelTalonFX.setControl(m_tunnelMotorRequest.withOutput(calculatedSpeed));
    }

}
