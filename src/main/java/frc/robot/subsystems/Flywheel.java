package frc.robot.subsystems;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;

public class Flywheel extends SubsystemBase {
    SparkMax motor = new SparkMax(11, MotorType.kBrushless);
    RelativeEncoder encoder = motor.getEncoder();
    SparkClosedLoopController pid;

    double targetReference;
    ControlType currentControlType;

    public Flywheel() {
        motor.configure(Configs.FlywheelConfig.flywheelConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        pid = motor.getClosedLoopController();

        targetReference = 0;
        currentControlType = ControlType.kDutyCycle;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Wheel Position", encoder.getPosition());
        SmartDashboard.putNumber("Wheel Velocity", encoder.getVelocity());
    }
    
    
    public void set(double speed) {
        motor.set(speed);
        currentControlType = ControlType.kDutyCycle;
    }

    
    public void setVelocity(double velocity) {
        pid.setReference(velocity, ControlType.kVelocity);

        targetReference = velocity;
        currentControlType = ControlType.kVelocity;
    }

    
    public void setPosition(double position) {
        pid.setReference(position, ControlType.kPosition);
        SmartDashboard.putNumber("Requested Wheel Position", position);

        targetReference = position;
        currentControlType = ControlType.kPosition;
    }

    
    public void setVoltage(double voltage) {
        motor.setVoltage(voltage);
        
        currentControlType = ControlType.kVoltage;
    }

    
    public void setEncoderPosition(double position) {
        encoder.setPosition(position);
    }

    
    public double getVelocity() {
        return encoder.getVelocity();
    }

    
    public double getPosition() {        
        return encoder.getPosition();
    }

    
    public boolean atTarget(double threshold) {
        if (currentControlType == ControlType.kVelocity) {
            return Math.abs(getVelocity() - targetReference) < threshold;
        } else if (currentControlType == ControlType.kPosition) {
            return Math.abs(getPosition() - targetReference) < threshold;
        } else {
            return false;
        }
    }

    
    public SubsystemBase returnSubsystem() {
        return this;
    }
}