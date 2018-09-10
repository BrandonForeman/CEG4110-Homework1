package edu.a29wright.foreman.ceg4110_homework_1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.divyanshu.draw.widget.DrawView;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;


public class CanvasActivity extends AppCompatActivity {
    private int red = 255, green = 255, blue = 255;
    private final int REQUEST_CODE = 123;
    private DrawView drawView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        drawView = findViewById(R.id.draw_view);
    }

    /* Button handler */
    public void canvas_onClick(View view) {
        switch (view.getId()) {
            case R.id.canvas_colorChange_button:
                // Send the child activity, ColorPickerActivity, any previously chosen color values or the default color values. This preserves any previously chosen color values by the user.
                Intent intent = new Intent(this, ColorPickerActivity.class);
                intent.putExtra("red", red);
                intent.putExtra("green", green);
                intent.putExtra("blue", blue);
                int REQUEST_CODE = 123;
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.canvas_clear_button:
                // Clear drawView
                drawView.clearCanvas();
                break;
            case R.id.canvas_save_button:
                // Save drawView to a compressed bitmap image
                Bitmap bt = getBitmapFromView(drawView);
                saveImage(bt);
                break;
        }
    }

    /* Override for when the child activity called finishes */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Child intent, ColorGeneratorActivity, has finished. We want to check the resultCode and if it is RESULT_OK, then retrieve the color values selected by the user.
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("red")) {
                red = data.getExtras().getInt("red");
                green = data.getExtras().getInt("green");
                blue = data.getExtras().getInt("blue");
                drawView.setColor(Color.rgb(red, green, blue));
            }
        }
    }

    /* Save image to external storage location and inform the user of its result */
    private void saveImage(Bitmap bitmap) {
        // create the file which will be our image. Image should be saved in the public pictures folder for the device
        File file = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Image_"+ LocalDateTime.now() +".jpg");
        try {
            // open up an output stream
            FileOutputStream out = new FileOutputStream(file);
            // Compress the bitmap into a image format and output the compressed bitmap into the output stream. 90% quality seems to not lose much quality.
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            // flush out anything that might of accumulated in the output stream just in-case
            out.flush();
            // close the file output stream
            out.close();

            // Update the image gallery of the phone to now see our newly created image
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));

            // Notify the user that the image was saved
            Context context = getApplicationContext();
            CharSequence text = "Image successfully saved. Please go to your mobile device's picture gallery to view the image.";
            int duration = Toast.LENGTH_LONG;
            sendToast(context, text, duration);

        } catch (Exception e) {
            e.printStackTrace();
            // Inform the user that the image didn't save
            Context context = getApplicationContext();
            CharSequence text = "Image unsuccessfully saved.";
            int duration = Toast.LENGTH_SHORT;
            sendToast(context, text, duration);
        }
    }

    private void sendToast(Context context, CharSequence text, int duration) {
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /* Get the a drawn bitmap from a view */
    private Bitmap getBitmapFromView(View view) {
        // create bitmap from view dimensions
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        // create canvas that way the bitmap can be rendered
        Canvas canvas = new Canvas(bitmap);
        // since drawView is transparent, then its rendered bitmap will be too. We must first set the background color to white before drawing the bitmap
        canvas.drawColor(Color.WHITE);
        // Setup the view for drawing out canvas with the bitmap
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        // have to the view draw the canvas with the bitmap inside of it. Without this, we just have a blank white canvas.
        view.draw(canvas);
        // Now that the bitmap has been drawn, we return it
        return bitmap;
    }

    /* This is the method that is called when the hardware back button is pressed. */
    @Override
    public void onBackPressed() {
        super.finish();
    }

}
