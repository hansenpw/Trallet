package com.microlabs.trallet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.microlabs.trallet.EditBookActivity;
import com.microlabs.trallet.ExpenseActivity;
import com.microlabs.trallet.MainActivity;
import com.microlabs.trallet.R;
import com.microlabs.trallet.model.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Book RecyclerView Adapter
 */
public class BookRVAdapter extends RecyclerView.Adapter<BookRVAdapter.ViewHolder> {

    //Langkah 1 extends RecyclerView.Adapter< (class name) .ViewHolder>
    //Langkah 2 buat constructor dan list of object
    //Langkah 3 Pada public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) => buat View view = View.inflate(context, R.layout. ( item in layout ), null);
    //Langkah 4 Pada public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) => return new ViewHolder(view);
    //Langkah 5 ButterKnife injection & Binding pada kelas view holder => ButterKnife.bind(this, itemView);
    //Langkah 6 public void updateItem pada kelas view holder
    //Langkah 7 onBindViewHolder(ViewHolder holder, int position) buat item & update dengan function di view holder
    //Langkah 8 Tampilkan data ke dalam view => contoh data text : lblTitle.setText(item.getTitle());
    //Langkah 9 Buat fungsi update list item di adapter


    List<Book> bookList = new ArrayList<>();
    Context context;

    public BookRVAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_books, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book item = bookList.get(position);
        holder.updateItem(item);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void updateList(List<Book> bookList)
    {
        this.bookList.clear();
        this.bookList.addAll(bookList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblDescription)
        TextView lblDescription;
        @BindView(R.id.lblTitle)
        TextView lblTitle;
        @BindView(R.id.lblPrice)
        TextView lblPrice;
        @BindView(R.id.btnEditCard)
        ImageView btnEdit;
        int id;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void updateItem(Book item)
        {
            id = item.getId();
            lblDescription.setText(item.getDesc());
            lblTitle.setText(item.getTitle());
            lblPrice.setText(String.valueOf(item.getTotal()));
        }

        @OnClick({R.id.btnDetails, R.id.btnDelete, R.id.cvBooks, R.id.btnEditCard})
        public void onClick(View view) {
            Intent i;
            switch (view.getId()) {
                case R.id.btnDetails:
                    i = new Intent(context, ExpenseActivity.class);
                    i.putExtra("lblTitle", lblTitle.getText().toString());
                    i.putExtra("bookId",id);
                    context.startActivity(i);
                    break;
                case R.id.btnDelete:
                    ((MainActivity) context).validateDeleteBook(id, lblTitle.getText().toString());
                    break;
                case R.id.cvBooks:
                    i = new Intent(context, ExpenseActivity.class);
                    i.putExtra("lblTitle", lblTitle.getText().toString());
                    i.putExtra("bookId",id);
                    context.startActivity(i);
                    break;
                case R.id.btnEditCard:
                    i = new Intent(context.getApplicationContext(), EditBookActivity.class);
                    i.putExtra("id", id);
                    i.putExtra("lblTitle", lblTitle.getText().toString());
                    i.putExtra("lblDescription", lblDescription.getText().toString());
                    context.startActivity(i);
                    break;
            }
        }
    }
}



















