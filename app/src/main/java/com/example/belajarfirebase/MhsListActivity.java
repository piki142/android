package com.example.belajarfirebase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MhsListActivity extends AppCompatActivity {

    MhsAdapter mhsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmhs);

        RecyclerView rcList = (RecyclerView) findViewById(R.id.rcList);

        ArrayList<MhsModel> mhsList = getIntent().getExtras().getParcelableArrayList("mhsList");

        mhsAdapter = new MhsAdapter(mhsList, new MhsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(ArrayList<MhsModel>mhsList, int position) {
                AlertDialog.Builder dialog =new AlertDialog.Builder(MhsListActivity.this);
                dialog.setTitle("Pilihan");
                dialog.setItems(new CharSequence[]{"Hapus", "Edit"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        DbHelper db = new DbHelper(getApplicationContext());
                        MhsModel mm = mhsList.get(position);

                        switch (item) {
                            case 0:
                                boolean stts = db.hapus(mm.getId());
                                if(stts){
                                    mhsAdapter.removeItem(position);
                                    Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus :)", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 1:
                                Intent intent_main = new Intent(MhsListActivity.this, MainActivity.class);
                                intent_main.putExtra("mhsData", mm);

                                startActivity(intent_main);
                                break;
                        }
                    }
                });
                dialog.create().show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MhsListActivity.this);
        rcList.setLayoutManager(layoutManager);
        rcList.setAdapter(mhsAdapter);

        FloatingActionButton fabTambah = findViewById(R.id.fabTambah);
        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MhsListActivity.this, MainActivity.class));
            }
        });
    }
}