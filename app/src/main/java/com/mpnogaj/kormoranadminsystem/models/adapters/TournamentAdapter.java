package com.mpnogaj.kormoranadminsystem.models.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mpnogaj.kormoranadminsystem.R;
import com.mpnogaj.kormoranadminsystem.helpers.Essentials;
import com.mpnogaj.kormoranadminsystem.models.Match;
import com.mpnogaj.kormoranadminsystem.models.Tournament;

import java.util.List;

public class TournamentAdapter extends ArrayAdapter<Tournament> {

    private final Context _context;
    private final List<Tournament> _tournaments;

    public TournamentAdapter(@NonNull Context context, int resource, @NonNull List<Tournament> objects) {
        super(context, resource, objects);
        _context = context;
        _tournaments = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null) {
            itemView = LayoutInflater.from(_context).inflate(R.layout.list_item_match, parent, false);
        }
        Tournament tournament = _tournaments.get(position);
        ((TextView)itemView.findViewById(R.id.listItemMatchDetailsText)).setText(tournament.toString());
        String matchState = tournament.getState();
        TextView stateText = itemView.findViewById(R.id.listItemMatchStateText);
        stateText.setText(Essentials.getStateReadableText(matchState));
        Pair<Integer, Integer> colors = Essentials.getColorsByState(matchState);
        stateText.setBackgroundColor(_context.getResources().getColor(colors.first));
        stateText.setTextColor(_context.getResources().getColor(colors.second));
        return itemView;
    }
}
