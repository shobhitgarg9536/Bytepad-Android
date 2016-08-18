package in.silive.bytepad;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class MarshMallowPermission {

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 3;
    private static final int SMS_PERMISSION_REQUEST_CODE = 4;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 5;
    private static final int ACCOUNTS_PERMISSION_REQUEST_CODE = 6;
    private Activity activity;

    public MarshMallowPermission(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermissionForExternalStorage() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForCamera() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForSms() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForAccounts() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.GET_ACCOUNTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForLocation() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForRead() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissionForRead() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, "Permission needed to write to external media. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(activity, "Location permission needed for fetching your location. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForAccounts() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.GET_ACCOUNTS)) {
            Toast.makeText(activity, "Accounts permission needed for google signIn. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.GET_ACCOUNTS}, ACCOUNTS_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForSms() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_SMS)) {
            Toast.makeText(activity, "Read sms permission needed for auto detecting OTP. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS}, SMS_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, "External Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }
}