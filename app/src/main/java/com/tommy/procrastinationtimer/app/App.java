package com.tommy.procrastinationtimer.app;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import com.tommy.procrastinationtimer.BuildConfig;
import com.tommy.procrastinationtimer.services.MockCrashLibrary;
import timber.log.Timber;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, @NonNull String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            MockCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    MockCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    MockCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
