package com.example.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewBooks extends AppCompatActivity implements BookAdapter.OnDeleteListner,BookAdapter.OnEditListener{
    ImageView imageView;
    private RecyclerView recyclerView;

    private BookAdapter adapter;

    Button edit;

    String key;
    private DBHelper dbHelper;
    TextView textView;
    ArrayList<Book> books;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        ViewGroup BookAdapter = null;
        View card= LayoutInflater.from(this).inflate(R.layout.cardview,BookAdapter,false);
//        View view2 = getLayoutInflater().inflate(R.layout.editdialogbox, null);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_books);
        textView = findViewById(R.id.emptystate);
        recyclerView = findViewById(R.id.recycle);

edit=card.findViewById(R.id.editButton);

//AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setTitle("EDIT BOOKS");
//        builder.setView(view2);
//        dialog3 = builder.create();
//
//        edit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Log.e("Viewbook", "onClick: edit");
//                dialog3.show();
//            }
//        });

//        String searchname = getIntent().getStringExtra("name");

        recyclerView.setLayoutManager(new LinearLayoutManager(ViewBooks.this));
        key = getIntent().toString();

        dbHelper = new DBHelper(this);
        if(getIntent().hasExtra("key") && getIntent().getStringExtra("key").equals("s")) {

//            recyclerView.setAdapter(null);
//            books.clear();
            books = getIntent().getParcelableArrayListExtra("updated_books");

            adapter = new BookAdapter(ViewBooks.this, books, (BookAdapter.OnDeleteListner) this,(com.example.bookapp.BookAdapter.OnEditListener) this);

            recyclerView.setAdapter(adapter);

            if (books != null && !books.isEmpty()) {
                textView.setVisibility(View.GONE);
            }
        }else {
                    ArrayList<Book> books = dbHelper. getAllBooksWithImagePath();

                    adapter = new BookAdapter(ViewBooks.this, books, (BookAdapter.OnDeleteListner) this,(com.example.bookapp.BookAdapter.OnEditListener) this);

                    recyclerView.setAdapter(adapter);


                    if (books != null && !books.isEmpty()) {
                        textView.setVisibility(View.GONE);
                    }
                }



    }


    @Override
    public void ondeleteClick(int position) {
//        if (position>=0 && position<books.size()) {
        ArrayList<Book> books = dbHelper. getAllBooksWithImagePath();
        Book booktoDelete = books.get(position);
        dbHelper.deleteBook(booktoDelete.getId());
        books.remove(position);
        adapter.UpdateDataset(books);
        adapter.notifyDataSetChanged();
//            adapter.notifyItemRemoved(position);
//           adapter.notifyItemRangeChanged(position,books.size());
        // recyclerView.invalidate();
    }

    public void oneditclik(Book book) {
        Log.e("view", "oneditclik: " );
        books=dbHelper. getAllBooksWithImagePath();

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.editdialogbox, null);
//        View view2= LayoutInflater.from(this).inflate(R.layout.dialogboxadd, null);
        builder.setView(view);
        EditText idb=view.findViewById(R.id.bookid);
        EditText editBookname = view.findViewById(R.id.editbookname);
        EditText editAuthor = view.findViewById(R.id.editauthor);
        EditText editPublish = view.findViewById(R.id.editpublishdate);
        idb.setText(book.getId());
        editBookname.setText(book.getName());
        editAuthor.setText(book.getAuthor());
        editPublish.setText(book.getDate());

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id=idb.getText().toString();
                String updbookName = editBookname.getText().toString();
                String updAuthor = editAuthor.getText().toString();
                String updpublish = editPublish.getText().toString();
               boolean isUpdate= dbHelper.Updatebook(id,updbookName,updAuthor,updpublish);


                if (isUpdate)
                {
                    books=dbHelper. getAllBooksWithImagePath();
                    adapter.UpdateDataset(books);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                else {
                    Log.e("updatebook", "onClick:update not success " );
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
//        else {
//            Log.e("Delete", "Invalid"+position);
//        }

}