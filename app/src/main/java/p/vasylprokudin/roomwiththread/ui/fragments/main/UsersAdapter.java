package p.vasylprokudin.roomwiththread.ui.fragments.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import p.vasylprokudin.roomwiththread.R;
import p.vasylprokudin.roomwiththread.model.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> userList;
    private OnItemClicked onClick;

    public UsersAdapter(List<User> userList, OnItemClicked onClick) {
        this.userList = userList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_item_list, viewGroup, false);
        return new UsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tv.setText(userList.get(i).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                onClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView tv;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_single_name);
            layout = (LinearLayout) itemView.findViewById(R.id.linear_layout_adapter_single_item);
            layout.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action:");
            menu.add(Menu.NONE, 0, getAdapterPosition(), "UPDATE");
            menu.add(Menu.NONE, 1, getAdapterPosition(), "DELETE");
        }
    }

    public interface OnItemClicked{
        void onItemClick(int position);
    }
}
