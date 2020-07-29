package com.example.randomtips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class RandomTipViewer extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner_category;
    private TextView tv_tip, tv_total;
    private CheckBox cb_changeCategory;
    private ConstraintLayout constraintLayout_tipViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_tip_viewer);

        initViews();
    }

    private void initViews(){
        constraintLayout_tipViewer=findViewById(R.id.constraintLayout_tipViewer);
        tv_tip=findViewById(R.id.textView_tip);
        spinner_category=findViewById(R.id.spinner_categories_tipViewer);
        tv_total=findViewById(R.id.textView_total);
        cb_changeCategory=findViewById(R.id.checkBox_changeCategory);

        String[] categories=(new BDTransactions()).selectAllCategories(this, constraintLayout_tipViewer);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.spinner_item_custom,categories);
        spinner_category.setAdapter(adapter);

        spinner_category.setOnItemSelectedListener(this);

        if(categories.length>0){
            int i1=generateRandomIntIntRange(0,categories.length-1);
            String category=categories[i1];
            String[] tips=(new BDTransactions()).selectAllTipsWhereCategoryEquals(this, constraintLayout_tipViewer, category);
            if(tips.length>0){
                int i2=generateRandomIntIntRange(0,tips.length-1);
                tv_tip.setText(tips[i2]);
            }else{
                tv_tip.setText(R.string.string_writeTip);
            }
            spinner_category.setSelection(i1);
        }
        tv_total.setText("Total tips: "+(new BDTransactions()).selectTipsCount(this, constraintLayout_tipViewer));
    }

    @Override
    protected void onResume(){
        super.onResume();

        initViews();
    }

    public void button_SeeAllTips_onClick(View v){
        Intent i=new Intent(this, TipList.class);
        i.putExtra("category", spinner_category.getSelectedItemPosition());
        startActivity(i);
    }

    public void button_OtherTip(View v){
        if(spinner_category.getCount()>0){
            if(cb_changeCategory.isChecked()){
                String[] categories=(new BDTransactions()).selectAllCategories(this, constraintLayout_tipViewer);
                int i=generateRandomIntIntRange(0,categories.length-1);
                spinner_category.setSelection(i);
                String category=categories[i];
                String[] tips=(new BDTransactions()).selectAllTipsWhereCategoryEquals(this, constraintLayout_tipViewer, category);
                if(tips.length>0){
                    int i2=generateRandomIntIntRange(0,tips.length-1);
                    tv_tip.setText(tips[i2]);
                }
            }else{
                String category=spinner_category.getSelectedItem().toString();
                String[] tips=(new BDTransactions()).selectAllTipsWhereCategoryEquals(this, constraintLayout_tipViewer, category);
                if(tips.length>0){
                    int i=generateRandomIntIntRange(0,tips.length-1);
                    tv_tip.setText(tips[i]);
                }
            }
        }
    }

    private static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String category=spinner_category.getItemAtPosition(i).toString();
        String[] tips=(new BDTransactions()).selectAllTipsWhereCategoryEquals(this, constraintLayout_tipViewer, category);
        if(tips.length>0){
            int i2=generateRandomIntIntRange(0,tips.length-1);
            tv_tip.setText(tips[i2]);
        }else{
            tv_tip.setText(R.string.string_aHelpfulTip);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }
}
