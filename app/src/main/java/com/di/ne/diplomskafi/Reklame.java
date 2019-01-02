package com.di.ne.diplomskafi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.di.ne.diplomska.SQLite.DatabaseHelper;
import com.di.ne.diplomska.SQLite.Nastavitve;

import java.io.File;
import java.util.List;

/**
 * Created by Nejc on 28.05.2016.
 * Is running in thread in background. pops up with a smal text box every n-seconds
 */
public class Reklame extends Activity {
    DatabaseHelper db = new DatabaseHelper(this);
    long VelikostCrk=22;
    String PhoFilePath = Environment.getExternalStorageDirectory()+"/DIPL/Slike/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.komponenta_reklam);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = 300;
        params.width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        params.alpha = 1.0f;
        params.dimAmount = 0.5f;
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        ImageView slika = (ImageView)findViewById(R.id.ReklamnaSlika);
        TextView naslov = (TextView)findViewById(R.id.NaslovReklame);
        TextView vsebina = (TextView)findViewById(R.id.VsebinaReklame);

        List<Nastavitve> nast = db.readNastavitve();
        if(nast.size()!=0){
            VelikostCrk = nast.get(0).getVelikostPisave();
        }else {
            VelikostCrk = 20;
        }

        List<com.di.ne.diplomska.SQLite.Reklame> ReklameList = db.readReklame();
        if(ReklameList.size()!=0){
            String sli = ReklameList.get(0).getSlika();
            File imgFile = new File(sli);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                slika.setImageBitmap(myBitmap);
            }else{
                int resId = getResources().getIdentifier("kvadrat", "drawable", getPackageName());
                slika.setImageResource(resId);
            }
            android.view.ViewGroup.LayoutParams layoutParams = slika.getLayoutParams();
            layoutParams.width = 200;
            layoutParams.height = 200;
            slika.setLayoutParams(layoutParams);
            naslov.setText(ReklameList.get(0).getNaslov());
            vsebina.setText(ReklameList.get(0).getVsebina());
        }else{
            int resId = getResources().getIdentifier("reklame", "drawable", getPackageName());
            slika.setImageResource(resId);
            android.view.ViewGroup.LayoutParams layoutParams = slika.getLayoutParams();
            layoutParams.width = 250;
            layoutParams.height = 250;
            slika.setLayoutParams(layoutParams);
            naslov.setText("Reklama");
            naslov.setTextSize(VelikostCrk+5);
            vsebina.setText("Tukaj bi lahko bil vaš tekst");
            vsebina.setTextSize(VelikostCrk);
        }

    }

    public void OpenURL (View v){
        List<com.di.ne.diplomska.SQLite.Reklame> ReklameList = db.readReklame();
        String url;
        if(ReklameList.size()!=0){
            url = ReklameList.get(0).getURL();
        }else{
            url = "http://www.fnm.um.si/";
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
