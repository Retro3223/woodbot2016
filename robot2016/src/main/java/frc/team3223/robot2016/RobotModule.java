package frc.team3223.robot2016;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {

    public static Logger logger;

    NetworkTable networkTable;
    Talon[] talons;
    Joystick leftJoystick;
    Joystick rightJoystick;
    boolean isbuttonpressed;
    RobotDrive drive;

    int i;
    boolean normaljoystickorentation;


    @Override
    public String getModuleName() {
        return "robot2016";
    }

    @Override
    public String getModuleVersion() {
        return "0.0.1";
    }

    @Override
    public void robotInit() {
        logger = new Logger("robot2016", Logger.ATTR_DEFAULT);
        networkTable = NetworkTable.getTable("SmartDashboard");
        i = 0;
        talons = new Talon[6];
        for(int j = 0; j < talons.length; j ++) {
            talons[j] = Registrar.talon(j);
        }
        leftJoystick = new Joystick(1);
        rightJoystick = new Joystick(0);
        isbuttonpressed = false;
        drive = new RobotDrive(talons[0], talons[1], talons[2], talons[3]);
        normaljoystickorentation = true;
    }

    @Override
    public void teleopPeriodic() {
        if(isbuttonpressed == false && leftJoystick.getRawButton(3));
        {
            normaljoystickorentation=!normaljoystickorentation;
            isbuttonpressed=true;
        }
        if (isbuttonpressed == true && leftJoystick.getRawButton(3)== false)
        {
            isbuttonpressed = false;

        }

        double leftvalue = leftJoystick.getAxis(Joystick.AxisType.kY)/2;
        double rightvalue = rightJoystick.getAxis(Joystick.AxisType.kY)/2;
        if( Math.abs(leftvalue - rightvalue) <= 0.2)
        {
            leftvalue = rightvalue;
        }
        if(!normaljoystickorentation){
            leftvalue=-leftvalue;
            rightvalue=-rightvalue;
        }
        drive.tankDrive(leftvalue, rightvalue, true);
    }

}