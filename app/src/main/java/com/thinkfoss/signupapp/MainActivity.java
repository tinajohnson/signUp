package com.thinkfoss.signupapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Context;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button register = (Button) findViewById(R.id.home_register_button);
        Button view = (Button) findViewById(R.id.home_view_button);
        register.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent registerForm = new Intent(MainActivity.this, SignupForm.class);
                registerForm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(registerForm);
            }
        });

        view.setOnClickListener( new Button.OnClickListener() {
            public void onClick( View v ) {
                Intent viewReg = new Intent( MainActivity.this, ViewReg.class );
                startActivity(viewReg);
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
