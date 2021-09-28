package com.mpnogaj.kormoranadminsystem.models;

public class Tournament {

    private final String _name, _game, _state, _repName, _tournamentType;
    private final int _id;

    public Tournament(String name, String game, String state, int id, String repName, String tournamentType){
            _name = name;
            _game = game;
            _state = state;
            _id = id;
            _repName = repName;
            _tournamentType = tournamentType;
    }

    public String getName() {
        return _name;
    }

    public String getGame() {
        return _game;
    }

    public String getState() {
        return _state;
    }

    public String getRepName() {
        return _repName;
    }

    public String getTournamentType() {
        return _tournamentType;
    }

    public int getId() {
        return _id;
    }
}
