package com.example.adam.androidtestapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.adam.androidtestapplication.rfid.InventoryModel;
import com.example.adam.androidtestapplication.rfid.ModelBase;
import com.example.adam.androidtestapplication.rfid.WeakHandler;
import com.uk.tsl.rfid.asciiprotocol.AsciiCommander;
import com.uk.tsl.rfid.asciiprotocol.DeviceProperties;
import com.uk.tsl.rfid.asciiprotocol.commands.InventoryCommand;
import com.uk.tsl.rfid.asciiprotocol.enumerations.TriState;
import com.uk.tsl.rfid.asciiprotocol.responders.LoggerResponder;

import java.util.Set;

public class RFIDActivity extends AppCompatActivity {

    // Debugging
    private static final String TAG = "TSLBTDeviceActivity";
    //    private static final boolean D = true;
    private static final boolean D = BuildConfig.DEBUG;

    private static AsciiCommander mCommander = null;
    private InventoryModel mModel;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothDevice mDevice = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //TODO Refine of detecting the RFID gun
        //Assume the bluetooth is already connected to the device
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mCommander = new AsciiCommander(getApplicationContext());
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                Log.i("Bluetooth", device.getName()+" "+device.getAddress());
                BluetoothDevice tempBTDev = mBluetoothAdapter.getRemoteDevice(device.getAddress());
                if(null != tempBTDev ){
                    mCommander.connect(tempBTDev );
                }
            }
        }
        DeviceProperties deviceProperties = mCommander.getDeviceProperties();
        //mCommander.addResponder(new LoggerResponder());
        mCommander.addSynchronousResponder();
        //Create a (custom) model and configure its commander and handler
        mModel = new InventoryModel();
        mModel.setCommander(getCommander());
        mModel.setHandler(mGenericModelHandler);
        mModel.setEnabled(true);
        mModel.scan();
        if(mCommander.isConnected()){
            Log.i("CommanderConnected","Yes");
        }else{
            Log.i("CommanderConnected","No");
        }
        //this.testForAntenna();

    }

    private AsciiCommander getCommander(){
        return this.mCommander;
    }

    public void testForAntenna()
    {
            InventoryCommand testCommand = InventoryCommand.synchronousCommand();
            testCommand.setTakeNoAction(TriState.YES);
            getCommander().executeCommand(testCommand);
            if( !testCommand.isSuccessful() ) {
                Log.e("testForAntenna", "ER:Error! Code: " + testCommand.getErrorCode() + " " + testCommand.getMessages().toString());
            }else{
                Log.i("testForAntenna", "Working fine");
            }

    }

    //----------------------------------------------------------------------------------------------
    // Model notifications
    //----------------------------------------------------------------------------------------------

    private final WeakHandler<RFIDActivity> mGenericModelHandler = new WeakHandler<RFIDActivity>(this) {

        @Override
        public void handleMessage(Message msg, RFIDActivity thisActivity) {
            try {
                switch (msg.what) {
                    case ModelBase.BUSY_STATE_CHANGED_NOTIFICATION:
                        //TODO: process change in model busy state
                        break;

                    case ModelBase.MESSAGE_NOTIFICATION:
                        // Examine the message for prefix
                        String message = (String)msg.obj;
                        if( message.startsWith("ER:")) {
                            //mResultTextView.setText( message.substring(3));
                            Log.d("Weakhandler_ER", message.substring(3));
                        }
                        else if( message.startsWith("BC:")) {
                            //mBarcodeResultsArrayAdapter.add(message);
                            //scrollBarcodeListViewToBottom();
                            Log.d("Weakhandler_BC", message);
                        }
                        else if( message.startsWith("EPC:")) {
                            message = message.substring(4);
                            Log.d("Weakhandler_EPC", convertHexToString(message));
                        }else {
                            //mResultsArrayAdapter.add(message);
                            //scrollResultsListViewToBottom();
                            Log.d("Weakhandler_else", message);
                        }
                        //UpdateUI();
                        break;

                    default:
                        break;
                }
            } catch (Exception e) {
            }

        }
    };

    public static String convertHexToString(String value){

        //TODO Correction of regex & functional if possible
        //Tradition method: Split 2 char (hex) and convert to value
        StringBuilder szDisplayValue = new StringBuilder();
        for(int i=0;i<value.length();i+=2){
            String szCurChar = value.substring(i, i+2);
            szDisplayValue.append((char) Integer.parseInt(szCurChar, 16));
        }
        return szDisplayValue.toString();

        //Spliting of the input value 2 by 2 for hex value
        //String[] aStr = value.split("(?<=\\G.{2})");
//        //Trying out first time java functional style of mapping hex to char
//        //String szDisplayValue = (Arrays.asList(aStr).stream().map(s->((char)Integer.parseInt(s, 16))+"").collect(Collectors.joining()));
//        //Building on Java 1.7, no functional to use
//        StringBuilder szDisplayValue = new StringBuilder();
//        for(int i = 0; i<aStr.length;i++){
//            //szDisplayValue.append((char) Integer.parseInt(aStr[i], 16));
//            //szDisplayValue.append(Integer.parseInt(aStr[i], 16));
//            szDisplayValue.append(aStr[i]);
//            szDisplayValue.append(",");
//        }
        //Log.i("convertHexToString", (char)Integer.parseInt(aStr[1], 16)+"");
//        return szDisplayValue.toString()+"CV";
    }

}
