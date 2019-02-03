package com.murat.getallprojects;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class TaslaklarJava extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    ArrayList<TaslakModel> gunler;
    ListView lvTaslaklar;
    FloatingActionButton fltbtnYeniunBelirle;
    Donusturucu cevir = new Donusturucu();
    String strIcerik;
    ProgressBar progressBar;

    ////////firebase ads
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taslaklar);
        Handler h = new Handler();
        //veriTabaniniCek();
        //loginprocess();,
        progressBar =  findViewById(R.id.pbTaslaklar);
        fltbtnYeniunBelirle = (FloatingActionButton) findViewById(R.id.floatYeniOzelGun);
        gunler = new ArrayList<>();
        lvTaslaklar =  findViewById(R.id.lvTaslaklar);
        initializeFirebaseDatabase();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDaysName();
            }
        }, 0000);
        MobileAds.initialize(this, "ca-app-pub-9057596586284356~6009005822");

        InterstitialAds();

        lvTaslaklar.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        lvTaslaklar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                strIcerik = ((TextView) (view.findViewById(R.id.btnOzelGunSatir))).getText().toString();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    Toast.makeText(TaslaklarJava.this, "" + strIcerik, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(TaslaklarJava.this, Taslak_Icerik.class);
                    i.putExtra("ozelGunExtra", cevir.stringToDb(strIcerik));
                    // i.putExtra("dbNameContent", gunler.get(position).getDbName());
                    startActivity(i);
                } else {
                    Toast.makeText(TaslaklarJava.this, "" + strIcerik, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(TaslaklarJava.this, Taslak_Icerik.class);
                    i.putExtra("ozelGunExtra", cevir.stringToDb(strIcerik));
                    // i.putExtra("dbNameContent", gunler.get(position).getDbName());
                    startActivity(i);
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }


            }
        });
    }

    int count = 0;

    private void InterstitialAds() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9057596586284356/9989429061");
       // mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("3B7901BF136B0DA7181DD6B1BCAB10F6").build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                count++;
                if (count == 2) {
                    count = 0;
                    InterstitialAds();
                }
                Log.i("Ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                // Code to be executed when when the interstitial ad is closed.
                Log.i("Ads", "onAdClosed");
            }
        });
    }

    private void initializeFirebaseDatabase() {
        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
        }
    }

    private void gunListView() {
        TaslaklarAdaptor taslaklarAdaptor = new TaslaklarAdaptor(this, gunler);
        lvTaslaklar.setAdapter(taslaklarAdaptor);
        lvTaslaklar.setTextFilterEnabled(true);

    }

    private void getDaysName() {
        progressBar.setVisibility(View.VISIBLE);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("OzelGunler");
        myRef.keepSynced(true);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    TaslakModel taslakOzel = new TaslakModel(cevir.dbToString(dsp.getKey().toString()));
                    gunler.add(taslakOzel);
                }
                if (gunler.size() <= 0) {
                    getDaysName();
                } else {
                    progressBar.setVisibility(View.GONE);
                    gunListView();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                // set title
                alertDialogBuilder.setTitle("EXIT ?");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Çıkmak istiyor musunuz ?")
                        .setCancelable(false)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
