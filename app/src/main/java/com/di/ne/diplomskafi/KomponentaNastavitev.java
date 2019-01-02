package com.di.ne.diplomskafi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.di.ne.diplomska.SQLite.DatabaseHelper;
import com.di.ne.diplomska.SQLite.Nastavitve;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.File;
import java.util.List;

/**
 * Created by Nejc on 28.05.2016.
 * Setings
 */
public class KomponentaNastavitev extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    Activity activity = this;
    Dialog errorDialog;
    int button = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.komponenta_nastavitev);
        EditText velikostCrk = (EditText)findViewById(R.id.VelikostCrk);
        EditText barva = (EditText)findViewById(R.id.Barva);
        TextView filePath = (TextView)findViewById(R.id.locDatoteke);
        TextView imagePath = (TextView)findViewById(R.id.locSlik);
        TextView enacbaPath = (TextView)findViewById(R.id.locEnacbe);

         List<Nastavitve> nast = db.readNastavitve();
        if(nast.size()!=0){
            velikostCrk.setText(nast.get(0).getVelikostPisave()+"");
             barva.setText(nast.get(0).getBarva());
             filePath.setText(nast.get(0).getFilePath());
             imagePath.setText(nast.get(0).getImagePath());
             enacbaPath.setText(nast.get(0).getEnacbePath());
        }else{
            velikostCrk.setText("20");
            barva.setText("Blue");
            filePath.setText("/DIPL/InputFile.txt");
            imagePath.setText("/DIPL/Slike/");
            enacbaPath.setText("/DIPL/SlikeEnacbe/");
        }
    }

    public void SetNast(View view) {
        EditText velikostCrk = (EditText)findViewById(R.id.VelikostCrk);
        EditText barva = (EditText)findViewById(R.id.Barva);
        TextView filePath = (TextView)findViewById(R.id.locDatoteke);
        TextView imagePath = (TextView)findViewById(R.id.locSlik);
        TextView enacbaPath = (TextView)findViewById(R.id.locEnacbe);

        Nastavitve nast = new Nastavitve();
        nast.setVelikostPisave(Long.parseLong(velikostCrk.getText().toString().trim()));
        nast.setBarva(barva.getText().toString().trim());
        nast.setImagePath(imagePath.getText().toString());
        nast.setEnacbePath(enacbaPath.getText().toString());
        nast.setFilePath(filePath.getText().toString());
        db.clearNastavitve();
        db.writeNastavitve(nast);
        Log.d("Nastavitve", "set");
    }

    public void Branje(View view) {
        Log.d("Nastavitve", "branje");
        Intent intent = new Intent(this, Aplikacija.class);
        intent.putExtra("Par","Branje");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Choose how to find the File?").setPositiveButton("File Picker", dialogClickListener)
                .setNegativeButton("Google Drive Download", dialogClickListener).show();
    }

    public void LoDatoteke(View view)  {
        button = Constants.BUTTON_LO_DAT;
        openDialog();
    }

    public void LoSlike (View view) {
        button = Constants.BUTTON_LO_SLI;
        openDialog();
    }
    public void LoEnacbe(View view)  {
        button = Constants.BUTTON_LO_ENA;
        openDialog();
    }
    public void Pobrisi(View view)  {
        db.clearNastavitve();
    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {

                if (errorDialog == null) {
                    errorDialog = googleApiAvailability.getErrorDialog(this, resultCode, 2404);
                    errorDialog.setCancelable(false);
                }

                if (!errorDialog.isShowing())
                    errorDialog.show();
            }
        }

        return resultCode == ConnectionResult.SUCCESS;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    switch (button){
                        case Constants.BUTTON_LO_DAT:
                            new FileChooser(activity).setFileListener(new FileChooser.FileSelectedListener() {
                                  @Override
                                  public void fileSelected(final File file) {
                                      Log.d("Dat", file.toString());
                                      TextView filePath = (TextView)findViewById(R.id.locDatoteke);
                                      filePath.setText(file.toString());
                                  }
                              }
                            ).showDialog();
                            break;
                        case Constants.BUTTON_LO_ENA:
                            new FileChooser(activity).setFileListener(new FileChooser.FileSelectedListener() {
                                  @Override
                                  public void fileSelected(final File file) {
                                      Log.d("Dat", file.toString().substring(0,file.toString().lastIndexOf("/")+1));
                                      TextView imagePath = (TextView)findViewById(R.id.locSlik);
                                      imagePath.setText(file.toString().substring(0,file.toString().lastIndexOf("/")+1));
                                  }
                              }
                            ).showDialog();
                            break;
                        case Constants.BUTTON_LO_SLI:
                            new FileChooser(activity).setFileListener(new FileChooser.FileSelectedListener() {
                                  @Override
                                  public void fileSelected(final File file) {
                                      Log.d("Dat", file.toString().substring(0,file.toString().lastIndexOf("/")+1));
                                      TextView enacbaPath = (TextView)findViewById(R.id.locEnacbe);
                                      enacbaPath.setText(file.toString().substring(0,file.toString().lastIndexOf("/")+1));
                                  }
                                }
                            ).showDialog();
                            break;
                    }

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    if (checkPlayServices()) {
                        //TODO Download Google drive File

                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("Phone not suports Play Services").setPositiveButton("File Picker", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
                    }
                    switch (button){
                        case Constants.BUTTON_LO_DAT:
                            //Log.d("Dat", file.toString());
                            //TextView filePath = (TextView)findViewById(R.id.locDatoteke);
                            //filePath.setText(file.toString());
                            break;
                        case Constants.BUTTON_LO_ENA:
                            //Log.d("Dat", file.toString().substring(0,file.toString().lastIndexOf("/")+1));
                            //TextView imagePath = (TextView)findViewById(R.id.locSlik);
                            //imagePath.setText(file.toString().substring(0,file.toString().lastIndexOf("/")+1));
                            break;
                        case Constants.BUTTON_LO_SLI:
                            //Log.d("Dat", file.toString().substring(0,file.toString().lastIndexOf("/")+1));
                            //TextView enacbaPath = (TextView)findViewById(R.id.locEnacbe);
                            //enacbaPath.setText(file.toString().substring(0,file.toString().lastIndexOf("/")+1));
                            break;
                    }
                    break;
            }
        }
    };
}

