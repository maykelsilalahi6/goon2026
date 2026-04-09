package frc.robot.Subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Constants.SpindexterConstants.*;

public class SpindexterSubsystem extends SubsystemBase {
    
    //  Master configs for Indexer
    private final TalonFX m_indexerTalonFX = new TalonFX(kIndexerMotorID);
    private final VelocityVoltage m_indexerVelocityRequest = new VelocityVoltage(0.0);
    private TalonFXConfiguration m_indexerConfigs = new TalonFXConfiguration();

    //  Master configs for Tunnel
    private final TalonFX m_tunnelTalonFX = new TalonFX(kTunnelMotorID);
    private final VelocityVoltage m_tunnelVelocityRequest = new VelocityVoltage(0.0);
    private TalonFXConfiguration m_tunnelConfigs = new TalonFXConfiguration();

    public SpindexterSubsystem() {

        //  ------Indexer Configs------
        m_indexerConfigs.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        m_indexerConfigs.CurrentLimits.StatorCurrentLimit = kIndexerStatorCurrentLimit;
        m_indexerConfigs.CurrentLimits.SupplyCurrentLimit = kIndexerSupplyCurrentLimit;
        m_indexerConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
        m_indexerConfigs.CurrentLimits.SupplyCurrentLimitEnable = true;
        m_indexerConfigs.ClosedLoopRamps.VoltageClosedLoopRampPeriod = kIndexerRampTime;

        //  ------Indexer PID------
        m_indexerConfigs.Slot0.kV = kIndexerV;
        m_indexerConfigs.Slot0.kP = kIndexerP;

        //  ------Tunnel Configs------
        m_tunnelConfigs.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        m_tunnelConfigs.CurrentLimits.StatorCurrentLimit = kTunnelStatorCurrentLimit;
        m_tunnelConfigs.CurrentLimits.SupplyCurrentLimit = kTunnelSupplyCurrentLimit;
        m_tunnelConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
        m_tunnelConfigs.CurrentLimits.SupplyCurrentLimitEnable = true;
        m_tunnelConfigs.ClosedLoopRamps.VoltageClosedLoopRampPeriod = kTunnelRampTime;
        
        //  ------Tunnel PID------
        m_tunnelConfigs.Slot0.kV = kTunnelV;
        m_tunnelConfigs.Slot0.kP = kTunnelP;

        //  ------Applies Configs------
        m_indexerTalonFX.getConfigurator().apply(m_indexerConfigs);
        m_tunnelTalonFX.getConfigurator().apply(m_tunnelConfigs);

    }

    //  Sets the Indexer Motor speed
    public void setMotorIndexer(double RPS) {

        //  Applies indexer speed multiplier
        double calculatedRPS = RPS * -kIndexerSpeedMultiplier;

        //  Apply only the requested speed to the motor
        m_indexerTalonFX.setControl(m_indexerVelocityRequest.withVelocity(calculatedRPS));
    }

    //  Sets the Tunnel Motor speed
    public void setMotorTunnel(double RPS) {

        //  Applies tunnel speed multiplier
        double calculatedRPS = RPS * kTunnelSpeedMultiplier;

        //  Apply only the requested speed to the motor
        m_tunnelTalonFX.setControl(m_tunnelVelocityRequest.withVelocity(calculatedRPS));
    }

    /**
     * Runs the indexer continuously at the specified speed.
     */
    public Command runSpindexterCommand(double speed) {

        return this.run(() -> {
            setMotorIndexer(speed);
            setMotorTunnel(speed);
        });
    }

    /**
     * Stops the indexer instantly.
     */
    public Command stopSpindexterCommand() {

        return this.runOnce(() -> {
            setMotorIndexer(0.0);
            setMotorTunnel(0.0);
        });
    }

}
