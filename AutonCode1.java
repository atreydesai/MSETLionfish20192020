package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="RedPlat", group="LinearOpMode")
public class AutonCode1 extends LinearOpMode { //redPlat
    private DcMotor frontLeft, backLeft, frontRight, backRight, claw, lift;
    private Servo hooks1, hooks2, block;
    public int d = 4; //Diameter of Wheel
    public double tick = 537.6; //# of ticks for one rotation
    public boolean left=true;
    public boolean right=false;
    public void runOpMode(){
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");
        claw = hardwareMap.get(DcMotor.class, "claw");
        lift = hardwareMap.get(DcMotor.class, "lift1");
        hooks1 = hardwareMap.get(Servo.class, "hooks1");
        hooks2 = hardwareMap.get(Servo.class, "hooks2");
        //block = hardwareMap.get(Servo.class, "block");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection ( DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        waitForStart();

        forward(51);
        hDown(); //maybe hUp();
        backwards(51); //same as the first forward
        hUp();
        strafe(left,48);
        //forward(24); if we need to park far area


    }
    /*
    public void bUp(){//hook servo up
        block.setPosition(0);
        telemetry.addData("Servo Power", block.getPosition());
    }
    public void bDown(){//hook servo down
        block.setPosition(1);
        telemetry.addData("Servo Power", block.getPosition ());
    }

     */
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
    public int distanceCalc(int distance){
        int ticks=(int)(tick*(distance/(12.56)));
        return ticks;
    }
    public void forward(int distance) {
        reset();
        int tick = distanceCalc(distance);
        setTarget(tick);
        frontLeft.setPower(0.5);
        frontRight.setPower(0.5);
        backRight.setPower(0.5);
        backLeft.setPower(0.5);
        while(frontLeft.isBusy() && opModeIsActive()) {
        }
        stopDrive();
    }
    public void backwards(int distance) {
        reset();
        int tick = distanceCalc(-distance);
        setTarget(tick);
        frontLeft.setPower(-0.5);
        backLeft.setPower(-0.5);
        frontRight.setPower(-0.5);
        backRight.setPower(-0.5);
        while(frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy() && opModeIsActive()) {

        }
        stopDrive();

    }
    public int strafeRatio = 0;
    public int strafeCalc(int distance){
        int encoderTick = strafeRatio*distance;
        return encoderTick;
    }
    public void strafe(boolean strafe, int distance){ //true = left, false = right
        if(strafe){
            reset();
            int tick = strafeCalc(distance);
            setTarget(tick);
            frontLeft.setPower(-0.5);
            frontRight.setPower(0.5);
            backRight.setPower(0.5);
            backLeft.setPower(-0.5);
            while(frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy() && opModeIsActive()) {

            }
            stopDrive();

        }
        else if(!strafe){
            reset();
            int tick = strafeCalc(distance);
            setTarget(tick);
            frontLeft.setPower(0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(-0.5);
            backLeft.setPower(0.5);
            while(frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy() && opModeIsActive()) {

            }
            stopDrive();
        }
    }


    public int ratioTurn = 0; //needs to be calculated
    public int calcTurn(int degrees){

        int encoderTick = ratioTurn*degrees;
        return encoderTick;
    }
    public void turn(boolean turn,int degrees) { //true = left, false = right
        if(turn){
            reset();
            int tick = calcTurn(degrees);
            setTarget(tick);
            frontLeft.setPower(-0.5);
            frontRight.setPower(0.5);
            backRight.setPower(0.5);
            backLeft.setPower(-0.5);
            while(frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy() && opModeIsActive()) {

            }
            stopDrive();

        }
        else if(!turn){
            reset();
            int tick = calcTurn(degrees);
            setTarget(tick);
            frontLeft.setPower(0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(-0.5);
            backLeft.setPower(0.5);
            while(frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy() && opModeIsActive()) {

            }
            stopDrive();
        }
    }

    public void reset(){ //resetEncoder Values
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void stopDrive(){ //stop DriveTrain
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);

    }
    public void setTarget(int tick){
        frontLeft.setTargetPosition(tick);
        frontRight.setTargetPosition(tick);
        backLeft.setTargetPosition(tick);
        backRight.setTargetPosition(tick);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void beginClaw(){
        claw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int resetAngle = 0;
        claw.setTargetPosition(resetAngle);
        claw.setMode( DcMotor.RunMode.RUN_TO_POSITION);
        claw.setPower(0.5);
        while(claw.isBusy() && opModeIsActive()) {
            claw.getCurrentPosition();
        }
        claw.setPower(0);
        claw.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        claw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
}


