package frc.robot;


public class Constants {

    // Constants for the drivetrain
    public class Drivetrain {
        // I would leave these in fractional form...easier to check if you have L1/L2/L3
        // https://www.swervedrivespecialties.com/products/mk4i-swerve-module
        public static final double kDriveGearRatio = 6.746031746031747;
        public static final double kSteerGearRatio = 21.428571428571427;

        public static final boolean kSteerMotorReversed = true;
        public static final boolean kInvertLeftSide = false;
        public static final boolean kInvertRightSide = true;
        public static final String kCANbusName = "CANivore";
        public static final int kPigeonId = 1;

        // Front Left
        public static final int kFrontLeftDriveMotorId = 21;
        public static final int kFrontLeftSteerMotorId = 22;
        public static final int kFrontLeftEncoderId = 23;
        public static final double kFrontLeftEncoderOffset = -0.12939453125;
        public static final double kFrontLeftXPosInches = 10;
        public static final double kFrontLeftYPosInches = 10;

        // Front Right
        public static final int kFrontRightDriveMotorId = 11;
        public static final int kFrontRightSteerMotorId = 12;
        public static final int kFrontRightEncoderId = 13;
        public static final double kFrontRightEncoderOffset = 0.331298828125;
        public static final double kFrontRightXPosInches = 10;
        public static final double kFrontRightYPosInches = -10;

        // Back Left
        public static final int kBackLeftDriveMotorId = 31;
        public static final int kBackLeftSteerMotorId = 32;
        public static final int kBackLeftEncoderId = 33;
        public static final double kBackLeftEncoderOffset = -0.233154296875;
        public static final double kBackLeftXPosInches = -10;
        public static final double kBackLeftYPosInches = 10;

        // Back Right
        public static final int kBackRightDriveMotorId = 41;
        public static final int kBackRightSteerMotorId = 42;
        public static final int kBackRightEncoderId = 43;
        public static final double kBackRightEncoderOffset = 0.27587890625;
        public static final double kBackRightXPosInches = -10;
        public static final double kBackRightYPosInches = -10;
        
        // These are only used for simulation
        public static final double kSteerInertia = 0.00001;
        public static final double kDriveInertia = 0.001;
    }

}
