package com.v2v.notetakingapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> notes;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listView);
        notes = new ArrayList<>();
        notes.add("Welcome Note");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(adapter);


        registerForContextMenu(listView);

        listView.setOnItemClickListener((parent, view, position, id) -> showEditDialog(position));
    }

    // Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            showAddDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        if (item.getItemId() == R.id.menu_delete) {
            showDeleteConfirmation(position);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showAddDialog() {
        EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Add Note")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    notes.add(input.getText().toString());
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showEditDialog(int position) {
        EditText input = new EditText(this);
        input.setText(notes.get(position));
        new AlertDialog.Builder(this)
                .setTitle("Edit Note")
                .setView(input)
                .setPositiveButton("Save", (dialog, which) -> {
                    notes.set(position, input.getText().toString());
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDeleteConfirmation(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    notes.remove(position);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
