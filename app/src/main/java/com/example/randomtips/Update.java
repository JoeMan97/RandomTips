package com.example.randomtips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class Update extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner_categories, spinner_categories2, spinner_tips;
    private EditText et_tip, et_category;
    private boolean init;
    private ConstraintLayout constraintLayout_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        init=true;

        initViews();
    }

    private void initViews() {
        constraintLayout_update=findViewById(R.id.constraintLayout_update);
        spinner_categories=findViewById(R.id.spinner_categories_update);
        spinner_categories2=findViewById(R.id.spinner_categories2_update);
        spinner_tips=findViewById(R.id.spinner_tips);
        et_category=findViewById(R.id.editText_category);
        et_tip=findViewById(R.id.editText_tip);

        setSpinner_categories();
        spinner_categories.setOnItemSelectedListener(this);
        spinner_categories.setSelection(getIntent().getIntExtra("category",0));

        setSpinner_tips();
        spinner_tips.setOnItemSelectedListener(this);
        spinner_tips.setSelection(getIntent().getIntExtra("tip", 0));

        setSpinner_categories2();
        spinner_categories2.setOnItemSelectedListener(this);
        spinner_categories2.setSelection(getIntent().getIntExtra("category",0));

        et_tip.setText(spinner_tips.getSelectedItem().toString());
        et_category.setText(spinner_categories2.getSelectedItem().toString());
    }

    private void setSpinner_tips(){
        String[] tips = (new BDTransactions()).selectAllTipsWhereCategoryEquals(this, constraintLayout_update, spinner_categories.getSelectedItem().toString());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item_custom2, tips);
        spinner_tips.setAdapter(adapter2);
    }

    private void setSpinner_categories(){
        String[] categories = (new BDTransactions()).selectAllCategories(this, constraintLayout_update);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_custom, categories);
        spinner_categories.setAdapter(adapter);
    }

    private void setSpinner_categories2(){
        String[] categories2 = (new BDTransactions()).selectAllCategories(this, constraintLayout_update);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.spinner_item_custom, categories2);
        spinner_categories2.setAdapter(adapter3);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spinner_categories_update:
                if(init)
                    init=false;
                else
                    setSpinner_tips();
                break;
            case R.id.spinner_tips:
                if(spinner_tips.getCount()>0)
                    et_tip.setText(spinner_tips.getItemAtPosition(i).toString());
                else
                    et_tip.setText("");
                break;
            case R.id.spinner_categories2_update:
                if(spinner_categories2.getCount()>0)
                    et_category.setText(spinner_categories2.getItemAtPosition(i).toString());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

    public void button_updateTip_onClick(View view){
        String updated_tip=et_tip.getText().toString();
        if(spinner_tips.getCount() == 0) {
            Snackbar.make(view, "The category does not have tips yet", Snackbar.LENGTH_SHORT).show();
        }else if(et_tip.getText().toString().isEmpty()) {
            Snackbar.make(view, "Write the updated tip", Snackbar.LENGTH_SHORT).show();
        }else if(et_tip.length()>=120){
            Snackbar.make(view, "The tip is very long ("+updated_tip.length()+" characters). It should be less than 120 characters", Snackbar.LENGTH_LONG).show();
        }else if(updated_tip.contains("'")){
            Snackbar.make(view, "Avoid using the simple quote character", Snackbar.LENGTH_SHORT).show();
        }else{
            String tip = spinner_tips.getSelectedItem().toString();
            int item=spinner_tips.getSelectedItemPosition();
            (new BDTransactions()).updateTip(this, constraintLayout_update, tip, updated_tip);

            setSpinner_tips();
            spinner_tips.setSelection(item);
        }
    }

    public void button_updateCategory_onClick(View view){
        String updated_category=et_category.getText().toString();
        if(spinner_categories2.getCount() == 0) {
            Snackbar.make(view, "There are no categories yet", Snackbar.LENGTH_SHORT).show();
        }else if(et_tip.getText().toString().isEmpty()){
            Snackbar.make(view, "Write the updated category", Snackbar.LENGTH_SHORT).show();
        }else if(updated_category.length()>=40){
            Snackbar.make(view, "The category name is very long ("+updated_category.length()+" characters). It should be less than 40 characters", Snackbar.LENGTH_LONG).show();
        }else if(updated_category.contains("'")){
            Snackbar.make(view, "Avoid using the simple quote character", Snackbar.LENGTH_SHORT).show();
        }else{
            String category = spinner_categories2.getSelectedItem().toString();
            (new BDTransactions()).updateCategory(this, constraintLayout_update, category, updated_category);

            int item_categories2=spinner_categories2.getSelectedItemPosition();
            setSpinner_categories2();
            spinner_categories2.setSelection(item_categories2);
            int item_categories=spinner_categories.getSelectedItemPosition();
            spinner_tips.setSelected(true);
            int item_tips=spinner_tips.getSelectedItemPosition();
            setSpinner_categories();
            spinner_categories.setSelection(item_categories);
            spinner_tips.setSelection(item_tips);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("category", spinner_categories.getSelectedItemPosition() );
        setResult(RESULT_OK,i);
        finish();
    }
}
