package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Flywheel;

public class SetFlywheelToSpeed extends Command {
    private Flywheel flywheel;
    private double rpm;

    public SetFlywheelToSpeed(Flywheel flywheel){
        this.flywheel = flywheel;
        addRequirements(flywheel);
        SmartDashboard.putNumber("Desired Flywheel Speed (rpm)", 1000);
    }

    @Override
    public void initialize() {
        rpm = SmartDashboard.getNumber("Desired Flywheel Speed (rpm)", 1000);

    @Override
    public void execute() {
        flywheel.setVelocity(rpm);
    }

    @Override
    public void end(boolean interrupted) {
        flywheel.set(0);
    }
}
