package com.murat.getallprojects;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Taslak_Icerik extends AppCompatActivity {
    String lvIcerikOzelGun;
    ArrayList<TaslakIcerikModel> sozIcerik = null;
    ListView lvIcerik;
    Donusturucu donusturucu = new Donusturucu();
    String childString;
    String queryString;
    ImageButton fltYeniSoz;
    ////////firebase ads
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taslak__icerik);
        train();
        sozIcerik = new ArrayList<>();
        fltYeniSoz = (ImageButton) findViewById(R.id.floatyenisozekle);
        lvIcerik = (ListView) findViewById(R.id.lvTaslakIcerik);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                lvIcerikOzelGun = null;
                queryString = null;
            } else {
                lvIcerikOzelGun = extras.getString("ozelGunExtra");
            }
        } else {
            lvIcerikOzelGun = (String) savedInstanceState.getSerializable("ozelGunExtra");
        }
        childString = lvIcerikOzelGun;


        getAllPrivateDaysContext(childString);
        InterstitialAds();

    }

    String icerik, no;

    private void AdaptorGoster() {

        TaslaklarIcerikAdaptor adaptor = new TaslaklarIcerikAdaptor(this, sozIcerik);
        lvIcerik.setAdapter(adaptor);
        lvIcerik.setTextFilterEnabled(true);
        lvIcerik.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView tvContext = (TextView) view.findViewById(R.id.tvIcerik);
                TextView tvNo = (TextView) view.findViewById(R.id.tvSira);

                icerik = tvContext.getText().toString();
                no = tvNo.getText().toString();
                AlertDialog(no, icerik);
            }
        });
    }

    private void AlertDialog(String no, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(no + ".söz" + "\n" + message);
        alertDialogBuilder.setPositiveButton("paylaş",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        AllShared();
                    }
                });

        alertDialogBuilder.setNegativeButton("yenisi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void train() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Açıklama");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setMessage("Göndermek istediğiniz yazının üstüne tıklayın ve gönderiniz");
        alertDialogBuilder.setPositiveButton("tamam",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    int count = 0;

    private void InterstitialAds() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9057596586284356/1439205428");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("3B7901BF136B0DA7181DD6B1BCAB10F6").build());
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

    private void AllShared() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, donusturucu.dbToString(lvIcerikOzelGun));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, icerik);
        startActivity(Intent.createChooser(sharingIntent, "paylaş"));

    }

    public void getAllPrivateDaysContext(String childStrings) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("OzelGunler").child(childStrings);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp: dataSnapshot.getChildren()){
                    TaslakIcerikModel taslakIcerikModel = new TaslakIcerikModel();
                    taslakIcerikModel.setKey(dsp.getKey());
                    taslakIcerikModel.setValue(dsp.getValue().toString());
                    sozIcerik.add(taslakIcerikModel);
                    if (sozIcerik.size() <= 0) {
                        getAllPrivateDaysContext(childString);
                    } else {
                        AdaptorGoster();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
