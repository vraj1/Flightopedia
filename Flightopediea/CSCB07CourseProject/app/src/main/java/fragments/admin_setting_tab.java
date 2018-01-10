package fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import client.Client;
import client.ClientDateBase;
import cs.b07.cscb07courseproject.AdminActivity;
import cs.b07.cscb07courseproject.R;
import flights.Flight;
import flights.FlightDataBase;

/**
 * Class for the admin for the tab that holds all the settings.
 */
public class admin_setting_tab extends Fragment implements View.OnClickListener {

    private EditText client_file;
    private EditText flight_file;
    private Button client_upload;
    private Button flight_upload;
    private ClientDateBase clientDB;
    private FlightDataBase flightDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.admin_setting_tab,container,false);
        client_file = (EditText)view.findViewById(R.id.client_file);
        flight_file = (EditText)view.findViewById(R.id.flight_file);
        client_upload = (Button)view.findViewById(R.id.client_button);
        flight_upload = (Button)view.findViewById(R.id.flight_button);

        clientDB = new ClientDateBase(getActivity());
        flightDB = new FlightDataBase(getActivity());
        flight_upload.setOnClickListener(this);
        client_upload.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view){
        if(view.getId() == client_upload.getId()){
            uploadClient();
        }else{
            uploadFlight();
        }
    }

    /**
     * Used to upload client information into the admin setting tab.
     */
    private void uploadClient(){
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), client_file.getText().toString());
        Log.d("File: ",file.toString());
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                clientDB.addClient(new Client(line));
            }
            client_file.setText("");
            Toast.makeText(getActivity(), "Client File Uploaded",Toast.LENGTH_SHORT).show();
            ((AdminActivity)getActivity()).updateClient();
        } catch (IOException | ParseException | ArrayIndexOutOfBoundsException e) {
            Toast.makeText(getActivity(), "Invalid Client File",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Used to upload flight information into the admin setting tab.
     */
    private void uploadFlight(){
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), flight_file.getText().toString());
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                flightDB.addFlight(new Flight(line));
            }
            Toast.makeText(getActivity(), "Flight File Uploaded", Toast.LENGTH_SHORT).show();
            flight_file.setText("");
        } catch (IOException | ParseException | ArrayIndexOutOfBoundsException e) {
            Toast.makeText(getActivity(), "Invalid Flight File",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
