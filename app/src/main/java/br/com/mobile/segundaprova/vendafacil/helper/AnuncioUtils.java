package br.com.mobile.segundaprova.vendafacil.helper;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import br.com.mobile.segundaprova.vendafacil.R;

public class AnuncioUtils {

    public static void carregarDadosSpinner(AppCompatActivity activity, Spinner campoEstado, Spinner campoCategoria) {
        //Configura spinner de estados
        String[] estados = activity.getResources().getStringArray(R.array.estados);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity.getApplicationContext(), android.R.layout.simple_spinner_item,
                estados
        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        campoEstado.setAdapter( adapter );

        //Configura spinner de categorias
        String[] categorias = activity.getResources().getStringArray(R.array.categorias);
        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(
                activity.getApplicationContext(), android.R.layout.simple_spinner_item,
                categorias
        );
        adapterCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        campoCategoria.setAdapter( adapterCategoria );
    }
}
