/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

    /**
     * Talon SRX objects. Talons connected to the right side motors should have the
     * CAN IDs 0-2. The right side encoder should be connected to Talon 0. Talons
     * connected to the left side motors should have CAN IDs 3-5, with an encoder
     * connected to Talon 3.
     * 
     * If running a 6 motor drivetrain, uncomment all lines containing m_rightTalon3
     * or m_leftTalon3. Feel free to remove them if running a 4 motor drivetrain
     */
    TalonSRX m_rightTalon1 = new TalonSRX(0), m_rightTalon2 = new TalonSRX(1), m_leftTalon1 = new TalonSRX(3),
            m_leftTalon2 = new TalonSRX(4);
    // TalonSRX m_rightTalon3 = new TalonSRX(2), m_leftTalon3 = new TalonSRX(5);

    Joystick m_xbox = new Joystick(1);

    boolean m_invertRightMotors = false;
    boolean m_invertLeftMotors = false;
    boolean m_invertRightEncoder = false;
    boolean m_invertLeftEncoder = false;

    public void robotInit() {

        /**
         * Initalize encoders on m_leftTalon1 and m_rightTalon1 and invert them if
         * nessecary
         */
        m_leftTalon1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        m_leftTalon1.setSensorPhase(m_invertLeftEncoder);
        m_rightTalon1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        m_rightTalon1.setSensorPhase(m_invertRightEncoder);

        /**
         * Set talons without an encoder to follow the one that does based on the
         * boolean values above
         */
        m_rightTalon2.set(ControlMode.Follower, m_rightTalon1.getBaseID());
        m_leftTalon2.set(ControlMode.Follower, m_leftTalon1.getBaseID());
        // m_rightTalon3.set(ControlMode.Follower, m_rightTalon1.getBaseID());
        // m_leftTalon3.set(ControlMode.Follower, m_leftTalon1.getBaseID());

        /**
         * Inverts the drivetrain based on the boolean values above
         */
        m_leftTalon1.setInverted(m_invertLeftMotors);
        m_leftTalon2.setInverted(m_invertLeftMotors);
        m_rightTalon1.setInverted(m_invertRightMotors);
        m_rightTalon2.setInverted(m_invertRightMotors);
        // m_leftTalon3.setInverted(m_invertLeftMotors);
        // m_rightTalon3.setInverted(m_invertRightMotors);
    }

    public void teleopPeriodic() {

        // These obtain the values of the Xbox controller's joysticks
        double x_Axis = m_xbox.getRawAxis(0);
        double y_Axis = -m_xbox.getRawAxis(1); // The Driver's Station reports the opposite value of the Y axis

        arcadeDrive(y_Axis, x_Axis);
    }

    public void autonomousInit() {

    }

    public void autonomousPeriodic() {

    }

    public int toTicks(double inches) {
        return (int) (inches * ((3.9 * Math.PI) / 512));
    }

    /**
     * Function created to remove Differential Drive because of conflicts during
     * Autonomus. Works the exact same as DifferentialDrive's arcadeDrive function.
     */
    public void arcadeDrive(double y, double z) {
        m_rightTalon1.set(ControlMode.PercentOutput, y - z);
        m_rightTalon1.set(ControlMode.PercentOutput, y + z);
    }
}