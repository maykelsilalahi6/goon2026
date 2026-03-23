package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RollerSubsystem extends SubsystemBase {
    
    //motors for Roller
    private final TalonFX rollerTalonFX = new TalonFX(13);
    private TalonFXConfiguration RollerConfigs = new TalonFXConfiguration();

    //sets the Intake Motors' speed
    public void setMotorRoller(double speed){
        var limitConfigs = new CurrentLimitsConfigs();
        RollerConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        limitConfigs.StatorCurrentLimit = 160;
        limitConfigs.StatorCurrentLimitEnable = true;
        
        rollerTalonFX.set(-speed*.5);

        rollerTalonFX.getConfigurator().apply(limitConfigs);
    }

}
