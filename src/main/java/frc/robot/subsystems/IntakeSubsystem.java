package frc.robot.subsystems;


import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Constants.IntakeConstants.*;

public class IntakeSubsystem extends SubsystemBase {

    //motor and master configs for Intake
    private final TalonFX m_intakeTalonFX = new TalonFX(kIntakeMotorID);
    private final DutyCycleOut m_intakeMotorRequest = new DutyCycleOut(0.0);
    private TalonFXConfiguration m_intakeConfigs = new TalonFXConfiguration();

    public IntakeSubsystem() {

        //sets m_intakeConfigs
        m_intakeConfigs.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        m_intakeConfigs.CurrentLimits.StatorCurrentLimit = kIntakeStatorCurrentLimit;
        m_intakeConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
        m_intakeConfigs.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = kIntakeRampTime;

        m_intakeTalonFX.getConfigurator().apply(m_intakeConfigs);

    }
    
    //sets the Intake Motors' speed
    public void setMotorIntake(double speed) {
        double calculatedSpeed = speed * -kIntakeSpeedMultiplier;

        // Apply only the requested speed to the motor
        m_intakeTalonFX.setControl(m_intakeMotorRequest.withOutput(calculatedSpeed));
    }
    

}
