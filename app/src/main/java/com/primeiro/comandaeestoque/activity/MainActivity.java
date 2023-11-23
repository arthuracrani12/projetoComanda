package com.primeiro.comandaeestoque.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.primeiro.comandaeestoque.R;
import com.primeiro.comandaeestoque.helper.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    private Button botaoAcessar;
    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        abrirTelaPrincipal();
        verificarUsuarioLogado();

        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                if ( !email.isEmpty()){
                    if ( !senha.isEmpty()){

                        if( tipoAcesso.isChecked()){//cadastro

                            autenticacao.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){

                                        Toast.makeText(MainActivity.this, " Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                                    abrirTelaPrincipal();
                                    }else {
                                        String erroExcecao = "";

                                        try {
                                            throw task.getException();
                                        }catch (FirebaseAuthWeakPasswordException e){
                                            erroExcecao = "Digite uma senha mais forte";
                                        }catch (FirebaseAuthInvalidCredentialsException e){
                                            erroExcecao = "Digite um email valido";
                                        }catch (FirebaseAuthUserCollisionException e){
                                            erroExcecao = "Essa conta ja foi cadastrada";
                                        } catch (Exception e){
                                            erroExcecao = "ao cadastrar usuário: " +e.getMessage();
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(MainActivity.this, "Erro" +erroExcecao, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        }else{//login

                            autenticacao.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                                    abrirTelaPrincipal();
                                    }else{
                                     Toast.makeText(MainActivity.this,"Erro ao fazer login:" +task.getException(),
                                             Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }


                    }else{
                        Toast.makeText(MainActivity.this,"Preencha a senha!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Preencha o e-mail!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void verificarUsuarioLogado(){
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if (usuarioAtual !=null){
            abrirTelaPrincipal();
        }

    }
    private void abrirTelaPrincipal(){
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
    private void inicializarComponentes(){
        campoEmail = findViewById(R.id.editMesaNome);
        campoSenha = findViewById(R.id.editMesaDescricao);
        botaoAcessar = findViewById(R.id.buttonAcesso);
        tipoAcesso = findViewById(R.id.switchAcesso);
    }
}