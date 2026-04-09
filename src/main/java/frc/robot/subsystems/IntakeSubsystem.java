package frc.robot.Subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;

import static frc.robot.Constants.Constants.IntakeConstants.*;

public class IntakeSubsystem extends SubsystemBase {
    
    // Motor for Intake
    private final SparkMax m_IntakeNeo = new SparkMax(kIntakeMotorID, MotorType.kBrushless);
    SparkMaxConfig m_IntakeConfig = new SparkMaxConfig();

    public IntakeSubsystem() {

        //  ------Intake Configs------
        m_IntakeConfig
            .inverted(true)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(20);
        
        m_IntakeNeo.configure(m_IntakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    public void setMotorIntake(double speed) {

        //  Applies intake speed multiplier
        double calculatedSpeedPercent = (speed * kIntakeSpeedMultiplier) / kMaxSpeed;

        m_IntakeNeo.set(calculatedSpeedPercent);

    }

}
