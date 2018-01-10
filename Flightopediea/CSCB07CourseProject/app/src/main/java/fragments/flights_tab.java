package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import cs.b07.cscb07courseproject.MainActivity;
import cs.b07.cscb07courseproject.R;
import flights.FlightDataBase;
import flights.Itinerary;
/**
 * Tab used to hold the flight information.
 */
public class flights_tab extends Fragment implements View.OnClickListener {

    private EditText origin;
    private EditText date;
    private EditText destination;
    private Button flight_upload;
    private Itinerary itinerary;
    private CheckBox checkBoxdirect;
    private CheckBox cbcost;
    private CheckBox cbtime;
    private boolean stateCheck;
    private boolean cost;
    private boolean time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.flights_tab,container,false);

        stateCheck = false;
        origin = (EditText)view.findViewById(R.id.origin);
        date = (EditText)view.findViewById(R.id.date);
        destination = (EditText)view.findViewById(R.id.destination);

        itinerary = new Itinerary(getActivity());
        checkBoxdirect = (CheckBox)view.findViewById(R.id.checkBoxFlight);
        cbcost = (CheckBox)view.findViewById(R.id.costdecreasing);
        cbtime = (CheckBox)view.findViewById(R.id.timedecreasing);
        checkBoxdirect.setChecked(false);
        cbtime.setChecked(false);
        cbcost.setChecked(false);
        checkBoxdirect.setOnClickListener(this);
        cbtime.setOnClickListener(this);
        cbcost.setOnClickListener(this);

        flight_upload = (Button)view.findViewById(R.id.get_flight);
        flight_upload.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view){

        if(view.getId() == checkBoxdirect.getId()){
            if(!stateCheck){
                stateCheck = true;
            }else{
                stateCheck = false;
            }
        }else if(view.getId() == cbcost.getId()){
            if(!cost){
                cost = true;
            }else{
                cost = false;
            }
            Log.d("cost", String.valueOf(cost));
        }else if(view.getId() == cbtime.getId()){
            if(!time){
                time = true;
            }else{
                time = false;
            }
            Log.d("time", String.valueOf(time));
        }else if(view.getId() == flight_upload.getId()){
            FlightDataBase flightDataBase = new FlightDataBase(getActivity());

            if(itinerary.getIntinerary(origin.getText().toString(),destination.getText().toString(),
                    date.getText().toString(),true).isEmpty()){
                Toast.makeText(getActivity(), "No Such Flights",
                        Toast.LENGTH_SHORT).show();
            }else if(stateCheck && flightDataBase.getFlights(
                origin.getText().toString(),destination.getText().toString(),
                date.getText().toString()).isEmpty()){
                Toast.makeText(getActivity(), "No Such Direct Flights",
                    Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(getActivity(), ListFlights.class);

                checkBoxdirect = (CheckBox) view.findViewById(R.id.checkBoxFlight);

                if (stateCheck) {
                    intent.putExtra("DIRECT", "true");
                } else {
                    intent.putExtra("DIRECT", "false");
                }
                if (cost) {
                    intent.putExtra("Cost", "true");
                } else {
                    intent.putExtra("Cost", "false");
                }
                if (time) {
                    intent.putExtra("Time", "true");
                } else {
                    intent.putExtra("Time", "false");
                }

                intent.putExtra("ORIGIN", origin.getText().toString());
                intent.putExtra("DESTINATION", destination.getText().toString());
                intent.putExtra("DATE", date.getText().toString());

                if (!MainActivity.account.equals("")) {
                    intent.putExtra("CLIENT", MainActivity.account.toString());
                } else {
                    intent.putExtra("CLIENT", "false");
                }
                startActivity(intent);
                origin.setText("");
                date.setText("");
                destination.setText("");
            }
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        checkBoxdirect.setChecked(false);
        cbtime.setChecked(false);
        cbcost.setChecked(false);
    }
}
