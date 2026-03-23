package frc.robot.subsystems;


import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    //motors for Intake
    private final TalonFX intakeTalonFX = new TalonFX(16);
    private TalonFXConfiguration IntakeConfigs = new TalonFXConfiguration();

    //sets the Intake Motors' speed
    public void setMotorIntake(double speed){
        var limitConfigs = new CurrentLimitsConfigs();
        IntakeConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        limitConfigs.StatorCurrentLimit = 160;
        limitConfigs.StatorCurrentLimitEnable = true;
        
        intakeTalonFX.set(-speed*.5);

        intakeTalonFX.getConfigurator().apply(limitConfigs);
    }
    

}
