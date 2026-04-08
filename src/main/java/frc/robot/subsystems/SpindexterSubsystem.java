package frc.robot.Subsystems;

//  ------Phoenix 6 Imports------
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

//  ------REV Robotics Imports------
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

//  ------Spindexter Constants Imports------
import static frc.robot.Constants.Constants.SpindexterConstants.*;

//  ------Other Imports------
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SpindexterSubsystem extends SubsystemBase {
    
    //   ------Master Configs/Initialization for Indexer------
    private final SparkMax m_IndexerNeo = new SparkMax(kIndexerMotorID, MotorType.kBrushless);
    SparkMaxConfig m_IndexerConfig = new SparkMaxConfig();

    //  ------Master Configs/Initialization for Tunnel------
    private final TalonFX m_tunnelTalonFX = new TalonFX(kTunnelMotorID);
    private final VelocityVoltage m_tunnelVelocityRequest = new VelocityVoltage(0.0);
    private TalonFXConfiguration m_tunnelConfigs = new TalonFXConfiguration();

    public SpindexterSubsystem() {

        //  ------Indexer Configs------
        m_IndexerConfig
            .inverted(true)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(20);
        
        //  ------Indexer PID------
        m_IndexerConfig.closedLoop
            .p(kIndexerP)
            .i(kIndexerI)
            .d(kIndexerD)
            .feedForward
                .kV(kIndexerV)
                .kS(kIndexerS);

        //  ------Tunnel Configs------
        m_tunnelConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        m_tunnelConfigs.CurrentLimits.StatorCurrentLimit = kTunnelStatorCurrentLimit;
        m_tunnelConfigs.CurrentLimits.SupplyCurrentLimit = kTunnelSupplyCurrentLimit;
        m_tunnelConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
        m_tunnelConfigs.CurrentLimits.SupplyCurrentLimitEnable = true;
        m_tunnelConfigs.ClosedLoopRamps.VoltageClosedLoopRampPeriod = kTunnelRampTime;
        
        //  ------Tunnel PID------
        m_tunnelConfigs.Slot0.kV = kTunnelV;
        m_tunnelConfigs.Slot0.kP = kTunnelP;

        //  ------Apply Configs------
        m_IndexerNeo.configure(m_IndexerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_tunnelTalonFX.getConfigurator().apply(m_tunnelConfigs);

    }

    //  ------Setting Indexer Surface Speed------
    public void setMotorIndexer(double targetRPS) {
        double calculatedRPM = (targetRPS * 60) * kIndexerSpeedMultiplier; // REVLib uses RPM
        m_IndexerNeo.set(calculatedRPM);
    }

    //  ------Setting Tunnel Speed------
    public void setMotorTunnel(double targetRPS) {
        double calculatedRPS = targetRPS * -kTunnelSpeedMultiplier;
        m_tunnelTalonFX.setControl(m_tunnelVelocityRequest.withVelocity(calculatedRPS)); // Phoenix 6 uses RPS
    }

}
