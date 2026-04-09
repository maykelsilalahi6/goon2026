package frc.robot.Constants;

/*
 * Note while tuning PID:
 * REVLib kV - DutyCycleOutput / Speed
 * Phoenix 6 kV - VelocityVoltage / Speed
 */
public class Constants {

    /*
     *  ------INTAKE------
     * -Intake: Neo 550 12:1 Gear
     * 
     */
    public static class IntakeConstants {

        //  Intake Motor IDs
        public static final int kIntakeMotorID = 18;

        //  Intake Current Limits
        public static final double kIntakeStatorCurrentLimit = 40.0;

        //  Intake Speed Multiplier
        public static final double kIntakeSpeedMultiplier = 0.5;

        //  Intake Ramp Time Value
        public static final double kIntakeRampTime = 1;

        //  Intake Gear Ratio/Max Speed Logic
        public static final double kIntakeConversionFactor = 1 / 12;
        public static final double kMaxSpeed = 11000.0 / 12; // Max Speed of Intake Roller in RPM

    }

    /*
     *  ------SPINDEXTER------
     * -Indexer: Neo 550 Direct
     * -Tunnel: Falcon 500 Direct
     * 
     */
    public static class SpindexterConstants {

        //  ------Spindexter Physical Measurements------
        public static final double kOmniwheelRadiusInInches = 3.0;
        public static final double kTunnelFlywheelRadiusInInches = 1.0;

        //  ------Spindexter Motor IDs------
        public static final int kIndexerMotorID = 17;
        public static final int kTunnelMotorID = 14;

        //  ------Spindexter Current Limits------
        public static final int kIndexerStatorCurrentLimit = 40;
        public static final int kIndexerSupplyCurrentLimit = 40;
        public static final int kTunnelStatorCurrentLimit = 40;
        public static final int kTunnelSupplyCurrentLimit = 40;

        //  ------Spindexter Speed Multipliers------
        public static final double kIndexerSpeedMultiplier = 1.0 / 3.0; // (ratio of Omniwheel r to Tunnel Flywheel r)
        public static final double kTunnelSpeedMultiplier = 1.0;

        //  ------Spindexter Ramp Time Value------
        public static final double kIndexerRampTime = 0.5;
        public static final double kTunnelRampTime = 0.5;

        //  ------Indexer PID Values------
        public static final double kIndexerV = 0.00009;
        public static final double kIndexerS = 0.0;
        public static final double kIndexerP = 0.0001;
        public static final double kIndexerI = 0.0; // Must be 0 because it doesn't need to go to a certain position
        public static final double kIndexerD = 0.0; // Must be 0 because it doesn't need to go to a certain position

        //  ------Tunnel PID Values------
        public static final double kTunnelV = 0.1128; // 12V / (6380 / 60)
        public static final double kTunnelP = 0.11;

    }

    //  2 Motors for Turret Subsystem
    public static class TurretConstants {

        //  Turret Motor IDs
        public static final int kTurretHoodMotorID = 19;
        public static final int kTurretBaseMotorID = 13;
        public static final int kTurretShooterMotorID = 15;

        //  Turret Current Limits
        public static final int kTurretHoodStatorCurrentLimit = 40;
        public static final int kTurretShooterStatorCurrentLimit = 40;
        public static final int kTurretBaseStatorCurrentLimit = 20;

        //  Turret Speed Multipliers
        public static final int kTurretHoodSpeedMultiplier = 1;
        public static final double kTurretShooterSpeedMultiplier = 1;
        public static final double kTurretBaseSpeedMultiplier = 1;

        //  Turret Base Angle Limitations
        public static final int kTurretBaseForwardSoftLimitThreshold = 10;
        public static final int kTurretBaseReverseSoftLimitThreshold = -10;

        //  Turret Shooter PV Values
        public static final double kShooterP = 0.12;
        public static final double kShooterV = 0.11;

        //  Turret Base PID Values
        public static final double kBaseP = 0.0015;
        public static final double kBaseI = 0.0;
        public static final double kBaseD = 0.0;

        //  Turret Hood PID Values
        public static final double kHoodP = 0.0015;
        public static final double kHoodI = 0.0;
        public static final double kHoodD = 0.0;

        //  Turret Hood Gear Ratio
        public static final double kHoodConversionFactor = 360.0 / 100.0; // Conversion Factor = 360 / Total Gear Ratio

    }

    public static class LimelightConstants {

        //  Limelight Specifications
        public static final double kLimelightHeightInInches = 17.5;
        public static final double kLimelightAngleinDegrees = 15;
    }

    public static class FieldConstants {

        //  Field Specifications
        public static final double kTowerHeightInInches =  72.0;

    }

}
