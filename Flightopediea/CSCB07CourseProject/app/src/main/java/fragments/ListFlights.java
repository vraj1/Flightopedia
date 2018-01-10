package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import client.ClientDateBase;
import cs.b07.cscb07courseproject.R;
import flights.Flight;
import flights.FlightDataBase;
import flights.Itinerary;

/**
 *
 */
public class ListFlights extends AppCompatActivity {

    private String orgin;
    private String destination;
    private String date;
    private FlightDataBase flightDataBase;
    private Itinerary itinerary;
    private ArrayAdapter adapter;
    private ListView listView;
    private String client;
    private String direct;
    private String time;
    private String cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        itinerary = new Itinerary(this);
        flightDataBase = new FlightDataBase(this);
        orgin = intent.getStringExtra("ORIGIN");
        destination = intent.getStringExtra("DESTINATION");
        date = intent.getStringExtra("DATE");
        client = intent.getStringExtra("CLIENT");
        direct = intent.getStringExtra("DIRECT");
        time = intent.getStringExtra("Time");
        cost = intent.getStringExtra("Cost");
        setContentView(R.layout.list_flights_fragment);
        listView = (ListView)findViewById(R.id.list_fight_fragment);
        final ClientDateBase dateBase = new ClientDateBase(this);

        ArrayList<String> flightsinfo = new ArrayList<>();

        if(direct.equals("true")){
            for(Flight flight: flightDataBase.getFlights(orgin,destination,date)){
                flightsinfo.add(flight.displayFlightString());
            }
        }else{
            if(cost.equals("true")){
                for(String flight: itinerary.getItinerariesSortedByCost(
                        itinerary.getIntinerary(orgin,destination,date,true),true)){
                    flightsinfo.add(flight);
                }
            }else if(time.equals("true")){
                for(String flight: itinerary.getItinerariesSortedByTime(
                        itinerary.getIntinerary(orgin,destination,date,true),true)){
                    flightsinfo.add(flight);
                }
            }else{
                for(String flight: itinerary.getIntinerary(orgin,destination,date,true)){
                    flightsinfo.add(flight);
                }
            }
        }

        adapter = new ArrayAdapter(this,R.layout.flights_fragment,flightsinfo);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                if(!client.equals("false")){
                    String item = ((TextView)view).getText().toString();
                    dateBase.addItinerary(client, item);
                    finish();
                }else{
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
