package com.imandroid.simplefoursquare.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

public class PermissionUtils {

    private static PermissionUtils instance;

    private FullCallback fullCallback;
    private SimpleCallback simpleCallback;
    private AskAgainCallback askAgainCallback;
    private SmartCallback smartCallback;

    private boolean askAgain = false;

    private ArrayList<PermissionEnum> permissions;
    private ArrayList<PermissionEnum> permissionsGranted;
    private ArrayList<PermissionEnum> permissionsDenied;
    private ArrayList<PermissionEnum> permissionsDeniedForever;
    private ArrayList<PermissionEnum> permissionToAsk;

    private int key = 1000;

    public static PermissionUtils Builder() {
        if (instance == null) {
            instance = new PermissionUtils();
        }
        return instance;
    }

    public static boolean isGranted(Context context, PermissionEnum permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ContextCompat.checkSelfPermission(context, permission.toString()) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isGranted(Context context, PermissionEnum... permission) {
        for (PermissionEnum permissionEnum : permission) {
            if (!isGranted(context, permissionEnum)) {
                return false;
            }
        }
        return true;
    }

    public static Intent openApplicationSettings(String packageName) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        return intent;
    }

    public static void openApplicationSettings(Context context, String packageName) {
        context.startActivity(openApplicationSettings(packageName));
    }

    public static void handleResult(@NonNull android.app.Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        handleResult(activity, null, requestCode, permissions, grantResults);
    }

    public static void handleResult(@NonNull Fragment v4fragment, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        handleResult(null, v4fragment, requestCode, permissions, grantResults);
    }

