package br.com.ifba.emerson.androidcommysql.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.ifba.emerson.androidcommysql.CadastroActivity;
import br.com.ifba.emerson.androidcommysql.ListarActivity;
import br.com.ifba.emerson.androidcommysql.R;
import br.com.ifba.emerson.androidcommysql.models.Cliente;

/**
 * Created by Emerson Santos on 30/07/18.
 */

public class ClienteAdapter extends BaseAdapter {

    private final List<Cliente> clientes;
    private final Activity act;
    private LayoutInflater mInflater;


    public ClienteAdapter(List<Cliente> clientes, Activity act) {
        this.act = act;

            //Itens do listview
        this.clientes = clientes;
            //Objeto responsável por pegar o Layout do item.
            mInflater = LayoutInflater.from(act.getApplicationContext());
    }

    /*public void atualizarCliente(Cliente cliente){
        clientes.set(clientes.indexOf(cliente), cliente);
        notifyItemChanged(alunos.indexOf(cliente));
    }

    public void adicionarCliente(Cliente cliente){
        clientes.add(cliente);
        notifyItemInserted(getItemCount());
    }

    public void removerCliente(Cliente cliente){
        int position = clientes.indexOf(cliente);
        clientes.remove(position);
        notifyItemRemoved(position);
    }*/

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((Application)context).getBaseContext();
        }
        return null;
    }

    @Override
    public int getCount() {
        return clientes != null ? clientes.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return clientes.get(position).getId();
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //final int pos = position;
        View view = act.getLayoutInflater()
                .inflate(R.layout.item_lista_cliente, parent, false);
        Cliente cliente = clientes.get(position);

        //pegando as referências das Views
        TextView nome = (TextView)
                view.findViewById(R.id.nome);
        TextView email = (TextView)
                view.findViewById(R.id.email);
        /*ImageView imagem = (ImageView)
                view.findViewById(R.id.lista_curso_personalizada_imagem);*/

        //populando as Views
        /*nome.setText(cliente.getNome());
        email.setText(cliente.getEmail());
        //imagem.setImageResource(R.drawable.facebook);

        //Button btnEditar = (Button) view.findViewById(R.id.btnEdit);
        //Button btnDelete = (Button) view.findViewById(R.id.btnDelete);

        /*btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = new Intent(v.getContext(), CadastroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                //intent.putExtra("cliente", getItem(pos).toString());
                activity.finish();
                activity.startActivity(intent);
            }
        });*/

        //return view;
    //}

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ItemSuporte itemHolder;
        //se a view estiver nula (nunca criada), inflamos o layout nela.
            if (view == null) {
            //infla o layout para podermos pegar as views
            view = mInflater.inflate(R.layout.item_lista_cliente, null);

            //cria um item de suporte para não precisarmos sempre
            //inflar as mesmas informacoes
            itemHolder = new ItemSuporte(view);

            //define os itens na view;
            view.setTag(itemHolder);
        } else {
            //se a view já existe pega os itens.
            itemHolder = (ItemSuporte) view.getTag();
        }

        //pega os dados da lista
        //e define os valores nos itens.
        final Cliente item = clientes.get(position);
            itemHolder.txtNome.setText(item.getNome());
            itemHolder.txtEmail.setText(item.getEmail());

        itemHolder.btnExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v.getRootView();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este cliente?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                StringBuilder strUrl = new StringBuilder();

                                strUrl.append("http://10.134.17.17/deletar.php?id=");
                                strUrl.append(item.getId());

                                (new HttpRequest()).execute(strUrl.toString());
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });

        itemHolder.btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CadastroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("cliente", item);
                act.finish();
                act.startActivity(intent);
            }
        });

        //retorna a view com as informações
            return view;
    }

    /**
     * Classe de suporte para os itens do layout.
     */
    private class ItemSuporte {

        TextView txtNome;
        TextView txtEmail;
        public TextView titulo;
        public ImageButton btnEditar;
        public ImageButton btnExcluir;

        public ItemSuporte(View itemView) {
            txtNome = (TextView) itemView.findViewById(R.id.nome);
            txtEmail = (TextView) itemView.findViewById(R.id.email);
            btnEditar = (ImageButton) itemView.findViewById(R.id.btnEdit);
            btnExcluir = (ImageButton) itemView.findViewById(R.id.btnDelete);
        }
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
                Toast.makeText(act.getApplicationContext(), "Cliente deletado com sucesso!", Toast.LENGTH_LONG).show();
                act.startActivity(new Intent(act.getBaseContext(), ListarActivity.class));
            }
            else
                Toast.makeText(act.getBaseContext(), "Erro ao deletar o cliente!", Toast.LENGTH_LONG).show();
        }
    }
}