package si.feri.projekt.studentskaprehrana.activity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.Perferences;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.ParcelFileDescriptor;

public class RecordActivity extends Activity {
	public void onCreate() throws UnknownHostException, IOException {
		String hostname = MyPerferences.getInstance().getServer();
		int port = 7775;
		
		Socket socket = new Socket(InetAddress.getByName(hostname), port);
        OutputStream oos = socket.getOutputStream();
        oos.write(1);
		ParcelFileDescriptor pfd = ParcelFileDescriptor.fromSocket(socket);
		MediaRecorder recorder = new MediaRecorder();
		recorder.setOutputFile(pfd.getFileDescriptor());
		recorder.prepare();
		recorder.start();
	}
}
