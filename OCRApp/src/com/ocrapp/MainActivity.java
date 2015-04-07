package com.ocrapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import br.com.thinkti.android.filechooser.FileChooser;

import com.googlecode.tesseract.android.TessBaseAPI;

public class MainActivity extends Activity {
	private static final String TESSBASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/tesseract/";
	private static final String DEFAULT_LANGUAGE = "eng";

	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView = (TextView) findViewById(R.id.textView1);

		final String inputText = "Test text";
		final Bitmap bmp = getTextImage(inputText, 640, 480);

		final TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
		baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
		baseApi.setImage(bmp);
		String text = baseApi.getUTF8Text();
		
		textView.setText(text);
		
		baseApi.end();
		
		Intent intent = new Intent(this, FileChooser.class);
		ArrayList<String> extensions = new ArrayList<String>();
		extensions.add(".pdf");
		extensions.add(".xls");
		extensions.add(".xlsx");
		intent.putStringArrayListExtra("filterFileExtension", extensions);
		startActivityForResult(intent, 1);
	}
	
    private static Bitmap getTextImage(String text, int width, int height) {
        final Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Paint paint = new Paint();
        final Canvas canvas = new Canvas(bmp);

        canvas.drawColor(Color.WHITE);

        paint.setColor(Color.BLACK);
        paint.setStyle(Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextAlign(Align.CENTER);
        paint.setTextSize(24.0f);
        canvas.drawText(text, width / 2, height / 2, paint);

        return bmp;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
