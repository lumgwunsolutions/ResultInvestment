package group.lsg.resultinvestmentapp.Class;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyInvestments {
    private double totalRevenue;
    private int investmentTime;
    private List<InvestmentPackage> investmentPath;

    public MyInvestments(List<InvestmentPackage> investmentPath) {
        this.investmentPath = investmentPath;
        buildInfo();
    }

    public MyInvestments(InvestmentPackage iP) {
        investmentPath = new ArrayList<>();
        investmentPath.add(iP);
        buildInfo();
    }

    private void buildInfo() {
        numberOfMonths = 0;
        referal = 0;

        if (!investmentPath.isEmpty()) {


            //long diff = d2.getTime() - d1.getTime();
            //long diffSeconds = diff / 1000 % 60;
            //long diffMinutes = diff / (60 * 1000) % 60;
            //long diffHours = diff / (60 * 60 * 1000) % 24;
            //long totalDiffDays = diff / (24 * 60 * 60 * 1000);
            //long diffDays = totalDiffDays % 7;


            Date startTime = investmentPath.get(0).getDateOfActivation();
            Date endTime = investmentPath.get(investmentPath.size() - 1).getDateOfCompletion();


            investmentTime += endTime.getTime() - startTime.getTime();
            // build the price
            for (InvestmentPackage iP : investmentPath) {
                totalRevenue += (iP.getAmountInvested() + getInterestRate() *
                        getAmountInvested()) * investmentTime;
            }
        }
        public double getInvestmentTimeMinutes () {
            return investmentTime / (60 * 1000);
        }

        public double getInvestmentTimeHours () {
            return getInvestmentTimeMinutes() / 60.0;
        }
        public double getInvestmentTimeDays () {
            return getInvestmentTimeMinutes() / (24 * 60 * 60 * 1000);
        }

        public double getTotalRevenue () {
            return totalRevenue;
        }

        public List<String> getInvestmentPathString () {
            List<String> result = new ArrayList<>();
            for (InvestmentPackage package :investmentPath){
                result.add(package.getFlightNumber());
            }
            return result;
        }
        public List<InvestmentPackage> getInvestmentPath () {
            return this.investmentPath;
        }
        @Override
        public String toString () {
            String result = "";
            String[] investmentInfo;
            String investmentString;

            // format each flight, the last index containing price is ignored
            for (InvestmentPackage package :investmentPath){
                investmentInfo = package.toString().split(";");
                investmentString = String.format("%s;%s;%s;%s;%s;%s",
                        investmentInfo) + ";" + investmentInfo[7];

                result += investmentString + "\n";
            }

            result += String.format("%.2f\n%.2f", getTotalRevenue(),
                    getInvestmentTimeDays());

            return result;
        }
    }
}
