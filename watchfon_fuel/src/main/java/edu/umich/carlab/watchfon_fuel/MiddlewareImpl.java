package edu.umich.carlab.watchfon_fuel;

import edu.umich.carlab.DataMarshal;
import edu.umich.carlab.loadable.Middleware;

import java.util.HashMap;
import java.util.Map;

public class MiddlewareImpl implements Middleware {
    public final static String APP = "watchfon_fuel";
    public final static String SPEED = "speed";

    @Override
    public String getName() {
        return APP;
    }

    /**
     * This function splits an grouped together sensor source to it's complementary parts
     * This middleware doesn't need to split so this is the default case.
     * @param dataObject
     * @return
     */
    public Map<String, Float> splitValues(DataMarshal.DataObject dataObject) {
        Map<String, Float> splitMap = new HashMap<>();
        String device = dataObject.device;
        if (!device.equals(APP)) return null;
        splitMap.put(dataObject.sensor, dataObject.value[0]);
        return splitMap;
    }
}
