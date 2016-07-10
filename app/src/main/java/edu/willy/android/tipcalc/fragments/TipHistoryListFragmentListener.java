package edu.willy.android.tipcalc.fragments;

import edu.willy.android.tipcalc.models.TipRecord;

/**
 * Created by Z3r0x on 09/07/2016.
 */
public interface TipHistoryListFragmentListener {
    void addToList(TipRecord record);
    void clearList();
}
