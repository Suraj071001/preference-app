package com.example.android.setting;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static TextView textView,number;

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.setting_menu){
            Intent intent = new Intent(this,Setting.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        number = findViewById(R.id.textView2);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean a = sharedPreferences.getBoolean("show_bass",true);
        Toast.makeText(this, ""+a, Toast.LENGTH_SHORT).show();

        if (!a){
            textView.setVisibility(View.GONE);
        }else{
            textView.setVisibility(View.VISIBLE);
        }
        changeColor(sharedPreferences);
        changeNumber(sharedPreferences);

    }
    public void changeColor(SharedPreferences sharedPreferences){
        textView.setText(sharedPreferences.getString(getString(R.string.pref_color_key),getString(R.string.pref_color_red_value)));
    }
    public void changeNumber(SharedPreferences sharedPreferences){
        number.setText(sharedPreferences.getString(getString(R.string.pref_edit_key),getString(R.string.pref_default_value)));
    }
}