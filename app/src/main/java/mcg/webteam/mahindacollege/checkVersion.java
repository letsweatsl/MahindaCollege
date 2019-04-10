package mcg.webteam.mahindacollege;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/**
 * Created by Chandupa on 05-Apr-18.
 */

public class checkVersion {
    public static String KEY_UPDATE_ENABLE="updateAvailable";
    public static String KEY_UPDATE_URL="url";
    public static String KEY_UPDATE_VERSION="version";

    public static Builder with (Context context){
        return new Builder(context);
    }

    public interface OnUpdateListner{
        void OnUpdateListner(String appUrl);

    }
    private OnUpdateListner onUpdateListner;
    private Context context;

    public checkVersion( Context context,OnUpdateListner onUpdateListner) {
        this.onUpdateListner = onUpdateListner;
        this.context = context;
    }

    public void check(){
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        if (remoteConfig.getBoolean(KEY_UPDATE_ENABLE)){

            String currentVersion = remoteConfig.getString(KEY_UPDATE_VERSION);
            String appVersion = getAppVersion(context);
            String updateURL = remoteConfig.getString(KEY_UPDATE_URL);

            if(!TextUtils.equals(currentVersion,appVersion)&& onUpdateListner !=null)
                onUpdateListner.OnUpdateListner(updateURL);


        }
    }

    private String getAppVersion(Context context) {
        String result="";
        try{
            result = context.getPackageManager().getPackageInfo(context.getPackageName(),0)
                    .versionName;
            result = result.replaceAll("[a-z,A-Z]|-","");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
           return result;
    }

    public static class Builder{

        private Context context;
        private OnUpdateListner onUpdateListner;

        public Builder(Context context){
            this.context = context;
        }
        public Builder onUpdateCheck(OnUpdateListner onUpdateListner){
            this.onUpdateListner =onUpdateListner;
            return this;
        }

        public checkVersion build(){
            return new checkVersion(context,onUpdateListner);

        }

        public checkVersion check(){
            checkVersion CheckVersion = build();
            CheckVersion.check();
            return CheckVersion;
        }
    }

}
