package com.example.randomtips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class Add extends AppCompatActivity {

    private Spinner spinner_categories;
    private EditText et_tip, et_category;
    private ConstraintLayout constraintLayout_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initViews();
    }

    private void initViews() {
        constraintLayout_add=findViewById(R.id.constraintLayout_add);
        spinner_categories=findViewById(R.id.spinner_category_add);
        et_tip=findViewById(R.id.editText_tip);
        et_category=findViewById(R.id.editText_category);

        setSpinner_categories();
        spinner_categories.setSelection(getIntent().getIntExtra("category",0));
    }

    private void setSpinner_categories(){
        String[] categories=(new BDTransactions()).selectAllCategories(this, constraintLayout_add);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.spinner_item_custom,categories);
        spinner_categories.setAdapter(adapter);
    }

    public void button_addTip_onClick(View view){
        String tip=et_tip.getText().toString();
        if(tip.isEmpty()){
            Snackbar.make(view, "Write the tip", Snackbar.LENGTH_SHORT).show();
        }else if(spinner_categories.getCount()==0){
            Snackbar.make(view, "Add some categories first", Snackbar.LENGTH_SHORT).show();
        }else if(tip.length()>=120){
            Snackbar.make(view, "The tip is very long ("+tip.length()+" characters). It should be less than 120 characters", Snackbar.LENGTH_LONG).show();
        }else if(tip.contains("'")){
            Snackbar.make(view, "Avoid using the simple quote character", Snackbar.LENGTH_SHORT).show();
        } else {
            String category=spinner_categories.getSelectedItem().toString();
            (new BDTransactions()).insertTip(this, constraintLayout_add, category, tip);
            et_tip.setText("");
        }
    }

    public void button_addCategory_onClick(View view){
        String category=et_category.getText().toString();
        if(category.isEmpty()) {
            Snackbar.make(view, "Write the category", Snackbar.LENGTH_SHORT).show();
        }else if(category.length()>=40){
            Snackbar.make(view, "The category name is very long ("+category.length()+" characters). It should be less than 40 characters", Snackbar.LENGTH_LONG).show();
        }else if(category.contains("'")){
            Snackbar.make(view, "Avoid using the simple quote character", Snackbar.LENGTH_SHORT).show();
        }else{
            (new BDTransactions()).insertCategory(this, constraintLayout_add, category);
            et_category.setText("");
            setSpinner_categories();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("category", spinner_categories.getSelectedItemPosition());
        setResult(RESULT_OK,i);
        finish();
    }
}
