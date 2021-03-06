import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client2
{
    public static void main(String[] args)
    {
        Socket client = null;
        try
        {
            client = new Socket("127.0.0.3", 5000);
            System.out.println("Client 2 is connected to Server");
        } catch (Exception ex)
        {
            System.err.println(ex);
        }

        try
        {
            Thread r = new ReadingThread(client);
            Thread w = new WritingThread(client);
            w.start();
            r.start();
        } catch (Exception ex)
        {
            System.err.println(ex);
        }
    }

static  class ReadingThread extends Thread
    {
        private Socket connection;

        public ReadingThread(Socket con)
        {
            connection = con;
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
                    System.out.println("Client>>>" + message);
                }

            } catch (Exception ex)
            {
                System.err.println(ex);
            }
        }
    }

static  class WritingThread extends Thread
    {
        private Socket connection;

        public WritingThread(Socket con)
        {
            connection = con;
        }

        public void run()
        {
            try
            {
                ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                String message;
                Scanner s = new Scanner(System.in);
                while (true)
                {
                    System.out.print("Client>>>");
                    message = s.nextLine();
                    out.writeObject(message);
                }
            } catch (Exception ex)
            {
                System.err.println(ex);
            }
        }
    }
}