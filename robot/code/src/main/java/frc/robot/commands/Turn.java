package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.chassis.subsystems.Chassis;

public class Turn extends Command {
     
    private final Chassis chassis;
    private final double angle;
    private double wantedAngle;

    private final Timer timer;

    public Turn(Chassis chassis, double angle) {
        this.chassis = chassis;
        this.angle = angle / 180 * Math.PI;

        this.timer = new Timer();

        setName("Turn " + angle + " degrees");

        addRequirements(chassis);
    }

    @Override
    public void initialize() {
        this.wantedAngle = MathUtil.angleModulus(chassis.getGyroAngle().getRadians() + angle);
        timer.start();
    }

    @Override
    public void execute() {
        chassis.setVelocities(new ChassisSpeeds(0, 0, angle < 0 ? Math.PI / 4 : -Math.PI / 4));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(wantedAngle - MathUtil.angleModulus(chassis.getGyroAngle().getRadians())) <= (Math.PI / 25) || timer.hasElapsed(2.4 * Math.abs(angle) / (Math.PI / 2));
    }

    @Override
    public void end(boolean interrupted) {
        chassis.stop();
        timer.stop();
        timer.reset();
    }
}
