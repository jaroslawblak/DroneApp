package b.big.dronprojects.ControlPanel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import b.big.dronprojects.R;
import b.big.dronprojects.Wifi.UDPClient;
import io.github.controlwear.virtual.joystick.android.JoystickView;

public class ControlPanel extends AppCompatActivity {

    public static final String TAG="ControlPanel";

    private TextView mTextViewAngleLeft;
    private TextView mTextViewStrengthLeft;

    private TextView mTextViewRotationH;
    private TextView mTextViewRotationV;
    private UDPClient udpClient = new UDPClient();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_camercontrol);

        mTextViewAngleLeft = findViewById(R.id.textView_angle_left);
        mTextViewStrengthLeft =  findViewById(R.id.textView_strength_left);
        mTextViewRotationH = findViewById(R.id.textView_rotationH);
        mTextViewRotationV =  findViewById(R.id.textView_rotationV);

        JoystickView joystickLeft =  findViewById(R.id.joystickView_left);
        joystickLeft.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                mTextViewAngleLeft.setText("Angle: "+angle + "°");
                udpClient.sendMessage(String.valueOf(angle));
                mTextViewStrengthLeft.setText("Power: "+strength + "%");
            }
        });


        JoystickView joystickRight =  findViewById(R.id.joystickView_Right);
        joystickRight.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                mTextViewRotationH.setText("RotateH: "+angle + " °");
                mTextViewRotationV.setText("RotateV: "+angle + " °");
            }
        });
    }




}
