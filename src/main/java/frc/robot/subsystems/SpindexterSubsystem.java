package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SpindexterSubsystem extends SubsystemBase {
    
    //motor for Spindexter
    private final TalonFX SpindexterTalonFX = new TalonFX(13); //replace with deviceID
    private TalonFXConfiguration SpindexterTalonFXConfigs = new TalonFXConfiguration();

    //motor for tunnel
    private final TalonFX TunnelTalonFX = new TalonFX(15); //replace with deviceID
    private TalonFXConfiguration TunnelTalonFXConfigs = new TalonFXConfiguration();

    //sets the Spindexter Motor speed
    public void setMotorSpindexter(double speed){
        var spindexterLimitConfigs = new CurrentLimitsConfigs();
        SpindexterTalonFXConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        spindexterLimitConfigs.StatorCurrentLimit = 160;
        spindexterLimitConfigs.StatorCurrentLimitEnable = true;
        
        SpindexterTalonFX.set(-speed*.5);

        SpindexterTalonFX.getConfigurator().apply(spindexterLimitConfigs);
    }

    //sets the Tunnel Motor speed
    public void setMotorTunnel(double speed){
        var tunnelLimitConfigs = new CurrentLimitsConfigs();
        TunnelTalonFXConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        tunnelLimitConfigs.StatorCurrentLimit = 160;
        tunnelLimitConfigs.StatorCurrentLimitEnable = true;
        
        TunnelTalonFX.set(-speed*.5);

        TunnelTalonFX.getConfigurator().apply(tunnelLimitConfigs);
    }

}
