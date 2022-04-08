package com.bettingtipsking.app.Helper;

import com.bettingtipsking.app.model.FinalFixturesModel;
import com.bettingtipsking.app.model.FinalMatchesModel;
import com.bettingtipsking.app.model.MatchesModel;

public interface ItemClickListener {

    void onItemClicked(int position, FinalMatchesModel finalFixturesModel);
    void onH2HIconClick(int teamHomeId, int teamAwayId);

}
