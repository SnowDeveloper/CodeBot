package com.example.snowwhite.codebot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MessageHolder> {
    private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1;

    private List<ChatMessage> mMessages;
    private Context mContext;

    public ChatMessageAdapter(Context context, List<ChatMessage> data) {
        mContext = context;
        mMessages = data;
    }

    @Override
    public int getItemCount() {
        return mMessages == null ? 0 : mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage item = mMessages.get(position);

        if (item.isMine()) return MY_MESSAGE;
        else return OTHER_MESSAGE;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MY_MESSAGE) {
            return new MessageHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mine_message, parent, false));
        } else {
            return new MessageHolder(LayoutInflater.from(mContext).inflate(R.layout.item_other_message, parent, false));
        }
    }

    public void add(ChatMessage message) {
        mMessages.add(message);
        notifyItemInserted(mMessages.size() - 1);
    }

    @Override
    public void onBindViewHolder(final MessageHolder holder, int position) {
        ChatMessage chatMessage = mMessages.get(position);
        holder.ivImage.setVisibility(View.GONE);
        holder.tvMessage.setVisibility(View.GONE);
        if (chatMessage.isImage()) {
            holder.ivImage.setVisibility(View.VISIBLE);
            holder.tvMessage.setVisibility(View.GONE);

            holder.ivImage.setImageResource(R.drawable.code_bot);
        } else if (chatMessage.isMultipleChoice()) {
            for (String option : chatMessage.getOptions()) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                Button button = (Button) inflater.inflate(R.layout.option_button, holder.options, false);

                button.setText(option);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((PractiseBot) mContext).onItemClicked(view, holder.options);
                    }
                });
                holder.options.addView(button);
            }
            if (!chatMessage.getContent().isEmpty()) {
                holder.tvMessage.setVisibility(View.VISIBLE);
                holder.tvMessage.setText(chatMessage.getContent());
            }

        } else {
            holder.ivImage.setVisibility(View.GONE);
            if (!chatMessage.getContent().isEmpty()) {
                holder.tvMessage.setVisibility(View.VISIBLE);
                holder.tvMessage.setText(chatMessage.getContent());
            }
        }

        String date = new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date());
        holder.tvTime.setText(date);
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime;
        ImageView ivImage;
        LinearLayout options;

        MessageHolder(final View itemView) {
            super(itemView);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_message);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
            options = (LinearLayout) itemView.findViewById(R.id.option_container);
        }
    }
}