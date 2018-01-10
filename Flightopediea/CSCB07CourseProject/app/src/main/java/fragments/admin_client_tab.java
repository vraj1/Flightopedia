package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import client.ClientDateBase;
import cs.b07.cscb07courseproject.MainActivity;
import cs.b07.cscb07courseproject.R;
import exception.NoSuchClientException;

/**
 * Class for the admin for the tab that holds all the clients.
 */
public class admin_client_tab extends ListFragment {

    private static final String USER_MESSAGE = "USERNAME";
    private ClientDateBase db;
    private ArrayAdapter adapter;
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.admin_client_frag,container,false);
        ArrayList<String> clientsInfo = new ArrayList<>();
        db = new ClientDateBase(getActivity());
        for (String client: db.getAllClientsEmail()){
            try{
                clientsInfo.add(db.getClient(client).getDisplayFormat());
            }catch (NoSuchClientException e){
                e.printStackTrace();
            }
        }
        adapter = new ArrayAdapter(getActivity(),R.layout.client_tab_fragment, clientsInfo);
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String item = ((TextView)view).getText().toString().split("\n")[0];
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(USER_MESSAGE, item);
                startActivity(intent);
            }
        });
        MainActivity.account = "";
    }
}
