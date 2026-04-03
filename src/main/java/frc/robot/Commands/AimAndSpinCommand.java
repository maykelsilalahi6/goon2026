package frc.robot.Commands;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Turret.TurretBase;
import frc.robot.Subsystems.Turret.TurretHood;
import frc.robot.Subsystems.Turret.TurretShooter;

public class AimAndSpinCommand extends Command {
    private final TurretBase m_base;
    private final TurretHood m_hood;
    private final TurretShooter m_shooter;

    // Interpolation Maps (Distance -> Angle/Speed)
    private final InterpolatingDoubleTreeMap m_hoodMap = new InterpolatingDoubleTreeMap();
    private final InterpolatingDoubleTreeMap m_shooterMap = new InterpolatingDoubleTreeMap();

    public AimAndSpinCommand(TurretBase base, TurretHood hood, TurretShooter shooter) {
        m_base = base;
        m_hood = hood;
        m_shooter = shooter;

        // Require all three parts of the turret so nothing else interrupts them!
        addRequirements(m_base, m_hood, m_shooter);

        // Example Data Points (Distance in inches, Target Value)
        m_hoodMap.put(40.0, 10.0);  
        m_hoodMap.put(100.0, 35.0); 

        m_shooterMap.put(40.0, 40.0); // 40 Rotations Per Second
        m_shooterMap.put(100.0, 80.0); 
    }

    @Override
    public void execute() {
        // 1. Aim the base left/right at the AprilTag
        m_base.aimAtAprilTag();

        // 2. Get the distance
        double distance = m_base.getDistanceToTarget();

        // 3. Set Hood and Shooter based on distance
        if (distance != -1.0) {
            double targetHoodAngle = m_hoodMap.get(distance);
            double targetShooterRps = m_shooterMap.get(distance);

            m_hood.setTargetAngle(targetHoodAngle);
            m_shooter.setMotorShooter(targetShooterRps);
        } else {
            // Safe defaults if Limelight loses tracking
            m_hood.setTargetAngle(15.0); 
            m_shooter.setMotorShooter(20.0); // Keep wheel spinning slowly
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_base.stopTurret();
        m_hood.stopHood();
        m_shooter.setMotorShooter(0.0); // Assuming 0.0 stops the VelocityVoltage request
    }
}
