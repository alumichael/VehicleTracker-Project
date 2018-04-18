package com.example.sunkee.vehicletracker.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.sunkee.vehicletracker.model.Location;
import com.example.sunkee.vehicletracker.util.PrefUtil;


/**
 * This class interface with the incoming sms on user's device
 *
 * @author toluadetuyi
 * @see Location
 * @see BroadcastReceiver
 */

public class SmsListener extends BroadcastReceiver {

    static String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        context = context;
        if (intent.getAction().equals(ACTION)) {

            Bundle bundle = intent.getExtras();

            if (bundle != null) {

                processSmsBundle(bundle);

            }
        }
    }

    /**
     * This allows you process the sms bundle received
     */
    private void processSmsBundle(Bundle bundle) {

        SmsMessage[] msgs = null;
        String msg_from;
        try {

            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            for (int i = 0; i < msgs.length; i++) {

                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                msg_from = msgs[i].getOriginatingAddress();
                String msgBody = msgs[i].getMessageBody();
                saveSMS(msgBody, msg_from);

            }

        } catch (Exception e) {

            Log.d(" Sms Exception", e.getMessage());
        }
    }

    /**
     * This allows you save retrieved sms locally
     *
     * @param body   This is the bosy of the sms
     * @param sender This is the sms sender name
     */
    void saveSMS(String body, String sender) {

        //TODO check first if the sender is the device

        //TODO track the longitude and latitude here by extracting it from the body

        //save the tracked longitude and latitude
        Location location = new Location();
        location.setLatitude(body);
        location.setLongitude(sender);

        PrefUtil.saveLatitude(location.getLatitude(), context);
        PrefUtil.saveLongitude(location.getLongitude(), context);
        
    }
}

