package com.example.tysonquek.tysonfurniture;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final boolean DEBUG = true;
    private static FurnitureOpenHelper mDbHelper;
    private EditText mFurnitureType;
    private EditText mFurnitureClass;
    private TextView mFurnitureRecords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (DEBUG) Log.d("[DEBUG] " + getClass().getSimpleName(), ": " + "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new FurnitureOpenHelper(this);

        Button btnAddRecord = (Button) findViewById(R.id.btn_addRecord);
        Button btnRemoveAllRecords = (Button) findViewById(R.id.btn_deleteAllRecords);
        mFurnitureType = (EditText) findViewById(R.id.et_furniture);
        mFurnitureClass = (EditText) findViewById(R.id.et_class);
        mFurnitureRecords = (TextView) findViewById(R.id.tv_furnitureRecords);

        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRecords();
                displayRecords();
            }
        });

        btnRemoveAllRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllRecords();
                mFurnitureRecords.setText(R.string.no_records_found);
            }
        });

        displayRecords(); //will display all records if theres any

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DEBUG) Log.d("[DEBUG] " + getClass().getSimpleName(), ": " + "onResume");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayRecords() {
        Cursor data = mDbHelper.retrieveAll();
        if (data == null) {
            return;
        }

        mFurnitureRecords.setText(""); //clear hint before appending dataset
        data.moveToFirst();
        while (!data.isAfterLast()) {
            mFurnitureRecords.append(data.getString(data.getColumnIndex(FurnitureDBContract.FurnitureEntry._ID)) + " ");
            mFurnitureRecords.append(data.getString(data.getColumnIndex(FurnitureDBContract.FurnitureEntry.COLUMN_NAME_FURNITURE)) + " ");
            mFurnitureRecords.append(data.getString(data.getColumnIndex(FurnitureDBContract.FurnitureEntry.COLUMN_NAME_CLASSIFICATION)) + "\n");
            data.moveToNext();
        }
    }

    private void insertRecords() {
        if (String.valueOf(mFurnitureType.getText()).equals("") || String.valueOf(mFurnitureClass.getText()).equals("")) {
            Toast.makeText(this, "Please provide all details", Toast.LENGTH_SHORT).show();
            return;
        }

        mDbHelper.insert(String.valueOf(mFurnitureType.getText()), String.valueOf(mFurnitureClass.getText()));
    }

    private void deleteAllRecords() {
        mDbHelper.deleteAll();
    }
}
