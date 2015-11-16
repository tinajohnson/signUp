package com.thinkfoss.signupapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbHelper dbhelper = new DbHelper(this);
        final SQLiteDatabase db = dbhelper.getWritableDatabase();
        setContentView(R.layout.activity_signup);
        Button register = (Button) findViewById(R.id.form_register_button);
        register.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                final String name = (((EditText) findViewById(R.id.name)).getText()).toString();
                final String design = (((EditText) findViewById(R.id.title)).getText()).toString();;
                final String email = (((EditText) findViewById(R.id.email)).getText()).toString();;
                final String mob = (((EditText) findViewById(R.id.mob)).getText()).toString();;
                final String area = (((EditText) findViewById(R.id.interest)).getText()).toString();;
                final String comments = (((EditText) findViewById(R.id.comments)).getText()).toString();;

                ContentValues content = new ContentValues();
                content.put(DbHelper.COLUMN_NAME_PNAME, name);
                content.put(DbHelper.COLUMN_NAME_DESIGN, design);
                content.put(DbHelper.COLUMN_NAME_EMAIL,email);
                content.put(DbHelper.COLUMN_NAME_MOBL, mob);
                content.put(DbHelper.COLUMN_NAME_AREA, area);
                content.put(DbHelper.COLUMN_NAME_COMMENTS, comments);

                if ( name.isEmpty() && design.isEmpty() && email.isEmpty() && mob.isEmpty() && area.isEmpty() && comments.isEmpty() ) {
                    Toast.makeText(SignupForm.this, "All fields are empty!", Toast.LENGTH_SHORT).show();
                } else if( name.isEmpty() || email.isEmpty() || mob.isEmpty() ) {
                    Toast.makeText(SignupForm.this, "Name, Email and mobile are required fields!", Toast.LENGTH_SHORT).show();
                } else{
                    db.insert(DbHelper.TABLE_NAME, null, content);
                    Toast.makeText(SignupForm.this, "Thank you for your response!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
