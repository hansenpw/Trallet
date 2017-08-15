package com.microlabs.trallet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    List<Book> bookList = new ArrayList<>();
    MainActivity.BookItemListener itemListener;

    public BookRVAdapter(MainActivity.BookItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_books, null);
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

    public void updateList(List<Book> bookList) {
        this.bookList.clear();
        this.bookList.addAll(bookList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblDescription)
        TextView lblDescription;
        @BindView(R.id.lblTitle)
        TextView lblTitle;
        @BindView(R.id.lblPrice)
        TextView lblPrice;
        @BindView(R.id.btnEditCard)
        ImageView btnEdit;
        int id;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void updateItem(Book item) {
            id = item.getId();
            lblDescription.setText(item.getDesc());
            lblTitle.setText(item.getTitle());
            lblPrice.setText(String.valueOf(item.getTotal()));
        }

        @OnClick({R.id.btnDetails, R.id.btnDelete, R.id.cvBooks, R.id.btnEditCard})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnDetails:
                    itemListener.onDetailClick(id, lblTitle.getText().toString());
                    break;
                case R.id.btnDelete:
                    itemListener.onDeleteClick(id, lblTitle.getText().toString());
                    break;
                case R.id.cvBooks:
                    itemListener.onDetailClick(id, lblTitle.getText().toString());
                    break;
                case R.id.btnEditCard:
                    itemListener.onEditClick(id, lblTitle.getText().toString(), lblDescription.getText().toString());
                    break;
            }
        }
    }
}
