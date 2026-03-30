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

    //sets the Spindexter Motor speed
    public void setMotorSpindexter(double speed){
        var limitConfigs = new CurrentLimitsConfigs();
        SpindexterTalonFXConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        limitConfigs.StatorCurrentLimit = 160;
        limitConfigs.StatorCurrentLimitEnable = true;
        
        SpindexterTalonFX.set(-speed*.5);

        SpindexterTalonFX.getConfigurator().apply(limitConfigs);
    }

}
