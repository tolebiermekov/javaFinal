package kz.edu.astanait.ajp2_final_project.loggers;


import kz.edu.astanait.ajp2_final_project.models.User;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginLogger implements Runnable {
    private final File file;
    private final User user;

    public LoginLogger(User user) {
        file = new File("src/main/java/kz/edu/astanait/ajp2_final_project/loggers/files/login.log");
        this.user = user;
    }

    @Override
    public void run() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        String logMsg = '[' + dateFormat.format(currentDate)
                + " " + user.getId()
                + " " + user.getUsername()
                + " " + user.getRole().getName()
                + "]";

        LogWriter.writeLog(file, logMsg);
    }
}
