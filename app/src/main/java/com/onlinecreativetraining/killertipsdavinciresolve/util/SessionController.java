package com.onlinecreativetraining.killertipsdavinciresolve.util;

import android.util.Log;


import com.onlinecreativetraining.killertipsdavinciresolve.App;
import com.snappydb.DB;
import com.snappydb.SnappydbException;

/**
 * Created by dev on 04/09/16.
 */
public class SessionController {
    public static final String TAG = SessionController.class.getSimpleName();
    private static final SessionController instance = new SessionController();

    public static SessionController getInstance() {
        return instance;
    }

    private static final String KEY_TUTORIAL = "tutorial";
    boolean isTutorial;


    private SessionController() {
    }

    public void setTutorialStatus(boolean status) {

        DB db = App.getInstance().getDb();
        try {
            db.put(KEY_TUTORIAL, status);
        } catch (SnappydbException e) {
            Log.e("error", "Unable to login");
        }
    }


    public boolean isTutorialStatus() {
        DB db = App.getInstance().getDb();
        try {
            return db.exists(KEY_TUTORIAL);
        } catch (SnappydbException e) {
            Log.e("error", "Failed on find data");
            return false;
        }
    }

    public boolean getTutorialStatus() {
        boolean status = false;
            if (isTutorialStatus()) {
                DB db = App.getInstance().getDb();
                try {
                    status = db.getObject(KEY_TUTORIAL, Boolean.class);
                } catch (SnappydbException e) {
                    Log.e("error", "Failed on getLoggedInUser");
                }
            }
        return status;
    }
}
