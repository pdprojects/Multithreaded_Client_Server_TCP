import java.net.Socket;


public class Client2
{
    public static void main(String[] args)
    {
        Socket client = null;
        try
        {
            client = new Socket("127.0.0.3",5000);
            System.out.println("Client 2 is connected to Server");
        }
        catch (Exception ex)
        {
            System.err.println(ex);
        }

        try
        {
            Thread r = new ReadingThread(client);
            Thread w = new WritingThread(client);
            w.start();
            r.start();
        }
        catch (Exception ex)
        {
            System.err.println(ex);
        }
    }
}
