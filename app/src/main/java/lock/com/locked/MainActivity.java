package lock.com.locked;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Context context;
    PassPref passPref;
    Button buttonChangePassword;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        notificationManager = (NotificationManager) getSystemService(context.NOTIFICATION_SERVICE);
        Notification notify = new Notification.Builder(context).setContentTitle("LOCKED?")
                .setContentText("Unlocked").setContentTitle("subject").setSmallIcon(R.mipmap.ic_launcher).setOngoing(true)
                .build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notify.flags |= Notification.FLAG_NO_CLEAR;
        notificationManager.notify(0,notify);
        buttonChangePassword = (Button) findViewById(R.id.buttonChangePassword);
        passPref = new PassPref(context);
        if (passPref.getIsPasswordSet() == false) {
            buttonChangePassword.setVisibility(View.INVISIBLE);
            giveEnterPasswordPrompt();
        }
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giveChangePasswordPrompt();
            }
        });
    }

    private void giveChangePasswordPrompt() {
        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.change_password, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        TextView tv_password_message = (TextView) promptsView.findViewById(R.id.tv_password_message);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("CHANGE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EditText et_old_password = (EditText) promptsView.findViewById(R.id.et_old_password);

                                if(et_old_password.getText().toString().equals(passPref.getPassword()))
                                {
                                    giveEnterPasswordPrompt();
                                }
                                else
                                {
                                    Toast.makeText(context,"given old password did not match",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void giveEnterPasswordPrompt() {
        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.enter_password, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        TextView tv_password_message = (TextView) promptsView.findViewById(R.id.tv_password_message);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("SET",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EditText et_password = (EditText) promptsView.findViewById(R.id.et_password);
                                EditText et_conf_password = (EditText) promptsView.findViewById(R.id.et_conf_password);
                                if (et_conf_password.getText().toString().equals("") || et_password.getText().toString().equals("")) {
                                    Toast.makeText(context, "cannot be blank", Toast.LENGTH_SHORT).show();
                                    giveEnterPasswordPrompt();
                                }
                                else
                                {
                                    if (et_conf_password.getText().toString().equals(et_password.getText().toString())) {
                                        passPref.setPassword(et_password.getText().toString());
                                        Toast.makeText(context, "password set!", Toast.LENGTH_SHORT).show();
                                        passPref.setIsPasswordSet(true);
                                        finish();
                                    } else if(!et_conf_password.getText().toString().equals(et_password.getText().toString())){
                                        Toast.makeText(context, "password and confirm passwords should be same", Toast.LENGTH_LONG).show();
                                        et_conf_password.setText("");
                                        et_password.setText("");
                                        giveEnterPasswordPrompt();
                                    }
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
