package ch.heigvd.res.labs.roulette.net.client;

import ch.heigvd.res.labs.roulette.data.EmptyStoreException;
import ch.heigvd.res.labs.roulette.data.JsonObjectMapper;
import ch.heigvd.res.labs.roulette.net.protocol.RouletteV1Protocol;
import ch.heigvd.res.labs.roulette.data.Student;
import ch.heigvd.res.labs.roulette.net.protocol.InfoCommandResponse;
import ch.heigvd.res.labs.roulette.net.protocol.RandomCommandResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the client side of the protocol specification (version 1).
 * 
 * @author Olivier Liechti
 */
public class RouletteV1ClientImpl implements IRouletteV1Client {

   private Socket socket         = null;
   private BufferedReader reader = null;
   private PrintWriter writer    = null; 
   
  private static final Logger LOG = Logger.getLogger(RouletteV1ClientImpl.class.getName());

  @Override
  public void connect(String server, int port) throws IOException {
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      socket = new Socket(server, port);
  }

  @Override
  public void disconnect() throws IOException {
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      
    //send the BYE command to close the connection
       writer.println(RouletteV1Protocol.CMD_BYE);
       writer.flush();       
       
       //close the objects that communicate with the server
        writer.close();
        reader.close();
        socket.close();
  }

  @Override
  public boolean isConnected() {
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  return socket != null && socket.isConnected();
  }

  @Override
  public void loadStudent(String fullname) throws IOException {
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         writer.println(RouletteV1Protocol.CMD_LOAD);
         reader.readLine();
         writer.println(fullname);
         writer.println(RouletteV1Protocol.CMD_LOAD_ENDOFDATA_MARKER);
         writer.flush();
         //reading of the summary done by the server
         reader.readLine();
    
    
     ;
  }

  @Override
  public void loadStudents(List<Student> students) throws IOException {
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     writer.println(RouletteV1Protocol.CMD_LOAD);
     reader.readLine();
    for (Student student : students) {
      writer.println(student.getFullname());
    }
   writer.println(RouletteV1Protocol.CMD_LOAD_ENDOFDATA_MARKER);
    reader.readLine();
  }

  @Override
  public Student pickRandomStudent() throws EmptyStoreException, IOException {
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   writer.println(RouletteV1Protocol.CMD_RANDOM);
    RandomCommandResponse answer = JsonObjectMapper.parseJson(reader.readLine(),
                                                      RandomCommandResponse.class);

    if (!answer.getError().isEmpty()) {
      LOG.log(Level.SEVERE, "not a student");
      throw new EmptyStoreException();
      }
    return Student.fromJson(answer.getFullname());
  }

  @Override
  public int getNumberOfStudents() throws IOException {
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  writer.println(RouletteV1Protocol.CMD_INFO);
  writer.flush();
  //number of students having exploited the information from the server
  return JsonObjectMapper.parseJson(reader.readLine(),
         InfoCommandResponse.class).getNumberOfStudents();
  }

  @Override
  public String getProtocolVersion() throws IOException {
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    writer.println(RouletteV1Protocol.CMD_INFO);
    InfoCommandResponse response = JsonObjectMapper.parseJson(reader.readLine(), InfoCommandResponse.class);
     return response.getProtocolVersion();
  }



}
