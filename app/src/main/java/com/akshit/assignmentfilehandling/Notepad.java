package com.akshit.assignmentfilehandling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Notepad extends AppCompatActivity {
    EditText e1,e2;
    Button b1;
    String FileName="",FileText="",data="";
    int changed=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        e1=findViewById(R.id.e1);
        e2=findViewById(R.id.e2);
        b1=findViewById(R.id.b1);

        Bundle b= getIntent().getExtras();
        FileName=b.getString("SavedFileName_key");
        if(!FileName.equals("none"))
            {
                FileName=b.getString("SavedFileName_key");
                e1.setText(FileName);
                try{
                    int val=0;
                    FileInputStream fsaved= openFileInput(FileName);
                    while ((val=fsaved.read())!=-1)
                        data+=(char)val;
                    e2.setText(data);
                    changed=1;
                }
                catch (Exception e)
                { }
            }
            else
            {
                e1.setText("");
                e2.setText("");
            }

            b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Notepad.this, MainActivity.class);
                FileName = e1.getText().toString();
                FileText = e2.getText().toString();
                if (FileName.isEmpty()) {
                    e1.setText("Untitled");
                    FileName = "Untitled";
                }
              /*  else if(FileName.equals("Untitled")) {
                    e1.setText(FileName);
                    MainActivity.arrayList.add(FileName);
                    setResult(1, i);
                }*/
                if (!FileText.isEmpty()&&!FileText.equals(data)) {
                    try {
                        FileOutputStream fout = openFileOutput(FileName, Context.MODE_PRIVATE);
                        fout.write(FileText.getBytes());
                        MainActivity.arrayList.remove(FileName);
                        MainActivity.arrayList.add(FileName);
                        i.putExtra("File_name_key", FileName);
                        setResult(1, i);
                        if(changed==0)
                            Toast.makeText(Notepad.this, "File Saved....", Toast.LENGTH_SHORT).show();
                        else if(changed==1)
                            Toast.makeText(Notepad.this, "File Updated And Saved ", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) { }
                    finish();
                }
                else {
                    Toast.makeText(Notepad.this, "File Saved With No Changes...", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
