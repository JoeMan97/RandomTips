package com.example.randomtips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class TipList extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener{

    private ListView lv_tips;
    private Spinner spinner_category;
    private ConstraintLayout constraintLayout_tipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_list);

        initViews();
    }

    private void initViews() {
        constraintLayout_tipList=findViewById(R.id.constraintLayout_tipList);
        spinner_category=findViewById(R.id.spinner_category_tipList);
        lv_tips=findViewById(R.id.listView_tips);

        String[] categories=(new BDTransactions()).selectAllCategories(this, constraintLayout_tipList);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.spinner_item_custom,categories);
        spinner_category.setAdapter(adapter);

        spinner_category.setOnItemSelectedListener(this);
        spinner_category.setSelection(getIntent().getIntExtra("category",0));

        lv_tips.setOnItemClickListener(this);
    }

    public void button_add_onClick(View view){
        Intent i=new Intent(this, Add.class);
        i.putExtra("category", spinner_category.getSelectedItemPosition());
        startActivityForResult(i,999);
    }

    public void button_delete_onClick(View view){
        Intent i=new Intent(this, Delete.class);
        i.putExtra("category", spinner_category.getSelectedItemPosition());
        startActivityForResult(i,999);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String category=spinner_category.getItemAtPosition(i).toString();
        String[] tips=(new BDTransactions()).selectAllTipsWhereCategoryEquals(this,constraintLayout_tipList, category);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.listview_item_custom,tips);
        lv_tips.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this, Update.class);
        intent.putExtra("category", spinner_category.getSelectedItemPosition());
        intent.putExtra("tip", i);
        startActivityForResult(intent,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==999&&resultCode==RESULT_OK){
            spinner_category.setSelection(data.getIntExtra("category",0));

            String category=spinner_category.getSelectedItem().toString();
            String[] tips=(new BDTransactions()).selectAllTipsWhereCategoryEquals(this,constraintLayout_tipList, category);
            ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.listview_item_custom,tips);
            lv_tips.setAdapter(adapter);
        }
    }
}
