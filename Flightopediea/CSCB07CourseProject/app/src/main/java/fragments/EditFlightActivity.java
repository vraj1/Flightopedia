package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;

import cs.b07.cscb07courseproject.R;
import exception.NoSuchFlightException;
import flights.Flight;
import flights.FlightDataBase;

/**
 * Class used to edit flight information by the admin.
 */
public class EditFlightActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText flightnumber;
    private EditText airline;
    private EditText origin;
    private EditText destination;
    private EditText depature_date;
    private EditText arrival_date;
    private EditText price;
    private EditText seats;
    private FlightDataBase flightdb;
    private Button flightChange;
    private String flightNumber;
    private Flight flight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_flight);
        Intent intent = getIntent();
        flightNumber = intent.getStringExtra("FLIGHT");
        flightdb = new FlightDataBase(this);
        try{
            flight = flightdb.getFlight(flightNumber);
        }catch (NoSuchFlightException e){
            e.printStackTrace();
        }
        flightnumber = (EditText)findViewById(R.id.flightnumber);
        airline = (EditText)findViewById(R.id.Airline);
        origin = (EditText)findViewById(R.id.origin);
        destination = (EditText)findViewById(R.id.destination);
        depature_date = (EditText)findViewById(R.id.depart_date);
        arrival_date = (EditText)findViewById(R.id.arrival_date);
        price = (EditText)findViewById(R.id.price);
        seats = (EditText)findViewById(R.id.seats);
        updateHint();
        flightChange = (Button)findViewById(R.id.client_button);
        flightChange.setOnClickListener(this);
    }

    /**
     * Updates the information for the flight.
     */
    private void updateHint(){
        flightnumber.setHint("Flight Number: "+flight.getFlightNumber());
        airline.setHint("Airline: " + flight.getAirline());
        origin.setHint("Origin: " +flight.getOrigin());
        destination.setHint("Destination: " + flight.getDestination());
        depature_date.setHint("Departure: " + flight.getDepatureDateTime());
        arrival_date.setHint("Arrival: " + flight.getArrivalDateTime());
        price.setHint("Cost: " + String.valueOf(flight.getCost()));
        seats.setHint("Seats: " + String.valueOf(flight.getNumSeats()));
    }
    @Override
    public void onClick(View view){
        Toast.makeText(this, "Flight Info Updated",
                Toast.LENGTH_SHORT).show();
        if(!flight.getAirline().equals(airline.getText().toString())
                && !airline.getText().toString().equals("")){
            flight.setAirline(airline.getText().toString());
        }
        if(!flight.getOrigin().equals(origin.getText().toString())
                && !origin.getText().toString().equals("")){
            flight.setOrigin(origin.getText().toString());
        }
        if(!flight.getDestination().equals(destination.getText().toString())
                && !destination.getText().toString().equals("")){
            flight.setDestination(destination.getText().toString());
        }


        try {
            if (!flight.getDepartDate().equals(depature_date.getText().toString().split(" ")[0])
                    && !depature_date.getText().toString().equals("")) {
                try {
                    flight.setDepartDate(depature_date.getText().toString().split(" ")[0]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (!flight.getDepartTime().equals(depature_date.getText().toString().split(" ")[1])
                    && !depature_date.getText().toString().equals("")) {
                try {
                    flight.setDepartTime(depature_date.getText().toString().split(" ")[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
        }

        try{
            if(!flight.getArrivalDate().equals(arrival_date.getText().toString().split(" ")[0])
                    && !arrival_date.getText().toString().equals("")){
                try {
                    flight.setArrivalDate(arrival_date.getText().toString().split(" ")[0]);
                    Log.d("Time", flight.getArrivalDateTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(!flight.getArrivalTime().equals(arrival_date.getText().toString().split(" ")[1])
                    && !arrival_date.getText().toString().equals("")){
                try {
                    flight.setArrivalTime(arrival_date.getText().toString().split(" ")[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

        if(!String.valueOf(flight.getCost()).equals(price.getText().toString())
                && !price.getText().toString().equals("")){
            try{
                flight.setCost(Double.valueOf(price.getText().toString()));
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        if(!String.valueOf(flight.getNumSeats()).equals(seats.getText().toString())
                && !seats.getText().toString().equals("")){
            try{
                flight.setNumSeats(Integer.valueOf(seats.getText().toString()));
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        if(!flight.getFlightNumber().equals(flightnumber.getText().toString())
                && !flightnumber.getText().toString().equals("")){
            flightdb.deleteFlight(flightnumber.getText().toString());
            flight.setFlightNumber(flightnumber.getText().toString());
            flightdb.addFlight(flight);
        }else{
            flightdb.update(flight);
        }

        flightnumber.setText("");
        airline.setText("");
        destination.setText("");
        origin.setText("");
        price.setText("");
        depature_date.setText("");
        arrival_date.setText("");
        seats.setText("");
        updateHint();
        finish();
    }
}
