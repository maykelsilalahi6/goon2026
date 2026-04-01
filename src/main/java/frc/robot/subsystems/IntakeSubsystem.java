package frc.robot.Subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Constants.IntakeConstants.*;

public class IntakeSubsystem extends SubsystemBase {

    //  Master configs for Intake
    private final TalonFX m_intakeTalonFX = new TalonFX(kIntakeMotorID);
    private final DutyCycleOut m_intakeMotorRequest = new DutyCycleOut(0.0);
    private TalonFXConfiguration m_intakeConfigs = new TalonFXConfiguration();

    //  Motor configs
    public IntakeSubsystem() {

        //  Intake configs
        m_intakeConfigs.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        m_intakeConfigs.CurrentLimits.StatorCurrentLimit = kIntakeStatorCurrentLimit;
        m_intakeConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
        m_intakeConfigs.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = kIntakeRampTime;

        //  Applies config
        m_intakeTalonFX.getConfigurator().apply(m_intakeConfigs);

    }
    
    //  Sets the Intake Motor speed
    public void setMotorIntake(double speed) {

        //  Applies intake speed multiplier
        double calculatedSpeed = speed * -kIntakeSpeedMultiplier;

        //  Apply only the requested speed to the motor
        m_intakeTalonFX.setControl(m_intakeMotorRequest.withOutput(calculatedSpeed));
    }
    

}
