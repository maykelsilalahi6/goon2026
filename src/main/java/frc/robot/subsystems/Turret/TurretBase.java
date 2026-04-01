package frc.robot.Subsystems.Turret;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Constants.TurretConstants.*;

public class TurretBase extends SubsystemBase{
    
    //  Motor for Turret Base
    private final TalonFX m_turretBaseTalonFX = new TalonFX(kTurretBaseMotorID);
    private final DutyCycleOut m_turretBaseMotorRequest = new DutyCycleOut(0.0);
    private TalonFXConfiguration m_turretBaseConfigs = new TalonFXConfiguration();

    //  PID Controller for Turret Base
    private final PIDController m_turretBasePID = new PIDController(kBaseP, kBaseI, kBaseD);

    //  Limelight Table
    private final NetworkTable m_limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

    public TurretBase() {

        //  Turret Base configs
        m_turretBaseConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        m_turretBaseConfigs.CurrentLimits.StatorCurrentLimit = kTurretBaseStatorCurrentLimit;
        m_turretBaseConfigs.CurrentLimits.StatorCurrentLimitEnable = true;

        //  Turret Base limitation configs
        m_turretBaseConfigs.SoftwareLimitSwitch.ForwardSoftLimitThreshold = kTurretBaseForwardSoftLimitThreshold;
        m_turretBaseConfigs.SoftwareLimitSwitch.ReverseSoftLimitThreshold = kTurretBaseReverseSoftLimitThreshold;
        m_turretBaseConfigs.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        m_turretBaseConfigs.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;

        m_turretBaseTalonFX.getConfigurator().apply(m_turretBaseConfigs);

        //  Applies tolerance
        m_turretBasePID.setTolerance(0.5);

    }

    /**
     *  Call this method continuously (e.g., while a button is held) 
     *  to track a specific AprilTag ID.
     */
    public void aimAtAprilTag(int targetID) {

        //  Read values from the Limelight
        double tv = m_limelightTable.getEntry("tv").getDouble(0.0);
        double tx = m_limelightTable.getEntry("tx").getDouble(0.0);
        double tid = m_limelightTable.getEntry("tid").getDouble(-1.0);

        //  Do we see a target AND is it the correct AprilTag?
        if (tv == 1.0 && tid == targetID) {
            
            //  Calculate the motor speed needed to bring tx to 0
            double pidOutput = m_turretBasePID.calculate(tx, 0.0);

            //  Clamp the speed to a safe maximum (e.g., +/- 30% power)
            double safeSpeed = Math.max(-0.3, Math.min(0.3, pidOutput));

            //  Spin the turret
            m_turretBaseTalonFX.setControl(m_turretBaseMotorRequest.withOutput(safeSpeed));
            
        } else {
            //  If we don't see the tag, stop the motor immediately
            stopTurret();
        }
    }

    public void stopTurret() {
        m_turretBaseTalonFX.setControl(m_turretBaseMotorRequest.withOutput(0.0));
    }

}
