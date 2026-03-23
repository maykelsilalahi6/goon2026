package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ConveyerSubsystem extends SubsystemBase {

     //motors for Conveyer
    private final TalonFX conveyerTalonFX = new TalonFX(14);
    private TalonFXConfiguration ConveyerConfigs = new TalonFXConfiguration();

    //sets the Conveyer Motors' speed
    public void setMotorConveyer(double speed){
        var limitConfigs = new CurrentLimitsConfigs();
        ConveyerConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        limitConfigs.StatorCurrentLimit = 160;
        limitConfigs.StatorCurrentLimitEnable = true;
        
        conveyerTalonFX.set(-speed*.25);

        conveyerTalonFX.getConfigurator().apply(limitConfigs);
    }
    
}
