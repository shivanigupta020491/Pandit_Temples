package com.indtrack.pandit_temples;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;



public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private static int REQUEST_CODE = 1;
    private static int REQUEST_CODE_READ_PHONE_STATE = 0;

    private Animation topAnimation;
    private Animation bottomAnimation;
    //private ImageView imageView;
    private TextView imageView;
    private TextView logo;
    private TextView slogon;

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    String[] permissionsRequired = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
      getPermissionFromUser();

        initView();

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

    }

    private void initView() {
        topAnimation = AnimationUtils.loadAnimation(this,
                R.anim.top_animation
        );
        bottomAnimation = AnimationUtils.loadAnimation(this,
                R.anim.bottom_animation
        );
        imageView = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView1);
        slogon = findViewById(R.id.textView2);

        imageView.setAnimation(topAnimation);
        logo.setAnimation(bottomAnimation);
        slogon.setAnimation(bottomAnimation);

//        Handler().postDelayed({
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(this, LoginActivity4::class.java)
//
//
//        val pair1 = UtilPair.create<View, String>(imageView!!,"logo_image")
//        val pair2 = UtilPair.create<View, String>(logo!!,"logo_text")
//
//        var option: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity4,pair1,pair2)
//        startActivity(mainIntent,option.toBundle())
//
//        //carry animation
//        //option.toBundle()
////            startActivity(mainIntent)
////            finish()
//        }, 5000)
        //}
    }


    //for hide system navigation bar

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUIAndNavigation(this);
        }
    }

    private void hideSystemUIAndNavigation(Activity activity) {
        // The DecorView is the view that actually holds the window’s background drawable. Calling getWindow().setBackgroundDrawable()
        View decorView = activity.getWindow().getDecorView();

        int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
        systemUiVisibility |= View.SYSTEM_UI_FLAG_IMMERSIVE;
        // Set the content to appear under the system bars so that the
        // content doesn't resize when the system bars hide and show.
        systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        // Hide the nav bar and status bar
        systemUiVisibility |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        systemUiVisibility |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(systemUiVisibility);

    }

    /**
     * this smethodm is use to get the permission from user.
     */
    private void getPermissionFromUser() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        if (ActivityCompat.checkSelfPermission(SplashActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashActivity.this, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashActivity.this, permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissionsRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissionsRequired[4])
            ) {

                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle("Need Permissions");
                builder.setMessage(" You have denied the permission needed to run this application, thus closing the app");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(SplashActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle("Need Permission");
                builder.setMessage("You have denied the permission needed to run this application, thus closing the app");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Location", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(SplashActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }

            // txtPermissions.setText("Permissions Required");

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.commit();
        } else {

//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, this);
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(SplashActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(SplashActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }


    private void proceedAfterPermission() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             //   if (!SessionManager.getInstance(SplashActivity.this).isLogin()) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View,String>(imageView,"logo_image");
                    pairs[1] = new Pair<View,String>(logo,"logo_text");
                    //startActivity(intent);
                    ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);
                    startActivity(intent,option.toBundle());

               // } else {
                    //  orgranizatzaiotnion
//                    if (SessionManager.getInstance(SplashActivity.this).getRoleId().equalsIgnoreCase("13")) {
                        // System.out.println("<<<<<<<<<<<<<<<<<< requestBody  login api roleId org" + getRoleId());
//                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//
//                        startActivity(intent);
//
//                        // individual
//                    } else if (SessionManager.getInstance(SplashActivity.this).getRoleId().equalsIgnoreCase("11")) {
//                        //System.out.println("<<<<<<<<<<<<<<<<<< requestBody  login api roleId ind" + getRoleId());
//                        Intent intent = new Intent(SplashActivity.this, MainActivityIndividual.class);
//                        startActivity(intent);
//                    }
                }


        },2000);
//        Thread background = new Thread() {
//            public void run() {
//                try {
//
//                    sleep(2 * 1000);
//
//               SessionManager.getInstance(SplashActivity.this).CheckLogin();
//
//
//                } catch (Exception e) {
//                    System.out.print(e.getMessage());
//                }
//            }
//        };
//        background.start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                proceedAfterPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissionsRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissionsRequired[4])
            ) {
//                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissionsRequired[3])) {
                //                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,permissionsRequired[4])){
                //txtPermissions.setText("Permissions Required");
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle("Need Permissions");
                builder.setMessage("You have denied the permission needed to run this application, thus closing the app");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(SplashActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }



}

