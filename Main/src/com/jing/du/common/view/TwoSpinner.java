package com.jing.du.common.view;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.jing.du.Main.R;
import com.jing.du.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by charle-chen on 15/6/28.
 */
public class TwoSpinner {

    private List<String> list;
    private HashMap<String, List<String>> hashMap;
    private Context mContex;
    private View spinnerView;
    private String textOne, textTwo;

    //二级联动列表id,数据
    //categoryId--->HashMap<String,List<String>>
    public TwoSpinner(Context context, View view, List<String> listOne, HashMap<String, List<String>> hashMap) {
        this.hashMap = hashMap;
        this.mContex = context;
        this.list = listOne;
        this.spinnerView = view;
        initSpinnerView();
    }

    private void initSpinnerView() {

        Spinner spinnerOne = (Spinner) spinnerView.findViewById(R.id.sp_one);
        final Spinner spinnerTwo = (Spinner) spinnerView.findViewById(R.id.sp_two);

        if (!StringUtils.isListEmpty(list)) {
            ArrayAdapter<String> adpaterOne = new ArrayAdapter<String>(mContex, android.R.layout.simple_spinner_item, list);
            spinnerOne.setAdapter(adpaterOne);
            textOne = list.get(0);
        } else {
            ArrayAdapter<String> adpaterOne = new ArrayAdapter<String>(mContex, android.R.layout.simple_spinner_item, new ArrayList<String>());
            spinnerOne.setAdapter(adpaterOne);
            textOne = "";
        }

        if (!StringUtils.isListEmpty(hashMap.get(list.get(0)))) {
            ArrayAdapter<String> adapterTwo = new ArrayAdapter<String>(mContex, android.R.layout.simple_spinner_item, hashMap.get(list.get(0)));
            spinnerTwo.setAdapter(adapterTwo);
            textTwo = hashMap.get(list.get(0)).get(0);
        } else {
            ArrayAdapter<String> adapterTwo = new ArrayAdapter<String>(mContex, android.R.layout.simple_spinner_item, new ArrayList<String>());
            spinnerTwo.setAdapter(adapterTwo);
            textTwo = "";
        }

        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!StringUtils.isListEmpty(hashMap.get(list.get(position)))) {
                    ArrayAdapter<String> adapterTwo = new ArrayAdapter<String>(mContex, android.R.layout.simple_spinner_item, hashMap.get(list.get(position)));
                    spinnerTwo.setAdapter(adapterTwo);
                    textTwo = hashMap.get(list.get(position)).get(0);
                } else {
                    ArrayAdapter<String> adapterTwo = new ArrayAdapter<String>(mContex, android.R.layout.simple_spinner_item, new ArrayList<String>());
                    spinnerTwo.setAdapter(adapterTwo);
                    textTwo = "";
                }

                textOne = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textTwo = hashMap.get(textOne).get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public String getDate(int spinnerId) {

        String result="";
        switch (spinnerId) {
            case 1:
                result = textOne;
            break;
            case 2:
                result = textTwo;
            break;
        }
        return result;
    }
}
