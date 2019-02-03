package com.murat.getallprojects;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TaslaklarIcerikAdaptor extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<TaslakIcerikModel> mTaslaklarIcerikModel;

    TaslaklarIcerikAdaptor(Activity activity, List<TaslakIcerikModel> mTaslaklar) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mTaslaklarIcerikModel = mTaslaklar;
    }


    @Override
    public int getCount() {
        return mTaslaklarIcerikModel.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

     @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.taslak_context_satir, null);

        TextView tvSira = (TextView) convertView.findViewById(R.id.tvSira);
        TextView tvGun_icerik_soz = (TextView) convertView.findViewById(R.id.tvIcerik);
         TaslakIcerikModel rhb = mTaslaklarIcerikModel.get(position);
        tvSira.setText(rhb.getKey());
        tvGun_icerik_soz.setText(rhb.getValue());

        return convertView;
    }
}
