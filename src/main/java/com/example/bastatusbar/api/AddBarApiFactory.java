package com.example.bastatusbar.api;

import com.example.bastatusbar.bar_stytle.StatusBarRequest;

/**
 * Created by BA on 2017/10/23 0023.
 *
 * @Function :
 */

public class AddBarApiFactory {

    public static AddStatusBarApi forKik(StatusBarRequest request){
        return new AddBarAftreKiK(request);
    }

    public static AddStatusBarApi forLollipop(StatusBarRequest request){
        return new AddBarAfterLollipop(request);
    }
}
