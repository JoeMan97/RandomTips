package com.example.randomtips;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class BDTransactions {

    public String[] selectAllCategories(Context context, View view){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper=new AdminSQLiteOpenHelper(context,"tips",null,1);
        SQLiteDatabase sqLiteDatabase=adminSQLiteOpenHelper.getWritableDatabase();

        String[] categories={};

        try {
            Cursor rows = sqLiteDatabase.rawQuery("SELECT category FROM categories ORDER BY category", null);

            if(rows.getCount()==0){
                Snackbar.make(view, "There are no categories yet", Snackbar.LENGTH_SHORT).show();
            } else {
                categories=new String[rows.getCount()];
                int index=0;
                while (rows.moveToNext()){
                    categories[index]=rows.getString(0);
                    index++;
                }
            }

            sqLiteDatabase.close();
        }catch (SQLException e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }

        return categories;
    }

    public void insertCategory(Context context, View view, String category){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper=new AdminSQLiteOpenHelper(context,"tips",null,1);
        SQLiteDatabase sqLiteDatabase=adminSQLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("category", category);

        try{
            sqLiteDatabase.insert("categories",null, contentValues);
            sqLiteDatabase.close();

            Snackbar.make(view, "The category was added successfully", Snackbar.LENGTH_SHORT).show();
        }catch (Exception e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    public void deleteCategory(Context context, View view, String category){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper=new AdminSQLiteOpenHelper(context,"tips",null,1);
        SQLiteDatabase sqLiteDatabase=adminSQLiteOpenHelper.getWritableDatabase();

        try{
            sqLiteDatabase.execSQL("DELETE FROM categories WHERE category ='"+category+"'");
            sqLiteDatabase.execSQL("DELETE FROM tips WHERE category ='"+category+"'");
            sqLiteDatabase.close();

            Snackbar.make(view, "The category was deleted successfully", Snackbar.LENGTH_SHORT).show();
        }catch (Exception e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }


    public int selectTipsCount(Context context,  View view){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper=new AdminSQLiteOpenHelper(context,"tips",null,1);
        SQLiteDatabase sqLiteDatabase=adminSQLiteOpenHelper.getWritableDatabase();

        int count=0;

        try {
            Cursor rows = sqLiteDatabase.rawQuery("SELECT tip FROM tips", null);

            count=rows.getCount();

            sqLiteDatabase.close();
        }catch (SQLException e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }

        return count;
    }

    public String[] selectAllTips(Context context, View view){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper=new AdminSQLiteOpenHelper(context,"tips",null,1);
        SQLiteDatabase sqLiteDatabase=adminSQLiteOpenHelper.getWritableDatabase();

        String[] tips={};

        try {
            Cursor rows = sqLiteDatabase.rawQuery("SELECT tip FROM tips", null);

            if(rows.getCount()==0){
                Snackbar.make(view, "There are no tips yet", Snackbar.LENGTH_SHORT).show();
            } else {
                tips=new String[rows.getCount()];
                int index=0;
                while (rows.moveToNext()){
                    tips[index]=rows.getString(0);
                    index++;
                }
            }

            sqLiteDatabase.close();
        }catch (SQLException e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }

        return tips;
    }

    public String[] selectAllTipsWhereCategoryEquals(Context context, View view, String category){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper=new AdminSQLiteOpenHelper(context,"tips",null,1);
        SQLiteDatabase sqLiteDatabase=adminSQLiteOpenHelper.getWritableDatabase();

        String[] tips={};

        try {
            Cursor rows = sqLiteDatabase.rawQuery("SELECT tip FROM tips WHERE category='"+category+"' ORDER BY tip", null);

            if(rows.getCount()==0){
                Snackbar.make(view, "The category does not have tips yet", Snackbar.LENGTH_SHORT).show();
            } else {
                tips=new String[rows.getCount()];
                int index=0;
                while (rows.moveToNext()){
                    tips[index]=rows.getString(0);
                    index++;
                }
            }

            sqLiteDatabase.close();
        }catch (SQLException e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }

        return tips;
    }

    public void insertTip(Context context,  View view, String category, String tip){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper=new AdminSQLiteOpenHelper(context,"tips",null,1);
        SQLiteDatabase sqLiteDatabase=adminSQLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("tip", tip);
        contentValues.put("category", category);

        try{
            sqLiteDatabase.insert("tips",null, contentValues);
            sqLiteDatabase.close();

            Snackbar.make(view, "The tip was added successfully", Snackbar.LENGTH_SHORT).show();
        }catch (Exception e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    public void deleteTip(Context context, View view, String tip){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper=new AdminSQLiteOpenHelper(context,"tips",null,1);
        SQLiteDatabase sqLiteDatabase=adminSQLiteOpenHelper.getWritableDatabase();

        try{
            sqLiteDatabase.execSQL("DELETE FROM tips WHERE tip ='"+tip+"'");
            sqLiteDatabase.close();

            Snackbar.make(view, "The tip was deleted successfully", Snackbar.LENGTH_SHORT).show();
        }catch (Exception e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    public void updateTip(Context context, View view, String tip, String updatedTip){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper=new AdminSQLiteOpenHelper(context,"tips",null,1);
        SQLiteDatabase sqLiteDatabase=adminSQLiteOpenHelper.getWritableDatabase();

        try{
            sqLiteDatabase.execSQL("UPDATE tips SET tip ='"+updatedTip+"'WHERE tip ='"+tip+"'");

            sqLiteDatabase.close();

            Snackbar.make(view, "The tip was updated successfully", Snackbar.LENGTH_SHORT).show();
        }catch (Exception e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    public void updateCategory(Context context, View view, String category, String updatedCategory){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper=new AdminSQLiteOpenHelper(context,"tips",null,1);
        SQLiteDatabase sqLiteDatabase=adminSQLiteOpenHelper.getWritableDatabase();

        try{
            sqLiteDatabase.execSQL("UPDATE categories SET category ='"+updatedCategory+"' WHERE category='"+category+"'");
            sqLiteDatabase.execSQL("UPDATE tips SET category ='"+updatedCategory+"' WHERE category='"+category+"'");
            sqLiteDatabase.close();

            Snackbar.make(view, "The category was updated successfully", Snackbar.LENGTH_SHORT).show();
        }catch (Exception e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }
}
