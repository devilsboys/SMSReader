package com.example.admin.smsread;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private static final int R_per =123 ;
    @TargetApi(Build.VERSION_CODES.M)
    public void buLoad(View view) {
        if((int) Build.VERSION.SDK_INT >= 23){
            // get permission
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED){
                if(!shouldShowRequestPermissionRationale(android.Manifest.permission.READ_SMS)){
                    requestPermissions(new String[]{android.Manifest.permission.READ_SMS},R_per);
                }
                return;
            }
        }
        loadInboxMessages();
    }

    public  void  onRequestPermissionsResult(int requestCode, String[] permissions, int [] grandResults){
        switch (requestCode){
            case  R_per:
                if(grandResults[0] == PackageManager.PERMISSION_GRANTED){
                    // permission Granted
                    loadInboxMessages();
                }
                else {
                    // permission Danied
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grandResults);
        }
    }

    // load user inbox messages
    void loadInboxMessages(){
        try{
            String sms = "";
            Uri uriSMSURI = Uri.parse("content://sms/inbox");
            Cursor cur = getContentResolver().query(uriSMSURI,null,null,null,null);
            cur.moveToPosition(0);
            do{
                //load sender and the message content
                sms += "From : " + cur.getString(cur.getColumnIndex("address")) + "\n" +  cur.getString(cur.getColumnIndex("body")) +"\n\n\n";
            }
            while (cur.moveToNext());
            /*
            while (cur.moveToNext()){
                //load sender and the message content
                sms += "From : " + cur.getString(cur.getColumnIndex("address")) + ":" +  cur.getString(cur.getColumnIndex("body")) +"\n";
            }
            */
            // display message in textbox
            TextView txt = (TextView)findViewById(R.id.textViewM);
            txt.setText(sms);
        }
        catch (Exception ex){

        }

    }
}
