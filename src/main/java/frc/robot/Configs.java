package frc.robot;

import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class Configs {
    public static final class FlywheelConfig {
        public static final SparkMaxConfig flywheelConfig = new SparkMaxConfig();

        static {
                flywheelConfig
                        .idleMode(IdleMode.kBrake)
                        .smartCurrentLimit(60)
                        .inverted(false)
                        .openLoopRampRate(0)
                        .closedLoopRampRate(0);
                flywheelConfig.encoder
                    .positionConversionFactor(1)
                    .velocityConversionFactor(1);
                flywheelConfig.closedLoop
                    .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                    // These are example gains you may need to them for your own robot!
                    .pid(0.5, 0, 0.05)
                    .velocityFF(0)
                    .outputRange(-0.9, 0.9);
        }
    }
}
