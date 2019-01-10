package edu.umich.carlab.watchfon_speed;

import android.content.Context;
import android.hardware.SensorManager;
import android.util.Pair;
import edu.umich.carlab.CLDataProvider;
import edu.umich.carlab.DataMarshal;
import edu.umich.carlab.loadable.App;
import edu.umich.carlab.sensors.PhoneSensors;

import java.util.Map;


/**
 * Speed estimation for WatchFon. This app uses GPS sensors and reads out
 * the speed. The next version of this app will use a complementary filter,
 * vehicle-aligned IMU, and GPS to estimate the speed.
 */
public class AppImpl extends App {
    final String TAG = "WatchfonSpeed";

    public AppImpl(CLDataProvider cl, Context context) {
        super(cl, context);
        name = "WatchFon/Speed";
        sensors.add(new Pair<>(PhoneSensors.DEVICE, PhoneSensors.GPS));
    }


    @Override
    public void newData(DataMarshal.DataObject dObject) {
        if (dObject.dataType != DataMarshal.MessageType.DATA) return;
        if (dObject.device.equals(MiddlewareImpl.APP)) return;
        if (dObject.value == null) return;

        Map<String, Float> gpsSplit = PhoneSensors.splitValues(dObject);
        Float speed = gpsSplit.get(PhoneSensors.GPS_SPEED);
        outputData(dObject, MiddlewareImpl.SPEED, new Float[] {speed});
    }

    void outputData(DataMarshal.DataObject dObject, String sensor, Float[] value) {
        DataMarshal.DataObject secondaryDataObject = dObject.clone();
        secondaryDataObject.device = MiddlewareImpl.APP;
        secondaryDataObject.sensor = sensor;
        secondaryDataObject.value = value;
        cl.newData(secondaryDataObject);
    }
}