    private static void handleResult(final android.app.Activity activity, final Fragment v4fragment, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (instance == null) return;
        if (requestCode == instance.key) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    instance.permissionsGranted.add(PermissionEnum.fromManifestPermission(permissions[i]));
                } else {
                    boolean permissionsDeniedForever = false;
                    if (activity != null) {
                        permissionsDeniedForever = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]);
                    } else if (v4fragment != null) {
                        permissionsDeniedForever = v4fragment.shouldShowRequestPermissionRationale(permissions[i]);
                    }
                    if (!permissionsDeniedForever) {
                        instance.permissionsDeniedForever.add(PermissionEnum.fromManifestPermission(permissions[i]));
                    }
                    instance.permissionsDenied.add(PermissionEnum.fromManifestPermission(permissions[i]));
                    instance.permissionToAsk.add(PermissionEnum.fromManifestPermission(permissions[i]));
                }
            }
            if (instance.permissionToAsk.size() != 0 && instance.askAgain) {
                instance.askAgain = false;
                if (instance.askAgainCallback != null && instance.permissionsDeniedForever.size() != instance.permissionsDenied.size()) {
                    instance.askAgainCallback.showRequestPermission(new AskAgainCallback.UserResponse() {
                        @Override
                        public void result(boolean askAgain) {
                            if (askAgain) {
                                instance.ask(activity, v4fragment);
                            } else {
                                instance.showResult();
                            }
                        }
                    });
                } else {
                    instance.ask(activity, v4fragment);
                }
            } else {
                instance.showResult();
            }
        }
    }

    public PermissionUtils permissions(ArrayList<PermissionEnum> permissions) {
        this.permissions = new ArrayList<>();
        this.permissions.addAll(permissions);
        return this;
    }

    public PermissionUtils permission(PermissionEnum permission) {
        this.permissions = new ArrayList<>();
        this.permissions.add(permission);
        return this;
    }

    public PermissionUtils permission(PermissionEnum... permissions) {
        this.permissions = new ArrayList<>();
        Collections.addAll(this.permissions, permissions);
        return this;
    }

    public PermissionUtils askAgain(boolean askAgain) {
        this.askAgain = askAgain;
        return this;
    }

    public PermissionUtils callback(FullCallback fullCallback) {
        this.simpleCallback = null;
        this.smartCallback = null;
        this.fullCallback = fullCallback;
        return this;
    }

    public PermissionUtils callback(SimpleCallback simpleCallback) {
        this.fullCallback = null;
        this.smartCallback = null;
        this.simpleCallback = simpleCallback;
        return this;
    }

    public PermissionUtils callback(SmartCallback smartCallback) {
        this.fullCallback = null;
        this.simpleCallback = null;
        this.smartCallback = smartCallback;
        return this;
    }

    public PermissionUtils askAgainCallback(AskAgainCallback askAgainCallback) {
        this.askAgainCallback = askAgainCallback;
        return this;
    }

    public PermissionUtils key(int key) {
        this.key = key;
        return this;
    }

    public void ask(android.app.Activity activity) {
        ask(activity, null);
    }

    public void ask(Fragment v4fragment) {
        ask(null, v4fragment);
    }

    public void ask(android.app.Fragment fragment) {
        ask(null, null);
    }

    private void ask(android.app.Activity activity, Fragment v4fragment) {
        initArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissionToAsk = permissionToAsk(activity, v4fragment);
            if (permissionToAsk.length == 0) {
                showResult();
            } else {
                if (activity != null) {
                    ActivityCompat.requestPermissions(activity, permissionToAsk, key);
                } else if (v4fragment != null) {
                    v4fragment.requestPermissions(permissionToAsk, key);
                }
            }
        } else {
            permissionsGranted.addAll(permissions);
            showResult();
        }
    }

    @NonNull
    private String[] permissionToAsk(android.app.Activity activity, Fragment v4fragment) {
        ArrayList<String> permissionToAsk = new ArrayList<>();
        for (PermissionEnum permission : permissions) {
            boolean isGranted = false;
            if (activity != null) {
                isGranted = PermissionUtils.isGranted(activity, permission);
            } else if (v4fragment != null) {
                isGranted = PermissionUtils.isGranted(v4fragment.getActivity(), permission);
            }
            if (!isGranted) {
                permissionToAsk.add(permission.toString());
            } else {
                permissionsGranted.add(permission);
            }
        }
        return permissionToAsk.toArray(new String[permissionToAsk.size()]);
    }

    private void initArray() {
        this.permissionsGranted = new ArrayList<>();
        this.permissionsDenied = new ArrayList<>();
        this.permissionsDeniedForever = new ArrayList<>();
        this.permissionToAsk = new ArrayList<>();
    }

    private void showResult() {
        if (simpleCallback != null)
            simpleCallback.result(permissionToAsk.size() == 0 || permissionToAsk.size() == permissionsGranted.size());
        if (fullCallback != null)
            fullCallback.result(permissionsGranted, permissionsDenied, permissionsDeniedForever, permissions);
        if (smartCallback != null)
            smartCallback.result(permissionToAsk.size() == 0 || permissionToAsk.size() == permissionsGranted.size(), !permissionsDeniedForever.isEmpty());
        instance = null;
    }

    public interface FullCallback {

        void result(ArrayList<PermissionEnum> permissionsGranted, ArrayList<PermissionEnum> permissionsDenied, ArrayList<PermissionEnum> permissionsDeniedForever, ArrayList<PermissionEnum> permissionsAsked);

    }

    public interface SimpleCallback {

        void result(boolean allPermissionsGranted);

    }

    public interface AskAgainCallback {

        void showRequestPermission(UserResponse response);

        interface UserResponse {

            void result(boolean askAgain);

        }

    }

    public interface SmartCallback {

        void result(boolean allPermissionsGranted, boolean somePermissionsDeniedForever);

    }

    @SuppressLint("InlinedApi")
    public enum PermissionEnum {

        BODY_SENSORS(Manifest.permission.BODY_SENSORS),
        READ_CALENDAR(Manifest.permission.READ_CALENDAR),
        WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR),
        READ_CONTACTS(Manifest.permission.READ_CONTACTS),
        WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS),
        GET_ACCOUNTS(Manifest.permission.GET_ACCOUNTS),
        READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE),
        WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE),
        ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),
        ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION),
        RECORD_AUDIO(Manifest.permission.RECORD_AUDIO),
        READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE),
        CALL_PHONE(Manifest.permission.CALL_PHONE),
        READ_CALL_LOG(Manifest.permission.READ_CALL_LOG),
        WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG),
        ADD_VOICEMAIL(Manifest.permission.ADD_VOICEMAIL),
        USE_SIP(Manifest.permission.USE_SIP),
        PROCESS_OUTGOING_CALLS(Manifest.permission.PROCESS_OUTGOING_CALLS),
        CAMERA(Manifest.permission.CAMERA),
        SEND_SMS(Manifest.permission.SEND_SMS),
        RECEIVE_SMS(Manifest.permission.RECEIVE_SMS),
        READ_SMS(Manifest.permission.READ_SMS),
        RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH),
        RECEIVE_MMS(Manifest.permission.RECEIVE_MMS),

        GROUP_CALENDAR(Manifest.permission_group.CALENDAR),
        GROUP_CAMERA(Manifest.permission_group.CAMERA),
        GROUP_CONTACTS(Manifest.permission_group.CONTACTS),
        GROUP_LOCATION(Manifest.permission_group.LOCATION),
        GROUP_MICROPHONE(Manifest.permission_group.MICROPHONE),
        GROUP_PHONE(Manifest.permission_group.PHONE),
        GROUP_SENSORS(Manifest.permission_group.SENSORS),
        GROUP_SMS(Manifest.permission_group.SMS),
        GROUP_STORAGE(Manifest.permission_group.STORAGE),

        NULL("");

        private final String permission;

        PermissionEnum(String permission) {
            this.permission = permission;
        }

        public static PermissionEnum fromManifestPermission(@NonNull String value) {
            for (PermissionEnum permissionEnum : PermissionEnum.values()) {
                if (value.equalsIgnoreCase(permissionEnum.permission)) {
                    return permissionEnum;
                }
            }
            return NULL;
        }

        @Override
        public String toString() {
            return permission;
        }

    }
}
