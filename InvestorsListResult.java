package group.lsg.resultinvestmentapp.Class;

import java.util.ArrayList;
import java.util.List;

public class InvestorsListResult {
    boolean isMoreDataAvailable;
    List<Investors> investors = new ArrayList<>();
    long lastItemCreatedDate;

    public boolean isMoreDataAvailable() {
        return isMoreDataAvailable;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public List<Investors> getInvestors() {
        return investors;
    }

    public void setInvestors(List<Investors> investors) {
        this.investors = investors;
    }
    public long getLastItemCreatedDate() {
        return lastItemCreatedDate;
    }

    public void setLastItemCreatedDate(long lastItemCreatedDate) {
        this.lastItemCreatedDate = lastItemCreatedDate;
    }
}
