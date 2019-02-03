package com.murat.getallprojects;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TaslaklarAdaptor extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<TaslakModel> mTaslaklarModel;

    TaslaklarAdaptor(Activity activity, List<TaslakModel> mTaslaklar) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mTaslaklarModel = mTaslaklar;
    }
     @Override
    public int getCount() {
        return mTaslaklarModel.size();
    }

    @Override
    public Object getItem(int position) {
        return mTaslaklarModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.taslak_satir, null);

        TextView tvGunAdi = (TextView) convertView.findViewById(R.id.btnOzelGunSatir);
        TextView tvTarih =(TextView) convertView.findViewById(R.id.tvTarihRow);
        TaslakModel rhb = mTaslaklarModel.get(position);
        tvGunAdi.setText(rhb.getOzelGun());
        tvTarih.setText(rhb.getTarih());
        return convertView;
    }
}
