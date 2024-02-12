package com.example.bookapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    Context context;
//private static final String COLUMN_IMAGE_PATH= Uri.parse("COLUMN_IMAGE_PATH");

private static  final String USERNAME="username";
    String tablename1="BookAppTable";
    String tablename2="Userlogo";
    public DBHelper(@Nullable Context context){
                    super(context, "BookApp.db", null, 1);
                    this.context=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table "+tablename2+"(NAME TEXT ,USERNAME TEXT,EMAIL TEXT,PASSWORD TEXT,CONFIRMPASS TEXT,MOBILENO TEXT)");

        db.execSQL("Create table "+tablename1+"(BOOKID TEXT PRIMARY KEY, BOOKNAME TEXT,AUTHOR TEXT,PUBLISHDATE DATE, COLUMN_IMAGE_PATH URI, ADDEDUSER TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


//    public  void delete(String id)
//    {
//         SQLiteDatabase dbs=this.getWritableDatabase();
//
//             dbs.execSQL("delete from "+tablename1+ "where BOOKID=" +id);
//         }

public  void deleteBook(String bId)
{

    SQLiteDatabase dbs=this.getWritableDatabase();
    dbs.delete(tablename1,"BOOKID = ?", new String[]{bId});
    dbs.close();
}


    public Cursor search(String nm)
    {
        SQLiteDatabase d=this.getReadableDatabase();
        return d.rawQuery("select * from "+tablename1+" where BOOKNAME like  '%" + nm + "%'",null);
    }

    public boolean userdataIn(String name,String username,String email,String password,String confirmpass,String mobile)
    {
        SQLiteDatabase dbs=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("USERNAME",username);
        contentValues.put("EMAIL",email);
        contentValues.put("PASSWORD",password);
        contentValues.put("CONFIRMPASS",confirmpass);
        contentValues.put("MOBILENO",mobile);
        long ins;
        try {

           ins = dbs.insert(tablename2, null, contentValues);
            Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            Toast.makeText(context, "DUPLICATE", Toast.LENGTH_SHORT).show();
            ins=-1;
        }
        return ins!=-1;
    }


    public boolean bookdataIn(String id,String bookname,String author,String publishdate,Uri imagePath,String useradded)
    {
        SQLiteDatabase dbs=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("BOOKID",id);
        contentValues.put("BOOKNAME",bookname);
        contentValues.put("AUTHOR",author);
        contentValues.put("PUBLISHDATE",publishdate);
        contentValues.put("COLUMN_IMAGE_PATH", String.valueOf(imagePath));
        contentValues.put("ADDEDUSER",useradded);
        Log.e("db", "bookdataIn: USERADDED"+useradded );
        long ins;
        try {

            ins = dbs.insert(tablename1, null, contentValues);
            Toast.makeText(context, "BOOK ADDED", Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            Toast.makeText(context, "DUPLICATE ID", Toast.LENGTH_SHORT).show();
            ins=-1;
        }
        return ins!=-1;
    }

public  Cursor viewdata()
{
    String q;
    SQLiteDatabase d=this.getReadableDatabase();
    q = "select BOOKID from " + tablename1;
    return d.rawQuery(q,null);
}

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tablename2 + " WHERE EMAIL=? AND PASSWORD=?", new String[]{email, password});
        return cursor.getCount() > 0;
    }
//    }
//    public ArrayList<Book> getAllBooks() {
//        ArrayList<Book> books;
//        books = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
////        String[] columns = {"title", "author", "other_column"}; // Specify the columns you want to retrieve
//        Cursor cursor = db.rawQuery("SELECT * FROM " + tablename1,null);
//
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                do {
//                    int idIndex = cursor.getColumnIndex("BOOKID");
//                    int nameIndex = cursor.getColumnIndex("BOOKNAME");
//                    int authorIndex = cursor.getColumnIndex("AUTHOR");
//                    int dateIndex = cursor.getColumnIndex("PUBLISHDATE");
//                    int imagePath = cursor.getColumnIndex(COLUMN_IMAGE_PATH);
//                    int addeduser=cursor.getColumnIndex("ADDEDUSER");
//
//
//                    if (idIndex != -1 && nameIndex != -1 && authorIndex != -1 && dateIndex != -1 && addeduser!=-1) {
//                        String bookId = cursor.getString(idIndex);
//                        String bookName = cursor.getString(nameIndex);
//                        String author = cursor.getString(authorIndex);
//                        String publishDate = cursor.getString(dateIndex);
//                        String imagepath=cursor.getString(imagePath);
//                        String addedu=cursor.getString(addeduser);
//
//                        // Create a new Book object with the retrieved data
//                        Book book = new Book(bookId, bookName, author, publishDate,imagepath,addedu);
//
//                        // Add the book to the ArrayList
//                        books.add(book);
//                    } else {
//                        // Handle missing columns
//                        // Log an error or display a message
//                    }
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//        }
//        db.close();
//
//        return books;
//    }
    public ArrayList<Book> getAllBooksWithImagePath() {
        ArrayList<Book> books = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tablename1, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("BOOKID");
                int nameIndex = cursor.getColumnIndex("BOOKNAME");
                int authorIndex = cursor.getColumnIndex("AUTHOR");
                int dateIndex = cursor.getColumnIndex("PUBLISHDATE");
                int imagePath=cursor.getColumnIndex("COLUMN_IMAGE_PATH");
                int addedindex=cursor.getColumnIndex("ADDEDUSER");
                if (idIndex != -1 && nameIndex != -1 && authorIndex != -1 && dateIndex != -1 && imagePath!=-1 && addedindex!=-1) {
                    // Retrieve book details including the image path from the cursor
                    String id = cursor.getString(idIndex);
                    String bookName = cursor.getString(nameIndex);
                    String author = cursor.getString(authorIndex);
                    String publishDate = cursor.getString(dateIndex);
                    String image = cursor.getString(imagePath);
                    String addeduser=cursor.getString(addedindex);


                    Uri imageuri=null;
                    if(image!=null && !image.isEmpty()){
                        imageuri=Uri.parse(image);
                    }
                    // Create a Book object with the retrieved details
                    Book book = new Book(id, bookName, author, publishDate,imageuri,addeduser);
//                    book.setImagePath(imagePath); // Set the image path in the Book object

                    // Add the book to the ArrayList
                    books.add(book);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return books;
    }
    public boolean Updatebook(String bookId,String bookName,String bookauthor,String pubdate)
    {
        Log.d("update","rows affect");

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("BOOKID",bookId);
        values.put("BOOKNAME",bookName);
        values.put("AUTHOR",bookauthor);
        values.put("PUBLISHDATE",pubdate);

        String select="BOOKID = ?";
        String[] selectArg={ bookId};

        int rowaffected= db.update(tablename1,values,select,selectArg);
        db.close();
        Log.d("update","rows affect"+ rowaffected);
        return rowaffected>0;

    }

    public ArrayList<User> getUser()
    {

        ArrayList<User> user= new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
//        Cursor cursor= db.query(tablename2,new String[]{NAME,EMAIL);
        Cursor cursor = db.rawQuery("SELECT * FROM " + tablename2, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {

                int nameIndex = cursor.getColumnIndex("NAME");
                int uNameIndex = cursor.getColumnIndex("USERNAME");
                int emailIndex = cursor.getColumnIndex("EMAIL");
                int passwordIndex=cursor.getColumnIndex("PASSWORD");
                int confirmpIndex=cursor.getColumnIndex("CONFIRMPASS");
                int mobileIndex= cursor.getColumnIndex("MOBILENO");
                if (uNameIndex != -1 && nameIndex != -1 && nameIndex != -1 && confirmpIndex != -1 && passwordIndex!=-1 && mobileIndex!=-1 && emailIndex!=-1) {
                    // Retrieve book details including the image path from the cursor
                    String uname = cursor.getString(uNameIndex);
                    String name = cursor.getString(nameIndex);
                    String email = cursor.getString(emailIndex);
                    String pass = cursor.getString(passwordIndex);
                    String confirmp = cursor.getString(confirmpIndex);
                    String mobile=cursor.getString(mobileIndex);

                    // Create a Book object with the retrieved details
                    User user2 = new User(name, uname, email, pass,confirmp,mobile);
//                    book.setImagePath(imagePath); // Set the image path in the Book object

                    // Add the book to the ArrayList
                    user.add(user2);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return user;
    }
    }

