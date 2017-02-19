package com.example.josedecorea.myapplication99;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    String lang_code, lang_tab_name;

    ListView listview;
    ListViewAdapter adapter;
    EditText editsearch;
    ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 1. 언어설정 가져와서 언어 테이블 컬럼명 가지고 오기  */

        lang_code = Locale.getDefault().getLanguage();

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        lang_tab_name = databaseAccess.getLangTableName(lang_code);
        databaseAccess.close();


        /* 2. 언어에 따른 국가명 가져오기  */
        databaseAccess.open();
        listViewItemList = databaseAccess.getNationName(lang_tab_name);
        databaseAccess.close();

        // Adapter 생성
        adapter = new ListViewAdapter(this, R.layout.listview_item, listViewItemList);

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);


       /* 3. 검색어 처리  **/

        editsearch = (EditText) findViewById(R.id.search);

        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

    }
}