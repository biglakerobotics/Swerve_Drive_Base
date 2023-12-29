package frc.robot.subsystems;

import java.util.function.Supplier;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.ClosedLoopOutputType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstantsFactory;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Calibrations;
import frc.robot.Constants;

/**
 * Class that extends the Phoenix SwerveDrivetrain class and implements subsystem
 * so it can be used in command-based projects easily.
 */
public class CommandSwerveDrivetrain extends SwerveDrivetrain implements Subsystem {

    // May need to update if calibrations require per-module gains
    private static final SwerveModuleConstantsFactory ConstantCreator = new SwerveModuleConstantsFactory()
        .withWheelRadius(Calibrations.Drivetrain.kWheelRadiusInches)
        .withSlipCurrent(Calibrations.Drivetrain.kSlipCurrentA)
        .withSteerMotorGains(Calibrations.Drivetrain.steerGains)
        .withDriveMotorGains(Calibrations.Drivetrain.driveGains)
        .withSpeedAt12VoltsMps(Calibrations.Drivetrain.kSpeedAt12VoltsMps)
        .withDriveMotorClosedLoopOutput(ClosedLoopOutputType.Voltage)
        .withSteerMotorClosedLoopOutput(ClosedLoopOutputType.Voltage)
        .withFeedbackSource(SwerveModuleConstants.SteerFeedbackType.RemoteCANcoder)
        .withDriveMotorGearRatio(Constants.Drivetrain.kDriveGearRatio)
        .withSteerMotorGearRatio(Constants.Drivetrain.kSteerGearRatio)
        .withCouplingGearRatio(Constants.Drivetrain.kCoupleRatio)
        .withSteerInertia(Constants.Drivetrain.kSteerInertia)
        .withDriveInertia(Constants.Drivetrain.kDriveInertia)
        .withSteerMotorInverted(Constants.Drivetrain.kSteerMotorReversed);

    private static final SwerveModuleConstants ModuleConstants[] = {
        ConstantCreator.createModuleConstants(Constants.Drivetrain.kFrontLeftSteerMotorId,  Constants.Drivetrain.kFrontLeftDriveMotorId,  Constants.Drivetrain.kFrontLeftEncoderId,  Constants.Drivetrain.kFrontLeftEncoderOffset,  Units.inchesToMeters(Constants.Drivetrain.kFrontLeftXPosInches),  Units.inchesToMeters(Constants.Drivetrain.kFrontLeftYPosInches),  Constants.Drivetrain.kInvertLeftSide),
        ConstantCreator.createModuleConstants(Constants.Drivetrain.kFrontRightSteerMotorId, Constants.Drivetrain.kFrontRightDriveMotorId, Constants.Drivetrain.kFrontRightEncoderId, Constants.Drivetrain.kFrontRightEncoderOffset, Units.inchesToMeters(Constants.Drivetrain.kFrontRightXPosInches), Units.inchesToMeters(Constants.Drivetrain.kFrontRightYPosInches), Constants.Drivetrain.kInvertRightSide),
        ConstantCreator.createModuleConstants(Constants.Drivetrain.kBackLeftSteerMotorId,   Constants.Drivetrain.kBackLeftDriveMotorId,   Constants.Drivetrain.kBackLeftEncoderId,   Constants.Drivetrain.kBackLeftEncoderOffset,   Units.inchesToMeters(Constants.Drivetrain.kBackLeftXPosInches),   Units.inchesToMeters(Constants.Drivetrain.kBackLeftYPosInches),   Constants.Drivetrain.kInvertLeftSide),
        ConstantCreator.createModuleConstants(Constants.Drivetrain.kBackRightSteerMotorId,  Constants.Drivetrain.kBackRightDriveMotorId,  Constants.Drivetrain.kBackRightEncoderId,  Constants.Drivetrain.kBackRightEncoderOffset,  Units.inchesToMeters(Constants.Drivetrain.kBackRightXPosInches),  Units.inchesToMeters(Constants.Drivetrain.kBackRightYPosInches),  Constants.Drivetrain.kInvertRightSide)
    };

    public CommandSwerveDrivetrain(double OdometryUpdateFrequency) {
        super(new SwerveDrivetrainConstants().withCANbusName(Constants.Drivetrain.kCANbusName).withPigeon2Id(Constants.Drivetrain.kBackLeftDriveMotorId), OdometryUpdateFrequency, ModuleConstants);
        this.updateMotionMagicConfig();
    }
    public CommandSwerveDrivetrain() {
        super(new SwerveDrivetrainConstants().withCANbusName(Constants.Drivetrain.kCANbusName).withPigeon2Id(Constants.Drivetrain.kBackLeftDriveMotorId), ModuleConstants);
        this.updateMotionMagicConfig();
    }

    public Command applyRequest(Supplier<SwerveRequest> requestSupplier) {
        return run(() -> this.setControl(requestSupplier.get()));
    }

    // The motion magic profile should be limited by calibrated values
    private void updateMotionMagicConfig() {
        MotionMagicConfigs talonMotionMagicConfig = new MotionMagicConfigs();
        talonMotionMagicConfig.MotionMagicCruiseVelocity = Calibrations.Drivetrain.SteerMotionMagicCruiseVelocity;
        talonMotionMagicConfig.MotionMagicAcceleration = Calibrations.Drivetrain.SteerMotionMagicAcceleration;
        for (int i = 0; i < this.ModuleCount; ++i) {
            this.Modules[1].getSteerMotor().getConfigurator().apply(talonMotionMagicConfig);
        }        
    }

    @Override
    public void simulationPeriodic() {
        /* Assume 20ms update rate, get battery voltage from WPILib */
        updateSimState(0.02, RobotController.getBatteryVoltage());
    }
}
