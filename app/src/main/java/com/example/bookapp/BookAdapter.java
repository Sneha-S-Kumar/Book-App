package com.example.bookapp;

import static android.app.PendingIntent.getActivity;

import static com.example.bookapp.databinding.ActivityLoginBinding.inflate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    public ArrayList<Book> books;

    //    private ArrayList<Integer>imageList;
    private String fType;
    OnDeleteListner onDeleteListner;

    OnEditListener onEditListener;
    Context context;
//    Helper helper;

    //        public void changeActivity() {
//
//        }
    public interface OnEditListener {
        void oneditclik(Book position);
    }

    public interface OnDeleteListner {
        void ondeleteClick(int position);
    }


    public BookAdapter(Context context, ArrayList<Book> books, OnDeleteListner onDeleteListner,OnEditListener onEditListener) {

        this.books = books;
        this.onDeleteListner = onDeleteListner;
        this.onEditListener=onEditListener;
    }

    @NonNull
    @Override

    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        return new BookViewHolder(view);

    }

    @Override

    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

//        holder.textViewCountryName.setText(textLIst.get(position));
//        String uname=textLIst.get(position);
//       holder.imageView.setImageResource(imageList.get(position));
        Book book = books.get(position);
//        String imagePathTemp= String.valueOf(book.getImagepath());
        holder.bookName.setText(book.getName());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookId.setText(book.getId());
        holder.bookDate.setText(book.getDate());
        holder.loginuser.setText(book.getUser());
        holder.imageView.setImageURI(book.getImagepath());
        holder.bookDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDeleteListner.ondeleteClick(position);
            }
        });
        holder.bookEditBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
             DBHelper dbHelper=new DBHelper(context);
             Book selectBook = books.get(position);
//      String bookid=books.get(position).getId();
//                Intent in =new Intent(context,ViewBooks.class);
//                in.putExtra("bookid",bookid);
//                context.startActivity(in);
                onEditListener.oneditclik(selectBook);



            }


        });

//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//   Toast.makeText(context, "Toasted", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });


    }

    @Override

    public int getItemCount() {

        return books.size();

    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        private TextView bookName, bookAuthor, bookId, bookDate,loginuser;

        private Button bookDeleteButton, bookEditBUtton;
      private ImageView imageView;

        public BookViewHolder(@NonNull View itemView) {

            super(itemView);

            bookName = itemView.findViewById(R.id.textView1);

            bookAuthor = itemView.findViewById(R.id.textview3);

            bookId = itemView.findViewById(R.id.textView2);

            bookDate = itemView.findViewById(R.id.textview4);
            bookEditBUtton = itemView.findViewById(R.id.editButton);
            bookDeleteButton = itemView.findViewById(R.id.deleteButton);
            loginuser=itemView.findViewById(R.id.userlogged);

            imageView=itemView.findViewById(R.id.imageView);

        }

    }

    public void UpdateDataset2(ArrayList<Book> updatedBooks) {
        this.books = updatedBooks;
        notifyDataSetChanged();
    }


    public void UpdateDataset(ArrayList<Book> updatedBooks) {
        this.books = updatedBooks;
        notifyDataSetChanged();
    }


}

