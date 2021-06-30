package com.akshit.assignmentfilehandling;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    static  ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    Intent i;
    String savedfilename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=findViewById(R.id.lv1);
        arrayList = new ArrayList<String>();
        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,arrayList);
        i= new Intent(MainActivity.this,Notepad.class);
        if (arrayList.isEmpty())
            arrayList.add("No recent files");
        arrayAdapter.notifyDataSetChanged();
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tocheck=arrayList.get(position);
                if(!tocheck.equals("No recent files")) {
                    savedfilename =arrayList.get(position);
                    i.putExtra("SavedFileName_key",savedfilename);
                    startActivityForResult(i,1);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       savedfilename="none";
        i.putExtra("SavedFileName_key",savedfilename);
       startActivityForResult(i,1);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            arrayList.remove("No recent files");
            arrayAdapter.notifyDataSetChanged();
            lv.setAdapter(arrayAdapter);
        }
    }
}
