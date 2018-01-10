package flights;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;

import dataBase.DataBaseHelper;
import exception.NoSuchFlightException;

/**
 * Database used to hold all flight information.
 */
public class FlightDataBase {

    private static final String TABLE_FLIGHT = "FLIGHT";
    private static final String KEY_Flight = "FlightNumber";
    private static final String KEY_Depart = "Depart";
    private static final String KEY_Arrival = "Arrive";
    private static final String KEY_Airline = "Airline";
    private static final String KEY_Origin = "Origin";
    private static final String KEY_Destination = "Destination";
    private static final String KEY_Price = "Price";
    private static final String KEY_Seats = "Seats";
    private static final String KEY_Depart_Time = "DepartTime";
    private static final String KEY_Arrive_Time = "ArriveTime";

    private DataBaseHelper dataBase;

    /**
     * Setting up the <code>FlightDataBase</code>.
     * @param context context.
     */
    public FlightDataBase(Context context){
        dataBase = new DataBaseHelper(context);
    }

    /**
     * Add a flight.
     * @param flight to be added
     */
    public void addFlight(Flight flight){
        try{
            getFlight(flight.getFlightNumber());
            update(flight);
            Log.d("Flight", flight.toString());
        }catch(NoSuchFlightException e){
            Log.d("Flight", "DOESNOT EXSISTS");
            SQLiteDatabase db = dataBase.getWritableDatabase();
            ContentValues values = getFlightContentValues(flight);
            db.insert(TABLE_FLIGHT,null,values);
            db.close();
        }
    }

    /**
     * Used to override flight informaton.
     * @param flight the flight to be overriden
     */
    public void update(Flight flight){
        SQLiteDatabase database = dataBase.getReadableDatabase();
        ContentValues values = getFlightContentValues(flight);
        values.remove(KEY_Flight);
        database.update(TABLE_FLIGHT,values,String.format("%s='%s'",
                KEY_Flight, flight.getFlightNumber()),null);
        database.close();
    }

    /**
     * Get the flight associated with the flightnumber.
     * @param flightnumber of the flight to be returned.
     * @return the flight
     * @throws NoSuchFlightException if no such flight exists
     */
    public Flight getFlight(String flightnumber) throws NoSuchFlightException{
        SQLiteDatabase database = dataBase.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = '%s'",
                TABLE_FLIGHT,KEY_Flight,flightnumber);

        Cursor c = database.rawQuery(query, null);

        if(c.getCount() == 0){
            c.close();
            database.close();
            throw new NoSuchFlightException("No such Flight Exists");
        }else{
            c.moveToFirst();
        }

        Flight flight = new Flight();
        flight.setFlightNumber(c.getString(c.getColumnIndex(KEY_Flight)));
        flight.setAirline(c.getString(c.getColumnIndex(KEY_Airline)));
        flight.setCost(Double.valueOf(c.getString(c.getColumnIndex(KEY_Price))));
        flight.setNumSeats(Integer.valueOf(c.getString(c.getColumnIndex(KEY_Seats))));
        flight.setOrigin(c.getString(c.getColumnIndex(KEY_Origin)));
        flight.setDestination(c.getString(c.getColumnIndex(KEY_Destination)));

        try{
            flight.setArrivalDate(c.getString(c.getColumnIndex(KEY_Arrival)));
            flight.setDepartDate(c.getString(c.getColumnIndex(KEY_Depart)));
            flight.setArrivalTime(c.getString(c.getColumnIndex(KEY_Arrive_Time)));
            flight.setDepartTime(c.getString(c.getColumnIndex(KEY_Depart_Time)));
        }catch (ParseException e){
            e.printStackTrace();
        }

        c.close();
        database.close();
        return flight;
    }

    /**
     * Get the values from the given flight.
     * @param flight where the data is going to be extracted from
     * @return the information of the flight
     */
    private ContentValues getFlightContentValues(Flight flight){
        ContentValues values = new ContentValues();
        values.put(KEY_Airline, flight.getAirline());
        values.put(KEY_Arrival, flight.getArrivalDate());
        values.put(KEY_Depart, flight.getDepartDate());
        values.put(KEY_Destination, flight.getDestination());
        values.put(KEY_Flight,flight.getFlightNumber());
        values.put(KEY_Origin, flight.getOrigin());
        values.put(KEY_Price,flight.getCost());
        values.put(KEY_Seats,flight.getNumSeats());
        values.put(KEY_Arrive_Time, flight.getArrivalTime());
        values.put(KEY_Depart_Time, flight.getDepartTime());
        return values;
    }

    /**
     * Get an ArrayList of flights from the origin to the arrival specified.
     * @param origin of the flight
     * @param arrival of the flight
     * @param date of arrival
     * @return an ArrayList of flights given the data
     */
    public ArrayList<Flight> getFlights(String origin, String arrival, String date){
        ArrayList<Flight> flights = new ArrayList<>();
        SQLiteDatabase database = dataBase.getReadableDatabase();

        String query;

        if((arrival == null) && (date == null)){
            query = String.format("SELECT %s FROM %s WHERE %s = '%s'",
                    KEY_Flight,TABLE_FLIGHT,KEY_Origin,origin);
        }else if(arrival == null){
            query = String.format("SELECT %s FROM %s WHERE %s = '%s' AND %s = '%s'",
                    KEY_Flight,TABLE_FLIGHT,KEY_Origin,origin,KEY_Depart,date);
        }else{
            query = String.format("SELECT %s FROM %s WHERE %s = '%s' AND %s = '%s' AND %s = '%s'",
                    KEY_Flight,TABLE_FLIGHT,KEY_Origin,origin,KEY_Destination,arrival,
                    KEY_Depart,date);
        }
        Cursor cursor = database.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            try{
                flights.add(getFlight(cursor.getString(cursor.getColumnIndex(KEY_Flight))));
            }catch (NoSuchFlightException e){
                e.printStackTrace();
            }
        }
        cursor.close();
        database.close();
        return flights;
    }

    /**
     * Get all the client's email in the database.
     * @return ArrayList of client email's
     */
    public ArrayList<String> getAllFlights(){
        ArrayList<String> flights = new ArrayList<>();
        SQLiteDatabase database = dataBase.getReadableDatabase();
        Cursor cursor = database.rawQuery(String.format("SELECT %s FROM %s",
                KEY_Flight,TABLE_FLIGHT), null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                flights.add(cursor.getString(cursor.getColumnIndex(KEY_Flight)));
                cursor.moveToNext();
            }
        }
        database.close();
        cursor.close();
        return flights;
    }

    /**
     * Delete a Flight given the e-mail address.
     * @param number of the client to be deleted
     */
    public void deleteFlight(String number){
        SQLiteDatabase database = dataBase.getReadableDatabase();
        database.delete(TABLE_FLIGHT,String.format("%s='%s'",KEY_Flight,number),null);
        database.close();
    }
}
