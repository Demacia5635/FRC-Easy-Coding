package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.chassis.subsystems.Chassis;

public class MoveX extends Command {

    private final Chassis chassis;
    private final double dis;
    private double wantedX;

    private final Timer timer;

    public MoveX(Chassis chassis, double dis) {
        this.chassis = chassis;
        this.dis = dis;

        this.timer = new Timer();

        setName("Move " + dis + " meters");

        addRequirements(chassis);
    }

    @Override
    public void initialize() {
       wantedX = chassis.getPose().getX() + dis;
        timer.start();
    }

    @Override
    public void execute() {
        chassis.setRobotRelVelocities(new ChassisSpeeds(dis < 0 ? 0.4 : -0.4, 0, 0));
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(1.8 * Math.abs(dis));
    }

    @Override
    public void end(boolean interrupted) {
        chassis.stop();
        
        timer.stop();
        timer.reset();
    }
}
