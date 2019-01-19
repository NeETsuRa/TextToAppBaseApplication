package com.di.ne.diplomskafi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.di.ne.diplomska.SQLite.DatabaseHelper;
import com.di.ne.diplomska.SQLite.Nastavitve;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

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
            filePath.setText("/storage/emulated/0/dev/ExampleValues/Text/InputFile.txt");
            imagePath.setText("/storage/emulated/0/dev/ExampleValues/Slike/");
            enacbaPath.setText("/storage/emulated/0/dev/ExampleValues/Enacbe/");
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
        List<Nastavitve> n = db.readNastavitve();
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
        builder.setMessage("Choose how to find the File?").setPositiveButton("File Picker", dialogClickListener).show();
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

    private void showFileChooser(int FILE_SELECT_CODE) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            Uri uri = data.getData();
            String path = FileChooser.getPath(this, uri);
            TextView filePath;
            switch (requestCode) {
                case Constants.BUTTON_LO_DAT:

                    filePath = (TextView)findViewById(R.id.locDatoteke);
                    if (path!=null){
                        filePath.setText(path.toString());
                    }else{
                        filePath.setText("Please use an local file");
                    }
                    break;
                case Constants.BUTTON_LO_ENA:
                    filePath = (TextView)findViewById(R.id.locEnacbe);
                    if (path!=null){
                        filePath.setText(path.toString().substring(0,path.toString().lastIndexOf("/")+1));
                    }else{
                        filePath.setText("Please use an local file");
                    }
                    break;
                case Constants.BUTTON_LO_SLI:
                    filePath = (TextView)findViewById(R.id.locSlik);
                    if (path!=null){
                        filePath.setText(path.toString().substring(0,path.toString().lastIndexOf("/")+1));
                    }else{
                        filePath.setText("Please use an local file");
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    switch (button){
                        case Constants.BUTTON_LO_DAT:
                            showFileChooser(Constants.BUTTON_LO_DAT);
                            break;
                        case Constants.BUTTON_LO_ENA:
                            showFileChooser(Constants.BUTTON_LO_ENA);
                            break;
                        case Constants.BUTTON_LO_SLI:
                            showFileChooser(Constants.BUTTON_LO_SLI);
                            break;
                    }

                    break;
            }
        }
    };
}

