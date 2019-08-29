package ru.geron.ftploader.adapters;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ru.geron.ftploader.R;
import ru.geron.ftploader.data.SendBitmap;


public class ListViewAdapter extends ListAdapter<SendBitmap, ListViewAdapter.ViewHolder> {

//    @Override
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        if (fromPosition < toPosition) {
//            for (int i = fromPosition; i < toPosition; i++) {
//                Collections.swap(StatePresenter.getFiles(), i, i + 1);
//                Collections.swap(StatePresenter.getImages(), i, i + 1);
//            }
//        } else {
//            for (int i = fromPosition; i > toPosition; i--) {
//                Collections.swap(StatePresenter.getFiles(), i, i - 1);
//                Collections.swap(StatePresenter.getImages(), i, i - 1);
//            }
//        }
//        notifyItemMoved(fromPosition, toPosition);
//        return true;
//    }

    //    @Override
//    public void onItemDismiss(int position) {
//        StatePresenter.getFiles().remove(position);
//        StatePresenter.getImages().remove(position);
//        notifyItemRemoved(position);
//    }
    public ListViewAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<SendBitmap> DIFF_CALLBACK = new DiffUtil.ItemCallback<SendBitmap>() {
        @Override
        public boolean areItemsTheSame(@NonNull SendBitmap oldItem, @NonNull SendBitmap newItem) {
            return oldItem.fullBitmap.sameAs(newItem.fullBitmap);
        }

        @Override
        public boolean areContentsTheSame(@NonNull SendBitmap oldItem, @NonNull SendBitmap newItem) {
            return oldItem.compressedBitmap.sameAs(newItem.compressedBitmap) &&
                    oldItem.fullBitmap.sameAs(newItem.fullBitmap);
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagePreview;


        public ViewHolder(View v) {
            super(v);
            imagePreview = v.findViewById(R.id.image_preview);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        SendBitmap currentBitmap = getItem(i);
        holder.imagePreview.setImageBitmap(currentBitmap.compressedBitmap);
    }

    public SendBitmap getBitmapAt(int position) {
        return getItem(position);
    }

}