package frc.robot;

import com.ctre.phoenix6.configs.Slot0Configs;

public class Calibrations {
    
    public class Drivetrain {
        // These are not the robot physical limits, rather, what the driver can handle
        // and prefers.
        // TODO: Write commands and provide the logging to collect the calibration data
        public static final double MaxSpeed = 6; // 6 meters per second desired top speed
        public static final double MaxAngularRate = Math.PI; // Half a rotation per second max angular velocity
        
        // These change per controller, so it's a good idea to measure on multiple controllers
        // and make sure whatever is used on the DS at competitions falls within these limits
        // TODO: Use Windows OS to take controller measurements
        public static final double DriverTranslationDeadband = 0.05; // Driver controller X/Y translational deadband
        public static final double DriverRotationalDeadband = 0.05; // Driver controller angular deadband

        // The stator current at which the wheels start to slip
        // TODO: Write commands and provide the logging to collect the calibration data
        public static final double kSlipCurrentA = 300.0;

        // When using open-loop drive control, this specifies the speed at which the robot travels
        // when driven with 12 volts, in meters per second. This is used to approximate the output
        // for a desired velocity. If using closed loop control, this value is ignored.
        // TODO: Write commands and provide the logging to collect the calibration data
        public static final double kSpeedAt12VoltsMps = 6.0;

        // Although spec'd for a diameter, the best thing to do is calibrate this using the internal
        // drive motor controller and physically measuring a driven distance. Although each wheel
        // will have a slightly different wheel radius, the effective average is what we're after.
        // TODO: Write commands and provide the logging to collect the calibration data
        public static final double kWheelRadiusInches = 4;

        // Every 1 rotation of the azimuth results in kCoupleRatio drive motor turns
        // TODO: Write commands and provide the logging to collect the calibration data
        public static final double kCoupleRatio = 3.5714285714285716;

        // The max acceleration and velocity should be measured several times on a per wheel
        // basis. Use the wheel with the lowest acceleration/velocity and reduce those values
        // by 10-20% to add in margin.
        // TODO: Write commands and provide the logging to collect the calibration data
        public static final double SteerMotionMagicCruiseVelocity = 100.0 / 21.428571428571427;
        public static final double SteerMotionMagicAcceleration = SteerMotionMagicCruiseVelocity / 0.100;

        // The steering gains are used to control the PIDF of the MotionMagic controller.
        // A simple P-controller is a good place to start. The values of the kS, kV, and kA terms
        // of the feedforward controller depend on the commutation type (FOC or trapezoidal) and
        // output type (voltage or amperes). For the default cases, trapezoidal with voltage
        // output is used. For this case, the kS (static friction in volts) and kV (back-emf in
        // volts) terms need to be measured/calibrated. The kA term is going to be very noisy
        // and can be igored. For the FOC case with output being in current, the kS term (in amps)
        // is the only feedforward term needed. The FOC contoller directly controls the back-emf,
        // so kV is a moot point. Again, kA will be noisy and can ignored.
        // TODO: Write commands and provide the logging to collect the calibration data
        // TODO: Assume gains are similar enough that we can use 1 set for all 4 modules
        public static final Slot0Configs steerGains = new Slot0Configs()
            .withKP(100.0).withKI(0.0).withKD(0.0)
            .withKS(0.0).withKV(0.0).withKA(0.0);

        // The drive gains are used to control the PIDF of the default VelocityVoltage controller.
        // The feedback and feedforward controllers are similar to the steering. There's also the
        // option to use FOC commutation control here too.
        // TODO: Write commands and provide the logging to collect the calibration data
        // TODO: Assume gains are similar enough that we can use 1 set for all 4 modules
        public static final Slot0Configs driveGains = new Slot0Configs()
            .withKP(3.0).withKI(0.0).withKD(0.0)
            .withKS(0.0).withKV(0.0).withKA(0.0);
    }


}
