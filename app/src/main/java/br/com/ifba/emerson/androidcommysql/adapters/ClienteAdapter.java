package br.com.ifba.emerson.androidcommysql.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.ifba.emerson.androidcommysql.R;
import br.com.ifba.emerson.androidcommysql.models.Cliente;

/**
 * Created by Emerson Santos on 30/07/18.
 */

public class ClienteAdapter extends BaseAdapter {

    private final List<Cliente> clientes;
    private final Activity act;


    public ClienteAdapter(List<Cliente> clientes, Activity act) {
        this.clientes = clientes;
        this.act = act;
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
            context = ((ContextWrapper)context).getBaseContext();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        nome.setText(cliente.getNome());
        email.setText(cliente.getEmail());
        //imagem.setImageResource(R.drawable.facebook);

        return view;
    }
}