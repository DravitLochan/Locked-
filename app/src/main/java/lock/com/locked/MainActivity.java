package lock.com.locked;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        buttonChangePassword= (Button) findViewById(R.id.buttonChangePassword);
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
                                if (et_conf_password.getText().length() < 4)
                                {
                                    et_conf_password.setError("not less than 4 digits");
                                    giveEnterPasswordPrompt();
                                }
                                if (et_password.getText().length() < 4)
                                {
                                    et_password.setError("not less than 4 digits");
                                    giveEnterPasswordPrompt();
                                }
                                if (et_conf_password.getText().toString().equals(et_password.getText().toString())) {
                                    passPref.setPassword(et_password.getText().toString());
                                    Toast.makeText(context, "password set!", Toast.LENGTH_SHORT).show();
                                    passPref.setIsPasswordSet(true);
                                    finish();
                                } else {
                                    Toast.makeText(context, "password and confirm passwords should be same", Toast.LENGTH_LONG).show();
                                    et_conf_password.setText("");
                                    et_password.setText("");
                                    giveEnterPasswordPrompt();
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

    private void giveChangePasswordPrompt()
    {
        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.change_password, null);
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
                                EditText et_password = (EditText) promptsView.findViewById(R.id.et_change_password);
                                EditText et_conf_password = (EditText) promptsView.findViewById(R.id.et_change_conf_password);
                                if (et_conf_password.getText().length() < 4)
                                {
                                    et_conf_password.setError("not less than 4 digits");
                                    giveEnterPasswordPrompt();
                                }
                                if (et_password.getText().length() < 4)
                                {
                                    et_password.setError("not less than 4 digits");
                                    giveEnterPasswordPrompt();
                                }
                                if (et_conf_password.getText().toString().equals(et_password.getText().toString())) {
                                    passPref.setPassword(et_password.getText().toString());
                                    Toast.makeText(context, "password set!", Toast.LENGTH_SHORT).show();
                                    passPref.setIsPasswordSet(true);
                                    finish();
                                } else {
                                    Toast.makeText(context, "password and confirm passwords should be same", Toast.LENGTH_LONG).show();
                                    et_conf_password.setText("");
                                    et_password.setText("");
                                    giveEnterPasswordPrompt();
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
