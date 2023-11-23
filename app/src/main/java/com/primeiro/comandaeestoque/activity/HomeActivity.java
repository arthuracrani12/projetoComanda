package com.primeiro.comandaeestoque.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.primeiro.comandaeestoque.R;

public class HomeActivity extends AppCompatActivity {

    private Button buttonAddItens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }
    public void abrirTelaAddItens(View view){
        startActivity(new Intent(getApplicationContext(),AddItensActivity.class));
    }
    public void abrirTelaAddMesas(View view){
        startActivity(new Intent(getApplicationContext(),AddMesasActivity.class));
    }
}