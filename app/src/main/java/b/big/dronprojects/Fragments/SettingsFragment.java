package b.big.dronprojects.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import b.big.dronprojects.MainActivity;
import b.big.dronprojects.R;
import b.big.dronprojects.Wifi.WifiConnection;

import static b.big.dronprojects.Wifi.WifiConnection.getWifiStatus;
import static b.big.dronprojects.Wifi.WifiConnection.networkPass;
import static b.big.dronprojects.Wifi.WifiConnection.networkSSID;
import static b.big.dronprojects.Wifi.WifiConnection.turnOnOffWifi;

/**
 * Created by Jarek on 24.03.2018.
 */

public class SettingsFragment extends Fragment {

    View view;
    TextView title;
    Context context;
    WifiConnection wifiConnection;
    Button buttonwifi;
    Boolean connected;




    @Nullable
    @Override

        public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_settings, container, false);
            buttonwifi = view.findViewById(R.id.buttonWifi);
            title= view.findViewById(R.id.connected);

            context = getActivity().getApplicationContext();
            wifiConnection = new WifiConnection(context);


            buttonwifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connected= wifiConnection.wifi_connect(view);

                if(connected){
                    title.setTextColor(ContextCompat.getColor(context, R.color.connected));
                    title.setText("You are connected!");

                }
                if(!connected) {
                    title.setTextColor(ContextCompat.getColor(context, R.color.notconnected));
                    title.setText("You are not connected!");

                }

            }

        });
        return view;
        }





}




