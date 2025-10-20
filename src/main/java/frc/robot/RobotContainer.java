// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.robot.commands.SetFlywheelToSpeed;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Flywheel;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Flywheel m_Flywheel = new Flywheel();
  private final DriveSubsystem m_drive = new DriveSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OIConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    configureDefaultCommands();

  }

  private void configureDefaultCommands() {
    m_drive.setDefaultCommand(
        new RunCommand(
            () -> {
              double forward = -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband);
              double strafe = -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband);
              double turn = -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband);

              m_drive.drive(forward, strafe, turn, true);
            },
            m_drive));
    
    m_Flywheel.setDefaultCommand(
      new RunCommand(()->{
        if (m_driverController.getLeftTriggerAxis() > 0.1){
          m_Flywheel.set(-m_driverController.getLeftTriggerAxis());
        } else if (m_driverController.getRightTriggerAxis() > 0.1){
          m_Flywheel.set(m_driverController.getRightTriggerAxis());
        } else {
          m_Flywheel.set(0);
        }
      }, m_Flywheel)
    );
  }

  private void configureBindings() {
    m_driverController.a().toggleOnTrue(new SetFlywheelToSpeed(m_Flywheel));
    m_driverController.start().onTrue(new InstantCommand(m_drive::zeroHeading, m_drive));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new SequentialCommandGroup();
  }
}
