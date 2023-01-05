package com.example.belajarfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.belajarFirebase.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<MhsModel> mhsList;
    MhsModel mm;
    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edName = (EditText) findViewById(R.id.edName);
        EditText edNim = (EditText) findViewById(R.id.edNim);
        EditText edNoHp = (EditText) findViewById(R.id.edNoHp);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        Button btnShow = (Button) findViewById(R.id.btnShow);

        mhsList = new ArrayList<>();

        isEdit = false;

        Intent intent_main = getIntent();
        if(intent_main.hasExtra("mhsData")){
            mm = intent_main.getExtras().getParcelable("mhsData");
            edName.setText(mm.getName());
            edNim.setText(mm.getNim());
            edNoHp.setText(mm.getNoHp());

            isEdit = true;

            btnSave.setBackgroundColor(Color.GRAY);
            btnSave.setText("Edit");
        }

        DbHelper db = new DbHelper(getApplicationContext());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_input = edName.getText().toString();
                String nim_input = edNim.getText().toString();
                String noHP_input = edNoHp.getText().toString();

                if(name_input.isEmpty() || nim_input.isEmpty() || noHP_input.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Input cannot be empty", Toast.LENGTH_SHORT).show();
                }else {
//                  mhsList.add(new Mhs(1, name_input, nim_input, noHP_input));

                    boolean stts;

                    if(!isEdit){
                        mm = new MhsModel(-1, name_input, nim_input, noHP_input);
                        stts = db.simpan(mm);

                        edName.setText("");
                        edNim.setText("");
                        edNoHp.setText("");

                    }else{
                        mm = new MhsModel(mm.getId(), name_input, nim_input, noHP_input);
                        stts = db.ubah(mm);
                    }


                    if(stts){
                        Toast.makeText(getApplicationContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                    }

                    //intent.putParcelableArrayListExtra("mhsList", mhsList);
                    //startActivity(intent);
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mhsList = db.list();

                if(mhsList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Data masih kosong", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent_List = new Intent(MainActivity.this, MhsListActivity.class);
                    intent_List.putParcelableArrayListExtra("mhsList", mhsList);
                    startActivity(intent_List);
                }
            }
        });
    }
}