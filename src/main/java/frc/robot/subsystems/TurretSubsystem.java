package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Constants.TurretConstants.*;

public class TurretSubsystem extends SubsystemBase{
    
    //motors for Shoot
    private final TalonFX shootTalonFX = new TalonFX(kTurretShooterMotorID);
    
    private TalonFXConfiguration shootConfigs = new TalonFXConfiguration();

    //sets the Shoot Motors' speed
    public void setMotorShot(double speed){
        var limitConfigs = new CurrentLimitsConfigs();
        shootConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        limitConfigs.StatorCurrentLimit = 160;
        limitConfigs.StatorCurrentLimitEnable = true;
        
        shootTalonFX.set(-speed*.25);

        shootTalonFX.getConfigurator().apply(limitConfigs);        

    }
}


