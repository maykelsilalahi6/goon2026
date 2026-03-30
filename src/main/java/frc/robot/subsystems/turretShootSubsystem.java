package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Constants.TurretConstants.*;

public class turretShootSubsystem extends SubsystemBase{
    
    //motors for Shoot
    private final TalonFX shootTalonFX = new TalonFX(kTurretShooterMotorID);
    
    private TalonFXConfiguration shootConfigs = new TalonFXConfiguration();
    private Slot0Configs shootSlot0 = shootConfigs.Slot0;

    //sets the Shoot Motors' speed
    public void setMotorShot(double speed){
        var limitConfigs = new CurrentLimitsConfigs();
        shootConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        limitConfigs.StatorCurrentLimit = 160;
        limitConfigs.StatorCurrentLimitEnable = true;
        
        shootTalonFX.set(-speed*.25);

        double shootkG = 0.3;
        double shootkS = 0.25;
        double shootkV = 1.0;
        double shootkA = 0.5;
        double shootkP = 5.2;
        double shootkI = 0.0;
        double shootkD = 0.2;

        shootSlot0.kG = shootkG;
        shootSlot0.kS = shootkS;
        shootSlot0.kV = shootkV;
        shootSlot0.kA = shootkA;
        shootSlot0.kP = shootkP;
        shootSlot0.kI = shootkI;
        shootSlot0.kD = shootkD;

        shootTalonFX.getConfigurator().apply(shootSlot0);
        shootTalonFX.getConfigurator().apply(limitConfigs);


        

    }
}


