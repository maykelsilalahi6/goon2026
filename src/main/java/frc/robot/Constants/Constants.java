package frc.robot.Constants;

public class Constants {

    //  1 Motor for the Intake Subsystem
    public static class IntakeConstants {

        //  Intake Motor IDs
        public static final int kIntakeMotorID = 16;

        //  Intake Current Limits
        public static final double kIntakeStatorCurrentLimit = 40.0;

        //  Intake Speed Multiplier
        public static final double kIntakeSpeedMultiplier = 0.5;

        //  Intake Ramp Time Value
        public static final double kIntakeRampTime = 1;

    }

    //  2 Motors for the Spindexter Subsystem
    public static class SpindexterConstants {

        //Spindexter Physical Measurements
        public static final double kOmniwheelRadiusInInches = 3.0;
        public static final double kTunnelFlywheelRadiusInInches = 1.0;

        // Spindexter Motor IDs
        public static final int kIndexerMotorID = 16;
        public static final int kTunnelMotorID = 16;

        //  Spindexter Current Limits
        public static final int kIndexerStatorCurrentLimit = 40;
        public static final int kIndexerSupplyCurrentLimit = 40;
        public static final int kTunnelStatorCurrentLimit = 40;
        public static final int kTunnelSupplyCurrentLimit = 40;

        //  Spindexter Speed Multipliers
        public static final double kIndexerSpeedMultiplier = 1;
        public static final double kTunnelSpeedMultiplier = 1;

        //  Spindexter Ramp Time Value
        public static final double kIndexerRampTime = 0.5;
        public static final double kTunnelRampTime = 0.5;

        //  ------Spindexter PID Values------

        //  Indexer PID Values
        public static final double kIndexerV = 0.12;
        public static final double kIndexerP = 0.11;
        public static final double kIndexerI = 0.0;
        public static final double kIndexerD = 0.0;

        //  Tunnel PID Values
        public static final double kTunnelV = 0.12;
        public static final double kTunnelP = 0.11;
        public static final double kTunnelI = 0.0;
        public static final double kTunnelD = 0.0;

    }

    //  2 Motors for Turret Subsystem
    public static class TurretConstants {

        //  Turret Motor IDs
        public static final int kTurretBaseMotorID = 16;
        public static final int kTurretShooterMotorID = 15;

        //  Turret Current Limits
        public static final int kTurretShooterStatorCurrentLimit = 40;
        public static final int kTurretBaseStatorCurrentLimit = 20;

        //  Turret Speed Multipliers
        public static final double kTurretShooterSpeedMultiplier = 1;
        public static final double kTurretBaseSpeedMultiplier = 1;

        //  Turret Base Angle Limitations
        public static final int kTurretBaseForwardSoftLimitThreshold = 10;
        public static final int kTurretBaseReverseSoftLimitThreshold = -10;

        //  Turret Base PID values
        public static final double kP = 0.0015;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
    }
}
