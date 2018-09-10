package edu.a29wright.foreman.ceg4110_homework_1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

public class ColorPickerActivity extends AppCompatActivity {
    private int red, green, blue;
    private final int min = 0, max = 255;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        // Get the extras from the parent intent. This is necessary so that if a previous color has been picked, then we want that choice to be still reflected here
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            red = extras.getInt("red");
            green = extras.getInt("green");
            blue = extras.getInt("blue");
            updateRGB();
        }
        setupNumberPickerValues();
    }

    /* Setup the number pickers */
    private void setupNumberPickerValues() {
        NumberPicker np = findViewById(R.id.colorPicker_red_numberPicker);
        np.setMinValue(min);
        np.setMaxValue(max);
        np.setValue(red);
        np.setOnValueChangedListener(onValueChangeListener);

        np = findViewById(R.id.colorPicker_green_numberPicker);
        np.setMinValue(min);
        np.setMaxValue(max);
        np.setValue(green);
        np.setOnValueChangedListener(onValueChangeListener);

        np = findViewById(R.id.colorPicker_blue_numberPicker);
        np.setMinValue(min);
        np.setMaxValue(max);
        np.setValue(blue);
        np.setOnValueChangedListener(onValueChangeListener);
    }


    /* Update the RGB text display and image view display */
    private void updateRGB() {
        TextView rgb = findViewById(R.id.colorPicker_rgb_textView);
        ImageView rgbDisplay = findViewById(R.id.colorPicker_rgbPreview_imageView);

        rgb.setText("COLOR: " + red + "r, " + green + "g, " + blue + "b");
        rgbDisplay.setBackgroundColor(Color.rgb(red, green, blue));
    }

    /* Event listener for number pickers */
    NumberPicker.OnValueChangeListener onValueChangeListener =
            new 	NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    int value = numberPicker.getValue();
                    switch (numberPicker.getId()) {
                        case R.id.colorPicker_red_numberPicker:
                            red = value;
                            break;
                        case R.id.colorPicker_green_numberPicker:
                            green = value;
                            break;
                        case R.id.colorPicker_blue_numberPicker:
                            blue = value;
                            break;
                    }
                    updateRGB();
                }
            };

    /* This is the finish() method which is called when the user wants to exit the current activity i.e. clicked the back button. */
    @Override
    public void finish() {
        // Since this intent is now finished, we need to send the color selection choices back to the parent intent
        Intent data = new Intent();
        data.putExtra("red", red);
        data.putExtra("green", green);
        data.putExtra("blue", blue);
        setResult(RESULT_OK, data);
        super.finish();
    }

    /* This is the method that is called when the hardware back button is pressed. */
    @Override
    public void onBackPressed() {
        finish();
    }

}
