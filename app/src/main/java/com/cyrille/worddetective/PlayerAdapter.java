package com.cyrille.worddetective;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class PlayerAdapter extends ArrayAdapter<Player> {
    private Context context;
    private List<Player> players;

    public PlayerAdapter(@NonNull Context context, int resource, @NonNull List<Player> players) {
        super(context, resource, players);
        this.context = context;
        this.players = players;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listItemView = inflater.inflate(R.layout.list_item_player, parent, false);

        TextView playerRankTextView = listItemView.findViewById(R.id.player_rank);
        TextView playerNameTextView = listItemView.findViewById(R.id.player_name);
        TextView playerScoreTextView = listItemView.findViewById(R.id.player_score);

        Player player = players.get(position);
        playerRankTextView.setText(String.valueOf(position + 1));
        playerNameTextView.setText(player.getName());
        playerScoreTextView.setText(String.valueOf(player.getScore()));

        return listItemView;
    }
}
