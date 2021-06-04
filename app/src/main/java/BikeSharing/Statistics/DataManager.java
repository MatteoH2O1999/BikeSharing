package BikeSharing.Statistics;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.GregorianCalendar;

import BikeSharing.Subscription.DAO.RentDao;
import BikeSharing.Subscription.DAO.RentDataTransfer;
import BikeSharing.Subscription.DAO.SubscriptionDao;
import BikeSharing.Subscription.DAO.SubscriptionDataTransfer;

/**
 * Allows to save data from the persistent layer
 */

public class DataManager {

    /**
     * Writes the rent data to the specified path
     * @param path the directory to save the data in
     */

    public void writeRentData(String path) {
        path.replace("\\", "\\\\");
        if (this.isValidPath(path)) {
            GregorianCalendar now = new GregorianCalendar();
            Date date = new Date(now.getTimeInMillis());
            String filename = "Rent data " + date.toString() + " " + now.get(GregorianCalendar.HOUR_OF_DAY) + "-" + now.get(GregorianCalendar.MINUTE) + "-" + now.get(GregorianCalendar.SECOND) + ".csv";
            String completePath = path + "\\" + filename;
            FileWriter writer;
            try {
                writer = new FileWriter(completePath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            try {
                writer.write("rentId,subCode,bikeType,startTime,endTime\n");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    throw new RuntimeException();
                }
                throw new RuntimeException();
            }
            Collection<RentDataTransfer> data = RentDao.getAllRents();
            for (RentDataTransfer rent : data) {
                String toWrite = Long.toString(rent.id) + "," + rent.subCode + "," + Integer.toString(rent.bike.encode()) + ",";
                toWrite = toWrite + new Timestamp(rent.startRent.getTimeInMillis()).toString() + ",";
                if (rent.endRent != null) {
                    toWrite = toWrite + new Timestamp(rent.endRent.getTimeInMillis()).toString();
                }
                toWrite = toWrite + "\n";
                try {
                    writer.write(toWrite);
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        writer.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        throw new RuntimeException();
                    }
                    throw new RuntimeException();
                }
            }
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    /**
     * Writes the subscription data to the specified path
     * @param path the directory to save the data in
     */

    public void writeSubscriptionData(String path) {
        path.replace("\\", "\\\\");
        if (this.isValidPath(path)) {
            GregorianCalendar now = new GregorianCalendar();
            Date date = new Date(now.getTimeInMillis());
            String filename = "Subscription data " + date.toString() + " " + now.get(GregorianCalendar.HOUR_OF_DAY) + "-" + now.get(GregorianCalendar.MINUTE) + "-" + now.get(GregorianCalendar.SECOND) + ".csv";
            String completePath = path + "\\" + filename;
            FileWriter writer;
            try {
                writer = new FileWriter(completePath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            try {
                writer.write("subCode,password,payrate,type,strikes,expirationDate\n");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    throw new RuntimeException();
                }
                throw new RuntimeException();
            }
            Collection<SubscriptionDataTransfer> data = SubscriptionDao.getAllSubscriptions();
            for (SubscriptionDataTransfer subscription : data) {
                String toWrite = subscription.subCode + "," + subscription.password + "," + Integer.toString(subscription.payrate.getEncoding()) + "," + Integer.toString(subscription.type) + "," + Integer.toString(subscription.strikes) + ",";
                if (subscription.expirationDate != null) {
                    toWrite = toWrite + new Timestamp(subscription.expirationDate.getTimeInMillis()).toString();   
                }
                toWrite = toWrite + "\n";
                try {
                    writer.write(toWrite);
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        writer.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        throw new RuntimeException();
                    }
                    throw new RuntimeException();
                }
            }
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    /**
     * Checks if a string is a valid path
     * @param path the string to check
     * @return true if the string is a valid path
     */

    private boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException e) {
            return false;
        }
        return true;
    }
    
}
