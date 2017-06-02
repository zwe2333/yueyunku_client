package com.gdcp.yueyunku_client.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.db.City;
import com.gdcp.yueyunku_client.db.County;
import com.gdcp.yueyunku_client.db.Province;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.HttpUtils;
import com.gdcp.yueyunku_client.utils.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Asus on 2017/5/24.
 */

public class ChooseAreaFragment extends Fragment {
    private String province="";
    private String city="";
    private String county="";
    public static final int LEVEL_PROVINCE=0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;
    private ProgressDialog mProgressDialog;
    private TextView mTextView;
    private Button mButton;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> dataList=new ArrayList<>();
    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<County> mCountyList;
    private Province selectedProvince;
    private City selectCity;
    private int currentLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.choose_area,container,false);
        mTextView= (TextView) view.findViewById(R.id.title_text);
        mButton= (Button) view.findViewById(R.id.back_button);
        mListView= (ListView) view.findViewById(R.id.list_view);
        mAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,dataList);
        mListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel==LEVEL_PROVINCE){
                    province=mProvinceList.get(i).getProvinceName();
                    selectedProvince=mProvinceList.get(i);
                    queryCities();
                }else if (currentLevel==LEVEL_CITY){
                    city=mCityList.get(i).getCityName();
                    selectCity=mCityList.get(i);
                    queryCounties();
                }else {
                    county=mCountyList.get(i).getCountyName();
                    updateMsg();
                    //Toast.makeText(getContext(),mBuffer.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel==LEVEL_COUNTY){
                    queryCities();
                }else if (currentLevel==LEVEL_CITY){
                    queryProvinces();
                }else {
                    getActivity().finish();
                }

            }
        });
        queryProvinces();
    }

    private void updateMsg() {
        User user=new User();
        user.setArea(province+" "+city+" "+county);
        User bmobUser = User.getCurrentUser(User.class);
        user.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    Toast.makeText(getContext(),"修改成功",Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            }
        });
    }

    private void queryProvinces() {
        mTextView.setText("中国");
        mProvinceList= DataSupport.findAll(Province.class);
        if (mProvinceList.size()>0){
            dataList.clear();
            for (Province province:mProvinceList) {
                dataList.add(province.getProvinceName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel=LEVEL_PROVINCE;
        }else {
            String address="http://guolin.tech/api/china";
            queryFromServer(address,"province");
        }
    }

    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtils.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"fail",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responText=response.body().string();
                boolean result=false;
                if ("province".equals(type)){
                    result= Utility.handleProvinceResponse(responText);
                }else if ("city".equals(type)){
                    result=Utility.handleCityResponse(responText,selectedProvince.getId());
                }else if ("county".equals(type)){
                    result=Utility.handleCountyResponse(responText,selectCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryProvinces();
                            }else if ("city".equals(type)){
                                queryCities();
                            }else if ("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    private void closeProgressDialog() {
        if (mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog==null){
            mProgressDialog=new ProgressDialog(getActivity());
            mProgressDialog.setMessage("loading...");
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    private void queryCounties() {
        mTextView.setText(selectCity.getCityName());
        mButton.setVisibility(View.VISIBLE);
        mCountyList=DataSupport.where("cityid=?",String.valueOf(selectCity.getId())).find(County.class);
        if (mCountyList.size()>0){
            dataList.clear();
            for (County county:mCountyList) {
                dataList.add(county.getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel=LEVEL_COUNTY;
        }else {
            int provinceCode=selectedProvince.getProvinceCode();
            int cityCode=selectCity.getCityCode();
            String address="http://guolin.tech/api/china/"+provinceCode+"/"+cityCode;
            queryFromServer(address,"county");
        }
    }

    private void queryCities() {
        mTextView.setText(selectedProvince.getProvinceName());
        mButton.setVisibility(View.VISIBLE);
        mCityList=DataSupport.where("provinceid=?",String.valueOf(selectedProvince.getId())).find(City.class);
        if (mCityList.size()>0){
            dataList.clear();
            for (City city:mCityList) {
                dataList.add(city.getCityName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel=LEVEL_CITY;
        }else {
            int provinceCode=selectedProvince.getProvinceCode();
            String address="http://guolin.tech/api/china/"+provinceCode;
            queryFromServer(address,"city");
        }
    }
}
