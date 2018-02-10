package com.example.android.waitlist

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.widget.EditText

import com.example.android.waitlist.data.WaitlistContract
import com.example.android.waitlist.data.WaitlistDbHelper


class MainActivity : AppCompatActivity() {

    private var mAdapter: GuestListAdapter? = null
    private var mDb: SQLiteDatabase? = null
    private var mNewGuestNameEditText: EditText? = null
    private var mNewPartySizeEditText: EditText? = null


    /**
     * Query the mDb and get all guests from the waitlist table
     *
     * @return Cursor containing the list of guests
     */
    private val allGuests: Cursor
        get() = mDb!!.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME, null, null, null, null, null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val waitlistRecyclerView: RecyclerView

        // Set local attributes to corresponding views
        waitlistRecyclerView = this.findViewById(R.id.all_guests_list_view) as RecyclerView
        mNewGuestNameEditText = this.findViewById(R.id.person_name_edit_text) as EditText
        mNewPartySizeEditText = this.findViewById(R.id.party_count_edit_text) as EditText

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        waitlistRecyclerView.layoutManager = LinearLayoutManager(this)


        // Create a DB helper (this will create the DB if run for the first time)
        val dbHelper = WaitlistDbHelper(this)

        // Keep a reference to the mDb until paused or killed. Get a writable database
        // because you will be adding restaurant customers
        mDb = dbHelper.writableDatabase

        // Get all guest info from the database and save in a cursor
        val cursor = allGuests

        // Create an adapter for that cursor to display the data
        mAdapter = GuestListAdapter(this, cursor)

        // Link the adapter to the RecyclerView
        waitlistRecyclerView.adapter = mAdapter


        //TODO (3) Create a new ItemTouchHelper with a SimpleCallback that handles both LEFT and RIGHT swipe directions

        // TODO (4) Override onMove and simply return false inside

        // TODO (5) Override onSwiped

        // TODO (8) Inside, get the viewHolder's itemView's tag and store in a long variable id
        // TODO (9) call removeGuest and pass through that id
        // TODO (10) call swapCursor on mAdapter passing in getAllGuests() as the argument

        //TODO (11) attach the ItemTouchHelper to the waitlistRecyclerView

    }

    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    fun addToWaitlist(view: View) {
        if (mNewGuestNameEditText!!.text.length == 0 || mNewPartySizeEditText!!.text.length == 0) {
            return
        }
        //default party size to 1
        var partySize = 1
        try {
            //mNewPartyCountEditText inputType="number", so this should always work
            partySize = Integer.parseInt(mNewPartySizeEditText!!.text.toString())
        } catch (ex: NumberFormatException) {
            Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.message)
        }

        // Add guest info to mDb
        addNewGuest(mNewGuestNameEditText!!.text.toString(), partySize)

        // Update the cursor in the adapter to trigger UI to display the new list
        mAdapter!!.swapCursor(allGuests)

        //clear UI text fields
        mNewPartySizeEditText!!.clearFocus()
        mNewGuestNameEditText!!.text.clear()
        mNewPartySizeEditText!!.text.clear()
    }

    /**
     * Adds a new guest to the mDb including the party count and the current timestamp
     *
     * @param name  Guest's name
     * @param partySize Number in party
     * @return id of new record added
     */
    private fun addNewGuest(name: String, partySize: Int): Long {
        val cv = ContentValues()
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, name)
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, partySize)
        return mDb!!.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv)
    }

    companion object {
        private val LOG_TAG = MainActivity::class.java.simpleName
    }


    // TODO (1) Create a new function called removeGuest that takes long id as input and returns a boolean

    // TODO (2) Inside, call mDb.delete to pass in the TABLE_NAME and the condition that WaitlistEntry._ID equals id


}