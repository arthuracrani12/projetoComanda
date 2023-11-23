package com.primeiro.comandaeestoque.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.primeiro.comandaeestoque.R;
import com.primeiro.comandaeestoque.helper.ConfiguracaoFirebase;
import com.primeiro.comandaeestoque.helper.UsuarioFirebase;
import com.primeiro.comandaeestoque.model.Mesa;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddMesasActivity extends AppCompatActivity {

    private EditText editMesaNome, editMesaDescricao;
    private DatabaseReference firebaseRef;
    private DataSnapshot dataSnapshot;
    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mesas);

        inicializarComponentes();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();
        firebaseRef = ConfiguracaoFirebase.getFirebase();

        recuperarDadosMesa();
    }

    private void recuperarDadosMesa(){
        DatabaseReference mesaRef = firebaseRef.child("mesas").child( idUsuarioLogado );
        mesaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if( dataSnapshot.getValue() !=null){

                    Mesa mesa = dataSnapshot.getValue( Mesa.class );
                    editMesaNome.setText(mesa.getNome().toString());
                    editMesaDescricao.setText(mesa.getDescricao());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void validarDadosMesa(View view){
        String nome = editMesaNome.getText().toString();
        String descricao = editMesaDescricao.getText().toString();

        if (!nome.isEmpty()){
            Mesa mesa = new Mesa();
            mesa.setIdUsuario( idUsuarioLogado );
            mesa.setNome( nome );
            mesa.setDescricao( descricao );
            mesa.salvar();
            finish();
            Toast.makeText(this, "mesa adicionada como sucesso", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Favor inserir o numero ou nome da mesa", Toast.LENGTH_SHORT).show();
        }
    }
    private void inicializarComponentes(){
        editMesaNome = findViewById(R.id.editMesaNome);
        editMesaDescricao = findViewById(R.id.editMesaDescricao);
    }
}