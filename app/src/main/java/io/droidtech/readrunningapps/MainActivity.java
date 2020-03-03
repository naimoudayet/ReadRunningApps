package io.droidtech.readrunningapps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView textViewProcessName;

    private static Pattern ActivityNamePattern;
    private static String LogCatCommand;
    private static String ClearLogCatCommand;

    ActivityManager activityManager;
    Context context = MainActivity.this;

    List<ActivityManager.RunningAppProcessInfo> runningAppProcessList;
    private static Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewProcessName = findViewById(R.id.textview_process_name);
        //runningAppProcessList = activityManager.getRunningAppProcesses();
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        textViewProcessName.setText("packages:"+ packages.size());
        for (ApplicationInfo applicationInfo : packages) {
            Log.d("MainActivity:Loop", "Installed package: " + applicationInfo.packageName);
            textViewProcessName.append(applicationInfo.packageName + "\n");
        }
        /*try {
            Process p = Runtime.getRuntime().exec(new String[]{"su", "-c", "system/bin/sh"});
            DataOutputStream stdin = new DataOutputStream(p.getOutputStream());
//from here all commands are executed with su permissions
            stdin.writeBytes("ls /data\n"); // \n executes the command
            InputStream stdout = p.getInputStream();
            byte[] buffer = new byte[1024];
            int read;
            String out = new String();
//read method will wait forever if there is nothing in the stream
//so we need to read it in another way than while((read=stdout.read(buffer))>0)
            while (true) {
                read = stdout.read(buffer);
                out += new String(buffer, 0, read);
                if (read < 1024) {
                    //we have read everything
                    break;
                }
                textViewProcessName.append(read + "\n");
            }
        } catch (IOException e) {

        }
//do something with the output

        //new SimpleThread().start();

/*
         runningAppProcessList = activityManager.getRunningAppProcesses();

         for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessList) {

             textViewProcessName.setText(textViewProcessName.getText() + " " + processInfo.processName + "\n");
        }

        PackageManager packageManager = getPackageManager();

        // get list of apps
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        textViewProcessName.setText("packages:"+ packages.size());
        for (ApplicationInfo applicationInfo : packages) {
             Log.d("MainActivity:Loop", "Installed package: " + applicationInfo.packageName);
            textViewProcessName.append(applicationInfo.packageName + "\n");
        }*/


    }

    class SimpleThread extends Thread {

        public void run() {
            Process logcat;
            final StringBuilder log = new StringBuilder();
            try {
                logcat = Runtime.getRuntime().exec(new String[]{"logcat", "-d"});
                BufferedReader br = new BufferedReader(new InputStreamReader(logcat.getInputStream()), 4 * 1024);
                String line;
                String separator = System.getProperty("line.separator");
                while ((line = br.readLine()) != null) {
                   // updateText(line);
                    // textViewProcessName.append(separator);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}
