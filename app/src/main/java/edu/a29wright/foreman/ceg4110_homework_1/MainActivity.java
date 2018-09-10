package edu.a29wright.foreman.ceg4110_homework_1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView)findViewById(R.id.menu_warningMessage);
        tv.setText("Images are saved in your device's default picture directory. They will be viewable from your device's image gallery.");
    }

    public void menuButtonClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.menu_part1_button:
                intent = new Intent(this, ColorGeneratorActivity.class);
                break;
            case R.id.menu_part2_button:
                intent = new Intent(this, CanvasActivity.class);
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }

}
