package frc.robot.Subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Constants.SpindexterConstants.*;

public class SpindexterSubsystem extends SubsystemBase {
    
    //  Master configs for Indexer
    private final SparkMax m_IndexerNeo = new SparkMax(kIndexerMotorID, MotorType.kBrushless);
    SparkMaxConfig m_IndexerConfig = new SparkMaxConfig();

    //  Master configs for Tunnel
    private final TalonFX m_tunnelTalonFX = new TalonFX(kTunnelMotorID);
    private final VelocityVoltage m_tunnelVelocityRequest = new VelocityVoltage(0.0);
    private TalonFXConfiguration m_tunnelConfigs = new TalonFXConfiguration();

    public SpindexterSubsystem() {

        //  ------Indexer Configs------
        m_IndexerConfig
            .inverted(true)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(20);

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
        m_IndexerNeo.configure(m_IndexerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_tunnelTalonFX.getConfigurator().apply(m_tunnelConfigs);

    }

    //  Sets the Indexer Motor speed
    public void setMotorIndexer(double speed) {

        //  Applies indexer speed multiplier
        double calculatedSpeed = speed * -kIndexerSpeedMultiplier;

        //  Apply only the requested speed to the motor
        m_IndexerNeo.set(calculatedSpeed);
    }

    //  Sets the Tunnel Motor speed
    public void setMotorTunnel(double speed) {

        //  Applies tunnel speed multiplier
        double calculatedSpeed = speed * -kTunnelSpeedMultiplier;

        //  Apply only the requested speed to the motor
        m_tunnelTalonFX.setControl(m_tunnelVelocityRequest.withVelocity(calculatedSpeed));
    }

}
