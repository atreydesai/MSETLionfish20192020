package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="DriverControl", group="LinearOpmode")
public class DriverControl extends LinearOpMode{

    private DcMotor frontLeft, backLeft, frontRight, backRight;
    private Servo hooks1,hooks2, platform;
    public void runOpMode(){
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");
        //platform = hardwareMap.get(Servo.class, "platform");
        hooks1 = hardwareMap.get(Servo.class, "hooks1");
        hooks2 = hardwareMap.get(Servo.class, "hooks2");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection ( DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        waitForStart();
        double fbLeftStick = 0;
        double lrLeftStick = 0;
        double lrRightStick = 0;
        double fbRightStick = 0;
        double ppUp = 1; //subject to change
        double ppDown= 0 ; // subject to change
        double hookUp = 0;//subject to change
        double hookDown = 1;//subject to change
        boolean rev = false;
        //put all movement code in here
        while(opModeIsActive()){
            //joystick control code
            fbLeftStick = -this.gamepad1.left_stick_y;
            fbRightStick = -this.gamepad1.right_stick_y;
            lrLeftStick = -this.gamepad1.left_stick_x; //if turning the wrong direction reverse the sign
            lrRightStick = this.gamepad1.right_stick_x; //if turning the wrong direction reverse the sign
            /*
            if(this.gamepad1.left_trigger > 0.1){
                hUp(ppUp); //move the platform up

            }
            else if(this.gamepad1.left_bumper == true){
                platformDown(ppDown); //move the platform down

            }

             */
            if(this.gamepad1.right_trigger > 0.1){
                hUp(hookUp);

            }
            else if(this.gamepad1.right_bumper == true){
                hDown(hookDown);

            }
            /*
            if(this.gamepad1.a == true){
                if(rev ==true){
                    //reverse(false);
                    rev = false;
                }
                else if(rev ==false){
                    //reverse(true);
                    rev = true;
                }

            }

             */

            //movingFB(fbLeftStick);
            //strafe(lrLeftStick);
            //turn(lrRightStick);

            tank(fbLeftStick, fbRightStick);
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
    public void tank(double fbLeftStick, double fbRightStick){ //tank drive (one joystick controls one side of wheels)

        frontLeft.setPower(fbLeftStick);
        backLeft.setPower(fbLeftStick);
        backRight.setPower(fbRightStick);
        frontRight.setPower(fbRightStick);
        telemetry.addData("Target Power", fbLeftStick);
        telemetry.addData("Target Power", fbRightStick);
        telemetry.addData("Motor Power", frontLeft.getPower());
        telemetry.addData("Motor Power", frontRight.getPower());
        telemetry.addData("Motor Power", backLeft.getPower());
        telemetry.addData("Motor Power", backRight.getPower());

    }
    public void movingFB(double fbLeftStick){// moving forwards and backwards

        frontLeft.setPower(fbLeftStick);
        frontRight.setPower(fbLeftStick);
        backLeft.setPower(fbLeftStick);
        backRight.setPower(fbLeftStick);
        telemetry.addData("Target Power", fbLeftStick);
        telemetry.addData("Motor Power", frontLeft.getPower());
        telemetry.addData("Motor Power", frontRight.getPower());
        telemetry.addData("Motor Power", backLeft.getPower());
        telemetry.addData("Motor Power", backRight.getPower());
    }

    public void strafe(double lrLeftStick){ //moving the root side to side w/o turning

        frontLeft.setPower(lrLeftStick);
        backLeft.setPower(-lrLeftStick);
        frontRight.setPower(-lrLeftStick);
        backRight.setPower(lrLeftStick);
        telemetry.addData("Target Power", lrLeftStick);
        telemetry.addData("Motor Power", frontLeft.getPower());
        telemetry.addData("Motor Power", frontRight.getPower());
        telemetry.addData("Motor Power", backLeft.getPower());
        telemetry.addData("Motor Power", backRight.getPower());
    }
    public void turn(double lrRightStick){//turning in place

        frontLeft.setPower(-lrRightStick);
        backLeft.setPower(lrRightStick);
        frontRight.setPower(-lrRightStick);
        backRight.setPower(lrRightStick);
        telemetry.addData("Target Power", lrRightStick);
        telemetry.addData("Motor Power", frontLeft.getPower());
        telemetry.addData("Motor Power", frontRight.getPower());
        telemetry.addData("Motor Power", backLeft.getPower());
        telemetry.addData("Motor Power", backRight.getPower());
    }

    /*public void platformUp(double plat){//platform servo up
        platform.setPosition(plat);
        telemetry.addData("Target Power", plat);
        telemetry.addData("Servo Power", platform.getPosition());
    }
    public void platformDown(double plat){//platform servo down
        platform.setPosition(plat);
        telemetry.addData("Target Power", plat);
        telemetry.addData("Servo Power", platform.getPosition());
    }

     */
    public void hUp(double hook){//hook servo up
        hooks1.setPosition(hook);
        hooks2.setPosition(hook+1);
        telemetry.addData("Servo Power", hooks1.getPosition());
        telemetry.addData("Servo Power", hooks2.getPosition());

    }
    public void hDown(double hook){//hook servo down
        hooks1.setPosition(hook);
        hooks2.setPosition(hook-1);
        telemetry.addData("Servo Power", hooks1.getPosition());
        telemetry.addData("Servo Power", hooks2.getPosition());
    }
    /*public void reverse(boolean rev){
        if(rev == true){
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
            backLeft.setDirection(DcMotor.Direction.FORWARD);
            frontRight.setDirection ( DcMotor.Direction.FORWARD);
            backRight.setDirection(DcMotor.Direction.FORWARD);
        }
        else if(rev == false){
            frontLeft.setDirection(DcMotor.Direction.REVERSE);
            backLeft.setDirection(DcMotor.Direction.REVERSE);
            frontRight.setDirection ( DcMotor.Direction.REVERSE);
            backRight.setDirection(DcMotor.Direction.REVERSE);
        }

    }*/

}
