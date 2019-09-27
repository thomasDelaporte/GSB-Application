package fr.delaporte.laboratoireGSB;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

import fr.delaporte.laboratoireGSB.helper.Session;

public class LaboratoireApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FlowConfig flowConfig = new FlowConfig.Builder(this).build();
        FlowManager.init(flowConfig);

        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
        Session.init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        FlowManager.destroy();
    }
}
