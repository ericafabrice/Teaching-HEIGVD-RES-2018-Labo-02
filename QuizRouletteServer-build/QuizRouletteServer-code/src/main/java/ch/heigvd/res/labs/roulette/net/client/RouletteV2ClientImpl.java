package ch.heigvd.res.labs.roulette.net.client;

import ch.heigvd.res.labs.roulette.data.EmptyStoreException;
import ch.heigvd.res.labs.roulette.data.JsonObjectMapper;
import ch.heigvd.res.labs.roulette.data.Student;
import ch.heigvd.res.labs.roulette.data.StudentsList;
import ch.heigvd.res.labs.roulette.net.protocol.*;
import ch.heigvd.res.labs.roulette.net.protocol.LoadCommandResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * This class implements the client side of the protocol specification (version 2).
 *
 * @author Olivier Liechti
 */
public class RouletteV2ClientImpl extends RouletteV1ClientImpl implements IRouletteV2Client {
   
     private int nbSuccessCmd = 0;
     private BufferedReader reader   = null;
     private PrintWriter   writer    = null;
     private int nbStudents = 0;
     private boolean cmdSuccess = false;

  @Override
  public void clearDataStore() throws IOException {
    
        writer.println(RouletteV2Protocol.CMD_CLEAR);
        writer.flush();
       
        if (reader.readLine().equals(RouletteV2Protocol.RESPONSE_CLEAR_DONE)) {
            nbSuccessCmd++;
        }
  }

  public List<Student> listStudents() throws IOException{
   // send command
        writer.println(RouletteV2Protocol.CMD_LIST);
        writer.flush();

        StudentsList studentsList;
        
           
            studentsList = JsonObjectMapper.parseJson(reader.readLine(), StudentsList.class);
            cmdSuccess = true;
            nbSuccessCmd++;
return studentsList.getStudents();
  }
  @Override
 public void loadStudents(List<Student> students) throws IOException {
   // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   writer.println(RouletteV2Protocol.CMD_LOAD);

        for (Student student : students) {
            writer.println(student.getFullname());
        }

      
        writer.println(RouletteV2Protocol.CMD_LOAD_ENDOFDATA_MARKER);
        writer.flush();
        

       try {
            LoadCommandResponse serverResponse = JsonObjectMapper.parseJson(reader.readLine(), LoadCommandResponse.class);
            cmdSuccess = serverResponse.getStatus().equals("success");

            if (cmdSuccess) {
                nbStudents = serverResponse.getNumberOfNewStudents(); 
                nbSuccessCmd++;                                   
            }

        } catch (IOException e) {
            cmdSuccess = false;
            e.getStackTrace();

        }

  }
 
  public boolean checkSuccessOfCommand() {
        return cmdSuccess;
}
  
  public int getNumberOfCommands() {
        return nbSuccessCmd;
}
  
  public int getNumberOfStudentAdded() {
        return nbStudents;
}
  
     @Override
    public void disconnect() throws IOException {
        super.disconnect();
        nbSuccessCmd++;
    }

    @Override
    public Student pickRandomStudent() throws EmptyStoreException, IOException {
        try {
            Student student = super.pickRandomStudent();
            nbSuccessCmd++;
            return student;
        } catch (EmptyStoreException e) {
            nbSuccessCmd++;
            throw e;
        }
    }

    @Override
    public int getNumberOfStudents() throws IOException { 
        nbSuccessCmd++;
        return super.getNumberOfStudents();
    }

    @Override
    public String getProtocolVersion() throws IOException {
        nbSuccessCmd++;
        return super.getProtocolVersion();
}
}

 