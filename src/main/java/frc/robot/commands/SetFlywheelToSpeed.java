package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Flywheel;

public class SetFlywheelToSpeed extends Command {
    private Flywheel flywheel;
    private double rpm;

    public SetFlywheelToSpeed(Flywheel flywheel, double rpm){
        this.flywheel = flywheel;
        this.rpm = rpm;
        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        SmartDashboard.putNumber("Desired Flywheel Speed (rpm)", rpm);
    }

    @Override
    public void execute() {
        flywheel.setVelocity(rpm);
    }

    @Override
    public void end(boolean interrupted) {
        flywheel.set(0);
    }
}
