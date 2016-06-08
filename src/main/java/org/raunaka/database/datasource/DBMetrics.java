package org.raunaka.database.datasource;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.raunaka.database.datasource.org.raunaka.util.RequestScope;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

/**
 * @author rAun007
 */

@Data
public class DBMetrics {
    private final MetricRegistry metricRegistry;
    private final String         metricPrefix;
    private final String         apiNameKey;

    public Timer getReadTimer() {

        String apiName = getApiName();
        String timerKey = metricPrefix + "." + apiName + ".read";
        return metricRegistry.timer(timerKey);
    }

    public Timer getWriteTimer() {

        String apiName = getApiName();
        String timerKey = metricPrefix + "." + apiName + ".write";
        return metricRegistry.timer(timerKey);
    }

    public Timer getUnknownQueryTimer() {

        String apiName = getApiName();
        String timerKey = metricPrefix + "." + apiName + ".unknown";
        return metricRegistry.timer(timerKey);
    }

    public Timer getStoredProcTimer() {

        String apiName = getApiName();
        String timerKey = metricPrefix + "." + apiName + ".storedProc";
        return metricRegistry.timer(timerKey);
    }

    private String getApiName() {

        String apiName = null;

        if (RequestScope.isSet()) {
            apiName = (String) RequestScope.getScope().get(apiNameKey);
        }

        if (StringUtils.isBlank(apiName)) {
            apiName = "unknownapi";
        }
        return apiName;
    }
}
