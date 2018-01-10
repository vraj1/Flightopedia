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

import cs.b07.cscb07courseproject.R;
import exception.NoSuchFlightException;
import flights.FlightDataBase;

/**
 * Class used to change the information for the flights.
 */
public class flightschangetab extends ListFragment {

    private static final String USER_MESSAGE = "USERNAME";
    private FlightDataBase db;
    private ArrayAdapter adapter;
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.admin_client_frag,container,false);
        ArrayList<String> flightsinfo = new ArrayList<>();
        db = new FlightDataBase(getActivity());
        for (String flight: db.getAllFlights()){
            try{
                flightsinfo.add(db.getFlight(flight).displayFlightString());
            }catch (NoSuchFlightException e){
                e.printStackTrace();
            }
        }
        adapter = new ArrayAdapter(getActivity(),R.layout.client_tab_fragment, flightsinfo);
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
                String item = ((TextView)view).getText().toString().split("\n")[0].split(" ")[0];
                Intent intent = new Intent(getActivity(), EditFlightActivity.class);
                intent.putExtra("FLIGHT",item);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
