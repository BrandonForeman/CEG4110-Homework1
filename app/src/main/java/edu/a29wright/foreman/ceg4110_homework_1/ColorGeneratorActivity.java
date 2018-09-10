package edu.a29wright.foreman.ceg4110_homework_1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ColorGeneratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_generator);
    }

    public void colorButton_OnClick(View v)
    {
        TextView colorInfo = findViewById(R.id.colorGenerator_colorInfo_textView);
        EditText coloredText = findViewById(R.id.colorGenerator_coloredText_editText);
        int[] colors = generateColor();
        // Format for hexadecimal display
        coloredText.setTextColor(Color.rgb(colors[0], colors[1], colors[2]));
        String red = String.format("%02X", colors[0]);
        String green = String.format("%02X", colors[1]);
        String blue = String.format("%02X", colors[2]);
        colorInfo.setText("COLOR: " + colors[0] +"r, "+ colors[1] + "g, "+ colors[2] +"b, #" + red + green + blue);

    }

    private int[] generateColor()
    {
        int max = 255;
        int min = 0;
        int range = max - min + 1;
        return new int[]
                { ((int)(Math.random() * range) + min), ((int)(Math.random() * range) + min), (int)(Math.random() * range) + min};
    }

    /* This is the method that is called when the hardware back button is pressed. */
    @Override
    public void onBackPressed() {
        super.finish();
    }

}
