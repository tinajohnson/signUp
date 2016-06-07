package com.thinkfoss.signupapp;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ViewReg extends AppCompatActivity
        implements SelectCityFragment.NoticeDialogListener
{

    private SQLiteDatabase db;
    private String emailSubject;
    private ArrayList<String> result = new ArrayList<String>();
    private ArrayList<String> resultToFile = new ArrayList<String>();
    private ArrayList<String> resultToEmail = new ArrayList<String>();

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String selected) {
        writeEmail(selected);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reg);
        Button deleteBtn = (Button) findViewById(R.id.view_reg_del_button);
        Button saveBtn = (Button) findViewById(R.id.view_save_button);
        TextView yesRegistrations = (TextView) findViewById(R.id.viewReg);
        getData();
        if( result.isEmpty() ) {
            yesRegistrations.setVisibility(View.INVISIBLE);
            deleteBtn.setVisibility(View.INVISIBLE);
            saveBtn.setVisibility(View.INVISIBLE);
        } else {
            TextView noRegistrations = (TextView) findViewById(R.id.noRegText);
            noRegistrations.setVisibility(View.INVISIBLE);
            final ListView listview = (ListView) findViewById(R.id.list);
            final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, result);
            listview.setAdapter(adapter);

            deleteBtn.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog confirmBox = ConfirmDelete();
                }
            });
        }

        saveBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (isExternalStorageWritable()) {
                    DialogFragment selectCity = new SelectCityFragment();
                    selectCity.show(getFragmentManager(), null);
                }
            }
        });

    }

    private AlertDialog ConfirmDelete() {
        AlertDialog confirmDialogBox = new AlertDialog.Builder(this)
                .setTitle("Confirm Deletion")
                .setMessage("Do you want to delete all data?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        DbHelper dbhelper = new DbHelper(getBaseContext());
                        final SQLiteDatabase db = dbhelper.getWritableDatabase();
                        db.delete(DbHelper.TABLE_NAME, null, null);
                        Toast.makeText(ViewReg.this, "All data erased", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
        return confirmDialogBox;

    }

    private void getData() {
        try {
            DbHelper dbhelper = new DbHelper(this);
            db = dbhelper.getWritableDatabase();
            String[] columns = {
                    DbHelper.COLUMN_NAME_PNAME,
                    DbHelper.COLUMN_NAME_DESIGN,
                    DbHelper.COLUMN_NAME_EMAIL,
                    DbHelper.COLUMN_NAME_MOBL,
                    DbHelper.COLUMN_NAME_CITY,
                    DbHelper.COLUMN_NAME_AREA,
                    DbHelper.COLUMN_NAME_COMMENTS
            };

            Cursor c = db.query(
                    DbHelper.TABLE_NAME,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if( c != null ){
                if( c.moveToFirst() ) {
                    do {
                        String name = c.getString(c.getColumnIndex("name"));
                        String design = c.getString(c.getColumnIndex("design"));
                        String email = c.getString(c.getColumnIndex("email"));
                        String mobnum = c.getString(c.getColumnIndex("mobnum"));
                        String city = c.getString(c.getColumnIndex("city"));
                        String interestedarea = c.getString(c.getColumnIndex("interestedarea"));
                        String comments = c.getString(c.getColumnIndex("comments"));
                        //format in which the data is shown in view
                        result.add( "Name: "+ name + "\nDesignation: " + design + "\nEmail: " + email + "\nMobile: "
                                    + mobnum + "\nCity: " + city + "\nArea or Department: " + interestedarea + "\nComments: " + comments );
                        //format in which data is entered to file
                        resultToFile.add( "Name: "+ name + "\nDesignation: " + design + "\nEmail: " + email + "\nMobile: " + mobnum + "\nCity: " + city
                                + "\nArea or Department: " + interestedarea + "\nComments: " + comments + "\n----------------------\n");

                    } while( c.moveToNext() );
                    db.close();
                }
            }
        } catch ( SQLiteException e ) {
            Log.e(getClass().getSimpleName(), "Could not create or open database!!");
        }

    }

    private void getEmailData( String city ) {
        for( int i = 0; i<resultToFile.size(); i++) {
            if( resultToFile.get(i).contains("City: " + city)){
                resultToEmail.add(resultToFile.get(i));
            }
        }
    }

    //writes to SD file and also composes email with registration list as the content
    private void writeEmail( String city ){
            getEmailData(city);
            emailSubject = resultToEmail.toString().substring(1,resultToEmail.toString().length()-1).replace("---\n,","\n");
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, "");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Registration List");
            intent.putExtra(Intent.EXTRA_TEXT, emailSubject );
            startActivity(intent);

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_reg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
