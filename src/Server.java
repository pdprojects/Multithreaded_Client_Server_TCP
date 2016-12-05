import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static android.R.id.content;
import static android.R.id.message;


public class Server
{
    public static void main(String[] args)
    {
        ServerSocket server = null;
        try
        {
            server = new ServerSocket(5000, 10);
            System.out.println("Server is running on port 5000");
        } catch (Exception ex)
        {
            System.err.println(ex);
        }
        while (true)
        {
            try
            {
                System.out.println("Waiting for Client to Connect");
                Socket client = server.accept();
                Thread r = new ReadingThread(client);
                Thread w = new WritingThread(client);
                w.start();
                r.start();

            } catch (Exception ex)
            {
                System.err.println(ex);
            }
        }
    }
}
class ReadingThread extends Thread
{
    private Socket connection;


    public ReadingThread(Socket connection)
    {
        this.connection = connection;
    }

    public void run()
    {

        try
        {
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            String message;
            while (true)
            {
                message = (String) in.readObject();
                if(message.equals("end"))
                {
                    System.out.println("Server>>>" + "Good Bye");
                    connection.close();

                }

                System.out.println("Server>>>" + message);


            }
        } catch (Exception ex)
        {
            System.err.println(ex);
        }
    }
}

    class WritingThread extends Thread
    {
        private Socket connection;
        public WritingThread (Socket connection)
        {
            this.connection = connection;
        }

        public void run()
        {
            try
            {
                ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                String message;
                Scanner s = new Scanner(System.in);
                while(true)
                {
                    System.out.println("Client>>>");
                    message = s.nextLine();
                    out.writeObject(message);
                }
            }
            catch (Exception ex)
            {

                System.err.println(ex);
            }

        }
    }
