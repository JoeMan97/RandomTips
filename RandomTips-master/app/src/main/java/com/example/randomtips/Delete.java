package com.example.randomtips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class Delete extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner_categories, spinner_categories2, spinner_tips;
    private ConstraintLayout constraintLayout_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        initViews();
    }

    private void initViews() {
        constraintLayout_delete=findViewById(R.id.constraintLayout_Delete);
        spinner_categories=findViewById(R.id.spinner_categories_delete);
        spinner_categories2 = findViewById(R.id.spinner_categories2_delete);
        spinner_tips = findViewById(R.id.spinner_tips);

        setSpinner_categories();
        spinner_categories.setOnItemSelectedListener(this);
        spinner_categories.setSelection(getIntent().getIntExtra("category",0));

        setSpinner_tips();

        setSpinner_categories2();
        spinner_categories2.setSelection(getIntent().getIntExtra("category",0));
    }

    public void setSpinner_categories(){
        String[] categories = (new BDTransactions()).selectAllCategories(this, constraintLayout_delete);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_custom, categories);
        spinner_categories.setAdapter(adapter);
    }

    public void setSpinner_categories2(){
        String[] categories2 = (new BDTransactions()).selectAllCategories(this, constraintLayout_delete);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.spinner_item_custom, categories2);
        spinner_categories2.setAdapter(adapter3);
    }

    public void setSpinner_tips(){
        if(spinner_categories.getCount()>0){
            String[] tips = (new BDTransactions()).selectAllTipsWhereCategoryEquals(this, constraintLayout_delete, spinner_categories.getSelectedItem().toString());
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item_custom2, tips);
            spinner_tips.setAdapter(adapter2);
        }
    }

    public void button_DeleteTip_onClick(View view) {
        if(spinner_categories.getCount() == 0){
            Snackbar.make(view, "Add some categories first", Snackbar.LENGTH_SHORT).show();
        }else if(spinner_tips.getCount() == 0) {
            Snackbar.make(view, "The category does not have tips yet", Snackbar.LENGTH_SHORT).show();
        } else {
            String tip = spinner_tips.getSelectedItem().toString();
            (new BDTransactions()).deleteTip(this, constraintLayout_delete, tip);
            setSpinner_tips();
        }
    }

    public void button_DeleteCategory_onClick(View view) {
        if (spinner_categories2.getCount() == 0) {
            Snackbar.make(view, "Add some categories first", Snackbar.LENGTH_SHORT).show();
        } else {
            String category = spinner_categories2.getSelectedItem().toString();
            (new BDTransactions()).deleteCategory(this, constraintLayout_delete, category);
            setSpinner_categories();
            setSpinner_tips();
            setSpinner_categories2();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String[] tips = (new BDTransactions()).selectAllTipsWhereCategoryEquals(this, constraintLayout_delete, spinner_categories.getItemAtPosition(i).toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_custom2, tips);
        spinner_tips.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("category", spinner_categories.getSelectedItemPosition());
        setResult(RESULT_OK,i);
        finish();
    }
}
