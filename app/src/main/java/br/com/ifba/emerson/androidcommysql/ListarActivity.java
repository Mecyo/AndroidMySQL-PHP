package br.com.ifba.emerson.androidcommysql;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.ifba.emerson.androidcommysql.adapters.ClienteAdapter;
import br.com.ifba.emerson.androidcommysql.models.Cliente;

public class ListarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        StringBuilder strUrl = new StringBuilder();
        strUrl.append("http://10.134.17.17/listar.php?");
        (new CatalogClient()).execute(strUrl.toString());
    }

    private class CatalogClient extends AsyncTask<String, String, List<Cliente>> {
        @Override
        protected List<Cliente> doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;
            List<Cliente> clientes = new ArrayList<>();

            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                int responseCode = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    String responseString = readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient-Response", responseString);
                    clientes = parseBookData(responseString);
                }else{
                    Log.v("CatalogClient", "Response code:"+ responseCode);
                    Log.v("CatalogClient", "Response message:"+ responseMessage);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null)
                    urlConnection.disconnect();
            }

            return clientes;
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer response = new StringBuffer();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
        }


        private List<Cliente> parseBookData(String jString){

            List<Cliente> clientes = new ArrayList<Cliente>();
            try {
                JSONArray items = new JSONArray(jString);
                if(items != null) {
                    for (int i = 0; i < items.length(); i++) {
                        int id = items.getJSONObject(i).getInt("id");
                        String nome = items.getJSONObject(i).getString("nome");
                        String email = items.getJSONObject(i).getString("email");
                        //the value of progress is a placeholder here....
                        Cliente book = new Cliente(id, nome, email);
                        clientes.add(book);
                    }

                }

            } catch (JSONException e) {
                Log.e("CatalogClient", "unexpected JSON exception", e);
            }

            return clientes;
        }

        @Override
        protected void onPostExecute(List<Cliente> clientes) {
            super.onPostExecute(clientes);
            if(clientes.size() > 0) {
                ListView listaDeCursos = (ListView) findViewById(R.id.list_cliente);

                //chamada da nossa implementação
                ClienteAdapter adapter = new ClienteAdapter(clientes, ListarActivity.this);

                listaDeCursos.setAdapter(adapter);
            }
            else
                Toast.makeText(getBaseContext(), "Erro ao receber lista de clientes!", Toast.LENGTH_LONG).show();
        }
    }
}
