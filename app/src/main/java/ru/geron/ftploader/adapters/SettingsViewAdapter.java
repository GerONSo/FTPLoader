package ru.geron.ftploader.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;


import ru.geron.ftploader.R;
import ru.geron.ftploader.data.FtpEntity;


public class SettingsViewAdapter extends ListAdapter<FtpEntity, SettingsViewAdapter.FtpHolder> {

    private OnItemClickListener listener;
    private OnCheckListener checkListener;

    public SettingsViewAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<FtpEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<FtpEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull FtpEntity oldItem, @NonNull FtpEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull FtpEntity oldItem, @NonNull FtpEntity newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getIp().equals(newItem.getIp()) &&
                    oldItem.getPort().equals(newItem.getPort()) &&
                    oldItem.getConnection_type().equals(newItem.getConnection_type()) &&
                    oldItem.getDirectory().equals(newItem.getDirectory()) &&
                    oldItem.getLogin().equals(newItem.getLogin()) &&
                    oldItem.getActive() == newItem.getActive();
        }
    };


    class FtpHolder extends RecyclerView.ViewHolder {

        TextView ip_address;
        RadioButton active;

        public FtpHolder(View v) {
            super(v);
            ip_address = v.findViewById(R.id.tv_ip);
            active = v.findViewById(R.id.cb_active);
//            active.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                int position = getAdapterPosition();
//                if (checkListener != null && position != RecyclerView.NO_POSITION) {
//                    checkListener.onCheck(getItem(position), isChecked);
//                }
//            });
//            itemView.setOnClickListener(v1 -> {
//                int position = getAdapterPosition();
//                if (listener != null && position != RecyclerView.NO_POSITION) {
//                    listener.onItemClick(getItem(position));
//                }
//            });
        }
    }

    @NonNull
    @Override
    public FtpHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.server_view_card, parent, false);
        return new FtpHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FtpHolder holder, int position) {
        holder.active.setOnClickListener(buttonView -> {
            if (checkListener != null && position != RecyclerView.NO_POSITION) {
                checkListener.onCheck(getItem(position));
            }
        });
        holder.itemView.setOnClickListener(vl -> {
            if (listener != null && position != RecyclerView.NO_POSITION) {
                listener.onItemClick(getItem(position));
            }
        });
        FtpEntity currentFtp = getItem(position);
        String formatted_address = currentFtp.getName();
        holder.ip_address.setText(formatted_address);
        holder.active.setChecked((currentFtp.getId() == currentFtp.getActive()));
    }

    public FtpEntity getFtpAt(int position) {
        return getItem(position);
    }

    public interface OnItemClickListener {
        void onItemClick(FtpEntity ftp);
    }

    public interface OnCheckListener {
        void onCheck(FtpEntity ftp);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnCheckListener(OnCheckListener listener) {
        this.checkListener = listener;
    }
}