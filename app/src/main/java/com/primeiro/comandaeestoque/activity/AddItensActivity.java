package com.primeiro.comandaeestoque.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.primeiro.comandaeestoque.R;
import com.primeiro.comandaeestoque.helper.UsuarioFirebase;
import com.primeiro.comandaeestoque.model.Produto;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddItensActivity extends AppCompatActivity {

    private EditText editProdutoNome, editProdutoDescricao,
            editProdutoPreco;
    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_itens);
        /*Configurações inicias*/
        inicializarComponentes();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

    }

    public void validarDadosProduto(View view){

        //Valida se os campos foram preenchidos
        String nome = editProdutoNome.getText().toString();
        String descricao = editProdutoDescricao.getText().toString();
        String preco = editProdutoPreco.getText().toString();

        if( !nome.isEmpty()){
            if( !descricao.isEmpty()){
                if( !preco.isEmpty()){

                    Produto produto = new Produto();
                    produto.setIdUsuario( idUsuarioLogado );
                    produto.setNome( nome );
                    produto.setDescricao( descricao );
                    produto.setPreco( Double.parseDouble(preco) );
                    produto.salvar();

                    finish();
                    exibirMensagem("Produto salvo com sucesso!");

                }else{
                    exibirMensagem("Digite um preço para o produto");
                }
            }else{
                exibirMensagem("Digite uma descrição para o produto");
            }
        }else{
            exibirMensagem("Digite um nome para o produto");
        }

    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT)
                .show();
    }

    private void inicializarComponentes(){
        editProdutoDescricao = findViewById(R.id.editProdutoDescricao);
        editProdutoNome = findViewById(R.id.editProdutoNome);
        editProdutoPreco = findViewById(R.id.editProdutoPreco);
    }

}
