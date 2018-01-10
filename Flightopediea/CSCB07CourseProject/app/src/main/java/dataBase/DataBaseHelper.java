package dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class used to create the database.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "DATABASE";

    private static final String FLIGHT_TABLE = "CREATE TABLE FLIGHT ( FlightNumber " +
            "STRING PRIMARY KEY, Origin TEXT, Destination TEXT, Airline TEXT, " +
            "Depart TEXT, Arrive TEXT, Price TEXT, Seats TEXT, DepartTime TEXT, " +
            "ArriveTime TEXT )";

    private static final String CLIENT_TABLE = "CREATE TABLE CLIENT ( "
            + "Email STRING PRIMARY KEY, " + "Password TEXT, " +
            "FirstName TEXT, " + "LastName TEXT, " + "Address TEXT, " +
            "CreditCard TEXT, " + "Expiry TEXT, Itinerary TEXT )";

    private static final String TABLE_CLIENT = "CLIENT";
    private static final String TABLE_FLIGHT = "FLIGHT";

    /**
     * Create the database.
     * @param context used to create the database
     */
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CLIENT_TABLE);
        db.execSQL(FLIGHT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);
        // create fresh books table
        this.onCreate(db);
    }
}