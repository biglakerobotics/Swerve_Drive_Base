// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.SteerRequestType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class RobotContainer {
  
    CommandXboxController joystick = new CommandXboxController(0);
    CommandSwerveDrivetrain drivetrain = new CommandSwerveDrivetrain();
    SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric(); // Field-centric driving defaults to open-loop
    SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake(); // I don't think this is working (only holds current position)
    SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt(); // What good is this?
    Telemetry logger = new Telemetry(Calibrations.Drivetrain.MaxSpeed); // See comments below on telemetry logging

    private void configureBindings() {
  
        // The default drivetrain command is always set to the driver
        drivetrain.setDefaultCommand(

            // Add all of the options for clarity and a deeper unstanding of what's being done
            // Moved all calibration parameters to the Calibrations class
            // TODO: Characterize the controller translational deadband (use the max of both axis)
            // TODO: Characterize the controller rotational deadband (this is a single axis)
            // TODO: Default drive request is OpenLoopVoltage, the other choice is velocity closed-loop...consider the trade-offs
            // TODO: Default steer request is MotionMagic, the other choice is the exponential profile...consider the trade-offs
            drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * Calibrations.Drivetrain.MaxSpeed) // Drive forward with negative Y (forward)
                .withVelocityY(-joystick.getLeftX() * Calibrations.Drivetrain.MaxSpeed) // Drive left with negative X (left)
                .withRotationalRate(-joystick.getRightX() * Calibrations.Drivetrain.MaxAngularRate) // Drive counterclockwise with negative X (left)
                .withDeadband(Calibrations.Drivetrain.DriverTranslationDeadband)
                .withRotationalDeadband(Calibrations.Drivetrain.DriverRotationalDeadband)
                .withDriveRequestType(DriveRequestType.OpenLoopVoltage) 
                .withSteerRequestType(SteerRequestType.MotionMagic)
            )
        );

        // Use the same control of all swerve drive requests unless there's a good reason to deviate
        // TODO: From what I can tell, this command will only hold the current angle on the swerve modules and not
        // actually drive to the X pattern...need to test this out
        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake.withDriveRequestType(DriveRequestType.OpenLoopVoltage) // The speed is set to 0 m/s under the hood
            .withSteerRequestType(SteerRequestType.MotionMagic))); // Default is MotionMagic, but the target position is the current position

        // Not sure what this would be used for??
        joystick.b().whileTrue(drivetrain.applyRequest(() -> point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))));

        // Reset the field-centric heading on left bumper press
        // This is a must as the IMU will drift during a match
        // Be sure to align the robots coordinate frame to the fields coordinate frame before resetting the IMU
        joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

        if (Utils.isSimulation()) {
            drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
        }

        // The data going back to the DS is ok for debug and working in the shop. For competitions, I recommend turning off 90% of the data going to the DS (use only what
        // actually gets used during a match). The WPI logging (https://docs.wpilib.org/en/stable/docs/software/telemetry/datalog.html) is worth it's weight in gold. The
        // registerTelemetry framework is good, but it does have it's limitations. Since it's a consumer of SwerveDriveState, it can only log the things like Pose and the
        // module states. We want more than that. We should be logging swerve requests (target position, target velocities), feedback sensors (drive velocity, steering
        // angle, IMU, etc.), and other useful debug/telemetry. 
        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public RobotContainer() {
        configureBindings();
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
