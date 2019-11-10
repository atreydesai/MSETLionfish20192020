package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="DriverControl", group="LinearOpmode")
public class DriverControl extends LinearOpMode{

    private DcMotor frontLeft, backLeft, frontRight, backRight, claw, lift1, lift2;
    private Servo hooks1,hooks2;
    public void runOpMode(){
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");
        claw = hardwareMap.get(DcMotor.class, "claw");
        lift1 = hardwareMap.get(DcMotor.class, "lift1");
        lift2 = hardwareMap.get(DcMotor.class, "lift2");
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

        //put all movement code in here
        while(opModeIsActive()){
            //joystick control code
            fbLeftStick = -this.gamepad1.left_stick_y;
            lrRightStick = this.gamepad1.right_stick_x; //if turning the wrong direction reverse the sign

            if(this.gamepad1.right_trigger > 0.1){
                hUp();

            }
            else if(this.gamepad1.right_bumper == true) {
                hDown();

            }
            if(this.gamepad2.a==true){
                clawOpen();
            }
            else if(this.gamepad2.b==true){
                clawOff();
            }
            if(this.gamepad2.dpad_up==true){
                liftUp();
            }
            else if(this.gamepad2.dpad_down==true){
                liftDown();
            }
            else if(this.gamepad2.dpad_right==true){
                liftOff();
            }
            movingFB(fbLeftStick);
            strafe(lrLeftStick);
            turn(lrRightStick);


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

    public void clawOpen(){
        claw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        claw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        int angle = 0;
        claw.setTargetPosition (angle);
        claw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        claw.setPower(0.5);
        while(claw.isBusy() && opModeIsActive()) {
            claw.getCurrentPosition();
        }
        claw.setPower(0);
    }
    public void clawOff(){
        claw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        claw.setPower(0);
        telemetry.addData("Target Power", 0);
        telemetry.addData("Motor Power", claw.getPower());
    }
    public void liftUp(){
        lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift1.setPower(0.5);
        lift2.setPower(0.5);
        telemetry.addData("Target Power", 0.5);
        telemetry.addData("Motor Power", lift1.getPower());
        telemetry.addData("Motor Power", lift2.getPower());
    }
    public void liftDown(){
        lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift1.setPower(-0.5);
        lift2.setPower(-0.5);
        telemetry.addData("Target Power", -0.5);
        telemetry.addData("Motor Power", lift1.getPower());
        telemetry.addData("Motor Power", lift2.getPower());
    }
    public void liftOff(){
        lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        lift2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        lift1.setPower(0);
        lift2.setPower(0);
        telemetry.addData("Target Power", 0);
        telemetry.addData("Motor Power", lift1.getPower());
        telemetry.addData("Motor Power", lift2.getPower());
    }
    public void hUp(){//hook servo up
        hooks1.setPosition(0);
        hooks2.setPosition(1);
        telemetry.addData("Servo Power", hooks1.getPosition());
        telemetry.addData("Servo Power", hooks2.getPosition());

    }
    public void hDown(){//hook servo down
        hooks1.setPosition(1);
        hooks2.setPosition(0);
        telemetry.addData("Servo Power", hooks1.getPosition());
        telemetry.addData("Servo Power", hooks2.getPosition());
    }


}
