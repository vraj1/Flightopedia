package client;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.TreeSet;

import dataBase.DataBaseHelper;
import exception.NoSuchClientException;

/**
 * Clients access to database class.
 */
public class ClientDateBase {

    private static final String TABLE_CLIENT = "CLIENT";
    private static final String KEY_Email = "Email";
    private static final String KEY_Pass = "Password";
    private static final String KEY_FirstName= "FirstName";
    private static final String KEY_LastName = "LastName";
    private static final String KEY_Address = "Address";
    private static final String KEY_Card = "CreditCard";
    private static final String KEY_Expiry = "Expiry";
    private static final String KEY_ITI = "Itinerary";
    private DataBaseHelper dataBase;

    /** Create a <code>ClientDataBase</code>  that will store the information */
    public ClientDateBase(Context context){
        dataBase = new DataBaseHelper(context);
    }

    /**
     * Adds a client to the <code>ClientDataBase</code>.
     * @param client to be added to <code>ClientDataBase</code>
     */
    public void addClient(Client client){
        try{
            getClient(client.getEmail());
            Log.d("Client", "EXSISTS");
            update(client, null);
        }catch(NoSuchClientException e){
            Log.d("Client", "DOESNOT EXSISTS");
            SQLiteDatabase db = dataBase.getWritableDatabase();
            ContentValues values = getClientContentValues(client);
            db.insert(TABLE_CLIENT,null,values);
            db.close();
        }
    }

    /**
     * Gets a client from <code>ClientDataBase</code>, given a client's email.
     * @param email The email of the client to be returned
     * @return client with the email address
     * @throws NoSuchClientException if no such client exists
     */
    public Client getClient(String email) throws NoSuchClientException{
        SQLiteDatabase database = dataBase.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = '%s'",
                TABLE_CLIENT,KEY_Email,email);

        Cursor c = database.rawQuery(query, null);

        if(c.getCount() == 0){
            c.close();
            database.close();
            throw new NoSuchClientException("No such Client Exists");
        }else{
            c.moveToFirst();
        }

        Client client = new Client();
        client.setEmail(c.getString(c.getColumnIndex(KEY_Email)));
        client.setPassword(c.getString(c.getColumnIndex(KEY_Pass)));
        client.setFirstName(c.getString(c.getColumnIndex(KEY_FirstName)));
        client.setLastName(c.getString(c.getColumnIndex(KEY_LastName)));
        client.setAddress(c.getString(c.getColumnIndex(KEY_Address)));
        client.setCreditCardNumber(c.getString(c.getColumnIndex(KEY_Card)));
        try{
            client.setExpiryDate(c.getString(c.getColumnIndex(KEY_Expiry)));
        }catch (ParseException e){
            e.printStackTrace();
        }

        TreeSet<String> iti = new TreeSet<>();
        String itiString = c.getString(c.getColumnIndex(KEY_ITI));
        itiString = itiString.substring(1, itiString.length() - 1);
        for(String flight: itiString.split(", ")){
            iti.add(flight);
        }
        client.setItinerary(iti);

        database.close();
        c.close();
        return client;
    }

    /**
     * Checks if the user entered the valid email and password combination.
     * @param email of the user
     * @param Pass of the user
     * @return email as a string
     * @throws NoSuchClientException if no such client exists
     */
    public String isLogin(String email, String Pass) throws NoSuchClientException{
        Client client = getClient(email);
        if(!client.getPassword().equals(Pass)){
            throw new NoSuchClientException("Incorrect Email or Password");
        }
        return client.getEmail();
    }

    /**
     * Overrides client information with the values in item.
     * @param client the client who will have their information overridden
     * @param item new values that will be used to override
     */
    public void update(Client client, String item){

        try{
            client.setItinerary(getClient(client.getEmail()).getItinerary());
        }catch (NoSuchClientException e){
            e.printStackTrace();
        }

        if(item!= null){
            client.addItinerary(item);
        }

        SQLiteDatabase database = dataBase.getWritableDatabase();
        ContentValues values = getClientContentValues(client);
        values.remove(KEY_Email);
        database.update(TABLE_CLIENT,values,String.format("%s='%s'",
                KEY_Email, client.getEmail()),null);
        database.close();
    }

    /**
     * Get the client's information
     * @param client who's information is being extracted
     * @return the email, password, first name, last name, address, credit card number, expiry date,
     * and itineraries of the client
     */
    private ContentValues getClientContentValues(Client client){
        ContentValues values = new ContentValues();
        values.put(KEY_Email, client.getEmail());
        values.put(KEY_Pass, client.getPassword());
        values.put(KEY_FirstName, client.getFirstName());
        values.put(KEY_LastName, client.getLastName());
        values.put(KEY_Address, client.getAddress());
        values.put(KEY_Card, client.getCreditCardNumber());
        values.put(KEY_Expiry,client.getExpiryDate());
        values.put(KEY_ITI,client.getItinerary().toString());
        return values;
    }

    /**
     * Get all the client's email in the database.
     * @return ArrayList of client email's
     */
    public ArrayList<String> getAllClientsEmail(){
        ArrayList<String> clients = new ArrayList<>();
        SQLiteDatabase database = dataBase.getReadableDatabase();
        Cursor cursor = database.rawQuery(String.format("SELECT %s FROM %s",
                KEY_Email,TABLE_CLIENT), null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                clients.add(cursor.getString(cursor.getColumnIndex(KEY_Email)));
                cursor.moveToNext();
            }
        }
        database.close();
        cursor.close();
        return clients;
    }

    /**
     * Add the itinerary to the client with the given email address.
     * @param email of the client
     * @param iti the new itinerary to be added
     */
    public void addItinerary(String email, String iti){
        try{
            update(getClient(email),iti);
        }catch (NoSuchClientException e){
            e.printStackTrace();
        }
    }

    /**
     * Delete a client given the e-mail address.
     * @param email of the client to be deleted
     */
    public void deleteClient(String email){
        SQLiteDatabase database = dataBase.getReadableDatabase();
        database.delete(TABLE_CLIENT,String.format("%s='%s'",KEY_Email,email),null);
        database.close();
    }
}
