package com.example.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Parcelable{

    DrawerLayout drawerLayout;
    CoordinatorLayout coordinatorLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    BookAdapter adapter;
    DBHelper dbHelper;
    public ArrayList<Book> books;
    CardView addCardView, viewCardView, searchCardView;
    ImageView imageView;
    TextView name;
    NavigationView navigationView;
    AlertDialog dialog, dialog2;
    ArrayList<Book> updated_books = new ArrayList<>();
    ImageView uploadimage;
    BookAdapter.OnDeleteListner onDeleteListner;
    public Uri image;



    @SuppressLint("MissingInflatedId")
    private static final int PIC_IMAGE_REQUEST=1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCardView = findViewById(R.id.addcard);
        viewCardView = findViewById(R.id.viewcard);
        imageView = findViewById(R.id.addbookimage);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        searchCardView = findViewById(R.id.searchbookscard);


//        navigationView.addHeaderView(LayoutInflater.from(this.getLayoutInflater().inflate(R.layout.nav_header,null,false),null));
        materialToolbar = findViewById(R.id.materialtoolbar);
        frameLayout = findViewById(R.id.framelayout);
        View view = navigationView.getHeaderView(0);
        name = (TextView) view.findViewById(R.id.textviewname);
        String user = getIntent().getStringExtra("username");
        String pass = getIntent().getStringExtra("password");
        name.setText(user);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("ADD BOOK DETAILS");
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("SEARCH BOX");
        View view1 = getLayoutInflater().inflate(R.layout.dialogboxadd, null);
        View view2 = getLayoutInflater().inflate(R.layout.search_dialogbox, null);

        uploadimage=view1.findViewById(R.id.imageadd);
       EditText bookid = view1.findViewById(R.id.bookid);
      EditText  bookname = view1.findViewById(R.id.bookname);
      EditText  Author = view1.findViewById(R.id.author);
      EditText  publishdate = view1.findViewById(R.id.publishdate);
       Button add = view1.findViewById(R.id.addbookbutton);
        Button search = view2.findViewById(R.id.searchbutton);
        EditText searchitem = view2.findViewById(R.id.searchbook);
        DBHelper dbHelper = new DBHelper(MainActivity.this);

        Cursor c = dbHelper.viewdata();
        while (c.moveToNext()) {
            //  c.moveToNext();
            Log.e("bookId", c.getString(0));
        }
        builder.setView(view1);
        builder1.setView(view2);
        dialog = builder.create();
        dialog2 = builder1.create();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "onclick initiated", Toast.LENGTH_SHORT).show();
                dialog.show();


            }
        });
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Main", "onClick: upload " );
                OpenImagePicker();

            }
        });
        searchCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.show();

            }
        });


        viewCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewBooks.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String book_id = bookid.getText().toString();
                String book_name = bookname.getText().toString();
                String author = Author.getText().toString();
                String publisheddate = publishdate.getText().toString();



                if (!book_name.isEmpty() && !author.isEmpty() && !publisheddate.isEmpty()) {
                    boolean a = dbHelper.bookdataIn(book_id, book_name, author, publisheddate,image,user);
                    Toast.makeText(MainActivity.this, " " + a, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    bookid.setText("");
                    bookname.setText("");
                    Author.setText("");
                    publishdate.setText("");

                } else {
                    Toast.makeText(MainActivity.this, "Please enter all the data", Toast.LENGTH_SHORT).show();
                    return;

                }


            }

        });


        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String key = "s";
                String name = searchitem.getText().toString().trim();
                Cursor cursor = dbHelper.search(name);
                if (cursor != null && cursor.moveToFirst()) {
                    Log.e("mainactivity", "onClick: entered into cursor ");
                    if (cursor.moveToFirst()) {
                        Log.e("mainactivity", "onClick: entered in sceond if     ");
                        do {

                            int idIndex = cursor.getColumnIndex("BOOKID");
                            int nameIndex = cursor.getColumnIndex("BOOKNAME");
                            int authorIndex = cursor.getColumnIndex("AUTHOR");
                            int dateIndex = cursor.getColumnIndex("PUBLISHDATE");
                            int imageindex=cursor.getColumnIndex("COLUMN_IMAGE_PATH");
                            int addeduser=cursor.getColumnIndex(("ADDEDUSER"));

                            if (idIndex != -1 && nameIndex != -1 && authorIndex != -1 && dateIndex != -1) {
                                String bookId = cursor.getString(idIndex);
                                String bookName = cursor.getString(nameIndex);
                                String author = cursor.getString(authorIndex);
                                String publishDate = cursor.getString(dateIndex);
                                Uri imagepath= Uri.parse(cursor.getString(imageindex));
                                String addedu=cursor.getString(addeduser);

                                Log.e("main", "onClick: user "+addedu );

                                updated_books.add(new Book(bookId, bookName, author, publishDate,imagepath,addedu));
//                                adapter = new BookAdapter(MainActivity.this, updated_books, onDeleteListner);
//                                adapter.UpdateDataset2(updated_books);
                            }

//                            } else {
//                                Toast.makeText(MainActivity.this, "No Book Found", Toast.LENGTH_LONG).show();
//                                // Handle missing columns
//                                // Log an error or display a message
//                            }
                        } while (cursor.moveToNext());

                    }
//                    } else {
//                        Toast.makeText(MainActivity.this, "not found", Toast.LENGTH_LONG).show();
//                    }
//                    //   String bookid=cursor.getString(cursor.getColumnIndex("BOOKID"));


                }
                Intent intent = new Intent(MainActivity.this, ViewBooks.class);
                intent.putParcelableArrayListExtra("updated_books", updated_books);
                intent.putExtra("key", key);
                startActivity(intent);
                dialog2.dismiss();


            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, materialToolbar, R.string.drawer_close, R.string.drawer_open);
        drawerLayout.addDrawerListener(toggle);



//        View view4= LayoutInflater.from(this).inflate(R.layout.nav_header, null);
        Menu menu=navigationView.getMenu();
        MenuItem profileItem= menu.findItem(R.id.profiles);
        MenuItem settinsItem=menu.findItem(R.id.settings);
        MenuItem aboutitem= menu.findItem(R.id.about);
        profileItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                Intent intent=new Intent(MainActivity.this,profileActivity.class);
//                Toast.makeText(MainActivity.this, "CLICKEDDD", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                return  true;
            }
        });
        settinsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent=new Intent(MainActivity.this, SettingsActivity.class);
                Toast.makeText(MainActivity.this, "CLICKEDD Settings", Toast.LENGTH_SHORT).show();

                startActivity(intent);
                return true;
            }
        });

        aboutitem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                Intent intent=new Intent(MainActivity.this,About.class);
                startActivity(intent);
                return  true;
            }
        });

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }
    private void OpenImagePicker()
    {
//        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PIC_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Log.e("MainActivity",">>>> onActivityResult............" + requestCode + " >>> " + resultCode + " >>> " + data);
        if(requestCode==PIC_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            Uri selectedImageUri=data.getData();
            Log.e("MainActivity", "onActivityResult: "+selectedImageUri );
             image=selectedImageUri;
        }
    }


}