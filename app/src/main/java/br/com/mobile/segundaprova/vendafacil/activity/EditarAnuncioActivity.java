package br.com.mobile.segundaprova.vendafacil.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.firebase.storage.StorageReference;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import br.com.mobile.segundaprova.vendafacil.R;
import br.com.mobile.segundaprova.vendafacil.helper.AnuncioUtils;
import br.com.mobile.segundaprova.vendafacil.model.Anuncio;
import br.com.mobile.segundaprova.vendafacil.model.TipoSpinner;

public class EditarAnuncioActivity extends AppCompatActivity {

    private Anuncio anuncioSelecionado;

    private EditText campoTitulo, campoDescricao;
    private Spinner campoEstado, campoCategoria;
    private CurrencyEditText campoValor;
    private MaskEditText campoTelefone;
    private Anuncio anuncio;
    private StorageReference storage;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_anuncio);

        //Recuperar anúncio para atualizacao
        anuncioSelecionado = (Anuncio) getIntent().getSerializableExtra("anuncioSelecionado");

        //Inicializar componentes de interface
        inicializarComponentes();
        carregarDadosSpinner();

        carregarDadosAnuncioSelecionado();
    }

    public void AtualizarDadosAnuncio(View view) {
        validarDadosAnuncio(view);
    }

    public void atualizarAnuncio() {
        anuncioSelecionado.remover();
        anuncio.salvar();
        startActivity(new Intent(this, MeusAnunciosActivity.class));
    }

    public void validarDadosAnuncio(View view){

        anuncio = configurarAnuncio();
        anuncio.setIdAnuncio(anuncioSelecionado.getIdAnuncio());
        anuncio.setFotos(anuncioSelecionado.getFotos());
        String valor = String.valueOf(campoValor.getRawValue());

            if( !anuncio.getEstado().isEmpty() ){
                if( !anuncio.getCategoria().isEmpty() ){
                    if( !anuncio.getTitulo().isEmpty() ){
                        if( !valor.isEmpty() && !valor.equals("0") ){
                            if( !anuncio.getTelefone().isEmpty()  ){
                                if( !anuncio.getDescricao().isEmpty() ){

                                    atualizarAnuncio();

                                }else {
                                    exibirMensagemErro("Preencha o campo descrição");
                                }
                            }else {
                                exibirMensagemErro("Preencha o campo telefone");
                            }
                        }else {
                            exibirMensagemErro("Preencha o campo valor");
                        }
                    }else {
                        exibirMensagemErro("Preencha o campo título");
                    }
                }else {
                    exibirMensagemErro("Preencha o campo categoria");
                }
            }else {
                exibirMensagemErro("Preencha o campo estado");
            }

    }

    private void exibirMensagemErro(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private Anuncio configurarAnuncio() {
        String estado = campoEstado.getSelectedItem().toString();
        String categoria = campoCategoria.getSelectedItem().toString();
        String titulo = campoTitulo.getText().toString();
        String valor = campoValor.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String descricao = campoDescricao.getText().toString();

        Anuncio anuncio = new Anuncio();
        anuncio.setEstado( estado );
        anuncio.setCategoria(categoria);
        anuncio.setTitulo(titulo);
        anuncio.setValor(valor);
        anuncio.setTelefone( telefone );
        anuncio.setDescricao(descricao);

        return anuncio;
    }

    private void carregarDadosSpinner(){
        AnuncioUtils.carregarDadosSpinner(this, campoEstado, campoCategoria);
    }

    private void carregarDadosAnuncioSelecionado() {
        campoTitulo.setText(anuncioSelecionado.getTitulo());
        campoDescricao.setText(anuncioSelecionado.getDescricao());
        campoValor.setText(anuncioSelecionado.getValor());
        campoTelefone.setText(anuncioSelecionado.getTelefone());
        campoEstado.setSelection(getIndexPosition(TipoSpinner.ESTADOS));
        campoCategoria.setSelection(getIndexPosition(TipoSpinner.CATEGORIAS));
    }

    private int getIndexPosition(TipoSpinner tipoSpinner) {
       List<String> list = new ArrayList<>();

       if (TipoSpinner.ESTADOS.equals(tipoSpinner)) {
           list = Arrays.asList(getResources().getStringArray(R.array.estados));
           return list.indexOf(anuncioSelecionado.getEstado());
       } else if (TipoSpinner.CATEGORIAS.equals(tipoSpinner)) {
           list = Arrays.asList(getResources().getStringArray(R.array.categorias));
           return list.indexOf(anuncioSelecionado.getCategoria());
       }
       return 0;
    }

    private void inicializarComponentes() {
        campoTitulo = findViewById(R.id.editTitulo);
        campoDescricao = findViewById(R.id.editDescricao);
        campoValor = findViewById(R.id.editValor);
        campoTelefone = findViewById(R.id.editTelefone);
        campoEstado = findViewById(R.id.spinnerEstado);
        campoCategoria = findViewById(R.id.spinnerCategoria);

        //Configura localidade para pt -> portugues BR -> Brasil
        Locale locale = new Locale("pt", "BR");
        campoValor.setLocale( locale );
    }
}