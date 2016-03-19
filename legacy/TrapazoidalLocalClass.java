//Class Variables
//===============
TrapezoidalMotionProfile tmp;
        TMPTurn tmpturn;

//Inside DriveTrain Subsystem
//===========================
public void initD2D(double distance) {
        tmp = new TrapezoidalMotionProfile(maxVelocity, maxAcceleration, distance, 20);
        }
public void updateD2D(int t) {
        positionControllerL.setSetpoint(tmp.posAtTime(t));
        positionControllerR.setSetpoint(tmp.posAtTime(t));

        setPIDFF(velocityStabilizerL, tmp.velAtTime(t));
        setPIDFF(velocityStabilizerR, tmp.velAtTime(t));

        }

public void initD2A(double theta) { //theta is bearing
        tmpturn = new TMPTurn(maxTurnVelocity, maxTurnAcceleration, theta, 20);
        }
public void updateD2A(int t) {
        positionControllerL.setSetpoint(tmpturn.posAtTimeL(t));
        positionControllerR.setSetpoint(tmpturn.posAtTimeR(t));

        setPIDFF(velocityStabilizerL, tmpturn.velAtTimeL(t));
        setPIDFF(velocityStabilizerR, tmpturn.velAtTimeR(t));

        }
public static void main(String... args) throws IOException {
        TrapezoidalMotionProfile.test(20);
        }


//local Classes
//=============

class TrapezoidalMotionProfile {
    double maxV;      // ft/ms
    double acc;       // ft/ms^2
    double distance;  // ft
    int dt;           // ms
    ArrayList<Double> timePos = new ArrayList<Double>();
    ArrayList<Double> timeVel = new ArrayList<Double>();
    public TrapezoidalMotionProfile(double maxV, double acc, double distance, int dt) {
        this.maxV     = maxV / 1000;
        this.acc      = acc / 1000000; //from ft/s^2 to ft/ms^2
        this.distance = distance;
        this.dt       = dt;
        generateProfile();
    }
    public void generateProfile() {
        int t = 0;              // ms
        double pos = 0;         // ft
        double curV = 0;        // ft/ms
        while (pos != distance) {

            double t1 = maxV / acc; // stops accelerating
            double t2 = (distance / maxV); //fun math to get this number, stops decelerating
            if(distance <= maxV * maxV / acc) {
                System.out.println("Triangle "+maxV * maxV / acc);
                t1 = Math.sqrt(distance / acc); //tbh not sure why it's not 2 times this but this works
                t2 = t1;
            }
            double t3 = t2 + t1; //stops
            if(t < t1) {
                pos = .5 * acc * t * t;
                curV = acc * t;
            }
            else if(t < t2) {
                pos = .5 * acc * t1 * t1 + maxV * (t-t1);
                curV = maxV;
            }
            else if(t < t3) {
                pos = maxV * (t - t2) - .5 * acc * (t - t2) * (t - t2) + maxV * (t2 - t1) + .5 * acc * t1 * t1;
                curV = maxV - acc * (t - t2);
            }
            else{
                pos = distance;
                curV = 0;
            }

            t += dt;
            timePos.add(pos);
            timeVel.add(curV);
            //System.out.println("Entry: " + t + " " + curV + " " + pos);
        }

    }
    public double posAtTime(int t) {
        return timePos.get(t/dt);
    }
    public double velAtTime(int t) {
        return timeVel.get(t/dt) * 1000; //back to ft/sec
    }
    public void exportProfile() throws IOException {
        FileWriter out = new FileWriter(new File("C:\\Users\\AW\\FRC2016\\3182 FRC 2016\\TrapProfile_" + distance + ".csv"));
        for(int i = 0; i < timePos.size(); i++) {
            out.write(String.format("%d, %f, %f %n", i * dt, timePos.get(i), timeVel.get(i)));

        }
        out.close();
    }
    public static void test(double dist) throws IOException {
        TrapezoidalMotionProfile tmp = new TrapezoidalMotionProfile(10, 5, 5, 20); //TODO change maxV and acc
        tmp.exportProfile();
    }
}
class TMPTurn extends TrapezoidalMotionProfile {
    static final double radius = 12.125; // inches

    /**
     * theta is bearing - degrees from the forward direction going right (negative should work)
     */
    public TMPTurn(double maxV, double acc, double theta, int dt) {
        super(maxV, acc, theta * Math.PI / 180 * radius, dt); //convert to radians and divide by radius
    }

    public double posAtTimeR(int t) {
        return posAtTime(t);
    }
    public double posAtTimeL(int t) {
        return -posAtTime(t);
    }
    public double velAtTimeR(int t) {
        return velAtTime(t);
    }
    public double velAtTimeL(int t) {
        return -velAtTime(t);
    }
}

