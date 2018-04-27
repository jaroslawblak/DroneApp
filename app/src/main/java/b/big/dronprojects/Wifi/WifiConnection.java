package b.big.dronprojects.Wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import b.big.dronprojects.Fragments.SettingsFragment;
import b.big.dronprojects.MainActivity;
import b.big.dronprojects.R;

public class WifiConnection extends AppCompatActivity {

    Boolean l1,l2,l3,turn= true;
    static WifiManager wifiManager;
    WifiConfiguration conf;
    Context context;
    public static String networkSSID="blakunetwork";
    public static String networkPass="password";


    public WifiConnection(Context context) {
        this.context = context;
    }

    byte[] buf = new byte[1024];//used to sending information to esp is a form of byte

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

		// this is for thread policy the AOS doesn't allow to transfer data using wifi module so we take the permission
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    @Override
    public void recreate() {
        super.recreate();
    }

    // conected with a wifi button.. it connect to esp module when it is pressed
	    //remember the nework ssid and pasword needs to be the same as given here
	    //other it won't connect
        public boolean wifi_connect(View v){



            wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);


            if (turn) {

                turnOnOffWifi(context, turn);
                turn = false;
                Toast.makeText(context, "turning on...", Toast.LENGTH_SHORT).show();

                //wifi configuration .. all the code below is to explain the wifi configuration of which type the wifi is
                //if it is a WPA-PSK protocol then it would work

                conf = new WifiConfiguration();
                conf.SSID = "\"" + networkSSID + "\"";
                conf.preSharedKey = "\"" + networkPass + "\"";
                conf.status = WifiConfiguration.Status.ENABLED;
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                int netid = wifiManager.addNetwork(conf);
                wifiManager.disconnect();
                wifiManager.enableNetwork(netid, true);
                wifiManager.reconnect();
                return true;


            } else {
                turnOnOffWifi(context, turn);
                turn = true;
                Toast.makeText(context, "turning off...", Toast.LENGTH_SHORT).show();
                return false;

            }
        }

//test
    public void led_1(View v){


        if(l1){

            l1=false;
            Client a=new Client();
            buf=null;
            buf=("9").getBytes();
            a.run();
            Toast.makeText(WifiConnection.this, "LED 1 ON", Toast.LENGTH_SHORT).show();
        }else{

            l1=true;
            Client a=new Client();//object of class client
            buf=null;
            buf=("10").getBytes();// value to be send to esp
            a.run(); //use run() in class client to send data
            Toast.makeText(WifiConnection.this, "LED 1 OFF", Toast.LENGTH_SHORT).show();
        }


    }

    public static void turnOnOffWifi(Context context, boolean isTurnToOn) {
        if(context!=null) {
            wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(isTurnToOn);
        }

    }

    public static boolean getWifiStatus(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
        return networkInfo.isConnected();
    }

    //used to send data to esp module
    public class Client implements Runnable {
        private final static String SERVER_ADDRESS = "192.168.4.1";//public ip of my server
        private final static int SERVER_PORT = 8888; 


        public void run(){

            InetAddress serverAddr;
            DatagramPacket packet;
            DatagramSocket socket;


            try {
                serverAddr = InetAddress.getByName(SERVER_ADDRESS);
                 socket = new DatagramSocket(); //DataGram socket is created
                packet = new DatagramPacket(buf, buf.length, serverAddr, SERVER_PORT);//Data is loaded with information where to send on address and port number
                socket.send(packet);//Data is send in the form of packets
                socket.close();//Needs to close the socket before other operation... its a good programming
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
