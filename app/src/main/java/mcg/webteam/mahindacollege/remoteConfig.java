package mcg.webteam.mahindacollege;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chandupa on 05-Apr-18.
 */

public class remoteConfig extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

        Map<String,Object> defaultValue = new HashMap<>();
        defaultValue.put(checkVersion.KEY_UPDATE_ENABLE,false);
        defaultValue.put(checkVersion.KEY_UPDATE_VERSION,"3.1");
        defaultValue.put(checkVersion.KEY_UPDATE_URL,"https://play.google.com/store/apps/details?id=mcg.webteam.topfeeds");

        remoteConfig.setDefaults(defaultValue);
        remoteConfig.fetch(5)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            remoteConfig.activateFetched();
                        }
                    }
                });

    }
}