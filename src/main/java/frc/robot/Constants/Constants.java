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

        // Spindexter Motor IDs
        public static final int kIndexerMotorID = 16;
        public static final int kTunnelMotorID = 16;

        //  Spindexter Current Limits
        public static final int kIndexerStatorCurrentLimit = 40;
        public static final int kTunnelStatorCurrentLimit = 40;

        //  Spindexter Speed Multipliers
        public static final double kIndexerSpeedMultiplier = 1;
        public static final double kTunnelSpeedMultiplier = 1;

        //  Spindexter Ramp Time Value
        public static final double kIndexerRampTime = 0.5;
        public static final double kTunnelRampTime = 0.5;
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
    }
}
