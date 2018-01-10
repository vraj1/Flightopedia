package fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.TreeSet;

import client.ClientDateBase;
import cs.b07.cscb07courseproject.MainActivity;
import cs.b07.cscb07courseproject.R;
import exception.NoSuchClientException;

/**
 * Class for the client for the tab that holds all the bookings.
 */
public class client_booking_tab extends ListFragment {

    private ClientDateBase db;
    private ArrayAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.admin_client_frag,container,false);
        db = new ClientDateBase(getActivity());
        try{

            TreeSet<String> list = db.getClient(MainActivity.account.toString()).getItinerary();
            list.remove("");
            adapter = new ArrayAdapter(getActivity(),R.layout.flights_fragment,new ArrayList(list));
        }catch (NoSuchClientException e){
            e.printStackTrace();
        }
        setListAdapter(adapter);
        return view;
    }
}
