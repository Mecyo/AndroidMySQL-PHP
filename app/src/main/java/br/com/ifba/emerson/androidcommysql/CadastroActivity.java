package br.com.ifba.emerson.androidcommysql;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CadastroActivity extends AppCompatActivity {

    EditText txtNome;
    EditText txtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtNome = (EditText)findViewById(R.id.txtNome);
        txtEmail = (EditText)findViewById(R.id.txtEmail);

        ((Button) findViewById(R.id.btnSalvar)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText txtNome = (EditText)findViewById(R.id.txtNome);
                EditText txtEmail = (EditText)findViewById(R.id.txtEmail);

                StringBuilder strUrl = new StringBuilder();

                strUrl.append("http://10.134.17.17/inserir.php?");
                strUrl.append("nome=");
                strUrl.append(txtNome.getText().toString());
                strUrl.append("&email=");
                strUrl.append(txtEmail.getText().toString());
                (new HttpRequest()).execute(strUrl.toString());
            }
        });
    }

    private class HttpRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String retorno = "YES";

            try {
                String urlHttp = strings[0];
                URL url = new URL(urlHttp);

                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                InputStreamReader isr = new InputStreamReader(http.getInputStream());

                BufferedReader bfr = new BufferedReader(isr);

                retorno = bfr.readLine();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return retorno;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("YES")) {
                Toast.makeText(getApplicationContext(), "Cliente cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getBaseContext(), ListarActivity.class));
            }
            else
                Toast.makeText(getBaseContext(), "Erro ao cadastrar o cliente!", Toast.LENGTH_LONG).show();
        }
    }

}
